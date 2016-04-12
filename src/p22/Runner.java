package p22;

import java.io.*;
import java.net.URL;
import java.util.List;

/**
 * Created by dat12jkr on 12/04/16.
 */
public class Runner implements Runnable {
    private List<URL> urls;

    public Runner(List<URL> urls) {
        this.urls = urls;
    }

    public void run() {
        URL u = pop();
        while (u != null) {
            downloadPdf(u);
            u = pop();
        }
    }

    public synchronized URL pop() {
        if (urls.size() > 0) {
            return urls.remove(0);
        }
        return null;
    }


    private void downloadPdf(URL url) {
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
