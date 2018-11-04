/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package part_b;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author Rayhan
 */
public class Threaded {

    static final int PORT = 6789;

    public static void main(String[] args) throws IOException {

        ServerSocket serverConnect = new ServerSocket(PORT);
        int id = 1;
        System.out.println("Server started.\nListening for connections on port : " + PORT + " ...\n");
        while (true) {
            Socket s = serverConnect.accept();
            System.out.println("Client " + id + " connected to server");
            id++;
            Worker wt = new Worker(s, id);
            Thread t = new Thread(wt);
            t.start();

        }
    }
}
