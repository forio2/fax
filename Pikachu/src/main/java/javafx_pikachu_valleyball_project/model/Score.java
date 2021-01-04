package javafx_pikachu_valleyball_project.model;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class Score extends Pane {

        private Label score_label;

        public Score(int x,int y){
            score_label = new Label("0");
            score_label.setFont(Font.font("Verdana", FontWeight.BOLD,40));
            score_label.setTextFill(Color.web("#FFF"));
            setTranslateX(x);
            setTranslateY(y);

            getChildren().add(score_label);
        }

    public void setScore(int score) {
        score_label.setText(Integer.toString(score));
    }

}
