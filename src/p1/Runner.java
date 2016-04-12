package p1;

import java.io.*;
import java.net.URL;

/**
 * Created by dat12jkr on 12/04/16.
 */
public class Runner extends Thread {

    private URL url;

    public Runner(URL url) {
        this.url = url;
    }

    public void run(){
        File dir = new File("downloads/");

        File tmp = new File(dir, url.getFile().substring(url.getFile().lastIndexOf('/') + 1));
        BufferedInputStream in = null;
        FileOutputStream fOut = null;
        try {
            tmp.createNewFile();
            in = new BufferedInputStream(url.openStream());
            fOut = new FileOutputStream(tmp);

            final byte data[] = new byte[2048];
            int count;
            while ((count = in.read(data, 0, 2048)) != -1) {
                fOut.write(data, 0, count);
            }
        } catch (FileNotFoundException e) {

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fOut != null) {
                try {
                    fOut.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    

}
