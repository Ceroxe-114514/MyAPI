package api.ceroxe.print;

public class Printer {
    private Printer() {

    }

    /**
     * @param colour  颜色代号：背景颜色代号(41-46)；前景色代号(31-36)
     * @param type    样式代号：0无；1加粗；3斜体；4下划线
     * @param content 要打印的内容
     */
    public static String getFormatLogString(String content, int colour, int type) {
        boolean hasType = type != 1 && type != 3 && type != 4;
        if (hasType) {
            return String.format("\033[%dm%s\033[0m", colour, content);
        } else {
            return String.format("\033[%d;%dm%s\033[0m", colour, type, content);
        }
    }

    public static void print(String content, int colour, int type) {
        String c = Printer.getFormatLogString(content, colour, type);
        System.out.println(c);
    }

    public static void printNoNewLine(String content, int colour, int type) {
        String c = Printer.getFormatLogString(content, colour, type);
        System.out.print(c);
    }

    public static class color {
        public static final int RED = 31;
        public static final int YELLOW = 32;
        public static final int ORANGE = 33;
        public static final int BLUE = 34;
        public static final int PURPLE = 35;
        public static final int GREEN = 36;
    }

    public static class style {
        public static final int NONE = 0;
        public static final int BOLD = 1;
        public static final int ITALIC = 3;
        public static final int UNDERSCORE = 4;
    }

}
