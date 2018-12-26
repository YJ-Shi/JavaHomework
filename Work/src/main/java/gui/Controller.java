package gui;

import annotation.Author;
import ground.BattleField;
import imageView.cImageView;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.util.Scanner;

@Author
public class Controller{
    private BattleField field;
    @FXML GridPane girdPane;
    @FXML Label instruction;

    public Controller() {field = null;}
    public void setResult(int result) {
        if(result == 1)
            instruction.setText("Hulus Win!");
        else
            instruction.setText("Monsters Win!");
    }
    public void setInstruction(String text) {
        instruction.setText(text);
    }
    public void chooseField(BattleField f) {
        field = f;
    }
    public void paintViews() {
        for(int i = 0; i < field.Views.size(); i++) {
            cImageView civ = field.Views.get(i);
            girdPane.add(civ,civ.newX,civ.newY);
        }
    }
    public void reset() {
        for(int i = 0; i < field.Views.size(); i++) {
            cImageView civ = field.Views.get(i);
            girdPane.getChildren().remove(civ);
        }
    }
    public void paintOneView(int index) {
        cImageView civ = field.Views.get(index);
        girdPane.getChildren().remove(civ);
        girdPane.add(civ,civ.newX,civ.newY);
    }
}
