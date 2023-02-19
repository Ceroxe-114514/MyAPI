package api.ceroxe.net;

import com.trilead.ssh2.Connection;
import com.trilead.ssh2.SCPClient;
import com.trilead.ssh2.Session;
import com.trilead.ssh2.StreamGobbler;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class SSH {
    private Connection con;
    private Session session;

    /**
     * 登录用户名
     */
    private String username;
    /**
     * 登录密码
     */
    private String password;
    /**
     * 私钥
     */
    private String privateKey;
    /**
     * 服务器地址IP地址
     */
    private String host;
    /**
     * 端口
     */
    private int port;

    public SSH(String username, String password, String host, int port, String privateKey) {
        this.username = username;
        this.password = password;
        this.host = host;
        this.port = port;
        this.privateKey = privateKey;
    }

    public SSH(String username, String password, String host, int port) {
        this.username = username;
        this.password = password;
        this.host = host;
        this.port = port;
    }

    public SSH() {
    }


    /**
     * 私钥登录
     *
     * @throws IOException
     */
    public void login() throws IOException {
        con = new Connection(host, port);
        try {
            con.connect();
            boolean isAuthed = con.authenticateWithPublicKey(username, new File(privateKey), password);
            if (isAuthed) {
                System.out.println("192.168.149.* login success ------");
            } else {
                throw new IOException("Authentication Failed.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * @throws
     * @Title: login
     * @Description: 用户名密码方式  远程登录linux服务器
     * @return: Boolean
     */
    public Boolean loginPw() {
        boolean flag = false;
        try {
            con = new Connection(host, port);
            con.connect();// 连接
            flag = con.authenticateWithPassword(username, password);// 认证
            if (flag) {
                System.out.println("认证成功！");
            } else {
                System.out.println("认证失败！");
                con.close();
            }
        } catch (IOException e) {
            System.out.println("连接失败--" + e);
        }
        return flag;
    }


    /**
     * 本地文件上传到服务器
     *
     * @param localFile             本地文件
     * @param remoteTargetDirectory 要上传服务器上的路径
     * @throws IOException
     */
    public void upload(String localFile, String remoteTargetDirectory) {
        //boolean isAuthed = con.authenticateWithPassword("root", "SsUTUJrpfA6RDBZnX");
        //【2-1】建立SCP客户端，执行封装的方法
        try {
            SCPClient scpClient = con.createSCPClient();
            //从服务器获取文件
            //scpClient.get("/mnt/test.txt", "F:/yangyc/");
            //将本地文件上传到服务器
            //scpClient.put("H:/360-phone/20201215/zpp.txt", "/home/yw/zpp/");
            scpClient.put(localFile, remoteTargetDirectory);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    /**
     * 把 文件下载到本地
     *
     * @param localFile             本地要保持的路径
     * @param remoteTargetDirectory 服务器上文件的根目录
     * @throws IOException
     */
    public void down(String remoteTargetDirectory, String localFile) {
        //boolean isAuthed = con.authenticateWithPassword("root", "SsUTUJrpfA6RDBZnX");
        //【2-1】建立SCP客户端，执行封装的方法
        File file = new File(localFile);
        if (!file.exists()) {//如果文件夹不存在
            file.mkdir();//创建文件夹
        }
        try {
            SCPClient scpClient = con.createSCPClient();
            //从服务器获取文件
            scpClient.get(remoteTargetDirectory, localFile);
            //将本地文件上传到服务器
            //scpClient.put("H:/360-phone/20201215/zpp.txt", "/home/yw/zpp/");
            //scpClient.put(localFile,remoteTargetDirectory);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    /**
     * 关闭连接
     */
    public void logout() {
        if (con != null) {
            con.close();
        }
        if (session != null) {
            session.close();
        }
        System.out.println("ssh 关闭连接 success----");
    }

    /**
     * 创建文件夹
     *
     * @param folder 要创建的文件夹
     */
    public void mkdirFolder(String folder) {
        try {
            String cmd = "mkdir " + folder;
            System.out.println("创建文件夹--" + cmd);
            session = con.openSession();//打开一个会话
            session.execCommand(cmd);//执行命令
        } catch (Exception e) {
            System.out.println("创建文件夹失败--" + e);
        }
    }


    /**
     * 授权文件夹权限
     *
     * @param folder 要授权的文件夹
     */
    public void chownFolder(String folder) {
        try {
            String cmd = "chown -R yw " + folder;
            session = con.openSession();//打开一个会话
            session.execCommand(cmd);//执行命令
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取服务器上文件夹下的所有csv文件名
     *
     * @param remoteTargetDirectory 服务器的文件夹目录
     * @return
     */
    public List<String> getListFiles(String remoteTargetDirectory) {
        try {
            List<String> fileNameList = new ArrayList<>();
            String cmd = "ls " + remoteTargetDirectory;
            session = con.openSession();
            session.execCommand(cmd);
            InputStream stdout = new StreamGobbler(session.getStdout());
            BufferedReader br = new BufferedReader(new InputStreamReader(stdout));
            while (true) {
                String fileName = br.readLine();
                if (fileName == null)
                    break;
                //只取csv文件
                if ("csv".equals(fileName.split("\\.")[1])) {
                    fileNameList.add(fileName);
                    System.out.println(remoteTargetDirectory + "获取到的csv文件名称----：" + fileName);
                }
            }
            return fileNameList;
        } catch (IOException e) {
            System.out.println("## get fileName filed----" + e);
            return null;
        }
    }


}
