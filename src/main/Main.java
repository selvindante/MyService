package main;

import service.model.MyService;
import service.sql.Sql;
import service.storage.FileStorage;
import service.storage.MapStorage;
import service.storage.SqlStorage;

/**
 * Created by Selvin
 * on 23.07.2014.
 */
public class Main {
    public static void main(String[] args) {

        new Thread() {
            @Override
            public void run() {
                MyService ms = new MyService();
                while(ms.isWorking()) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }
}
