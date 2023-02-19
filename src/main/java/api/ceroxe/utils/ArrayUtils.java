package api.ceroxe.utils;

import api.ceroxe.management.bufferedFile.BufferedFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.CopyOnWriteArrayList;

public final class ArrayUtils {
    public static final int IGNORE_CASE = 0;
    public static final int WHOLE_WORD_MATCH = 1;
    public static ArrayList<String> STRING_LIST_RESULT = null;

    private ArrayUtils() {
    }

    public static CopyOnWriteArrayList<String> stringArrayToList(String[] arr) {
        CopyOnWriteArrayList<String> result = new CopyOnWriteArrayList<>();
        for (String s : arr) {
            result.add(s);
        }
        return result;
    }

    public static CopyOnWriteArrayList findStrings(CopyOnWriteArrayList<String> arrayList, String[] prefixes, int comparePolicy) {

        CopyOnWriteArrayList<String> result = new CopyOnWriteArrayList<>();
        for (String prefix : prefixes) {
            prefix = prefix.toLowerCase();
            if (comparePolicy >= WHOLE_WORD_MATCH) {

                for (String str : arrayList) {
                    if (str.contains(prefix)) {
                        result.add(str);
                    }
                }

            } else {

                for (int i = 0; i < arrayList.size(); i++) {
                    if (arrayList.get(i).toLowerCase().contains(prefix)) {
                        result.add(arrayList.get(i));
                    }
                }
            }
        }

        return result;
    }

    public static CopyOnWriteArrayList<BufferedFile> findFiles(CopyOnWriteArrayList<BufferedFile> arrayList, String[] prefixes, int comparePathPolicy) {
        CopyOnWriteArrayList<BufferedFile> result = new CopyOnWriteArrayList<>();
        if (comparePathPolicy >= WHOLE_WORD_MATCH) {
            for (String prefix : prefixes) {
                for (BufferedFile file : arrayList) {
                    if (file.getAbsolutePath().contains(prefix)) {
                        result.add(file);
                    }
                }
            }

        } else {
            for (String prefix : prefixes) {
                prefix = prefix.toLowerCase();
                for (BufferedFile file : arrayList) {
                    if (file.getAbsolutePath().toLowerCase().contains(prefix)) {
                        result.add(file);
                    }
                }
            }
        }
        return result;
    }

    public static CopyOnWriteArrayList<String> allToUpperCase(CopyOnWriteArrayList<String> arrayList) {
        arrayList.replaceAll(s -> s.toUpperCase(Locale.ROOT));
        return arrayList;
    }

    public static CopyOnWriteArrayList<String> allToLowerCase(CopyOnWriteArrayList<String> arrayList) {
        for (int i = 0; i < arrayList.size(); i++) {
            arrayList.set(i, arrayList.get(i).toLowerCase(Locale.ROOT));
        }
        return arrayList;
    }

    public static List arrToList(Object[] objectArr) {
        List result = new CopyOnWriteArrayList();
        for (Object o : objectArr) {
            result.add(o);
        }
        return result;
    }

    public static String[] stringListToStringArray(CopyOnWriteArrayList<String> stringArrayList) {
        String[] result = new String[stringArrayList.size()];
        for (int i = 0; i < result.length; i++) {
            result[i] = stringArrayList.get(i);
        }
        return result;
    }

    public static void excludeFiles(CopyOnWriteArrayList<BufferedFile> fileArrayList, String[] pathPrefixes) {
        CopyOnWriteArrayList<BufferedFile> a = new CopyOnWriteArrayList<>();
        for (String pathPrefix : pathPrefixes) {
            for (BufferedFile file : fileArrayList) {
                boolean b = file.getAbsolutePath().toUpperCase(Locale.ROOT).contains(pathPrefix.toUpperCase(Locale.ROOT));

                if (b) {
                    a.add(file);
                }
            }
        }
//        Iterator iterator=fileArrayList.iterator();
        for (File file : a) {
            System.out.println(file.getAbsolutePath());
            fileArrayList.remove(file);
        }
    }

    public static CopyOnWriteArrayList<BufferedFile> fileArrToFileList(File[] files) {
        CopyOnWriteArrayList<BufferedFile> bufferedFiles = new CopyOnWriteArrayList<>();
        for (File file : files) {
            bufferedFiles.add(BufferedFile.load(file));
        }
        return bufferedFiles;
    }

    public static CopyOnWriteArrayList<BufferedFile> fileListToBuffFileList(CopyOnWriteArrayList<BufferedFile> fileList) {
        CopyOnWriteArrayList<BufferedFile> result = new CopyOnWriteArrayList<>();
        for (File file : fileList) {
            result.add(BufferedFile.load(file));
        }
        return result;
    }

    public static void killUnAccessibleItem(CopyOnWriteArrayList<BufferedFile> fileList) {
        for (BufferedFile bufferedFile : fileList) {
            if (!bufferedFile.canAccess()) {
                fileList.remove(bufferedFile);
            }
        }
    }

    public static void killExcludeItem(CopyOnWriteArrayList<String> stringArrayList, String[] prefixes) {
        for (String prefix : prefixes) {
            stringArrayList.removeIf(str -> str.contains(prefix));
        }
    }

    public static void killExcludeItem(CopyOnWriteArrayList copyOnWriteArrayList, Object[] objects) {
        for (Object object : objects) {
            copyOnWriteArrayList.removeIf(obj -> obj == object);
        }
    }

}
