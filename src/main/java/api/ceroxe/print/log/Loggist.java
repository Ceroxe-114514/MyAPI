package api.ceroxe.print.log;

import api.ceroxe.management.bufferedFile.BufferedFile;
import api.ceroxe.os.detect.OSDetector;
import api.ceroxe.print.Printer;
import api.ceroxe.time.Time;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.concurrent.CopyOnWriteArrayList;

public class Loggist extends Object {
    public static int WINDOWS_VERSION = -1;
    private final BufferedFile logFile;
    private final boolean IS_LINUX_OS = OSDetector.isLinux();
    private BufferedWriter bufferedWriter;
    private boolean isOpenChannel = false;

    public Loggist(File logFile) {
        Loggist.WINDOWS_VERSION = OSDetector.getWindowsVersion();
        this.logFile = BufferedFile.load(logFile);
        if (!logFile.exists()) {
            try {
                logFile.getParentFile().mkdirs();
                logFile.createNewFile();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public BufferedFile getLogFile() {
        return logFile;
    }

    public void openWriteChannel() {
        try {
            this.bufferedWriter = new BufferedWriter(new FileWriter(logFile));
            this.isOpenChannel = true;
        } catch (Exception e) {
            e.printStackTrace();
            this.isOpenChannel = false;
        }
    }

    public void closeWriteChannel() {
        if (bufferedWriter != null) {
            try {
                this.bufferedWriter.close();
                this.bufferedWriter = null;
                this.isOpenChannel = false;
            } catch (Exception e) {
                e.printStackTrace();
                this.bufferedWriter = null;
                this.isOpenChannel = false;
            }
        }

    }

    public void write(String str) {
        if (isOpenChannel) {
            try {
                bufferedWriter.write(str);
                bufferedWriter.newLine();
                bufferedWriter.flush();
            } catch (Exception e) {
                e.printStackTrace();
                this.isOpenChannel = false;
            }
        }
    }

    public void say(State state) {
        if (WINDOWS_VERSION == -1) {// is linux or mac
            System.out.println(this.getLogString(state));
            this.write(this.getNoColString(state));
        } else if (WINDOWS_VERSION >= 22000) {// is Windows 11
            System.out.println(this.getLogString(state));
            this.write(this.getNoColString(state));
        } else {// is Windows 10 or less
            System.out.println(this.getNoColString(state));
            this.write(this.getNoColString(state));
        }

    }

    public void sayNoNewLine(State state) {
        if (WINDOWS_VERSION == -1) {// is linux or mac
            System.out.print(this.getLogString(state));
            this.write(this.getNoColString(state));
        } else if (WINDOWS_VERSION >= 22000) {// is Windows 11
            System.out.print(this.getLogString(state));
            this.write(this.getNoColString(state));
        } else {// is Windows 10 or less
            System.out.print(this.getNoColString(state));
            this.write(this.getNoColString(state));
        }
    }


    public String getLogString(State state) {
        // [2022.7.10 21:4:1] [type] [subject] content
        CopyOnWriteArrayList<String> time = Time.getCurrentTimeAsStringList();
        StringBuilder result = new StringBuilder(Printer.getFormatLogString("[", Printer.color.PURPLE, Printer.style.NONE));
        result.append(Printer.getFormatLogString(time.get(0), Printer.color.YELLOW, Printer.style.NONE));
        result.append(" ");
        result.append(Printer.getFormatLogString(time.get(1), Printer.color.YELLOW, Printer.style.NONE));
        result.append(Printer.getFormatLogString("]", Printer.color.PURPLE, Printer.style.NONE));
        result.append("  ");

        result.append(Printer.getFormatLogString("[", Printer.color.PURPLE, Printer.style.NONE));
        if (state.type == State.ERROR) {
            result.append(Printer.getFormatLogString("ERROR", Printer.color.RED, Printer.style.NONE));
        } else if (state.type == State.INFO) {
            result.append(Printer.getFormatLogString("INFO", Printer.color.GREEN, Printer.style.NONE));
        } else {
            result.append(Printer.getFormatLogString("WARNING", Printer.color.YELLOW, Printer.style.NONE));
        }
        result.append(Printer.getFormatLogString("]", Printer.color.PURPLE, Printer.style.NONE));

        result.append(" ");
        result.append(Printer.getFormatLogString("[", Printer.color.PURPLE, Printer.style.NONE));
        result.append(Printer.getFormatLogString(state.subject, Printer.color.ORANGE, Printer.style.NONE));
        result.append(Printer.getFormatLogString("]", Printer.color.PURPLE, Printer.style.NONE));

        result.append(" ");
        result.append(state.content);
        return result.toString();
    }

    public String getNoColString(State state) {
        // [2022.7.10 21:4:1] [type] [subject] content
        CopyOnWriteArrayList<String> time = Time.getCurrentTimeAsStringList();
        StringBuilder result = new StringBuilder("[");
        result.append(time.get(0));
        result.append(" ");
        result.append(time.get(1));
        result.append("]");
        result.append("  ");

        result.append("[");
        if (state.type == State.ERROR) {
            result.append("ERROR");
        } else if (state.type == State.INFO) {
            result.append("INFO");
        } else {
            result.append("WARNING");
        }
        result.append("]");

        result.append(" ");
        result.append("[");
        result.append(state.subject);
        result.append("]");

        result.append(" ");
        result.append(state.content);
        return result.toString();
    }


    public boolean isOpenChannel() {
        return isOpenChannel;
    }

    protected void finalize() {
        this.closeWriteChannel();
    }
}
