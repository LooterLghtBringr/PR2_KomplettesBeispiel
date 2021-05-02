package org.campus02.web;

import java.io.*;
import java.util.HashMap;

public class PageCache {

    private HashMap<String, WebPage> cache;

    public HashMap<String, WebPage> getCache() {
        return cache;
    }

    public WebPage readFromCache(String url) throws CacheMissException
    {
        if(!cache.containsKey(url))
            throw new CacheMissException("Key " + url + " not found in cache");

        WebPage webPage = cache.get(url);

        return webPage;
    }

    public void writeToCache(WebPage webPage)
    {
        cache.put(webPage.getUrl(), webPage);
    }

    public void warmUp(String pathToUrls) throws IOException {
        File f = new File(pathToUrls);
        BufferedReader br = new BufferedReader(new FileReader(f));
        String line = "";
        while((line = br.readLine()) != null)
        {
            try
            {
                WebPage webPage= UrlLoader.loadWebPage(line);
                cache.put(webPage.getUrl(), webPage);
            }
            catch(UrlLoaderException ex) {
                System.out.println(ex.getMessage());
                continue;
            }
        }
    }
}
