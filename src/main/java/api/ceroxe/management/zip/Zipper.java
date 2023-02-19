package api.ceroxe.management.zip;

import api.ceroxe.management.bufferedFile.BufferedFile;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;


public class Zipper {
    public boolean autoChangeTarget = true;
    private File inputFile;
    private File outputFile;

    public Zipper(File inputFile, File outputFile) {
        this.inputFile = inputFile;
        this.outputFile = outputFile;
    }

    public Zipper(String inputFile, String outputFile) {
        this.inputFile = new File(inputFile);
        this.outputFile = new File(outputFile);
    }

    public void compress() {
        try {
            //创建zip输出流
            ZipOutputStream out = new ZipOutputStream(new FileOutputStream(outputFile));
            //创建缓冲输出流
            BufferedOutputStream bos = new BufferedOutputStream(out);
            this.startCompress(out, bos, inputFile, null);
            bos.close();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void startCompress(ZipOutputStream out, BufferedOutputStream bos, File input, String name) throws IOException {
        if (name == null) {
            name = input.getName();
        }
        //如果路径为目录（文件夹）
        if (input.isDirectory()) {
            //取出文件夹中的文件（或子文件夹）
            File[] flist = input.listFiles();

            if (flist.length == 0) {//如果文件夹为空，则只需在目的地zip文件中写入一个目录进入
                out.putNextEntry(new ZipEntry(name + "/"));
            } else {//如果文件夹不为空，则递归调用compress，文件夹中的每一个文件（或文件夹）进行压缩
                for (int i = 0; i < flist.length; i++) {
                    this.startCompress(out, bos, flist[i], name + "/" + flist[i].getName());
                }
            }
        } else {//如果不是目录（文件夹），即为文件，则先写入目录进入点，之后将文件写入zip文件中
            out.putNextEntry(new ZipEntry(name));
            if (input.length() != 0) {
                FileInputStream fos = new FileInputStream(input);
                BufferedInputStream bis = new BufferedInputStream(fos);
                int len;
                //将源文件写入到zip文件中
                byte[] buf = new byte[this.getOptSpeed(input)];
                while ((len = bis.read(buf)) != -1) {
                    bos.write(buf, 0, len);
                }
                bis.close();
                fos.close();
            }

        }
    }

    public void uncompress() {
        if (!outputFile.exists()) {
            outputFile.mkdirs();
        }
        if (outputFile.isDirectory()) {
            try {
                this.startUncompress();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            if (this.autoChangeTarget) {
                this.toUncompressTarget();
                try {
                    this.startUncompress();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            } else {
                throw new IllegalStateException("Output File must be a directory !");
            }
        }
    }

    public void toUncompressTarget() {
//        Zipper zipCom = new Zipper("D:\\1", "D:\\2.zip");
        inputFile = outputFile;
        outputFile = BufferedFile.setSuffix(inputFile, "");
        outputFile.mkdirs();
    }

    private void startUncompress() throws Exception {
        // 判断源文件是否存在
        if (!inputFile.exists()) {
            StringBuilder s = new StringBuilder(inputFile.getAbsolutePath());
            throw new Exception(s.append(" not found !").toString());
        }
        //开始解压
        //构建解压输入流
        ZipInputStream zIn = new ZipInputStream(new FileInputStream(inputFile));
        ZipEntry entry = null;
        File file = null;
        while ((entry = zIn.getNextEntry()) != null) {
            if (!entry.isDirectory()) {
                file = new File(outputFile, entry.getName());
                if (!file.exists()) {
                    new File(file.getParent()).mkdirs();//创建此文件的上级目录
                }
                OutputStream out = new FileOutputStream(file);
                BufferedOutputStream bos = new BufferedOutputStream(out);
                int len = -1;
                byte[] buf = new byte[this.getOptSpeed(inputFile)];
                while ((len = zIn.read(buf)) != -1) {
                    bos.write(buf, 0, len);
                }
                // 关流顺序，先打开的后关闭
                bos.close();
                out.close();
            }
        }
    }

    private int getOptSpeed(File file) {
        return Math.abs((int) file.length());
    }
}