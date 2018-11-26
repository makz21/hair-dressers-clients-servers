package pl.makz.hdsalon.client;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.Socket;

public class HairDressersClient {
    private BufferedReader reader;
    private PrintWriter writer;
    private Socket socket;
    private String message;

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
            while (true) {
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
            System.out.println("Connected to Hair Dresser's Salon Application");

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
                    //System.out.println(wiadom);
                    if (wiadom.startsWith("!!!")) {
                        TrayIconDemo trayIconDemo = new TrayIconDemo(wiadom);
                    } else {
                        System.out.println(wiadom);
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public class TrayIconDemo {

        public TrayIconDemo(String line) throws AWTException, MalformedURLException {
            if (SystemTray.isSupported()) {
                displayTray(line);
            } else {
                System.err.println("System tray not supported!");
            }
        }

        public void displayTray(String line) throws AWTException {
            //Obtain only one instance of the SystemTray object
            SystemTray tray = SystemTray.getSystemTray();

            //If the icon is a file
            Image image = Toolkit.getDefaultToolkit().createImage("icon.png");
            //Alternative (if the icon is on the classpath):
            //Image image = Toolkit.getDefaultToolkit().createImage(getClass().getResource("icon.png"));

            TrayIcon trayIcon = new TrayIcon(image, "Tray Demo");
            //Let the system resize the image if needed
            trayIcon.setImageAutoSize(true);
            //Set tooltip text for the tray icon
            trayIcon.setToolTip("System tray icon demo");
            tray.add(trayIcon);

            trayIcon.displayMessage("HairDressersSalon", line, TrayIcon.MessageType.INFO);
        }
    }
}
