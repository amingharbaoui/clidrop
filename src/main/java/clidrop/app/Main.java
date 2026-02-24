package clidrop.app;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws Exception {
        ServerManager manager = new ServerManager();
        manager.start();
        System.out.println("Server started on: " + manager.getUrl());
    }
}


