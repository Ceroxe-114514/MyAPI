package api.ceroxe.exceptions;

import java.io.File;

public class IncorrectTypeException extends Exception {
    public static boolean canPrint = true;

    private IncorrectTypeException(String msg) {
        super(msg);
    }

    public static void throwException(File incorrectTypeFile) {
        try {
            String currentType = "file";
            String expectedType = "directory";
            if (incorrectTypeFile.isDirectory()) {
                expectedType = "file";
                currentType = "directory";
            }
            throw new IncorrectTypeException("This type is " + currentType + " , it should be " + expectedType + " !");
        } catch (IncorrectTypeException e) {
            if (canPrint) {
                e.printStackTrace();
            }
        }


    }
}
