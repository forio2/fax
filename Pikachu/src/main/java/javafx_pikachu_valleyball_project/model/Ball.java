package javafx_pikachu_valleyball_project.model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx_pikachu_valleyball_project.view.Platform;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class Ball extends Pane {

    private Platform platform;
    private AnimatedSprite imageView;
    private Image ballImg;
    private static final int WIDTH = 92,HEIGHT = 92;
    private static final float GRAVITY = .98f;

    private int x,y;
    private float smash_power,power,px,py;
    private int max_yDistance;
    private boolean is_falling;
    private boolean is_wall;

    private static final int[][] respawn_point = {{150,100},{Platform.WIDTH-150,100}};

    public Ball(Platform platform,int x,int y,float power,float smash_power){
        this.platform = platform;
        this.x = x;
        this.y = y;
        this.setTranslateX(this.x);
        this.setTranslateY(this.y);
        this.ballImg = new Image("/assets/ball_sprite.png");
        this.imageView = new AnimatedSprite(ballImg,5,5,0,0,42,40);
        this.imageView.setFitWidth(WIDTH);
        this.imageView.setFitHeight(HEIGHT);
        this.power = power;
        this.smash_power = smash_power;
        is_falling = true;
        is_wall = false;
        getChildren().add(imageView);
    }
    public void checkReachFloor(ArrayList<Character> c) {
        if(y >= Platform.GROUND - HEIGHT) {
            y = Platform.GROUND - HEIGHT;
            if (x<Platform.WIDTH/2){
                javafx.application.Platform.runLater(()->{
                    c.get(1).setScore(c.get(1).getScore()+1);
                    platform.getScoreList().get(1).setScore(c.get(1).getScore());
                    respawn(respawn_point[0][0],respawn_point[0][1]);
                });
            }else{
                javafx.application.Platform.runLater(()->{
                    c.get(0).setScore(c.get(0).getScore()+1);
                    platform.getScoreList().get(0).setScore(c.get(0).getScore());
                    respawn(respawn_point[1][0],respawn_point[1][1]);
                });
            }
        }

    }

    public void check_hit_character(Character character,Platform platform){
        if(getBoundsInParent().intersects(character.getBoundsInParent())){
            float p = platform.getKeys().isPressed(character.getSpecialKey())&&!character.canJump?smash_power:power;
            float cx = (float)character.getBoundsInParent().getCenterX();
            float cy = (float)character.getBoundsInParent().getCenterY();
            float bx = (float)getBoundsInParent().getCenterX();
            float by = (float)getBoundsInParent().getCenterY();
            float h = Math.abs((float) Math.sqrt(Math.pow(bx-cx,2)+Math.pow(by-cy,2)));
            float a = Math.abs(bx-cx);
            float o = Math.abs(by-cy);
            if (bx-cx<0){
                px = -(p * a / h);
                py = p * o/h;
            }else{
                px = p * a / h;
                py = p * o/h;
            }
            py = py<8?8:py;
            max_yDistance = (int) (getY()-(Math.abs((Math.pow(py,2)/(2*GRAVITY)))));
            is_falling = false;
            is_wall = true;
        }
    }

    public void checkHitWall(Wall wall){
        if (getX()<0||getX()+WIDTH>=Platform.WIDTH){
            px *= -1;
        }
        if(getBoundsInParent().intersects(wall.getBoundsInParent())&&is_wall){
            px*=-1;
            is_wall = false;
        }
        if(getY()<=0){
            is_falling = true;
        }

    }

    public void checkReachHeight(){
        if(py<=0){
            py = 0;
            is_falling = true;
        }
    }

    public void moveX(){
        setTranslateX(x);
        x = (int)(x+px);
    }
    public void moveY(){
        checkReachHeight();
        setTranslateY(y);
        if(is_falling){
            py += GRAVITY/10;
            y+=py;
        }else{
            y-=py;
            py-=GRAVITY;
        }
    }

    public void repaint(){
        imageView.ball_anim();
        moveX();
        moveY();
    }

    private void respawn(int x,int y){
        this.x = x;
        this.y = y;
        is_falling = true;
        is_wall = false;
        px = 0;
        py = 0;
    }

    public int getX() { return x; }
    public int getY() { return y; }

}
