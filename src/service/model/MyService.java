package service.model;

import service.storage.MapStorage;
import service.storage.SqlStorage;
import java.io.IOException;

/**
 * Created by Selvin
 * on 23.07.2014.
 */
public class MyService implements AccountService {
    private boolean isWorking = true;
    private long addAmountTotal;
    private long addAmountPeriod = 0;
    private long getAmountTotal;
    private long getAmountPeriod = 0;
    private int loggingPeriod = 1*60*1000;
    private int updatingDBPeriod = 10*60*1000;
    private SqlStorage sqlSt = new SqlStorage();
    private MapStorage mst;
    private UserInterface ui;

    public MyService() {
        addAmountTotal = sqlSt.getAddAmountTotal();
        getAmountTotal = sqlSt.getGetAmountTotal();
        ui = new UserInterface(this);
        ui.show("Loading database in memory...");
        mst = new MapStorage(sqlSt.getAll());
        ui.show("Database loaded!");
        new DLoggerAndDBUpdater(this, loggingPeriod, updatingDBPeriod);
        new DUIListener(this, ui);
        try {
            new DClientListener(this, ui);
        } catch (IOException e) {
            throw new ServiceException("IOException. Cannot create client listener!", e);
        }
    }

    @Override
    public synchronized Long getAmount(Integer id) {
        getAmountTotal++;
        return mst.get(id);
    }

    @Override
    public synchronized void addAmount(Integer id, Long value) {
        mst.add(id, value);
        addAmountTotal++;
    }

    public String getStatistic(String str) {
        switch (str) {
            case "get":
                return "Statistic for \"getAmount()\" method: " +
                        "total requests - " + getAmountTotal + "; " +
                        "requests in last period of " + loggingPeriod/1000 + " seconds - " + getAmountPeriod + ";";
            case "add":
                return "Statistic for \"addAmount()\" method: " +
                        "total requests - " + addAmountTotal + "; " +
                        "requests in last period of " + loggingPeriod/1000 + " seconds - " + addAmountPeriod + ";";
            default:
                return "Wrong input parameter. Must be \"get\" or \"add\"";
        }
    }

    public void resetTotalStatistic() {
        addAmountTotal = 0;
        getAmountTotal = 0;
        addAmountPeriod = 0;
        getAmountPeriod = 0;
        ui.show(getStatistic("get") + "\n" + getStatistic("add"));
        ui.show("Saving statistics in the database...");
        sqlSt.updateSt(getAmountTotal, addAmountTotal);
    }

    public void updateDataBase() {
        ui.show("Updating database...");
        sqlSt.updateAll(mst.getAll());
        ui.show("Database updated!");
    }

    public boolean isWorking() {
        return isWorking;
    }

    public void stop(){
        ui.show("Closing service...");
        updateDataBase();
        ui.show("Updating statistics in the database...");
        sqlSt.updateSt(getAmountTotal, addAmountTotal);
        ui.show("Statistic updated!");
        ui.show("Service closed.");
        isWorking = false;
    }

    public long getAddAmountTotal() {
        return addAmountTotal;
    }

    public long getGetAmountTotal() {
        return getAmountTotal;
    }

    public void setAddAmountPeriod(long addAmountPeriod) {
        this.addAmountPeriod = addAmountPeriod;
    }

    public void setGetAmountPeriod(long getAmountPeriod) {
        this.getAmountPeriod = getAmountPeriod;
    }
}
