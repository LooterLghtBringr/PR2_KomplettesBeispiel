package org.campus02.web;

import java.io.*;
import java.net.Socket;

public class ClientHandler implements Runnable{

    Socket client;
    WebProxy proxy;

    public ClientHandler(Socket client, WebProxy proxy) {
        this.client = client;
        this.proxy = proxy;
    }

    @Override
    public void run() {
        try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
             BufferedReader br = new BufferedReader(new InputStreamReader(client.getInputStream())))
        {
            String command;
            WebProxy webProxy = new WebProxy();
            while((command = br.readLine()) != null)
            {
                if (command.equals("bye"))
                    break;

                if (command.split(" ").length != 2)
                {
                    bw.write("error: command invalid");
                    bw.newLine();
                    bw.flush();
                }

                if (command.split(" ")[0].equals("fetch"))
                {
                    webProxy = new WebProxy();
                    try {
                        WebPage fetched = webProxy.fetch(command.split(" ")[1]);
                        bw.write(fetched.getContent());
                        bw.newLine();
                        bw.flush();
                    } catch (UrlLoaderException e) {
                        bw.write("error: loading the url failed");
                        bw.newLine();
                        bw.flush();
                    }
                }
                else if (command.split(" ")[0].equals("stats"))
                {
                    if (command.split(" ")[1].equals("hits"))
                    {
                        bw.write(webProxy.statsHits());
                    }
                    else if(command.split(" ")[1].equals("misses"))
                    {
                        bw.write(webProxy.statsMisses());
                    }
                    else
                    {
                        bw.write("error: invalid command");
                    }
                    bw.newLine();
                    bw.flush();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
