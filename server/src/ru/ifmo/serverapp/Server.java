package ru.ifmo.serverapp;


import ru.ifmo.lib.Connection;
import ru.ifmo.lib.Message;

import java.io.EOFException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

public class Server {
    private final int port;
    private final List<Connection> connections = new ArrayList<>();
    private final ArrayBlockingQueue<Message> queue = new ArrayBlockingQueue<>(30,true);

    public Server(int port) {
        this.port = port;
    }
    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Сервер запущен");
            new Thread(new Sender(connections, queue), "Sender").start();
            while (true) {
                Socket newClient = serverSocket.accept();
                Connection connection = new Connection(newClient);
                connections.add(connection);
                new Thread(new Reciever(connection)).start();
            }
        } catch (IOException e) {
            System.out.println("Ошибка "+e);
        }
    }
    private class Reciever implements Runnable {
        private final Connection connection;
        public Reciever(Connection connection) {
            this.connection = connection;
        }

        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    Message message = connection.readMessage();
                    if (!message.getText().isEmpty()) {
                        try {
                            queue.put(message);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (SocketException e) {
                    connections.remove(connection);
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    private static class Sender implements Runnable {
        private final List<Connection> connections;
        private final ArrayBlockingQueue<Message> queue;

        public Sender(List<Connection> connections, ArrayBlockingQueue<Message> queue) {
            this.connections = connections;
            this.queue = queue;
        }
        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    Message message = null;
                    try {
                        message = queue.take();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName() + ": " + message);
                    for (Connection connection : connections) {
                        if (connection.getSender().equals(message.getSender())) continue;
                        try {
                            connection.sendMessage(message);
                        }catch (EOFException |SocketException e) {
                            connection.close();
                            e.printStackTrace();
                        }
                        catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}