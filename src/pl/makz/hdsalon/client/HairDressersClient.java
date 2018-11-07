package pl.makz.hdsalon.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class HairDressersClient {
    BufferedReader reader;
    PrintWriter writer;
    Socket socket;
    String message;


    public static void main(String[] args) {
        HairDressersClient client = new HairDressersClient();
        client.startClient();
    }

    public void startClient() {
        configureCommunicationWithServer();
        Thread thread = new Thread(new MessageReceiver());
        thread.start();
        try{
            message = reader.readLine();
            writer.println(message);
            writer.flush();
        }catch (Exception ex){
            ex.printStackTrace();
        }

    }

    private void configureCommunicationWithServer() {
        try {
            InetAddress address = InetAddress.getLocalHost();
            socket = new Socket(address, 5000);
            InputStreamReader reader1 = new InputStreamReader(socket.getInputStream());
            reader = new BufferedReader(reader1);
            writer = new PrintWriter(socket.getOutputStream());
            System.out.println("connection is ready");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public class MessageReceiver implements Runnable {
        @Override
        public void run() {
            String wiadom;
            try {
                while ((wiadom = reader.readLine()) != null) {
                    System.out.println("Odczytano z serwera: " + wiadom);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

}
