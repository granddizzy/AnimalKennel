package logs;

public interface logInterface {
    boolean getInitOk();

    void append(String msg);

    void clear();
}
