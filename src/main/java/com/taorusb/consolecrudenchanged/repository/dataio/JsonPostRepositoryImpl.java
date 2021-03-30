package com.taorusb.consolecrudenchanged.repository.dataio;

import com.taorusb.consolecrudenchanged.model.BaseModel;
import com.taorusb.consolecrudenchanged.model.Post;
import com.taorusb.consolecrudenchanged.repository.DataIO;
import com.taorusb.consolecrudenchanged.repository.PostRepository;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

public class JsonPostRepositoryImpl implements PostRepository {

    private final static String POST_JSON_LOCATION = "src\\main\\resources\\posts.json";
    private DataIO<BaseModel> io;
    private static List<Post> postList = new LinkedList<>();
    private final static SimpleDateFormat dateFormat =
            new SimpleDateFormat("dd/MM/yyyy");
    private static long idCounter = 0;

    public void setIo(DataIO<BaseModel> io) {
        this.io = io;
    }

    public Post getById(Long id) {
        load();
        return postList
                .stream()
                .filter(post -> post.getId() == id)
                .findFirst().orElseThrow();
    }

    public void deleteById(Long id) {
        load();

        int index = postList.indexOf(new Post(id));
        if (index == -1) {
            throw new NoSuchElementException();
        }
        postList.remove(index);

        save();
    }

    public List<Post> findAll() {
        load();
        return postList;
    }

    public Post save(Post entity) {
        load();

        entity.setId(++idCounter);
        entity.setUpdated("-");
        entity.setCreated(dateFormat.format(new Date()));
        postList.add(entity);

        save();
        return entity;
    }

    public Post update(Post entity) {
        load();

        int index = postList.indexOf(entity);
        if (index == -1) {
            throw new NoSuchElementException();
        }

        Post updatableEntity = postList.get(index);
        updatableEntity.setContent(entity.getContent());
        updatableEntity.setUpdated(dateFormat.format(new Date()));

        save();
        return updatableEntity;
    }

    public void load() {
        postList = io.loadData(POST_JSON_LOCATION, Post.class);
        idCounter = JsonDataIOImpl.getIdCounter();
    }

    public void save() {
        io.saveData(POST_JSON_LOCATION, postList);
    }
}