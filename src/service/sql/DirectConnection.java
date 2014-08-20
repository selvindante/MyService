package service.sql;

import service.model.ServiceException;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by Selvin
 * on 06.07.2014.
 */
public class DirectConnection implements ConnectionFactory {
    private String url = "jdbc:postgresql://ec2-54-228-183-166.eu-west-1.compute.amazonaws.com:5432/dcevrddgbj46a6?ssl=true&sslfactory=org.postgresql.ssl.NonValidatingFactory";
    private String user = "xilitftxqetirc";
    private String password = "2xSK8NJ8krT59o0_bM_0TxiA2v";
    private String configPath = "config\\db_config.txt";
    private File config;
    private FileWriter fw;
    private FileReader fr;
    private BufferedWriter bw;
    private BufferedReader br;

    public DirectConnection() throws Exception {
        try {
            Class.forName("org.postgresql.Driver").newInstance();
        } catch (Exception e) {
            throw new ServiceException("Driver initialization exception!", e);
        }
        try {
            config = new File(configPath);
            File dir = config.getParentFile();
            if (!config.exists()) {
                dir.mkdir();
                config.createNewFile();
                fw = new FileWriter(config, true);
                bw = new BufferedWriter(fw);
                bw.write(url);
                bw.newLine();
                bw.write(user);
                bw.newLine();
                bw.write(password);
                bw.close();
                fw.close();
            }
            else {
                fr = new FileReader(configPath);
                br = new BufferedReader(fr);
                url = br.readLine();
                user = br.readLine();
                password = br.readLine();
                br.close();
                fr.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }
}
