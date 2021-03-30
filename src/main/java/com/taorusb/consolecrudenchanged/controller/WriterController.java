package com.taorusb.consolecrudenchanged.controller;

import com.taorusb.consolecrudenchanged.model.Writer;
import com.taorusb.consolecrudenchanged.repository.WriterRepository;

import java.util.List;
import java.util.NoSuchElementException;

import static com.taorusb.consolecrudenchanged.controller.Validator.*;

public class WriterController {

    private WriterRepository writerRepository;

    public void setWriterRepository(WriterRepository writerRepository) {
        this.writerRepository = writerRepository;
    }

    public List<Writer> showAll() {
        return writerRepository.findAll();
    }

    public String addNewWriter(String firstName, String lastName) {
        writerRepository.save(new Writer(firstName, lastName));
        return successful;
    }

    public String updateWriter(long id, String firstName, String lastName) {

        try {
            writerRepository.update(new Writer(id, firstName, lastName));
        } catch (NoSuchElementException e) {
            return elementNotFoundError;
        }
        return successful;
    }

    public String deleteWriter(long id) {

        try {
            writerRepository.deleteById(id);
        } catch (NoSuchElementException e) {
            return elementNotFoundError;
        }
        return successful;
    }
}