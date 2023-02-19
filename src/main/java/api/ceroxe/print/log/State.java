package api.ceroxe.print.log;

public class State {

    public static final int INFO = 0;
    public static final int WARNING = 1;
    public static final int ERROR = 2;
    public int type = State.INFO;
    public String subject = "null";
    public String content = "null";

    public State(int type, String subject, String content) {
        this.type = type;
        this.subject = subject;
        this.content = content;
    }
    // [2022.7.10 21:4:1] [type] [subject] content
}
