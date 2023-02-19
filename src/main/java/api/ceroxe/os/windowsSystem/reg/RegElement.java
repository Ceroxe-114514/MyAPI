package api.ceroxe.os.windowsSystem.reg;

import api.ceroxe.utils.StringUtils;
import api.ceroxe.os.windowsSystem.WindowsOperation;

import java.util.concurrent.CopyOnWriteArrayList;

public interface RegElement {
    String REG_BINARY = "REG_BINARY";
    String REG_DWORD = "REG_DWORD";
    String REG_DWORD_LITTLE_ENDIAN = "REG_DWORD_LITTLE_ENDIAN";
    String REG_DWORD_BIG_ENDIAN = "REG_DWORD_BIG_ENDIAN";
    String REG_EXPAND_SZ = "REG_EXPAND_SZ";
    String REG_LINK = "REG_LINK";
    String REG_MULTI_SZ = "REG_MULTI_SZ";
    String REG_NONE = "REG_NONE";
    String REG_QWORD = "REG_QWORD";
    String REG_QWORD_LITTLE_ENDIAN = "REG_QWORD_LITTLE_ENDIAN";
    String REG_SZ = "REG_SZ";

    static CopyOnWriteArrayList<RegElement> listElements(RegElement regElement) {
        CopyOnWriteArrayList<String> lines = WindowsOperation.runGetAsLine("reg query " + regElement.getPath());
        StringUtils.killSpaceLine(lines);
        if (lines.get(0).contains("错误: 系统找不到指定的注册表项或值")) {
            return null;
        } else {
            CopyOnWriteArrayList<RegElement> result = new CopyOnWriteArrayList<>();
            lines.remove(0);

            for (String line : lines) {
                if (line.contains("(") && line.contains(")")) {
                    continue;
                }
//                System.out.println(":::"+line);
                String[] info = line.split(" {4}");
//                for (String s : info) {
//                    System.out.println("s=" + s);
//                }
                if (line.startsWith("  ")) {
                    result.add(new RegValue(regElement.getPath(), info[1], info[2], info[3]));
                } else {
                    result.add(new RegKey(info[0]));
                }
            }
            return result;
        }
    }

    String getName();

    void setName(String name);

    String getPath();

    void setPath(String path);
}
