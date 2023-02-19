package api.ceroxe.javafx;

import java.awt.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class ScreenLocation {
    public static CopyOnWriteArrayList<Integer> getCenterXY() {
        Toolkit kit = Toolkit.getDefaultToolkit();    // 定义工具包
        Dimension screenSize = kit.getScreenSize();   // 获取屏幕的尺寸
        int screenWidth = screenSize.width / 2;         // 获取屏幕的宽
        int screenHeight = screenSize.height / 2;       // 获取屏幕的高
        CopyOnWriteArrayList<Integer> result = new CopyOnWriteArrayList<>();
        result.add(screenWidth);
        result.add(screenHeight);
        return result;
    }

    public static CopyOnWriteArrayList<Integer> getStageCenterPoint(int width, int height) {
        CopyOnWriteArrayList<Integer> result = new CopyOnWriteArrayList<>();
        CopyOnWriteArrayList<Integer> copyOnWriteArrayList = ScreenLocation.getCenterXY();
        result.add(copyOnWriteArrayList.get(0) - width / 2);
        result.add(copyOnWriteArrayList.get(1) - height / 2);
        return result;
    }
}
