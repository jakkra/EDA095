package lab3.chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by dat12jkr on 12/04/16.
 */
public class Client {

    public static void main(String[] args) throws IOException {
        String hostName = args[0];
        int portNumber = Integer.parseInt(args[1]);
        BufferedReader stdIn = null;
        PrintWriter out = null;
        try {
            Socket echoSocket = new Socket(hostName, portNumber);
            out = new PrintWriter(echoSocket.getOutputStream(), true);
            final BufferedReader in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
            stdIn = new BufferedReader(new InputStreamReader(System.in));

            new Thread(() -> {
                boolean running = true;
                while (running) {
                    try {
                        String read = in.readLine();
                        if (read == null) {
                            System.out.println("Quitting");
                            running = false;
                        } else {
                            System.out.println(read);
                        }
                    } catch (IOException e) {
                        running = false;
                        System.out.println("Exiting");
                    }
                }
            }).start();
            boolean running = true;
            while (running) {
                String line = stdIn.readLine();

                out.write(line + "\n");
                out.flush();
                if (line.startsWith("Q")) {
                    running = false;
                }

            }
        } catch (Exception e) {
            System.out.println("Failure");
            System.exit(1);
        } finally {
            out.close();
            stdIn.close();

        }
    }
}
