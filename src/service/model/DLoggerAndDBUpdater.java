package service.model;

import java.io.*;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;


/**
 * Created by Selvin
 * on 26.07.2014.
 */
public class DLoggerAndDBUpdater {
    private Logger logger = Logger.getLogger(DLoggerAndDBUpdater.class.getName());
    private FileHandler handler;
    private String logPath = "logs\\log.txt";
    private File log;

    public DLoggerAndDBUpdater(final MyService ms, final int loggingPeriod, final int updatingDBPeriod) {
        new DStatistician(ms, loggingPeriod);
        log = new File(logPath);
        File dir = log.getParentFile();
        if (!log.exists()) {
            dir.mkdir();
        }
        Thread dt = new Thread() {
            @Override
            public void run() {
                try {
                    handler = new FileHandler(logPath, 1048576, 1, true);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                handler.setLevel(Level.ALL);
                handler.setFormatter(new SimpleFormatter());
                logger.addHandler(handler);
                Timer loggingTimer = new Timer();
                loggingTimer.scheduleAtFixedRate(new TimerTask() {
                    @Override
                    public void run() {
                        logger.info("\n" + ms.getStatistic("get") + "\n" + ms.getStatistic("add"));
                    }
                }, loggingPeriod, loggingPeriod);
                Timer updatingDBTimer = new Timer();
                updatingDBTimer.scheduleAtFixedRate(new TimerTask() {
                    @Override
                    public void run() {
                        ms.updateDataBase();
                    }
                }, updatingDBPeriod, updatingDBPeriod);
            }
        };
        dt.setDaemon(true);
        dt.start();
    }
}
