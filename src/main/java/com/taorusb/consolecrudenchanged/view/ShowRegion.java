package com.taorusb.consolecrudenchanged.view;

import com.taorusb.consolecrudenchanged.controller.RegionController;
import com.taorusb.consolecrudenchanged.model.Region;

import static com.taorusb.consolecrudenchanged.controller.Validator.*;
import static java.lang.Long.parseLong;

public class ShowRegion {

    private RegionController regionController;
    private static final String[] template = {"%-8s%-22s%n", "id", "name"};
    private Region container = new Region();

    public ShowRegion(RegionController regionController) {
        this.regionController = regionController;
    }

    public void showByWriterId(String id) {

        String result;
        if (!checkId(id)) {
            System.out.println(idError);
            return;
        }

        result = regionController.showByWriterId(parseLong(id), container);

        if (result.equals(elementNotFoundError)) {
            System.out.println(elementNotFoundError);
            return;
        }
        printRegion();
    }

    public void addRegion(String writerId, String name) {

        String result;
        if (checkFields(writerId, name)) {

            container = new Region(name);
            result = regionController.addNewRegion(parseLong(writerId), container);

            if (result.equals(elementNotFoundError)) {
                System.out.println(elementNotFoundError);
                return;
            }
            printRegion();
        }
    }

    public void updateRegion(String id, String name) {

        String result;
        if (checkFields(id, name)) {

            container = new Region(parseLong(id), name);
            result = regionController.updateRegion(container);

            if (result.equals(elementNotFoundError)) {
                System.out.println(elementNotFoundError);
                return;
            }
            printRegion();
        }
    }

    public void deleteRegion(String id) {

        if (!checkId(id)) {
            System.out.println(idError);
            return;
        }
        System.out.println(regionController.deleteRegion(parseLong(id)));
    }

    private void printRegion() {
        System.out.printf(template[0], template[1], template[2]);
        System.out.printf(template[0], container.getId(), container.getName());
        System.out.print("\n");
    }

    private boolean checkFields(String id, String name) {

        if (!checkId(id)) {
            System.out.println(idError);
            return false;
        } else if (!checkString(name)) {
            System.out.println(nameError);
            return false;
        }
        return true;
    }
}