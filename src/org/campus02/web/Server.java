package org.campus02.web;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args)
    {
        try(ServerSocket serverSocket = new ServerSocket(5678))
        {
            System.out.println("Server started");
            PageCache pageCache = new PageCache();
            pageCache.warmUp("D:\\Temp\\demo_urls.txt");
            WebProxy webProxy = new WebProxy(pageCache);
            while(true)
            {
                System.out.println("Waiting for Client");
                Socket client = serverSocket.accept();
                ClientHandler clientHandler = new ClientHandler(client, webProxy);
                System.out.println("Starting clientHandler");
                clientHandler.run();
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
