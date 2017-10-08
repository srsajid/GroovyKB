package net;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by sajedur on 4/11/2016.
 */
public class EchoServer {

    public static void main(String[] args) {
        String hostName = "localhost";
        int portNumber = 80;

        try {
            Socket echoSocket = new Socket(hostName, portNumber);
            PrintWriter out =
                    new PrintWriter(echoSocket.getOutputStream(), true);
            BufferedReader in =
                    new BufferedReader(
                            new InputStreamReader(echoSocket.getInputStream()));
            BufferedReader stdIn =
                    new BufferedReader(
                            new InputStreamReader(System.in));
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {

        }
    }
}
