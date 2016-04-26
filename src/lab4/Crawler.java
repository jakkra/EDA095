package lab4;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by dat12jkr on 26/04/16.
 */
public class Crawler implements Runnable {
    private WebCrawlerMonitor m;
    ArrayList<URL> urls;
    ArrayList<String> mails;

    public Crawler(WebCrawlerMonitor m) {
        this.m = m;
        urls = new ArrayList<>();
        mails = new ArrayList<>();

    }

    @Override
    public void run() {
        URL url = m.getNextURL();
        while (url != null) {
            try {
                parse(url);
                m.addCrawledLinks(urls, mails);
                urls.clear();
                mails.clear();
                url = m.getNextURL();
            } catch (IOException e) {
                url = m.getNextURL();

            }

        }
        System.out.println("Exiting");
    }

    public void parse(URL url) throws IOException {
        InputStream is = url.openStream();
        Document doc = Jsoup.parse(is, "UTF-8", url.getHost());
        Elements base = doc.getElementsByTag("base");
        Elements links = doc.getElementsByTag("a");
        for (Element link : links) {
            String linkAbsHref = link.attr("abs:href");
            String frame = link.getElementsByTag("frame").attr("src");
            if (linkAbsHref.startsWith("mailto:")) {
                mails.add(linkAbsHref.substring(7));
            } else {
                try {
                    if (linkAbsHref.length() > 0) {
                        urls.add(new URL(linkAbsHref));
                    }
                    if (frame.length() > 0) {
                        urls.add(new URL(frame));
                    }
                } catch (IOException e) {

                }
            }
        }
        is.close();
    }

}
