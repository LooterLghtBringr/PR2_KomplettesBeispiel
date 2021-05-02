package org.campus02.web;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class UrlLoader {

    public static WebPage loadWebPage(String url) throws UrlLoaderException {
        WebPage webPage = null;
        try {
            URL urlToWebPage = new URL(url);
            BufferedReader br = new BufferedReader(new InputStreamReader(urlToWebPage.openStream()));
            String content = "";
            while ((content += br.readLine()) != null) {

            }
            webPage = new WebPage(url, content);

        } catch (Exception e) {
            new UrlLoaderException("UrlLoaderException occured", e);
        }
        return webPage;
    }

}
