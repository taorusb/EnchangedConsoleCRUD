package com.taorusb.consolecrudenchanged.repository.dataio;

import com.taorusb.consolecrudenchanged.model.BaseModel;
import com.taorusb.consolecrudenchanged.model.Region;
import com.taorusb.consolecrudenchanged.repository.DataIO;
import com.taorusb.consolecrudenchanged.repository.RegionRepository;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

public class JsonRegionRepositoryImpl implements RegionRepository {

    private DataIO<BaseModel> io;
    private static List<Region> regionList = new LinkedList<>();
    private final static String REGION_JSON_LOCATION = "src\\main\\resources\\regions.json";
    private static long idCounter = 0;

    public void setIo(DataIO<BaseModel> io) {
        this.io = io;
    }

    public Region getById(Long id) {
        load();
        return regionList
                .stream()
                .filter(region -> region.getId() == id)
                .findFirst().orElseThrow();
    }

    public void deleteById(Long id) {
        load();

        int index = regionList.indexOf(new Region(id));
        if (index == -1) {
            throw new NoSuchElementException();
        }

        regionList.remove(index);
        save();
    }

    public List<Region> findAll() {
        load();
        return regionList;
    }

    public Region save(Region entity) {
        load();

        entity.setId(++idCounter);
        regionList.add(entity);

        save();
        return entity;
    }

    public Region update(Region entity) {
        load();

        int index = regionList.indexOf(entity);
        if (index == -1) {
            throw new NoSuchElementException();
        }

        Region updatableEntity = regionList.get(index);
        updatableEntity.setName(entity.getName());

        save();
        return updatableEntity;
    }

    public void load() {
        regionList = io.loadData(REGION_JSON_LOCATION, Region.class);
        idCounter = JsonDataIOImpl.getIdCounter();
    }

    public void save() {
        io.saveData(REGION_JSON_LOCATION, regionList);
    }
}