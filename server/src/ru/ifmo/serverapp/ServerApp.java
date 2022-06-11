package ru.ifmo.serverapp;


public class ServerApp {
    public static void main(String[] args) {

        new Server(8999).start();
    }
}
