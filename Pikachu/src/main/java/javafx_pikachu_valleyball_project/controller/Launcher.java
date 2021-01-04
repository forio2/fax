package javafx_pikachu_valleyball_project.controller;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx_pikachu_valleyball_project.view.Platform;


public class Launcher extends Application {

    public static void main(String[] args) { launch(args); }

    private static Stage primaryStage;

    @Override
    public void start(Stage stage) {
        primaryStage = stage;
        Platform platform = new Platform();
        GameLoop gameLoop = new GameLoop(platform);
        DrawingLoop drawingLoop = new DrawingLoop(platform);

        Scene scene = new Scene(platform,platform.WIDTH,platform.HEIGHT);
        scene.setOnKeyPressed(event-> platform.getKeys().add(event.getCode()));
        scene.setOnKeyReleased(event ->  platform.getKeys().remove(event.getCode()));

        primaryStage.setTitle("platformer");
        primaryStage.setScene(scene);
        primaryStage.show();

        (new Thread(gameLoop)).start();
        (new Thread(drawingLoop)).start();

    }

    public static Stage getPrimaryStage() {
        return primaryStage;
    }
}
