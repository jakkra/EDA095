package lab4;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by dat12jkr on 26/04/16.
 */
public class WebCrawlerMonitor {
    static HashSet<String> mails;
    static ArrayList<URL> urls;
    static HashSet<String> crawledURLS;
    public static int index = 0;

    public WebCrawlerMonitor(URL startURL) {
        mails = new HashSet<>();
        urls = new ArrayList<>();
        crawledURLS = new HashSet<>();
        urls.add(startURL);
    }

    public synchronized URL getNextURL() {
        while (crawledURLS.size() < 500) {
            if (index < urls.size()) {

                if (!crawledURLS.contains(urls.get(index).getPath())) {
                    crawledURLS.add(urls.get(index).getPath());
                    if(crawledURLS.size() % 100 == 0){
                        System.out.println("Ack");
                    }
                    System.out.println(crawledURLS.size());
                    return urls.get(index);
                }
                index++;
            } else {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }
        return null;
    }

    public synchronized void addCrawledLinks(ArrayList<URL> newUrls, ArrayList<String> newMails) {
        urls.addAll(newUrls);
        mails.addAll(newMails);
        notifyAll();
    }
}
