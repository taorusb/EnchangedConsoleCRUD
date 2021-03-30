package com.taorusb.consolecrudenchanged.commandhandler.commands;

import com.taorusb.consolecrudenchanged.commandhandler.Handler;
import com.taorusb.consolecrudenchanged.commandhandler.Warehouse;
import com.taorusb.consolecrudenchanged.view.ShowPost;
import com.taorusb.consolecrudenchanged.view.ShowRegion;
import com.taorusb.consolecrudenchanged.view.ShowWriter;

public class InsertEndRequestReceiverController implements Handler {

    private ShowWriter showWriter;
    private ShowPost showPost;
    private ShowRegion showRegion;

    public void setShowWriter(ShowWriter showWriter) {
        this.showWriter = showWriter;
    }

    public void setShowPost(ShowPost showPost) {
        this.showPost = showPost;
    }

    public void setShowRegion(ShowRegion showRegion) {
        this.showRegion = showRegion;
    }

    @Override
    public void handle(String[] query) {

        Warehouse reqNumber = getCommandType(query[2]);

        switch (reqNumber) {
            case WRITERS:
                checkWriterFields(query[3], query[4]);
                break;
            case POSTS:
                checkPostFields(query[3], query[4]);
                break;
            case REGIONS:
                checkRegionFields(query[3], query[4]);
                break;
        }
    }

    private void checkWriterFields(String firstName, String lastName) {

        int firstNameLength = firstName.length();
        int lastNameLength = lastName.length();

        String arg1 = firstName.toLowerCase();
        String arg2 = lastName.toLowerCase();

        if (!arg1.startsWith("firstname=")) {
            System.out.println("invalid argument name: " + firstName.substring(0, 11));
            return;
        }
        if (!arg2.startsWith("lastname=")) {
            System.out.println("invalid argument name: " + lastName.substring(0, 10));
            return;
        }

        arg1 = firstName.substring(10, firstNameLength);
        arg2 = lastName.substring(9, lastNameLength);

        showWriter.addWriter(arg1, arg2);
    }

    private void checkPostFields(String writerId, String content) {

        int idLength = writerId.length();
        int postContentLength = content.length();

        String arg1 = writerId.toLowerCase();
        String arg2 = content.toLowerCase();

        if (!arg1.startsWith("writerid=")) {
            System.out.println("invalid argument name: " + writerId);
            return;
        }
        if (!arg2.startsWith("content=")) {
            System.out.println("invalid argument name: " + content);
            return;
        }

        arg1 = arg1.substring(9, idLength);
        arg2 = content.substring(8, postContentLength);

        showPost.addPost(arg1, arg2);
    }

    private void checkRegionFields(String writerId, String name) {

        int idLength = writerId.length();
        int nameLength = name.length();

        String arg1 = writerId.toLowerCase();
        String arg2 = name.toLowerCase();

        if (!arg1.startsWith("writerid=")) {
            System.out.println("invalid argument name: " + writerId);
            return;
        }
        if (!arg2.startsWith("name=")) {
            System.out.println("invalid argument name: " + name);
            return;
        }

        arg1 = arg1.substring(9, idLength);
        arg2 = name.substring(5, nameLength);

        showRegion.addRegion(arg1, arg2);
    }
}