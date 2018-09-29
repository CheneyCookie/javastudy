package com.cheney.socket.try1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static void main(String[] args) {
        new Server().init();
    }

    public void init() {
        ServerSocket serverSocket;
        try {
            serverSocket = new ServerSocket(66666);
            while (true) {
                Socket socket = serverSocket.accept();
                new ChatServer(socket).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

class ChatServer extends Thread {
    private Socket socket;

    public ChatServer(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        BufferedReader bufferedReader;
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String msg = null;
            while((msg = bufferedReader.readLine()) != null) {
                 OutputStream outputStream = socket.getOutputStream();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
