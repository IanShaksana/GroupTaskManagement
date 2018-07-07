package com.example.adrian.grouptaskmanagement;

import android.graphics.Bitmap;
import android.util.Base64;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

/**
 * Created by Adrian on 6/22/2018.
 */

public class TCPC {
    public String setupCon (Bitmap newData){
        String data=null;
        try {
            //String address = "192.168.142.230";
            String address = "192.168.43.138";

            InetAddress serverAddr = InetAddress.getByName(address);
            SocketAddress sockaddr = new InetSocketAddress(serverAddr, 1234);
            Socket socket = new Socket();
            socket.connect(sockaddr,0);
            //socket.setSoTimeout(1000);

            OutputStream outputStream = socket.getOutputStream();
            FileInputStream fis = new FileInputStream(new File("C:\\Users\\Adrian\\Pictures\\"+/*filename+*/".jpg"));
            DataOutputStream dos  = new DataOutputStream(outputStream);
            BufferedOutputStream dos1  = new BufferedOutputStream(outputStream);
            int io;
            while((io=fis.read())> -1){
                dos1.write(io);
            }
            fis.close();
            dos1.close();
            socket.close();

            /*
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            newData.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] imageBytes = baos.toByteArray();
            String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);

            PrintStream sendData = new PrintStream(socket.getOutputStream());
            sendData.println("image-"+encodedImage);

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            data = bufferedReader.readLine();
            */

        }catch (SocketTimeoutException e){
            e.printStackTrace();
            return "failed Timeout";
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return "failed Unknown Host";
        } catch (SocketException e) {
            e.printStackTrace();
            return "failed Socket";
        } catch (IOException e) {
            e.printStackTrace();
            return "failed I/O";
        }
        return data;
    }
}
