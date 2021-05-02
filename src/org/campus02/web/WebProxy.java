package org.campus02.web;

import java.util.HashMap;

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
        try {
            HashMap<String, WebPage> cacheMap = cache.getCache();
            if (cacheMap.get(url) == null)
                WebPage pageFromCache = cache.readFromCache(url);
        } catch (CacheMissException e) {
            e.printStackTrace();
        }
        return null;
    }
}
