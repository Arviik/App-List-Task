package org.arvik.applisttask;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.arvik.applisttask.app.controller.Sign_In_UpController;
import java.io.IOException;
import java.util.Objects;

public class MainApp extends Application {
    private static Stage stage;

    @Override
    public void start(Stage firststage) {
        stage = firststage;
        //stage.setResizable(false);
        stage.setTitle("App List Task");
        stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/img/Task.png"))));
        changeScene("/view/sign_In_Up-view", new Sign_In_UpController());
    }

    public static void main(String[] args) {
        launch();
    }

    public static void changeScene(String fxmlFile, Object controller) {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource(fxmlFile+".fxml"));
        try {
            fxmlLoader.setController(controller);
            Scene scene = new Scene(fxmlLoader.load());
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Stage getStage() {
        return stage;
    }
}