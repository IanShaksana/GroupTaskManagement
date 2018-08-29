package com.example.adrian.grouptaskmanagement;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

/**
 * Created by Adrian on 5/21/2018.
 */

public class TCP {
    public String setupCon (String newData)  {
        String data;
        //String address = "192.168.43.138";
        String address = "192.168.142.230";

        try {
            InetAddress serverAddr = InetAddress.getByName(address);
            SocketAddress sockaddr = new InetSocketAddress(serverAddr, 1234);
            Socket socket = new Socket();
            socket.connect(sockaddr,2000);
            socket.setSoTimeout(2000);
            PrintStream sendData = new PrintStream(socket.getOutputStream());
            sendData.println(newData);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            data = bufferedReader.readLine();

        }catch (SocketTimeoutException e){
            e.printStackTrace();
            return "failed Timeout";
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return "failed Unknown Host";
        } catch (SocketException e) {
            e.printStackTrace();
            return "failed Socket"+address;
        } catch (IOException e) {
            e.printStackTrace();
            return "failed I/O";
        }
        return data;
    }

    public String ping(int a){

        String reply = null;
        Socket socket = new Socket();
        String check = "false";
        String trueAddress=null;
        String[] address ={"192.168.142.230","192.168.43.138","192.168.100.130","192.168.138.57"};
        while (check.equals("false")) {
            try {
                InetAddress serverAddr = InetAddress.getByName(address[a]);
                SocketAddress sockaddr = new InetSocketAddress(serverAddr, 1234);
                socket.connect(sockaddr, 1000);
                PrintStream sendData = new PrintStream(socket.getOutputStream());
                sendData.println("ping");
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                reply = bufferedReader.readLine();
                if (reply.equals("Success")) {
                    trueAddress = address[a];
                    check = "true";
                } else {
                    check = "false";
                }
            } catch (IOException e) {
                ping(a++);
            }
        }

        return trueAddress;
    }
}
