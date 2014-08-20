package service.model;

import java.io.*;
import java.net.Socket;

/**
 * Created by Selvin
 * on 30.07.2014.
 */
public class ClientThread extends Thread {
    private Socket socket;
    private BufferedReader msgFromClnt;
    private PrintWriter msgToClnt;
    private MyService ms;
    private UserInterface ui;

    public ClientThread(Socket s, MyService ms, UserInterface ui) throws IOException {
        socket = s;
        this.ms = ms;
        this.ui = ui;
        msgFromClnt = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        msgToClnt = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
        start();
    }

    @Override
    public void run() {
        try {
            ui.show("Client connected.");
            String input;
            while ((input = msgFromClnt.readLine()) != null) {
                if (input.substring(0, input.indexOf("@")).equalsIgnoreCase("get")) {
                    final String finalInput = input;
                    ui.show("Client: " + finalInput);
                    final PrintWriter finalMsgToClnt = msgToClnt;
                    new Thread() {
                        @Override
                        public void run() {
                            finalMsgToClnt.println("Service: get" + "@" +
                                    finalInput.substring(4) + "@" +
                                    ms.getAmount(Integer.parseInt(finalInput.substring(4))));
                            ui.show("Service: " + finalInput);
                        }
                    }.start();
                }

                if (input.substring(0, input.indexOf("@")).equalsIgnoreCase("add")) {
                    final String finalInput = input;
                    ui.show("Client: " + finalInput);
                    new Thread() {
                        @Override
                        public void run() {
                            ms.addAmount(Integer.parseInt(finalInput.substring(finalInput.indexOf("@") + 1, finalInput.lastIndexOf("@"))),
                                    Long.parseLong(finalInput.substring(finalInput.lastIndexOf("@") + 1)));
                            ui.show("Service: " + finalInput);
                        }
                    }.start();
                }

                if (input.substring(0, input.indexOf("@")).equalsIgnoreCase("stat")) {
                    final String finalInput = input;
                    final PrintWriter finalMsgToClnt = msgToClnt;
                    ui.show(finalInput);
                    new Thread() {
                        @Override
                        public void run() {
                            switch (finalInput.substring(finalInput.indexOf("@") + 1)) {
                                case "add":
                                    finalMsgToClnt.println("stat@add@" + ms.getStatistic("add"));
                                    ui.show("Sending statistic to client for \"addAmount()\" method.");
                                    break;
                                case "get":
                                    finalMsgToClnt.println("stat@get@" + ms.getStatistic("get"));
                                    ui.show("Sending statistic to client for \"getAmount()\" method.");
                                    break;
                                case "reset":
                                    ui.show("Resetting statistic of service...");
                                    ms.resetTotalStatistic();
                                    break;
                            }
                        }
                    }.start();
                }
            }
            ui.show("Client disconnected.");
        }
        catch (IOException e) {
            throw new ServiceException("IO Exception. Client not connected!", e);
        }
        finally {
            try {
                socket.close();
                msgToClnt.close();
                msgFromClnt.close();
            }
            catch (IOException e) {
                ui.show("IOException. Socket not closed!");
            }
        }
    }
}
