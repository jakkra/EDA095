package lab5;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by dat12jkr on 03/05/16.
 */
public class SendUDP {
    DatagramPacket recPacket;
    DatagramPacket sendPacket;

    public SendUDP(InetAddress server, int port, String cmd){
        byte[] sendBytes = cmd.getBytes();
        sendPacket = new DatagramPacket(sendBytes, sendBytes.length, server, port);
        recPacket = new DatagramPacket(new byte[1024], 1024);
        
    }
    
    public void executeCommand() throws IOException {
        DatagramSocket socket = new DatagramSocket();
        socket.send(sendPacket);
        socket.receive(recPacket);
        System.out.println(new String(recPacket.getData()));
  
    }

    public static void main(String[] args) {
        SendUDP client = null;
        try {
            client = new SendUDP(InetAddress.getLocalHost(), 3000, "d");
            client.executeCommand();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
}
