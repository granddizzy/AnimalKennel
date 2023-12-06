package logs;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Log implements logInterface {
    private boolean initOk;
    private final String LogFilePath;

    public Log() {
        initOk = false;
        LogFilePath = "./log.txt";
        File file = new File(LogFilePath);

        if (!file.exists()) {
            try {
                boolean res = file.createNewFile();
                initOk = true;
            } catch (IOException ignored) {

            }
        } else {
            initOk = true;
        }
        clear();
    }

    @Override
    public void append(String msg) {
        try (FileWriter fw = new FileWriter(LogFilePath, true)) {
            fw.write(msg);
        } catch (IOException ignored) {

        }
    }

    @Override
    public void clear() {
        try (FileWriter fw = new FileWriter(LogFilePath, false)) {
            fw.write("");
        } catch (IOException ignored) {

        }
    }

    @Override
    public boolean getInitOk() {
        return initOk;
    }
}
