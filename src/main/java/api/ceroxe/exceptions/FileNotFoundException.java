package api.ceroxe.exceptions;

import java.io.File;

public class FileNotFoundException extends Exception {

    public static boolean canPrint = true;

    private FileNotFoundException(String msg) {
        super(msg);
    }

    public static void throwException(File file) {
        try {
            throw new FileNotFoundException(file.getAbsolutePath() + " not found !");
        } catch (Exception e) {
            if (canPrint) {
                e.printStackTrace();
            }
        }
    }
}
