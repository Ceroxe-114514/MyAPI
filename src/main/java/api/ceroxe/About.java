package api.ceroxe;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;

public class About implements Serializable {
    public static void main(String[] args) {

        JFrame jFrame = new JFrame("About");
        jFrame.setSize(new Dimension(400, 300));
        jFrame.setLocation(new Point(100, 100));
        Label label = new Label("This API version is 7.0,power by ceroxe");
        label.setAlignment(Label.CENTER);
        label.setFont(new Font("微软雅黑", Font.PLAIN, 20));
        jFrame.add(label);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setVisible(true);
    }

}
