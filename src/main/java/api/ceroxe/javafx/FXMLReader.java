package api.ceroxe.javafx;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;

public class FXMLReader {

    private final FXMLLoader fxmlLoader;
    private final Object controller;
    private final Pane pane;

    public FXMLReader(URL location) {

        try {
            fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(location);
            pane = fxmlLoader.load();
            controller = fxmlLoader.getController();

        } catch (IOException e) {

            throw new RuntimeException(e);

        }
    }

    public static URL getJarResourceURL(String path) {
        URL url = FXMLLoader.class.getClassLoader().getResource(path);
        return url;
    }

    public FXMLLoader getFxmlLoader() {
        return fxmlLoader;
    }

    public Object getController() {
        return controller;
    }

    public Pane getPane() {
        return pane;
    }
}
