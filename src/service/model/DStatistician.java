package service.model;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Selvin
 * on 26.07.2014.
 */
public class DStatistician {

    private long addAmountTotal;
    private long getAmountTotal;

    public DStatistician(final MyService ms, final int period) {

        this.getAmountTotal = ms.getGetAmountTotal();
        this.addAmountTotal = ms.getAddAmountTotal();

        Thread dt = new Thread() {
            @Override
            public void run() {
                Timer timer = new Timer();
                timer.scheduleAtFixedRate(new TimerTask() {
                    @Override
                    public void run() {
                        ms.setGetAmountPeriod(ms.getGetAmountTotal() - getAmountTotal);
                        getAmountTotal = ms.getGetAmountTotal();
                        ms.setAddAmountPeriod(ms.getAddAmountTotal() - addAmountTotal);
                        addAmountTotal = ms.getAddAmountTotal();
                    }
                }, period, period);
            }
        };
        dt.setDaemon(true);
        dt.start();
    }
}
