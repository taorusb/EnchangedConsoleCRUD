package com.taorusb.consolecrudenchanged.commandhandler.commands;

import com.taorusb.consolecrudenchanged.commandhandler.Handler;
import com.taorusb.consolecrudenchanged.commandhandler.Warehouse;
import com.taorusb.consolecrudenchanged.view.ShowPost;
import com.taorusb.consolecrudenchanged.view.ShowRegion;
import com.taorusb.consolecrudenchanged.view.ShowWriter;

public class DeleteEndRequestReceiverController implements Handler {

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

        if (checkString(query)) {

            Warehouse reqNumber = getCommandType(query[2]);
            String id = query[4].substring(3, query[4].length());

            switch (reqNumber) {
                case WRITER:
                    showWriter.deleteWriter(id);
                    break;
                case POST:
                    showPost.deletePost(id);
                    break;
                case REGION:
                    showRegion.deleteRegion(id);
                    break;
            }
        }
    }

    private boolean checkString(String[] strings) {

        if (!strings[3].toLowerCase().equals("where")) {
            System.out.println("Incorrect key: " + strings[3]);
            return false;
        } else if (!strings[4].toLowerCase().startsWith("id=")) {
            System.out.println("Incorrect argument name: " + strings[4]);
            return false;
        }
        return true;
    }
}