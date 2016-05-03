package lab5;

import java.io.IOException;
import java.net.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by dat12jkr on 03/05/16.
 */
public class TimeServerUDPMulti {
    public DateFormat dfTime;
    public DateFormat dfDate;
    private int port;

    public TimeServerUDPMulti(int port) {
        this.dfTime = new SimpleDateFormat("hh:mm:ss");
        this.dfDate = DateFormat.getDateInstance(DateFormat.SHORT, Locale.CHINESE);
        this.port = port;
    }

    public void startServer() throws IOException {

        MulticastSocket socket = new MulticastSocket(port);
        InetAddress ia = InetAddress.getByName("experiment.mcast.net");
        socket.joinGroup(ia);
        InetAddress us = InetAddress.getLocalHost();
        System.out.println(us.getHostName());
        while (true) {
            DatagramPacket p = new DatagramPacket(new byte[1024], 1);
            try {
                socket.receive(p);
                new Thread(() -> {
                    System.out.println(new String(p.getData()));
                    System.out.println(p.getLength());
                    System.out.println(p.getAddress().toString());
                    System.out.println(p.getPort());

                    byte ch = p.getData()[p.getOffset()];
                    String response = handleCommand(Character.toChars(ch)[0]);
                    System.out.println("Sending: " + response);
                    byte[] respBytes = response.getBytes();
                    DatagramPacket respPacket = new DatagramPacket(respBytes, respBytes.length, p.getAddress(), p.getPort());
                    try {
                        socket.send(respPacket);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }).start();


            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public String handleCommand(char command) {
        String result = "";
        switch (command) {
            case 'l':
                InetAddress us = null;
                try {
                    us = InetAddress.getLocalHost();
                    result = us.getHostName();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
                break;
            case 'd':
                result = dfDate.format(new Date());
                break;
            case 't':
                result = dfTime.format(new Date());
                break;
            default:
        }
        return result;

    }

    public static void main(String[] args) {
        TimeServerUDPMulti server = new TimeServerUDPMulti(3000);
        try {
            server.startServer();
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
