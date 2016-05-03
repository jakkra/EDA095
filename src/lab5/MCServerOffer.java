package lab5;

import java.net.*;
import java.io.*;

public class MCServerOffer {

    public static void main(String args[]) {
        try {
            MulticastSocket ms = new MulticastSocket(4099);
            InetAddress ia = InetAddress.getByName("experiment.mcast.net");
            ms.joinGroup(ia);
            InetAddress us = InetAddress.getLocalHost();
            System.out.println(us.getHostName());
            while(true) {
                byte[] buf = new byte[65536];
                DatagramPacket dp = new DatagramPacket(buf,buf.length);
                ms.receive(dp);
                String s = new String(dp.getData(),0,dp.getLength());
                System.out.println("Received: "+s);
                DatagramPacket sendPacket = new DatagramPacket(us.getHostName().getBytes(), us.getHostName().getBytes().length, dp.getAddress(), dp.getPort());
                ms.send(sendPacket);
            }
        } catch(IOException e) {
            System.out.println("Exception:"+e);
        }
    }

}
