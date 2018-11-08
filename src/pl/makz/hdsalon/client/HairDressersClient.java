package pl.makz.hdsalon.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class HairDressersClient {
    private BufferedReader reader;
    private PrintWriter writer;
    private Socket socket;
    private String message;
    private boolean closed = false;
    private BufferedReader readerCmd = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) {
        HairDressersClient client = new HairDressersClient();
        client.startClient();
    }

    private void startClient() {
        configureCommunicationWithServer();
        Thread thread = new Thread(new MessageReceiver());
        thread.start();
        try {
            while (!closed) {
                message = readerCmd.readLine();
                writer.println(message);
                writer.flush();
            }
        } catch (Exception ex) {
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
                    closed = true;
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

}
