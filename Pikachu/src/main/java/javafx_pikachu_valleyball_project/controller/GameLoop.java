package javafx_pikachu_valleyball_project.controller;


import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx_pikachu_valleyball_project.model.Character;
import javafx_pikachu_valleyball_project.view.Platform;

import java.util.ArrayList;
import java.util.Optional;

public class GameLoop implements Runnable {

    private Platform platform;
    private int frameRate;
    private float interval;
    private boolean running;

    public GameLoop(Platform platform) {
        this.platform = platform;
        frameRate = 5;
        interval = 1000.0f / frameRate; // 1000 ms = 1 second
        running = true;
    }

    private void update(ArrayList<Character> characterList) {

        for (Character character : characterList ) {
            character.trace(platform.getKeys());
            if (platform.getKeys().isPressed(character.getLeftKey()) || platform.getKeys().isPressed(character.getRightKey())) {
                character.getImageView().move_anim();
            }

            if (platform.getKeys().isPressed(character.getLeftKey())) {
                character.setScaleX(-1);
                character.moveLeft();
            }

            if (platform.getKeys().isPressed(character.getRightKey())) {
                character.setScaleX(1);
                character.moveRight();
            }

            if (!platform.getKeys().isPressed(character.getLeftKey()) && !platform.getKeys().isPressed(character.getRightKey()) ) {
                character.stop();
                character.getImageView().idle_anim();
            }

            if (platform.getKeys().isPressed(character.getUpKey())) {
                character.jump();
            }

            if (platform.getKeys().isPressed(character.getSpecialKey())){
                character.special();
            }
        }
    }



    @Override
    public void run() {
        while (running) {

            float time = System.currentTimeMillis();

            update(platform.getCharacterList());

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
