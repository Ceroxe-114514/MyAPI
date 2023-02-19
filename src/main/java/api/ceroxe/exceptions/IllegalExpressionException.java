package api.ceroxe.exceptions;

public class IllegalExpressionException extends Exception {
    public static boolean canPrint = true;

    private IllegalExpressionException(String msg) {
        super(msg);
    }

    public static void throwException(String str) {
        try {
            StringBuilder s = new StringBuilder("");
            throw new IllegalExpressionException(s.append("Expression \"").append(str).append("\"").append(" is illegal !").toString());
        } catch (IllegalExpressionException e) {
            if (canPrint) {
                e.printStackTrace();
            }
        }


    }
}
