package org.campus02.web;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class WebProxy {
    private PageCache cache;
    private int numCacheHits;
    private int numCacheMisses;

    public WebProxy() {
        cache = new PageCache();
    }

    public WebProxy(PageCache cache) {
        this.cache = cache;
    }

    public WebPage fetch(String url) throws UrlLoaderException{
        WebPage webPage = null;
        try{
            webPage = cache.readFromCache(url);
            numCacheHits++;
        }
        catch(CacheMissException ex){
            webPage = UrlLoader.loadWebPage(url);
            PageCache pageCache = new PageCache();
            pageCache.writeToCache(webPage);
            numCacheMisses++;
        }
        return webPage;
    }

    public String statsHits(){
        return "stats hits: " + numCacheHits;
    }

    public String statsMisses(){
        return "stats misses: " + numCacheMisses;
    }

    public boolean writePageCacheToFile(String pathToFile){
        HashMap<String,WebPage> cacheForFile = cache.getCache();
        File f = null;
        FileWriter fw = null;
        PrintWriter pw = null;
        try
        {
            f = new File(pathToFile);
            fw = new FileWriter(f);

            for (Map.Entry<String, WebPage> entry : cacheForFile.entrySet()) {
                pw.println(entry.getKey() +";"+entry.getValue());
            }
            return true;
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
            return false;
        }
    }
}
