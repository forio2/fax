package javafx_pikachu_valleyball_project.controller;


import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx_pikachu_valleyball_project.model.Ball;
import javafx_pikachu_valleyball_project.model.Character;
import javafx_pikachu_valleyball_project.model.Wall;
import javafx_pikachu_valleyball_project.view.Platform;

import java.util.ArrayList;
import java.util.Optional;

public class DrawingLoop implements Runnable {

    private Platform platform;
    private int frameRate;
    private float interval;
    private boolean running;
    private int counting;
    private int timerCount;

    public DrawingLoop(Platform platform) {
        this.platform = platform;
        frameRate = 40;
        interval = 1000.0f / frameRate; // 1000 ms = 1 second
        running = true;
        timerCount = 60;
        counting = 0;
    }

    private void check_character_collision(ArrayList<Character> characterList) {
        for (Character character : characterList ) {
            character.checkReachGameWall();
            character.checkReachHighest();
            character.checkReachFloor();
        }
        for (Character cA : characterList) {
            for (Character cB : characterList) {
                if (cA != cB) {
                    if (cA.getBoundsInParent().intersects(cB.getBoundsInParent())) {
                        cA.collided(cB);
                        cB.collided(cA);
                        return;
                    }
                }
            }
        }
    }
//   TODO: 1.)this function is not exist in the original function
    private int nP;
    private void check_score(ArrayList<Character> characterList){
        if(characterList.get(0).getScore()==7){
            nP = 1;
            System.out.println("p1");
            running = false;
        }else if (characterList.get(1).getScore()==7){
            nP = 2;
            System.out.println("p2");
            running = false;
        }
        if (running==false){
            javafx.application.Platform.runLater(()->{
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Game is end.");
                alert.setHeaderText("Player: "+nP+" is win.");
                alert.setContentText("Are you want to restart or exit?");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK){
                    running = true;
                    for (Character c : characterList){
                        c.setScore(0);
                    }
                    Launcher.getPrimaryStage().close();
                    new Launcher().start(new Stage());
                } else {
                    Launcher.getPrimaryStage().close();
                }
            });
        }

    }

    //   TODO: 2.)this function is not exist in the original function
    private void checkTime(ArrayList<Character> characterList){
        if (timerCount<=0){
            running = false;
            if(characterList.get(0).getScore()>characterList.get(1).getScore()){
                nP = 1;
                System.out.println("p1");
                running = false;
            }else{
                nP = 2;
                System.out.println("p2");
                running = false;
            }
            javafx.application.Platform.runLater(()->{
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Game is end.");
                alert.setHeaderText("Player: "+nP+" is win.");
                alert.setContentText("Are you want to restart or exit?");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK){
                    running = true;
                    for (Character c : characterList){
                        c.setScore(0);
                    }
                    Launcher.getPrimaryStage().close();
                    new Launcher().start(new Stage());
                } else {
                    Launcher.getPrimaryStage().close();
                }
            });
        }
    }

    private void check_ball_collision(Ball ball, ArrayList<Character> characterList, Wall wall){
        ball.checkReachFloor(characterList);
        ball.checkHitWall(wall);
        for (Character c:characterList) {
            ball.check_hit_character(c,platform);
        }
    }

    private void paint(Ball ball,ArrayList<Character> characterList){
        for (Character character : characterList ) {
            character.repaint();
        }
        ball.repaint();
        check_character_collision(characterList);
    }

    @Override
    public void run() {
        while (running) {

            float time = System.currentTimeMillis();

            check_score(platform.getCharacterList());
            checkTime(platform.getCharacterList());
            check_character_collision(platform.getCharacterList());
            check_ball_collision(platform.getBall(), platform.getCharacterList(), platform.getWall());
            paint(platform.getBall(),platform.getCharacterList());
            counting++;
            if(counting>=100){
                counting = 0;
                timerCount--;
                javafx.application.Platform.runLater(()->{
                    platform.setTimer(timerCount);
                });
            }

            time = System.currentTimeMillis() - time;

            if (time < interval) {
                try {
                    Thread.sleep((long) (interval - time));
                } catch (InterruptedException ignore) {
                }
            } else {
                try {
                    Thread.sleep((long) (interval - (interval % time)));
                } catch (InterruptedException ignore) {
                }
            }
        }
    }
}
