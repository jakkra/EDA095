import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {
        List<URL> pdfs = urlExtractor("http://cs.lth.se/eda095/foerelaesningar/?no_cache=1/");
        try {
            saveUrls(pdfs);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveUrls(List<URL> urls) throws IOException {
        System.out.println("Saving pdfs...");
        File dir = new File("downloads/");
        dir.mkdirs();

        for (URL url : urls) {
            File tmp = new File(dir, url.getFile().substring(url.getFile().lastIndexOf('/') + 1));
            tmp.createNewFile();

            BufferedInputStream in = null;
            FileOutputStream fOut = null;
            try {
                in = new BufferedInputStream(url.openStream());
                fOut = new FileOutputStream(tmp);

                final byte data[] = new byte[2048];
                int count;
                while ((count = in.read(data, 0, 2048)) != -1) {
                    fOut.write(data, 0, count);
                }
            } catch(FileNotFoundException e){

            } finally {
                if (in != null) {
                    in.close();
                }
                if (fOut != null) {
                    fOut.close();
                }
            }
        }
        System.out.println("Finished saving pdfs...");

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
            String patternHrefPdf = "<a\\s+\\n*\\s*href\\s*=\\s*\"(http://.*?.pdf)\"";

            Pattern pdfLinks = Pattern.compile(patternHrefPdf);
            Matcher m = pdfLinks.matcher(sb.toString());

            while (m.find()) {
                String thePdfUrl = m.group(1);
                urls.add(new URL(thePdfUrl));
                System.out.println(thePdfUrl);
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
