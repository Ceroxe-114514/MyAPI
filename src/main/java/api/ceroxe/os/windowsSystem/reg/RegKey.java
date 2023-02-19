package api.ceroxe.os.windowsSystem.reg;

import api.ceroxe.os.windowsSystem.WindowsOperation;

import java.util.concurrent.CopyOnWriteArrayList;

public class RegKey implements RegElement {
    private String name = "";
    private String path = "";

    public RegKey(String path) {
        this.setPath(path);
        String[] strs = path.split("\\\\");
        name = strs[strs.length - 1];
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        String[] a = path.split("\\\\");
        StringBuilder s = new StringBuilder("");
        for (int i = 0; i < a.length - 1; i++) {
            String str = a[i];
            s.append(str);
            if (i != a.length - 2) {
                s.append("\\");
            }
        }
        this.path = s.toString();
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        String[] a = path.split("\\\\");
        this.name = a[a.length - 1];
        this.path = path;
    }

    public boolean delete() {
        if (!this.exists()) {
            return false;
        }
        StringBuilder command = new StringBuilder("reg delete ");
        command.append(path).append(" /f");
        WindowsOperation.runGetString(command.toString());
        return !this.exists();
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

    public boolean exists() {
        RegKey parentKey = this.getParentKey();
        CopyOnWriteArrayList<RegElement> c = RegElement.listElements(parentKey);
        for (RegElement regElement : c) {
            if (regElement.getName().equals(this.name) && regElement instanceof RegKey) {
                return true;
            }
        }
        return false;
    }

    public CopyOnWriteArrayList<RegElement> listElement() {
        return RegElement.listElements(this);
    }
}
