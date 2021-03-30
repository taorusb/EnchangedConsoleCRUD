package com.taorusb.consolecrudenchanged.controller;

import com.taorusb.consolecrudenchanged.model.Region;
import com.taorusb.consolecrudenchanged.model.Writer;
import com.taorusb.consolecrudenchanged.repository.RegionRepository;
import com.taorusb.consolecrudenchanged.repository.WriterRepository;

import java.util.NoSuchElementException;

import static com.taorusb.consolecrudenchanged.controller.Validator.*;

public class RegionController {

    private RegionRepository regionRepository;
    private WriterRepository writerRepository;

    public void setRegionRepository(RegionRepository regionRepository) {
        this.regionRepository = regionRepository;
    }

    public void setWriterRepository(WriterRepository writerRepository) {
        this.writerRepository = writerRepository;
    }

    public String showByWriterId(long id, Region region) {

        Region temp;
        try {
            temp = writerRepository.getById(id).getRegion();
            region.setId(temp.getId());
            region.setName(temp.getName());
        } catch (NoSuchElementException e) {
            return elementNotFoundError;
        }
        return successful;
    }

    public String addNewRegion(long writerId, Region region) {

        Region temp;
        try {
            Writer writer = writerRepository.getById(writerId);
            temp = regionRepository.save(region);
            writer.setRegion(temp);
            writerRepository.update(writer);
            region.setId(temp.getId());
        } catch (NoSuchElementException e) {
            return elementNotFoundError;
        }
        return successful;
    }

    public String updateRegion(Region region) {

        try {
            regionRepository.update(region);
        } catch (NoSuchElementException e) {
            return elementNotFoundError;
        }
        return successful;
    }

    public String deleteRegion(long id) {

        try {
            regionRepository.deleteById(id);
        } catch (NoSuchElementException e) {
            return elementNotFoundError;
        }
        return successful;
    }
}