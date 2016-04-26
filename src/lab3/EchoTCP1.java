package lab3;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by dat12jkr on 12/04/16.
 */
public class EchoTCP1 {

    public static void main(String[] args) throws IOException {
        ServerSocket s = new ServerSocket(6666);
        Socket client = null;

        while (true) {
            client = s.accept();
            System.out.println(client.getInetAddress().getHostName());
            //BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            //PrintWriter out = new PrintWriter(client.getOutputStream(), true);
            OutputStream os = null;
            InputStream is = null;
            try {
                os = client.getOutputStream();
                is = client.getInputStream();
                int ch = is.read();
                while (ch != -1) {
                    os.write(ch);
                    ch = is.read();
                }
            } catch (IOException e) {
                e.printStackTrace();

            } finally {
                if (os != null && is != null) {
                    os.close();
                    is.close();
                    client.close();
                }
            }


        }


    }
}
