package javafx_pikachu_valleyball_project.model;

import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx_pikachu_valleyball_project.view.Platform;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Character extends Pane {

    private static final Logger logger = LogManager.getLogger(Character.class.getName());
    public static final int WIDTH = 90;
    public static final int HEIGHT = 80;
    public int player;

    private Image characterImg;


    private AnimatedSprite imageView;

    private int x;
    private int y;

    private KeyCode leftKey;
    private KeyCode rightKey;
    private KeyCode upKey;
    private KeyCode specialKey;
    private int score;

    int xVelocity = 0;
    int yVelocity = 0;
    int xAcceleration = 1;
    int yAcceleration = 1;
    int xMaxVelocity = 7;
    int yMaxVelocity = 17;
    boolean isMoveLeft = false;
    boolean isMoveRight = false;
    boolean falling = true;
    boolean canJump = false;
    boolean jumping = false;
    int currentDirection;
    int dashBound = 0;
    private boolean isDash;

    public Character(int x, int y, int offsetX, int offsetY, KeyCode leftKey, KeyCode rightKey, KeyCode upKey, KeyCode specialKey, int player) {
        this.player = player;
        if(player==1){
            currentDirection = 1;
        }else if(player == 2){
            currentDirection=-1;setScaleX(-1);
        }
        this.x = x;
        this.y = y;
        this.setTranslateX(x);
        this.setTranslateY(y);
        this.characterImg = new Image("/assets/pikachu_sprite.png");
        this.imageView = new AnimatedSprite(characterImg,6,6,offsetX,offsetY,65,65);
        this.imageView.setFitWidth(WIDTH);
        this.imageView.setFitHeight(HEIGHT);
        this.leftKey = leftKey;
        this.rightKey = rightKey;
        this.upKey = upKey;
        this.specialKey = specialKey;
        this.getChildren().addAll(this.imageView);
    }

    public void moveLeft() {
        currentDirection = -1;
        isMoveLeft = true;
        isMoveRight = false;
    }
    public void moveRight() {
        currentDirection = 1;
        isMoveRight = true;
        isMoveLeft = false;
    }

    public void stop() {
        isMoveLeft = false;
        isMoveRight = false;
        xVelocity = 0;
    }

    public void jump() {
        if (canJump) {
            yVelocity = yMaxVelocity;
            canJump = false;
            jumping = true;
            falling = false;
        }
    }

    public void checkReachHighest() {
        if(jumping &&  yVelocity <= 0) {
            jumping = false;
            falling = true;
            yVelocity = 0;
        }
    }

    public void checkReachFloor() {
        if(falling && y >= Platform.GROUND - HEIGHT) {
            falling = false;
            canJump = true;
            yVelocity = 0;
        }
    }

    public void checkReachGameWall() {
        if(x <= 0) {
            x = 0;
        } else if( x+getWidth() >= Platform.WIDTH) {
            x = Platform.WIDTH- WIDTH;
        }
        if (player == 1){
            if (x+getWidth()>=Platform.WIDTH/2){
                x = (Platform.WIDTH/2)- WIDTH;
            }
        }else if(player == 2){
            if (x<=Platform.WIDTH/2){
                x = (Platform.WIDTH/2);
            }
        }
    }
    public void collided(Character c) {
        if (isMoveLeft) {
            x = c.getX() + WIDTH + 1;
            stop();
        } else if (isMoveRight) {
            x = c.getX() - WIDTH - 1;
            stop();
        }
    }

    public void special(){
        if(canJump){
            imageView.dash_anim();
            isDash = true;
            if(currentDirection == -1&&dashBound==0){
                dashBound = x-100;
            }else if(currentDirection == 1&&dashBound==0){
                dashBound = x+100;
            }
            while(dashBound!=0){
                if(currentDirection == -1){
                    x-=5;
                    if (x<=dashBound){
                        dashBound = 0;
                        isDash = false;
                        break;
                    }
                }else if(currentDirection == 1){
                    x+=5;
                    if (x>=dashBound){
                        dashBound = 0;
                        isDash = false;
                        break;
                    }
                }
            }
        }
    }

    public void moveX() {
        setTranslateX(x);

        if(isMoveLeft) {
            xVelocity = xVelocity >= xMaxVelocity? xMaxVelocity : xVelocity+xAcceleration;
            x = x - xVelocity;
        }
        if(isMoveRight) {
            xVelocity = xVelocity >= xMaxVelocity? xMaxVelocity : xVelocity+xAcceleration;
            x = x + xVelocity;
        }
    }

    public void moveY() {
        setTranslateY(y);

        if(falling) {
            yVelocity = yVelocity >= yMaxVelocity? yMaxVelocity : yVelocity+yAcceleration;
            y = y + yVelocity;
        }
        else if(jumping) {
            yVelocity = yVelocity <= 0 ? 0 : yVelocity-yAcceleration;
            y = y - yVelocity;
            imageView.jump_anim();
        }
    }

    public void repaint() {
        moveX();
        moveY();
    }

    public void trace(Keys keys){
        String behavior = "";
        String action = "";
        if (isMoveLeft){
            behavior = "Left";
        }else if(isMoveRight){
            behavior = "Right";
        }else if(jumping){
            behavior = "Jump";
        }else{
            behavior = "idle";
        }
        if(keys.isPressed(specialKey)&&canJump==true){
            action = "Dash";
        }else if(keys.isPressed(specialKey)&&canJump==false){
            action = "Smash";
        }else{
            action = "None";
        }
        logger.debug("[Player:"+player+"] x:"+x+",y:"+y+", Move:"+behavior+", Action:"+action+", Score: "+score);
    }

    public KeyCode getLeftKey() {
        return leftKey;
    }

    public KeyCode getRightKey() {
        return rightKey;
    }

    public KeyCode getUpKey() {
        return upKey;
    }

    public KeyCode getSpecialKey() {
        return specialKey;
    }

    public AnimatedSprite getImageView() { return imageView; }

    public void resetScore(){
        score = 0;
    }

    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
    public boolean getIsDash(){
        return isDash;
    }
}
