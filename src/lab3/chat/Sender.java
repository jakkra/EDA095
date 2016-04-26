package lab3.chat;


import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * Created by dat12jkr on 12/04/16.
 */
public class Sender extends Thread {

    ArrayList<PrintWriter> clients;
    private MailBox mb;

    public Sender(MailBox mb) {
        this.mb = mb;
        clients = new ArrayList<>();
    }

    public void run() {
        String message;
        while (true) {
            message = mb.getMessage();
            System.out.println("Sending message to all: " + message);
            ArrayList<PrintWriter> receivers = getClients();
            for (int i = 0; i < receivers.size(); i++) {
                try {
                    receivers.get(i).write(message + "\n");
                    receivers.get(i).flush();
                } catch (Exception e) {
                    clients.remove(i);
                    System.out.println("Exception client left");
                }
            }
        }
    }

    private synchronized ArrayList<PrintWriter> getClients() {
        return clients;

    }

    public void add(PrintWriter printWriter) {
        clients.add(printWriter);
    }
}
