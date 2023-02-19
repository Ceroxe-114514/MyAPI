package api.ceroxe.config;

import api.ceroxe.exceptions.IllegalExpressionException;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CopyOnWriteArrayList;

public class LineConfigReader {

    private final File configFile;
    private CopyOnWriteArrayList<String> keys = new CopyOnWriteArrayList<>();
    private CopyOnWriteArrayList<String> values = new CopyOnWriteArrayList<>();

    public LineConfigReader(File configFile) {
        this.configFile = configFile;
    }

    public LineConfigReader(String configFilePath) {
        this.configFile = new File(configFilePath);
    }

    public void read() {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(configFile, StandardCharsets.UTF_8));


            String str;
            while ((str = bufferedReader.readLine()) != null) {
                if (str.contains("//")) {
                    int index = str.indexOf("//");
                    str = str.substring(0, index);
                    if (str.equals("") || this.justHas(str, " ")) {
                        continue;
                    }
                }
                String[] lineDatas = str.split("=");
                if (lineDatas.length < 2) {
                    IllegalExpressionException.throwException(str);
                    continue;
                }
                keys.add(lineDatas[0]);
                values.add(lineDatas[1]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean justHas(String str, String element) {
        char[] s = str.toCharArray();
        for (char a : s) {
            if (!String.valueOf(a).equals(element)) {
                return false;
            }
        }
        return true;
    }

    public String get(String key) {
        for (int i = 0; i < keys.size(); i++) {
            if (keys.get(i).equals(key)) {
                return values.get(i);
            }
        }
        return null;
    }

    public boolean set(String key, String element) {
        for (int i = 0; i < keys.size(); i++) {
            if (keys.get(i).equals(key)) {
                values.set(i, element);
                return true;
            }
        }
        return false;
    }

    public void rewrite() {
        try {
            if (!this.configFile.exists()) {
                this.configFile.createNewFile();
            } else {
                this.configFile.delete();
                this.configFile.createNewFile();
            }
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(configFile, StandardCharsets.UTF_8));
            for (int i = 0; i < keys.size(); i++) {
                bufferedWriter.write(keys.get(i));
                bufferedWriter.write("=");
                bufferedWriter.write(values.get(i));
                bufferedWriter.newLine();
            }
            bufferedWriter.flush();
            bufferedWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
