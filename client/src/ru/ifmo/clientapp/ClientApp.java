package ru.ifmo.clientapp;

public class ClientApp {
    public static void main(String[] args) {

        new Client("localhost", 8999).start();
    }
}