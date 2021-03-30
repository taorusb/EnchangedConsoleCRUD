package com.taorusb.consolecrudenchanged.repository.dataio;

import com.google.gson.*;
import com.taorusb.consolecrudenchanged.model.*;
import com.taorusb.consolecrudenchanged.repository.DataIO;
import com.taorusb.consolecrudenchanged.repository.GenericRepository;
import com.taorusb.consolecrudenchanged.repository.PostRepository;
import com.taorusb.consolecrudenchanged.repository.RegionRepository;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class Mediator implements DataIO<BaseModel> {

    private DataIO<BaseModel> dataIO;

    private static HashMap<String, GenericRepository<?, ?>> map = new HashMap<>();

    public void setDataIO(DataIO<BaseModel> dataIO) {
        this.dataIO = dataIO;
    }

    public Gson getGson() {
        return new GsonBuilder()
                .serializeNulls()
                .registerTypeAdapter(Writer.class, new WriterSerializer())
                .registerTypeAdapter(Writer.class, new WriterDeserializer())
                .create();
    }

    public static void addRepository(String name, GenericRepository<?, ?> repository) {
        map.put(name, repository);
    }

    public void removeRepository(String name) {
        map.remove(name);
    }

    @Override
    public <T> List<T> loadData(String fileLocation, Class<T> clazz) {
        return dataIO.loadData(fileLocation, clazz);
    }

    @Override
    public <T extends BaseModel> void saveData(String fileLocation, List<T> data) {
        dataIO.saveData(fileLocation, data);
    }


    static class WriterSerializer implements JsonSerializer<Writer> {

        @Override
        public JsonElement serialize(Writer writer, Type type, JsonSerializationContext jsonSerializationContext) {
            JsonObject result = new JsonObject();

            result.addProperty("id", writer.getId());
            result.addProperty("firstName", writer.getFirstName());
            result.addProperty("lastName", writer.getLastName());

            JsonArray array = new JsonArray();

            result.add("posts", array);
            writer.getPosts().forEach(post -> array.add(post.getId()));

            result.addProperty("region", writer.getRegion() == null ? 0 : writer.getRegion().getId());
            result.addProperty("role", writer.getRole() == null ? null : writer.getRole().name());

            return result;
        }
    }

    static class WriterDeserializer implements JsonDeserializer<Writer> {

        @Override
        public Writer deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            List<Post> postList;
            List<Long> postId = new ArrayList<>();
            PostRepository postRepository = (PostRepository) map.get("postRepository");
            RegionRepository regionRepository = (RegionRepository) map.get("regionRepository");

            long writerId = jsonObject.get("id").getAsLong();
            String firstName = jsonObject.get("firstName").getAsString();
            String lastName = jsonObject.get("lastName").getAsString();

            Writer writer = new Writer(writerId, firstName, lastName);

            JsonArray array = jsonObject.getAsJsonArray("posts");
            if (array.size() >= 1) {
                array.forEach(x -> postId.add(x.getAsLong()));

                postList = postId.stream()
                        .map(id -> postRepository.findAll().stream()
                                .filter(post -> post.getId() == id)
                                .findFirst().orElse(new Post()))
                        .collect(Collectors.toList());
                writer.setPosts(postList);
            }

            Role role = Role.valueOf(jsonObject.get("role").getAsString());
            long regionId = jsonObject.get("region").getAsLong();
            Region region = regionRepository.findAll().stream()
                    .filter(x -> x.getId() == regionId)
                    .findFirst().orElse(new Region(0L));

            writer.setRegion(region);
            writer.setRole(role);

            return writer;
        }
    }
}