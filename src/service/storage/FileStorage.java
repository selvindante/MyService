package service.storage;

import java.io.*;

/**
 * Created by Selvin
 * on 31.07.2014.
 */
public class FileStorage {
    private String tempDbPath = "temp_db\\db.txt";
    private File tempDb;
    private FileWriter fw;
    private FileReader fr;
    private BufferedWriter writer;
    private BufferedReader reader;

    public FileStorage() {
        try {
            tempDb = new File(tempDbPath);
            File dir = tempDb.getParentFile();
            if (!tempDb.exists()) {
                dir.mkdir();
            }
            tempDb.createNewFile();
            fw = new FileWriter(tempDb, true);
            writer = new BufferedWriter(fw);
            fr = new FileReader(tempDb);
            reader = new BufferedReader(fr);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized void add(Integer id, Long value) {
        try {
            String str;
            while((str = reader.readLine()) != null) {
                if(str.substring(0, str.indexOf("@")).equals(String.valueOf(id))) {

                }
            }
            writer.write(String.valueOf(id) + "@" + String.valueOf(value));
            writer.newLine();
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized Long get(Integer id) {
        try {
            String str;
            while((str = reader.readLine()) != null) {
                if(str.substring(0, str.indexOf("@")).equals(String.valueOf(id))) {
                    return Long.valueOf(str.substring(str.lastIndexOf("@") + 1));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0L;
    }

    public void close() {
        try {
            writer.close();
            reader.close();
            fw.close();
            fr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
