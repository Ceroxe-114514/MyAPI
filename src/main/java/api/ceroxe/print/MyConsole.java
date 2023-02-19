package api.ceroxe.print;

import java.util.Scanner;

public class MyConsole {
    public static boolean printInnerInfo = true;
    private static Scanner scan = new Scanner(System.in);
    private static boolean isOpen = false;
    private static String inputCommand = null;
    private static Runnable r = new Runnable() {
        @Override
        public void run() {

        }
    };

    private MyConsole() {
    }

    public static String getInputCommand() {
        return inputCommand;
    }

    public static void open() {
        scan = new Scanner(System.in);
        isOpen = true;
        if (MyConsole.printInnerInfo) {
            System.out.println("Ceroxe's console is now on !");
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (MyConsole.isOpen()) {
                    String prefix = Printer.getFormatLogString(">>>", Printer.color.ORANGE, Printer.style.BOLD);
                    inputCommand = MyConsole.scanInput(prefix);
                    r.run();
                }
            }
        }).start();
    }

    public static void close() {
        MyConsole.isOpen = false;
        scan.close();
    }

    public static String scanInput(String prefix) {
        System.out.print(prefix);
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    public static String scanInput() {
        return scanInput("");
    }

    public static void setOnAction(Runnable runnable) {
        if (runnable != null) {
            MyConsole.r = runnable;
        }
    }

    public static boolean isOpen() {
        return isOpen;
    }

    public void backSpace(int length) {
        StringBuilder s = new StringBuilder();
        String ba = "\b";
        for (int i = 1; i <= length; i++) {
            s.append(ba);
        }
        System.out.print(s);
    }

    public void backSpace(String str) {
        int length = str.length();
        StringBuilder s = new StringBuilder();
        String ba = "\b";
        for (int i = 1; i <= length; i++) {
            s.append(ba);
        }
        System.out.print(s);
    }
}
