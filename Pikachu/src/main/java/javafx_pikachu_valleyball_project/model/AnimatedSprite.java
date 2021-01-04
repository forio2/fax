package javafx_pikachu_valleyball_project.model;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class AnimatedSprite extends ImageView{

    int count, columns, offsetX, offsetY, width, height, curXIndex=0, curYIndex=0;

    public AnimatedSprite(Image image, int count, int columns, int offsetX, int offsetY, int width, int height){
        this.setImage(image);
        this.count = count;
        this.columns = columns;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.width = width;
        this.height = height;
        this.setViewport(new Rectangle2D(offsetX, offsetY, width, height));
    }

    public void move_anim() {
        curXIndex = (curXIndex+1)%columns;
        curYIndex = (curYIndex+1)/columns;
        interpolate();
    }
    public void ball_anim() {
        curXIndex = (curXIndex+1)%columns;
        curYIndex = (curYIndex+1)/columns;
        interpolate();
    }
    public void idle_anim(){
        curXIndex = 5%columns;
        curYIndex = 5/columns;
        interpolate();
    }
    public void jump_anim(){
        curXIndex = 6%columns;
        curYIndex = 6/columns;
        interpolate();
    }
    public void dash_anim(){
        curXIndex = 2%columns;
        curYIndex = 2/columns;
        manual_interpolate(0,128);
    }
    protected void interpolate() {
        final int x = curXIndex*width+offsetX;
        final int y = curYIndex*height+offsetY;
        this.setViewport(new Rectangle2D(x, y, width, height));
    }
    protected void manual_interpolate(int offX,int offY) {
        final int x = curXIndex*width+offX;
        final int y = curYIndex*height+offY;
        this.setViewport(new Rectangle2D(x, y, width, height));
    }
}
