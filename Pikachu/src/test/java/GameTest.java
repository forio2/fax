import de.saxsys.mvvmfx.testingutils.jfxrunner.JfxRunner;
import javafx.scene.input.KeyCode;
import javafx_pikachu_valleyball_project.controller.DrawingLoop;
import javafx_pikachu_valleyball_project.controller.GameLoop;
import javafx_pikachu_valleyball_project.model.Ball;
import javafx_pikachu_valleyball_project.model.Character;
import javafx_pikachu_valleyball_project.view.Platform;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

@RunWith(JfxRunner.class)
public class GameTest {
    private Platform platformTest;
    private Character characterTest;
    private ArrayList<Character> characterList ;
    private Ball ballTest;
    private GameLoop gameLoopTest;
    private DrawingLoop drawingLoopTest;
    private Method updateMethod;
    private Method redrawMethod;

    @Before
    public void setup(){
        platformTest = new Platform();
        characterList = new ArrayList<>();
        characterTest = new Character(30, Platform.GROUND- Character.HEIGHT,0,0, KeyCode.A,KeyCode.D,KeyCode.W,KeyCode.SPACE ,1);
        ballTest = new Ball(platformTest,180,100,17f,50f);
        characterList.add(characterTest);
        gameLoopTest = new GameLoop(platformTest);
        drawingLoopTest = new DrawingLoop(platformTest);
        try {
            updateMethod = GameLoop.class.getDeclaredMethod("update", ArrayList.class);
            redrawMethod = DrawingLoop.class.getDeclaredMethod("paint", Ball.class,ArrayList.class);
            updateMethod.setAccessible(true);
            redrawMethod.setAccessible(true);
        }catch (NoSuchMethodException e){
            e.printStackTrace();
            updateMethod = null;
            redrawMethod = null;
        }
    }

    @Test
    public void InitialShouldMatchConstructorArgument(){
        Assert.assertEquals("Initial X of Character",30,characterTest.getX(),0);
        Assert.assertEquals("Initial Y of Character",Platform.GROUND- Character.HEIGHT,characterTest.getY(),0);
        Assert.assertEquals("Initial X of Ball",180,ballTest.getX(),0);
        Assert.assertEquals("Initial Y of Ball",100,ballTest.getY(),0);
        Assert.assertEquals("Initial keycode right",KeyCode.D,characterTest.getRightKey());
        Assert.assertEquals("Initial keycode left",KeyCode.A,characterTest.getLeftKey());
        Assert.assertEquals("Initial keycode upper",KeyCode.W,characterTest.getUpKey());
        Assert.assertEquals("Initial keycode special",KeyCode.SPACE,characterTest.getSpecialKey());
    }

    @Test
    public void ShouldCharacterMoveRight() throws InvocationTargetException, IllegalAccessException, NoSuchFieldException {
        Character cTest = characterList.get(0);
        int startX = cTest.getX();
        platformTest.getKeys().add(KeyCode.D);
        updateMethod.invoke(gameLoopTest,characterList);
        redrawMethod.invoke(drawingLoopTest,ballTest,characterList);
        Field isMoveRight = cTest.getClass().getDeclaredField("isMoveRight");
        isMoveRight.setAccessible(true);
        Assert.assertTrue("Get KeyCode pressing acknowledged",platformTest.getKeys().isPressed(KeyCode.D));
        Assert.assertTrue("Character moving",isMoveRight.getBoolean(cTest));
        Assert.assertTrue("is Moving",cTest.getX()>startX);
    }

    @Test
    public void ShouldCharacterMoveLeft() throws InvocationTargetException, IllegalAccessException, NoSuchFieldException {
        Character cTest = characterList.get(0);
        int startX = cTest.getX();
        platformTest.getKeys().add(KeyCode.A);
        updateMethod.invoke(gameLoopTest,characterList);
        redrawMethod.invoke(drawingLoopTest,ballTest,characterList);
        Field isMoveLeft = cTest.getClass().getDeclaredField("isMoveLeft");
        isMoveLeft.setAccessible(true);
        Assert.assertTrue("Get KeyCode pressing acknowledged",platformTest.getKeys().isPressed(KeyCode.A));
        Assert.assertTrue("Character moving",isMoveLeft.getBoolean(cTest));
        Assert.assertTrue("is Moving",cTest.getX()<startX);
    }

    @Test
    public void ShouldCharacterMoveUpper() throws InvocationTargetException, IllegalAccessException, NoSuchFieldException {
        Character cTest = characterList.get(0);
        int startY = cTest.getY();
        platformTest.getKeys().add(KeyCode.W);
        updateMethod.invoke(gameLoopTest,characterList);
        redrawMethod.invoke(drawingLoopTest,ballTest,characterList);
        Field canJump = cTest.getClass().getDeclaredField("canJump");
        canJump.setAccessible(true);
        Assert.assertTrue("Get KeyCode pressing acknowledged",platformTest.getKeys().isPressed(KeyCode.W));
        Assert.assertTrue("Character moving",canJump.getBoolean(cTest));
        Assert.assertTrue("is Moving",cTest.getY()>startY);
    }


}
