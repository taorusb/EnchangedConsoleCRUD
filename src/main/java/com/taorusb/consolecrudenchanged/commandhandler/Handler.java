package com.taorusb.consolecrudenchanged.commandhandler;


import static com.taorusb.consolecrudenchanged.commandhandler.Warehouse.ERROR;

public interface Handler {

    void handle(String[] query);

    default Warehouse getCommandType(String s) {
        Warehouse key;
        try {
            key = Warehouse.valueOf(s.toUpperCase());
        } catch (IllegalArgumentException e) {
            key = ERROR;
        }
        return key;
    }
}