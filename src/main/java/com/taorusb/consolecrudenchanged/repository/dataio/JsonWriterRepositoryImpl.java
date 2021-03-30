package com.taorusb.consolecrudenchanged.repository.dataio;

import com.taorusb.consolecrudenchanged.model.BaseModel;
import com.taorusb.consolecrudenchanged.model.Role;
import com.taorusb.consolecrudenchanged.model.Writer;
import com.taorusb.consolecrudenchanged.repository.DataIO;
import com.taorusb.consolecrudenchanged.repository.Bootable;
import com.taorusb.consolecrudenchanged.repository.WriterRepository;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

public class JsonWriterRepositoryImpl implements WriterRepository {

    private final static String WRITER_JSON_LOCATION = "src\\main\\resources\\writers.json";
    private static List<Writer> writerList = new LinkedList<>();
    private static long idCounter = 0;
    private List<Bootable> bootableList = new ArrayList<>();
    private DataIO<BaseModel> io;

    public void addLoader(Bootable loader) {
        bootableList.add(loader);
    }

    public void setIo(DataIO<BaseModel> io) {
        this.io = io;
    }

    public Writer getById(Long id) {
        load();
        return writerList.stream()
                .filter(writer -> writer.getId() == id)
                .findFirst().orElseThrow();
    }

    public void deleteById(Long id) {
        load();

        int index = writerList.indexOf(new Writer(id));
        if (index == -1) {
            throw new NoSuchElementException();
        }

        writerList.remove(index);
        save();
    }

    public List<Writer> findAll() {
        load();
        return writerList;
    }

    public Writer save(Writer entity) {
        load();

        entity.setId(++idCounter);
        entity.setRole(Role.USER);
        writerList.add(entity);

        save();
        return entity;
    }

    public Writer update(Writer entity) {
        load();

        int index = writerList.indexOf(entity);
        if (index == -1) {
            throw new NoSuchElementException();
        }

        Writer updatableEntity = writerList.get(index);
        updatableEntity.setFirstName(entity.getFirstName());
        updatableEntity.setLastName(entity.getLastName());
        if (entity.getRegion() != null) {
            updatableEntity.setRegion(entity.getRegion());
        } else if (entity.getPosts() != null) {
            updatableEntity.setPosts(entity.getPosts());
        }

        save();

        return updatableEntity;
    }

    public void load() {
        bootableList.forEach(Bootable::load);

        writerList = io.loadData(WRITER_JSON_LOCATION, Writer.class);
        idCounter = JsonDataIOImpl.getIdCounter();
    }

    public void save() {
        io.saveData(WRITER_JSON_LOCATION, writerList);

        bootableList.forEach(Bootable::save);
    }
}