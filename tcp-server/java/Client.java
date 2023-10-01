package org.niket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
    public static void main(String[] args) {
        String serverHost = "localhost";
        int serverPort = 1234;

        try {
            // create a socket and connect to the server
            Socket socket = new Socket(serverHost, serverPort);

            // create input & output streams for the socket
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            // send a message to the server
            out.println("Hello, Server!");

            // read server response
            String response = in.readLine();
            System.out.println("Received following message from server: " + response);

            // close the socket
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
