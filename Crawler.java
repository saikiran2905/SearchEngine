package org.example;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashSet;

public class Crawler {
    HashSet<String> urlSet;
    int maxDepth = 2;
    Crawler(){
        urlSet = new HashSet<String>();
    }

    public void getPageTextAndLinks(String url, int depth){
        if(urlSet.contains(url)) {
            return;
        }
        if(depth >= maxDepth){
            return;
        }
        if(urlSet.add(url)){
            System.out.println(url);
        }
        depth++;
        try {
            Document document = Jsoup.connect(url).timeout(5000).get();
            //Indexer selects important part(relevance) of webpage according to user query.
            Indexer indexer = new Indexer(document, url);
            System.out.println(document.title());
            Elements availableLinksOnPage = document.select("a[href]");// a[href] selecting all the anchor tags href
            for (Element currentLink : availableLinksOnPage) {
                getPageTextAndLinks(currentLink.attr("abs:href"), depth); //attr used to convert Element object to string object
            }
        }
        catch(IOException ioException){
            ioException.printStackTrace();
        }
    }
    public static void main(String[] args) {
        Crawler crawler = new Crawler();
        crawler.getPageTextAndLinks("https://www.javatpoint.com/", 0);
    }
}