package com.taorusb.consolecrudenchanged.commandhandler.commands;

import com.taorusb.consolecrudenchanged.commandhandler.Handler;
import com.taorusb.consolecrudenchanged.commandhandler.Warehouse;

public class InsertDirectionController implements Handler {

    private Handler insertEndRequestReceiverController;

    public void setInsertEndRequestReceiverController(Handler insertEndRequestReceiverController) {
        this.insertEndRequestReceiverController = insertEndRequestReceiverController;
    }

    @Override
    public void handle(String[] query) {

        if (query.length != 5) {
            System.out.println("Something is incorrect, please try again.");
            return;
        }

        if (checkString(query)) {

            Warehouse reqNumber = getCommandType(query[2]);

            if (reqNumber.getNum() < 11 || reqNumber.getNum() > 13) {
                System.out.println("Incorrect type of model name.");
                return;
            }

            insertEndRequestReceiverController.handle(query);
        }
    }

    private boolean checkString(String[] strings) {

        if (!strings[1].toLowerCase().equals("into")) {
            System.out.println("Incorrect key: " + strings[1]);
            return false;
        }
        return true;
    }
}