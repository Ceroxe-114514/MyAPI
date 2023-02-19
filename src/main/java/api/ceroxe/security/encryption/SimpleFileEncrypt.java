package api.ceroxe.security.encryption;

import api.ceroxe.management.bufferedFile.BufferedFile;

public class SimpleFileEncrypt {
    public static final int ENCRYPTION_MODE = 0;
    public static final int DECRYPT_MODE = 1;
    public static String defaultSuffix = null;
    public int currentMode = 3;
    private byte key = 1;
    private String suffix = ".encrypted";
    private BufferedFile targetFile;
    private BufferedFile outPutFile;

    public SimpleFileEncrypt(int mode) {
        currentMode = mode;
        if (defaultSuffix != null) {
            this.suffix = defaultSuffix;
        }
    }

    public SimpleFileEncrypt() {
        if (defaultSuffix != null) {
            this.suffix = defaultSuffix;
        }
    }

    public static void encrypt(BufferedFile file, byte key) {
        SimpleFileEncrypt simpleFileEncrypt = new SimpleFileEncrypt(SimpleFileEncrypt.ENCRYPTION_MODE);
        simpleFileEncrypt.setKey(key);
        simpleFileEncrypt.setTargetFile(file);
        simpleFileEncrypt.startEncryption();
        simpleFileEncrypt.getTargetFile().delete();
        simpleFileEncrypt.gc();
    }

    public static void decrypt(BufferedFile file, byte key) {
        SimpleFileEncrypt simpleFileEncrypt = new SimpleFileEncrypt(SimpleFileEncrypt.DECRYPT_MODE);
        simpleFileEncrypt.setKey(key);
        simpleFileEncrypt.setTargetFile(file);
        simpleFileEncrypt.startDecryption();
        simpleFileEncrypt.getTargetFile().delete();
        simpleFileEncrypt.gc();
    }

    public int getKey() {
        return key;
    }

    public void setKey(byte key) {
        this.key = key;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public BufferedFile getTargetFile() {
        return targetFile;
    }

    public void setTargetFile(BufferedFile targetFile) {
        this.targetFile = targetFile;
        if (currentMode == ENCRYPTION_MODE) {
            StringBuilder s = new StringBuilder(targetFile.getAbsolutePath());
            outPutFile = new BufferedFile(s.append(suffix).toString());
        }
    }

    public void setTargetFile(String path) {
        this.setTargetFile(new BufferedFile(path));
    }

    public BufferedFile getOutPutFile() {
        return outPutFile;
    }

    public void setOutPutFile(BufferedFile outPutFile) {
        this.outPutFile = outPutFile;
        if (currentMode == DECRYPT_MODE) {
            StringBuilder s = new StringBuilder(outPutFile.getAbsolutePath());
            targetFile = new BufferedFile(s.append(suffix).toString());
        }
    }

    public void setOutPutFile(String path) {
        this.setOutPutFile(new BufferedFile(path));
    }

    public void startEncryption() {
        targetFile.setOnCopyAction(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < targetFile.buffer.length; i++) {
                    targetFile.buffer[i] = (byte) (targetFile.buffer[i] + key);
                }
            }
        });
        targetFile.copyAs(outPutFile);
    }

    public void startDecryption() {
        targetFile.setOnCopyAction(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < targetFile.buffer.length; i++) {
                    targetFile.buffer[i] = (byte) (targetFile.buffer[i] - key);
                }
            }
        });
        targetFile.copyAs(outPutFile);
    }

    public void gc() {
        System.gc();
    }
}
