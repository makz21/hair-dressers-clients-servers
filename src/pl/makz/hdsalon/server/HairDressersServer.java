package pl.makz.hdsalon.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Iterator;

public class HairDressersServer {
    ArrayList outputStreams;
    HandlingClient client;
    HairDressersSalonTimetable ttUtil = new HairDressersSalonTimetable();
    static int i;

    public class HandlingClient implements Runnable {
        BufferedReader reader;
        Socket socket;
        int clientId;

        private HandlingClient(Socket clientSocket, int clientId) {
            this.clientId = clientId;
            try {
                socket = clientSocket;
                InputStreamReader isReader = new InputStreamReader(socket.getInputStream());
                reader = new BufferedReader(isReader);

            } catch (Exception ex) {
                ex.printStackTrace();
            }


        }

        public void run() {
            PrintWriter writer;
            String message;
            String name;
            String hour;
            try {
                while ((message = reader.readLine()) != null) {
                    System.out.println("Client message:" + message);
                    writer = (PrintWriter) outputStreams.get(getClientId());
                    switch (message) {
                        case "1":
                            writer.println(ttUtil.printTimetable());
                            writer.flush();
                            break;
                        case "2":
                            writer.println("wybierz godzine");
                            writer.flush();
                            hour = reader.readLine();
                            writer.println("Podaj imie i nazwisko");
                            writer.flush();
                            name = reader.readLine();
                            if (ttUtil.bookTheDate(hour, name)) {
                                sendToAll("***Własnie dokonano rezerwacji na godzine: " + hour + ":00***");
                            }
                            //System.out.println(hour + " " + name);
                            //sendToAll(ttUtil.printTimetable());
                            break;
                        case "3":
                            writer.println("Podaj godzine");
                            writer.flush();
                            hour = reader.readLine();
                            writer.println("Podaj imie i nazwisko");
                            writer.flush();
                            name = reader.readLine();
                            if (ttUtil.cancelTheDate(hour, name)) {
                                sendToAll("***Własnie anulowano rezerwacje na godzine: " + hour + ":00***");

                            }
                        case "4":
                            //outputStreams.remove(getClientId());
                            reader.close();
                            client.socket.close();
                            break;
                        default:
                            writer.println("Invalid input");
                            writer.flush();
                    }
                    // writer.println(message);
                    // writer.flush();

                    System.out.println(outputStreams.get(getClientId()));
                }
            } catch (IndexOutOfBoundsException ec) {
                System.out.println("usunięto klienta");
            } catch (SocketException ex) {
                System.out.println("zerwane połączenie");
            } catch (IOException ex) {
                System.out.println("closed streams");
            }
        }

        public int getClientId() {
            return clientId;
        }
    }

    public static void main(String[] args) {
        new HairDressersServer().startServer();
    }

    private void startServer() {

        outputStreams = new ArrayList();
        //sendToAll(ttUtil.printTimetable());
        try {
            ServerSocket serverSock = new ServerSocket(5000);
            while (true) {
                Socket clientSocket = serverSock.accept();
                PrintWriter writer = new PrintWriter(clientSocket.getOutputStream());
                client = new HandlingClient(clientSocket, i);
                outputStreams.add(writer);
                writer.println(welcomeInformationAndMenu());
                writer.flush();
                Thread t = new Thread(client);
                t.start();
                i++;
                System.out.println("Client connected");
                System.out.println(outputStreams);
                // System.out.println(outputStreams.get(client.getClientId()));
                //sendToAll(ttUtil.printTimetable());
                //sendToAll(welcomeInformationAndMenu());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void sendToAll(String message) {
        Iterator it = outputStreams.iterator();
        while (it.hasNext()) {
            try {
                PrintWriter writer = (PrintWriter) it.next();
                writer.println(message);
                writer.flush();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    private String welcomeInformationAndMenu() {
        String welcomeInformationAndMenu;
        welcomeInformationAndMenu = "Welcome to the hairdresser's salon booking app\n" +
                "choose the option that interests you:\n" +
                "1 - view the schedule\n" +
                "2 - book the date of the visit\n" +
                "3 - cancel the date of the visit\n" +
                "4 - close applications";

        return welcomeInformationAndMenu;
    }


}


