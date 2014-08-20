package service.model;

import java.util.Scanner;

/**
 * Created by Selvin
 * on 28.07.2014.
 */
public class DUIListener {
    private Scanner input = new Scanner(System.in);

    public DUIListener(final MyService ms, final UserInterface ui) {
        Thread dl = new Thread() {
            @Override
            public void run() {
                while(ms.isWorking()) {
                    switch (input.nextLine()) {
                        case "help":
                            ui.showCommands();
                            break;
                        case "stadd":
                            ui.show(ms.getStatistic("add"));
                            break;
                        case "stget":
                            ui.show(ms.getStatistic("get"));
                            break;
                        case "rstst":
                            ui.show("Resetting statistic of service.");
                            ms.resetTotalStatistic();
                            break;
                        case "stop":
                            ms.stop();
                            break;
                        default:
                            ui.show("Unknown command. Use \"help\" for list of commands.");
                    }

                }
            }
        };
        dl.setDaemon(true);
        dl.start();
    }
}
