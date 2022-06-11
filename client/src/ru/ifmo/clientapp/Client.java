package ru.ifmo.clientapp; // тест2

import ru.ifmo.lib.Connection;
import ru.ifmo.lib.Message;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {
    private final String ip;
    private final int port;
    private final Scanner scanner;



    public Client(String ip, int port) {
        this.ip = ip;
        this.port = port;
        scanner = new Scanner(System.in);
    }


    public void start() {
        System.out.println("Введите имя:");
        String userName = scanner.nextLine();
        String text;
        try (Connection connection = new Connection(new Socket(ip, port))) {
            new Thread(new Read(connection), userName).start();
            while (true) {
                System.out.println("/");
                text = scanner.nextLine();
                connection.sendMessage(Message.getMessage(userName, text));
            }
        }catch (SocketException e) {
            e.printStackTrace();
        }
        catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static class Read implements Runnable {
        private final Connection connection;


        public Read(Connection connection) {
            this.connection = connection;
        }


        @Override
        public void run() {
            while (true) {
                try {
                    Message message = connection.readMessage();
                    System.out.println(message);
                } catch (SocketException e) {
                    e.printStackTrace();
                    break;
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

