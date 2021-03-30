package com.taorusb.consolecrudenchanged.repository.dataio;

import com.google.gson.*;
import com.taorusb.consolecrudenchanged.model.BaseModel;
import com.taorusb.consolecrudenchanged.repository.DataIO;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class JsonDataIOImpl implements DataIO<BaseModel> {

    private static final String FILE_READING_ERROR = "Error reading \"%s\". Check the file and restart the program.";
    private Gson gson;
    private static long idCounter;

    public void setGson(Gson gson) {
        this.gson = gson;
    }

    public static long getIdCounter() {
        return idCounter;
    }

    @Override
    public <T> List<T> loadData(String fileLocation, Class<T> clazz) {

        try {
            if (Files.exists(Path.of(fileLocation)) && Files.size(Path.of(fileLocation)) > 4L) {

                try (Stream<String> stream =
                             Files.lines(Path.of(fileLocation));
                     FileReader fr = new FileReader(fileLocation)) {

                    idCounter = Character.getNumericValue(fr.read());

                    return stream
                            .skip(1)
                            .filter(x -> x.length() > 0)
                            .map(string -> gson.fromJson(string, clazz))
                            .collect(Collectors.toList());

                } catch (IOException e) {
                    System.err.printf(FILE_READING_ERROR, fileLocation);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        idCounter = 0;
        return new LinkedList<>();
    }

    @Override
    public <T extends BaseModel> void saveData(String fileLocation, List<T> entities) {

        try (FileWriter fw = new FileWriter(fileLocation, false)) {

            if (entities.size() >= 1) {
                long id = entities.get(entities.size() - 1).getId();
                fw.write((int) id + "\n");
            }

            for (T entity : entities) {
                fw.write(gson.toJson(entity) + "\n");
            }
        } catch (IOException e) {
            System.err.printf(FILE_READING_ERROR, fileLocation);
        }
    }
}