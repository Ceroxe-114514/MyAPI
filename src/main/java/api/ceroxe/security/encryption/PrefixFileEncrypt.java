package api.ceroxe.security.encryption;

import api.ceroxe.management.bufferedFile.BufferedFile;

public class PrefixFileEncrypt {
    public static final int ENCRYPTION_MODE = 0;
    public static final int DECRYPT_MODE = 1;
    public static String defaultSuffix = null;
    public int currentMode = 3;
    private BufferedFile outPutFile;
    private BufferedFile targetFile;
    private int encryptLength = 5;
    private byte key = 1;
    private String suffix = ".encrypted";

    public PrefixFileEncrypt() {
        if (defaultSuffix != null) {
            this.suffix = defaultSuffix;
        }
    }

    public PrefixFileEncrypt(int mode) {
        if (mode != PrefixFileEncrypt.ENCRYPTION_MODE && mode != PrefixFileEncrypt.DECRYPT_MODE) {
            this.currentMode = PrefixFileEncrypt.ENCRYPTION_MODE;
        } else {
            this.currentMode = mode;
        }

        if (defaultSuffix != null) {
            this.suffix = defaultSuffix;
        }
    }

    public static void encrypt(BufferedFile file, byte key, int len) {
        PrefixFileEncrypt prefixFileEncrypt = new PrefixFileEncrypt(PrefixFileEncrypt.ENCRYPTION_MODE);
        prefixFileEncrypt.setEncryptLength(len);
        prefixFileEncrypt.setKey(key);
        prefixFileEncrypt.setTargetFile(file);
        prefixFileEncrypt.startEncryption();
        prefixFileEncrypt.getTargetFile().delete();
        prefixFileEncrypt.gc();
    }

    public static void decrypt(BufferedFile file, byte key, int len) {
        PrefixFileEncrypt prefixFileEncrypt = new PrefixFileEncrypt(SimpleFileEncrypt.DECRYPT_MODE);
        prefixFileEncrypt.setEncryptLength(len);
        prefixFileEncrypt.setKey(key);
        prefixFileEncrypt.setTargetFile(file);
        prefixFileEncrypt.startDecryption();
        prefixFileEncrypt.getTargetFile().delete();
        prefixFileEncrypt.gc();
    }

    public int getEncryptLength() {
        return encryptLength;
    }

    public void setEncryptLength(int encryptLength) {
        if (encryptLength > 0) {
            this.encryptLength = encryptLength;
        }
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

    public void startDecryption() {
        int len = this.encryptLength;
        targetFile.setOnCopyAction(new Runnable() {
            @Override
            public void run() {
                int bufferLen = targetFile.buffer.length;
                if (bufferLen >= len) {
                    for (int i = 0; i < len; i++) {
                        targetFile.buffer[i] = (byte) (targetFile.buffer[i] - key);
                    }
                } else {
                    for (int i = 0; i < bufferLen; i++) {
                        targetFile.buffer[i] = (byte) (targetFile.buffer[i] - key);
                    }
                }
            }
        });
        targetFile.copyAs(outPutFile);
    }

    public void startEncryption() {
        int len = this.encryptLength;
        targetFile.setOnCopyAction(new Runnable() {
            @Override
            public void run() {
                int bufferLen = targetFile.buffer.length;
                if (bufferLen >= len) {
                    for (int i = 0; i < len; i++) {
                        targetFile.buffer[i] = (byte) (targetFile.buffer[i] + key);
                    }
                } else {
                    for (int i = 0; i < bufferLen; i++) {
                        targetFile.buffer[i] = (byte) (targetFile.buffer[i] + key);
                    }
                }
            }
        });
        targetFile.copyAs(outPutFile);
    }

    public void gc() {
        System.gc();
    }
}
