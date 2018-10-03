package com.example.adrian.grouptaskmanagement;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.text.format.Formatter;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.List;

import static android.content.Context.WIFI_SERVICE;

/**
 * Created by Adrian on 5/21/2018.
 */

public class TCP {
    Context currAct;

    public TCP(Context context) {
        currAct = context;
    }

    public String setupCon(String newData) {

        String data;
        String address = "192.168.1.5";


        String ipAddress = getIPAddress(true);
        String[] splitIP = ipAddress.split("\\.");

        //Toast.makeText(currAct,ipAddress,Toast.LENGTH_SHORT).show();

        switch (splitIP[2]) {
            case "31":
                address = "192.168.31.180";
                break;
            case "43":
                address = "192.168.43.138";
                break;
            case "136":
                address = "192.168.142.230";
                break;
            case "1":
                address = "192.168.1.5";
                break;
        }

        //address = "112.215.173.42";
        try {
            InetAddress serverAddr = InetAddress.getByName(address);
            SocketAddress sockaddr = new InetSocketAddress(serverAddr, 1234);
            Socket socket = new Socket();
            socket.connect(sockaddr, 2000);
            socket.setSoTimeout(2000);
            PrintStream sendData = new PrintStream(socket.getOutputStream());
            sendData.println(newData);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            data = bufferedReader.readLine();

        } catch (SocketTimeoutException e) {
            e.printStackTrace();
            return "failed Timeout";
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return "failed Unknown Host";
        } catch (SocketException e) {
            e.printStackTrace();
            return "failed Socket " + address;
        } catch (IOException e) {
            e.printStackTrace();
            return "failed I/O";
        }
        return data;
    }

    public String ping(int a) {

        String reply = null;
        Socket socket = new Socket();
        String check = "false";
        String trueAddress = null;
        String[] address = {"192.168.142.230", "192.168.43.138", "192.168.100.130", "192.168.138.57"};
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

    public String getIPAddress(boolean useIPv4) {
        try {
            List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface intf : interfaces) {
                List<InetAddress> addrs = Collections.list(intf.getInetAddresses());
                for (InetAddress addr : addrs) {
                    if (!addr.isLoopbackAddress()) {
                        String sAddr = addr.getHostAddress();
                        //boolean isIPv4 = InetAddressUtils.isIPv4Address(sAddr);
                        boolean isIPv4 = sAddr.indexOf(':') < 0;

                        if (useIPv4) {
                            if (isIPv4)
                                return sAddr;
                        } else {
                            if (!isIPv4) {
                                int delim = sAddr.indexOf('%'); // drop ip6 zone suffix
                                return delim < 0 ? sAddr.toUpperCase() : sAddr.substring(0, delim).toUpperCase();
                            }
                        }
                    }
                }
            }
        } catch (Exception ex) {
        } // for now eat exceptions
        return "";
    }
}
