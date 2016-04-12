package p2;


import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {
        List<URL> pdfs = urlExtractor("http://cs229.stanford.edu/materials.html");
        System.out.println("Saving pdfs...");
        File dir = new File("downloads/");
        dir.mkdirs();

        //Start threads
        for (int i = 0; i < 10; i++) {
            Runner downloader = new Runner(pdfs);
            downloader.start();
        }
    }


    public static List<URL> urlExtractor(String urlToPage) {
        URL url;
        InputStream is = null;
        BufferedReader br;
        String line;
        ArrayList<URL> urls = new ArrayList<URL>();

        try {
            url = new URL(urlToPage);
            is = url.openStream();  // throws an IOException
            br = new BufferedReader(new InputStreamReader(is));

            StringBuilder sb = new StringBuilder();
            while ((line = br.readLine()) != null) {
                sb.append(line + "\n");
            }
            String patternHrefPdf = "<a\\s+\\n*\\s*href\\s*=\\s*\"([^>]*?.pdf)\"";

            Pattern pdfLinks = Pattern.compile(patternHrefPdf);
            Matcher m = pdfLinks.matcher(sb.toString());
            //System.out.println(sb.toString());

            while (m.find()) {
                String thePdfUrl = m.group(1);
                URL urlPdf = new URL(url, thePdfUrl);
                urls.add(new URL(url, thePdfUrl));
                System.out.println(urlPdf.toString());
            }
        } catch (MalformedURLException mue) {
            mue.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            try {
                if (is != null) is.close();
            } catch (IOException ioe) {
                // nothing to see here
            }
        }

        return urls;
    }
}
