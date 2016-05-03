package lab5;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by dat12jkr on 03/05/16.
 */
public class TimeServerUDP {
    public DateFormat dfTime;
    public DateFormat dfDate;
    private int port;

    public TimeServerUDP(int port) {
        this.dfTime = new SimpleDateFormat("hh:mm:ss");
        this.dfDate = DateFormat.getDateInstance(DateFormat.SHORT, Locale.CHINESE);


        this.port = port;
    }

    public void startServer() throws SocketException {

        DatagramSocket socket = new DatagramSocket(port);
        new Thread(() -> {
            DatagramPacket p = new DatagramPacket(new byte[1024], 1);
            while (true) {
                try {
                    socket.receive(p);
                    System.out.println(new String(p.getData()));
                    System.out.println(p.getLength());
                    System.out.println(p.getAddress().toString());
                    System.out.println(p.getPort());
                    
                    byte ch = p.getData()[p.getOffset()];
                    String response = handleCommand(Character.toChars(ch)[0]);
                    byte[] respBytes = response.getBytes();
                    DatagramPacket respPacket = new DatagramPacket(respBytes, respBytes.length, p.getAddress(), p.getPort());
                    socket.send(respPacket);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }

    public String handleCommand(char command) {
        String result = "";
        switch (command) {
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
        TimeServerUDP server = new TimeServerUDP(3000);
        try {
            server.startServer();
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }
}
