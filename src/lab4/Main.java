package lab4;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by dat12jkr on 26/04/16.
 */
public class Main {

    public static void main(String[] args) throws MalformedURLException {
        WebCrawlerMonitor monitor = new WebCrawlerMonitor(new URL("http://cs.lth.se/pierre_nugues/"));
        ArrayList<Thread> crawlers = new ArrayList<>();
        Thread thread;
        long start = System.currentTimeMillis();
        for (int i = 0; i < 10; i++) {
            thread = new Thread(new Crawler(monitor));
            thread.start();
            crawlers.add(thread);
        }
        for (int i = 0; i < crawlers.size(); i++) {
            try {
                crawlers.get(i).join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        long done = System.currentTimeMillis();
        
        System.out.println("Crawling took: " + (done - start) / 1000 + " seconds");
        
        ArrayList<URL> urls = monitor.urls;
        Set<URL> hs = new HashSet<>();
        hs.addAll(urls);
        urls.clear();
        urls.addAll(hs);

        System.out.println("Found urls: " + urls.size());
        System.out.println("Found mails: " + monitor.mails.size());
        
        
        System.out.println("Crawled sites: " + monitor.crawledURLS.size());
        System.out.println(urls);
        System.out.println(monitor.mails);
        //System.out.println(monitor.mails.toString());
        //System.out.println(monitor.urls.toString());

    }
}
