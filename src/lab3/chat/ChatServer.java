package lab3.chat;

/**
 * Created by dat12jkr on 12/04/16.
 */


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by dat12jkr on 12/04/16.
 */
public class ChatServer {

    public static void main(String[] args) throws IOException {
        ServerSocket s = new ServerSocket(6666);
        MailBox mb = new MailBox();
        Sender sender = new Sender(mb);
        sender.start();

        Socket sock;
        while (true) {
            sock = s.accept();
            ConnectionHandler ch = new ConnectionHandler(sock, mb);
            ch.start();
            sender.add(new PrintWriter(sock.getOutputStream(), true));
        }
    }

    static class ConnectionHandler extends Thread {
        private Socket client;
        private MailBox mb;
        private boolean running = false;

        public ConnectionHandler(Socket client, MailBox mb) {
            this.client = client;
            this.mb = mb;
            running = true;
        }

        public void run() {

            System.out.println(client.getInetAddress().getHostName());
            PrintWriter writer = null;
            BufferedReader reader = null;

            try {
                writer = new PrintWriter(client.getOutputStream(), true);
                reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
                while (running) {
                    String line = reader.readLine();

                    if (line.startsWith("M")) {
                        mb.setMessage(line.substring(2));
                    } else if (line.startsWith("E")) {
                        writer.write(line.substring(2) + "\n");
                        writer.flush();
                    } else if (line.startsWith("Q")) {
                        //reader.close();
                        //writer.close();
                        //client.close();
                        running = false;
                    }

                }
            } catch (IOException e) {

            } finally {
                try {
                    reader.close();
                    writer.close();
                    client.close();
                } catch (IOException e) {
                    //e.printStackTrace();
                }


            }

        }

    }
}

