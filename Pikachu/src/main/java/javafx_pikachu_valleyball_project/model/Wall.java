package javafx_pikachu_valleyball_project.model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx_pikachu_valleyball_project.view.Platform;

public class Wall extends Pane {

    private static final int WIDTH = 126,HEIGHT = 154;
    private Image wall_img;
    private ImageView imgView;

    public Wall(){
        wall_img = new Image("/assets/wall.png");
        imgView = new ImageView(wall_img);
        setTranslateX((Platform.WIDTH/2)-WIDTH/2);
        setTranslateY(250);
        imgView.setFitWidth(WIDTH);
        imgView.setFitHeight(HEIGHT);
        getChildren().add(imgView);
    }

}
