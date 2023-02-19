package api.ceroxe.javafx.msger;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.net.URL;

public class Msger {
    public static final int ERROR = 0;
    public static final int INFO = 1;
    private final int YPoint = 170;
    private final FXMLLoader fxmlLoader = new FXMLLoader();
    private Stage stage;
    private Scene scene;
    private Pane root;
    private int XPoint = 340;
    private ErrorMsgerController errorMsgerController;
    private InfoMsgerController infoMsgerController;

    public Msger(int type) {

        try {

            this.initFXML(type);
            scene = new Scene(root);
            stage = new Stage();
            stage.setScene(scene);
            stage.setResizable(false);
//            ArrayList<Integer> a= Location.getCenterXY();
//            int centerX=a.get(0);
//            int centerY=a.get(1);
//            stage.setX(centerX);
//            stage.setY(centerY);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected static MsgerButton[] lastArray(MsgerButton[] msgerButtons) {
        MsgerButton[] result = new MsgerButton[msgerButtons.length];
        for (int i = 0; i < result.length; i++) {
            result[i] = msgerButtons[msgerButtons.length - i - 1];
        }
        return result;
    }

    public static void shouContent(String content, int type) {

        if (type != Msger.ERROR && type != Msger.INFO) {
            type = Msger.INFO;
        }
        Msger msger = new Msger(type);
        msger.setDownText(content);
        MsgerButton yes = new MsgerButton("确定");
        MsgerButton no = new MsgerButton("取消");
        yes.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                msger.close();
            }
        });
        no.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                msger.close();
            }
        });
        if (type == Msger.INFO) {
            msger.setButtons(new MsgerButton[]{yes});
        } else {
            msger.setButtons(new MsgerButton[]{yes, no});
        }

        msger.show();

    }

    public void setButtons(MsgerButton[] msgerButtons) {
        int buttonNum = 0;
        msgerButtons = Msger.lastArray(msgerButtons);
        for (int i = 0; i < msgerButtons.length; i++) {
            MsgerButton msgerButton = msgerButtons[i];
            XPoint = (int) (XPoint - msgerButton.getButton().getPrefWidth() * buttonNum - 80);
            msgerButton.setLayoutX(XPoint);
            msgerButton.setLayoutY(YPoint);
            root.getChildren().add(msgerButton.getButton());
            buttonNum++;
        }
    }

    public void show() {
        stage.show();
    }

    public void setUpText(String str) {
        errorMsgerController.getUpTextLabel().setText(str);
    }

    public void setDownText(String str) {
        errorMsgerController.getDownTextLabel().setText(str);
    }

    public void initFXML(int type) {
        try {
            switch (type) {
                case ERROR:
                    URL url = this.getClass().getClassLoader().getResource("api/ceroxe/javafx/msger/fxml/ErrorMsger.fxml");
                    fxmlLoader.setLocation(url);
                    root = fxmlLoader.load();
                    errorMsgerController = fxmlLoader.getController();
                    break;
                case INFO:
                    URL url2 = this.getClass().getClassLoader().getResource("api/ceroxe/javafx/msger/fxml/InfoMsger.fxml");
                    fxmlLoader.setLocation(url2);
                    root = fxmlLoader.load();
                    infoMsgerController = fxmlLoader.getController();
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + type);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void close() {
        stage.close();
    }

}
