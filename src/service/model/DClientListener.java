package service.model;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Selvin
 * on 30.07.2014.
 */
public class DClientListener {
    private int port = 1234;
    private String configPath = "config\\port_config.txt";
    private File config;
    private FileWriter fw;
    private FileReader fr;
    private BufferedWriter bw;
    private BufferedReader br;

    public DClientListener(final MyService ms, final UserInterface ui) throws IOException {
        try {
            config = new File(configPath);
            File dir = config.getParentFile();
            if (!config.exists()) {
                dir.mkdir();
                config.createNewFile();
                fw = new FileWriter(config, true);
                bw = new BufferedWriter(fw);
                bw.write(String.valueOf(port));
                bw.close();
                fw.close();
            }
            else {
                fr = new FileReader(configPath);
                br = new BufferedReader(fr);
                port = Integer.parseInt(br.readLine());
                br.close();
                fr.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        Thread dcl = new Thread() {
            @Override
            public void run() {
                ui.show("Waiting for clients...");
                try (ServerSocket server = new ServerSocket(port)) {
                    while (true) {
                        Socket socket = server.accept();
                        try {
                            new ClientThread(socket, ms, ui);
                        } catch (Exception e) {
                            socket.close();
                            throw new ServiceException("Client thread not created.", e);
                        }
                    }
                } catch (IOException e) {
                    throw new ServiceException("IO Exception!", e);
                } finally {
                    ui.show("Service stopped.");
                }
            }
        };
        dcl.setDaemon(true);
        dcl.start();
    }
}
