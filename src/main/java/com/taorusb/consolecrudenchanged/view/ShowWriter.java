package com.taorusb.consolecrudenchanged.view;

import com.taorusb.consolecrudenchanged.controller.WriterController;
import com.taorusb.consolecrudenchanged.model.Writer;

import java.util.List;

import static com.taorusb.consolecrudenchanged.controller.Validator.*;
import static java.lang.Long.parseLong;

public class ShowWriter {

    private static final String[] template = {"%-8s%-16s%-16s%-16s%-16s%-8s%n", "id", "firstName", "lastName", "region", "postCount", "role"};
    private WriterController writerController;
    private List<Writer> container;

    public ShowWriter(WriterController writerController) {
        this.writerController = writerController;
    }

    public void showAll() {
        container = writerController.showAll();
        printWriters();
    }

    public void addWriter(String firstName, String lastName) {
        if (checkFields(firstName, lastName)) {
            writerController.addNewWriter(firstName, lastName);
            showAll();
        }
    }

    public void updateWriter(String id, String firstName, String lastName) {
        String result;
        if (checkId(id) || checkFields(firstName, lastName)) {
            result = writerController.updateWriter(parseLong(id), firstName, lastName);
            if (result.equals(elementNotFoundError)) {
                System.out.println(elementNotFoundError);
                return;
            }
            showAll();
        }
    }

    public void deleteWriter(String id) {
        if (!checkId(id)) {
            System.out.println(idError);
            return;
        }
        System.out.println(writerController.deleteWriter(parseLong(id)));
    }

    private void printWriters() {
        System.out.printf(template[0], template[1], template[2], template[3], template[4], template[5], template[6]);
        container
                .forEach(x -> System.out.printf
                        (template[0], x.getId(), x.getFirstName(), x.getLastName(), x.getRegion().getId(), x.getPostsCount(), x.getRole().name()));
        System.out.print("\n");
    }

    private boolean checkFields(String firstName, String lastName) {
        if (!checkString(firstName) || !checkString(lastName)) {
            System.out.println(nameError);
            return false;
        }
        return true;
    }
}
