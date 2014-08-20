package service.model;

/**
 * Created by Selvin
 * on 28.07.2014.
 */
public class UserInterface {

    public UserInterface(MyService ms) {
        System.out.println("Service started.");
        showCommands();
    }

    public void show() {
        System.out.println();
    }

    public void show(String str) {
        System.out.println(str);
    }

    public void showCommands() {
        System.out.println( "Service commands:\n" +
                "\"help\" - show commands of service,\n" +
                "\"stadd\" - get statistic for getAmount() method,\n" +
                "\"stget\" - get statistic for addAmount() method,\n" +
                "\"rstst\" - reset statistic of service,\n" +
                "\"stop\" - stop service" +
                ".");
    }
}
