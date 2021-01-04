package javafx_pikachu_valleyball_project.view;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx_pikachu_valleyball_project.model.*;
import javafx_pikachu_valleyball_project.model.Character;


import java.util.ArrayList;

public class Platform extends Pane {

    public static final int WIDTH = 1200;
    public static final int HEIGHT = 500;
    public static final int GROUND = 400;

    private Image platformImg;
    private ArrayList<Character> characterList = new ArrayList();
    private ArrayList<Score> scoreList = new ArrayList<>();
    private Label time;
    private Ball ball;
    private Keys keys;
    private Wall wall;

    public Platform() {
        keys = new Keys();
        platformImg = new Image(getClass().getResourceAsStream("/assets/background.png"));
        ImageView backgroundImg = new ImageView(platformImg);
        backgroundImg.setFitHeight(HEIGHT);
        backgroundImg.setFitWidth(WIDTH);
        time = new Label("60");
        time.setFont(Font.font("Verdana", FontWeight.BOLD,40));
        time.setTextFill(Color.web("#FFF"));
        time.setTranslateX(WIDTH/2);
//        setTranslateY(y);
        characterList.add(new Character(30, GROUND- Character.HEIGHT,0,0, KeyCode.A,KeyCode.D,KeyCode.W,KeyCode.SPACE ,1));
        characterList.add(new Character(Platform.WIDTH-30, GROUND - Character.HEIGHT,0,0, KeyCode.LEFT,KeyCode.RIGHT,KeyCode.UP,KeyCode.ENTER,2));
        scoreList.add(new Score(30,30));
        scoreList.add(new Score(WIDTH-90,30));
        ball = new Ball(this,180,100,17f,50f);
        wall = new Wall();
        getChildren().add(backgroundImg);
        getChildren().addAll(scoreList);
        getChildren().addAll(characterList);
        getChildren().add(ball);
        getChildren().add(wall);
        getChildren().add(time);
    }

    public ArrayList<Character> getCharacterList() {
        return characterList;
    }

    public ArrayList<Score> getScoreList() {
        return scoreList;
    }

    public Wall getWall() {
        return wall;
    }

    public void setTimer(int t){
        time.setText(String.valueOf(t));
    }

    public Ball getBall() { return ball; }
    public Keys getKeys() {
        return keys;
    }
}

