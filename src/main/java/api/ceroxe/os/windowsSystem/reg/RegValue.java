package api.ceroxe.os.windowsSystem.reg;

import api.ceroxe.utils.StringUtils;
import api.ceroxe.os.windowsSystem.WindowsOperation;

import java.util.concurrent.CopyOnWriteArrayList;

public class RegValue implements RegElement {

    private String path = "";
    private String name = "";
    private String value = "";
    private String type = REG_SZ;

    public RegValue(String parentPath, String name, String type, String value) {
        StringBuilder s = new StringBuilder(parentPath);
        this.setPath(s.append("\\").append(name).toString());
//        System.out.println(this.getPath());
        this.name = name;
        this.setType(type);
        this.setValue(value);
    }

    public RegValue(String path, String type, String value) {
        this.setPath(path);
        this.setType(type);
        this.setValue(value);
    }

    public RegKey getParentKey() {
        if (!this.path.equals("")) {
            String[] a = this.path.split("\\\\");
            StringBuilder s = new StringBuilder("");
            for (int i = 0; i < a.length - 1; i++) {
                s.append(a[i]);
                if (i != a.length - 2) {
                    s.append("\\");
                }
            }
            return new RegKey(s.toString());
        } else {
            return null;
        }
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        if (path != null) {
            this.path = path;
            String[] a = path.split("\\\\");
            this.name = a[a.length - 1];
        }
    }


    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        if (value != null) {
            this.value = value;
        }
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        String[] a = path.split("\\\\");
        StringBuilder s = new StringBuilder("");
        for (int i = 0; i < a.length - 1; i++) {
            String str = a[i];
            s.append(str).append("\\");
        }
        s.append(name);
        this.path = s.toString();
    }

    public boolean createNewRegValue() {
        StringBuilder command = new StringBuilder("reg add ");
        RegKey key = this.getParentKey();
        if (key == null) {
            return false;
        } else {
            command.append(StringUtils.addDoubleQuotes(key.getPath())).append(" ");
        }
        if (!this.getName().equals("")) {
            command.append("/v ").append(this.getName()).append(" ");
        }
        if (!(this.getType().equals(REG_SZ))) {
            command.append("/t ").append(this.getType()).append(" ");
        }
        command.append("/d ").append(this.getValue()).append(" /f");
        String str = WindowsOperation.runGetString(command.toString());
//        System.out.println(command.toString());
        return str.contains("成功") || str.toLowerCase().contains("succe");
    }

    public boolean delete() {
        if (!this.exists()) {
            return false;
        }
        RegKey parentKey = this.getParentKey();
        StringBuilder command = new StringBuilder("reg delete ");
        command.append(parentKey.getPath()).append(" /v ").append(name).append(" /f");
        WindowsOperation.runGetString(command.toString());
        return !this.exists();
        //reg delete HKEY_CURRENT_USER\Software\Microsoft\Windows\CurrentVersion\Explorer\TrayNotify /v PastIconsStream /f
        //reg delete HKEY_LOCAL_MACHINE\SOFTWARE\Microsoft\Windows\CurrentVersion\Run\111 /f
    }

    public boolean exists() {
        RegKey parentKey = this.getParentKey();
        CopyOnWriteArrayList<RegElement> c = RegElement.listElements(parentKey);
        for (RegElement regElement : c) {
            if (regElement.getName().equals(this.name) && regElement instanceof RegValue) {
                return true;
            }
        }
        return false;
    }
}
