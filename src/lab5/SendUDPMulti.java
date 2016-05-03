package lab5;

import java.io.IOException;
import java.net.*;

/**
 * Created by dat12jkr on 03/05/16.
 */
public class SendUDPMulti {

    MulticastSocket ms;
    private int port;
    private String cmd;

    public SendUDPMulti(int port, String cmd) {
        this.port = port;
        this.cmd = cmd;
    }

    public DatagramPacket discover() throws IOException {
        ms = new MulticastSocket();
        ms.setTimeToLive(1);
        InetAddress ia = InetAddress.getByName("experiment.mcast.net");

        byte[] buf = "l".getBytes();
        DatagramPacket dp = new DatagramPacket(buf, buf.length, ia, port);
        ms.send(dp);

        DatagramPacket rec = new DatagramPacket(new byte[1024], 1024);
        ms.receive(rec);
        System.out.println("Response from selected broadcast " + new String(rec.getData()));

        return rec;
    }

    public void executeCommand(int port, InetAddress ip) throws IOException {
        System.out.println(ip);
        System.out.println(port);
        System.out.println(ip.getCanonicalHostName());
        System.out.println(ip.getHostAddress());
        
        byte[] sendBytes = cmd.getBytes();

        DatagramPacket sendPacket = new DatagramPacket(sendBytes, sendBytes.length, ip, port);
        DatagramPacket recPacket = new DatagramPacket(new byte[1024], 1024, ip, port);
        DatagramSocket s = new DatagramSocket();
        s.send(sendPacket);
        
        s.receive(recPacket);
        System.out.println("Response from command " + new String(recPacket.getData()));

    }

    public static void main(String[] args) {
        SendUDPMulti client = null;
        try {
            client = new SendUDPMulti(3000, "d");
            DatagramPacket p = client.discover();
            client.executeCommand(p.getPort(), p.getAddress());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
