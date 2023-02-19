package api.ceroxe.utils;

import java.util.Arrays;
import java.util.concurrent.CopyOnWriteArrayList;

public final class StringUtils {
    private StringUtils() {
    }

    public static String addDoubleQuotes(String str) {
        StringBuilder s = new StringBuilder("\"");
        s.append(str).append("\"");
        return s.toString();
    }

    public static CopyOnWriteArrayList<String> sortAsLine(String str) {
        String[] strs = str.split("\n");
        return new CopyOnWriteArrayList<>(Arrays.asList(strs));
    }

    public static void killSpaceLine(CopyOnWriteArrayList<String> lines) {
        for (String line : lines) {
            if (line.equals("") || StringUtils.justHas(line, " ")) {
                lines.remove(line);
            }
        }
    }

    public static String killSpaceLine(String str) {
        CopyOnWriteArrayList<String> c = StringUtils.sortAsLine(str);
        StringUtils.killSpaceLine(c);
        StringBuilder s = new StringBuilder("");
        for (String line : c) {
            s.append(line).append("\n");
        }
        return s.toString();
    }

    public static boolean justHas(String str, String element) {
        char[] s = str.toCharArray();
        for (char a : s) {
            if (!String.valueOf(a).equals(element)) {
                return false;
            }
        }
        return true;
    }
}
