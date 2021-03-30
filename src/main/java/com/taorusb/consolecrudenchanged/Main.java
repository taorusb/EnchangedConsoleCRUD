package com.taorusb.consolecrudenchanged;

import java.io.*;

public class Main {
    public static void main(String[] args) {

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        Facade facade = Facade.getInstance();
        facade.assembleApplication();

        try {
            while (true) {
                facade.startApp(reader.readLine());
            }
        } catch (IOException e) {
            System.out.println("I/O error.");
        }
    }
}