package api.ceroxe.os.detect;

import api.ceroxe.os.windowsSystem.WindowsOperation;

public class OSDetector {
    private OSDetector() {
    }

    public static boolean isLinux() {
        String s = System.getProperty("os.name");
        return s.toLowerCase().contains("linux");
    }

    public static boolean isWindows() {
        return !OSDetector.isLinux();
    }

    public static int getWindowsVersion() {
        if (OSDetector.isWindows()) {
            String s = WindowsOperation.runGetString("cmd /c ver");
            return Integer.parseInt(s.split("\\.")[2]);
        } else {
            return -1;
        }
    }
}
