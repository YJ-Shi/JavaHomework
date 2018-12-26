package gui;

import java.io.File;
import java.net.URL;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import ground.*;
import javafx.stage.WindowEvent;


public class Main extends Application {
    private Controller controller;
    private BattleField field;

    @Override
    public void start(Stage primaryStage) throws Exception{
        URL location = getClass().getClassLoader().getResource("HuluWa.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(location);
        fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root,800,600);
        controller = fxmlLoader.getController();
        field = new BattleField(controller);

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if(event.getCode() == KeyCode.SPACE) {
                    if(!field.isFighting && !field.isReFighting) {
                        if (field != null && field.Views != null)
                            controller.reset();
                        controller.chooseField(field);
                        controller.instruction.setText("Fighting!");
                        field.startBattle();
                    }
                    else {
                        String text = field.isFighting ? "is fighting!" : "is replaying!";
                        controller.instruction.setText("Wait, "+text);
                    }
                }
                else if(event.getCode() == KeyCode.L) {
                    if(!field.isFighting && !field.isReFighting) {
                        field.closeFile();
                        if (field != null && field.Views != null)
                            controller.reset();
                        controller.chooseField(field);
                        FileChooser fileChooser = new FileChooser();
                        fileChooser.setTitle("Open Replay");
                        File file = fileChooser.showOpenDialog(primaryStage);
                        if(file != null) {
                            controller.instruction.setText("Replaying " + file.getName());
                            field.replayBattle(file);
                        }
                        else controller.instruction.setText("Cancle : Replay!");
                    }
                    else {
                        String text = field.isFighting ? "is fighting!" : "is replaying!";
                        controller.instruction.setText("Wait, "+text);
                    }
                }
                else if(event.getCode() == KeyCode.R) {
                    if(!field.isFighting && !field.isReFighting) {
                        boolean flag = field.closeFile();
                        if(flag) {
                            File log = field.getLog();
                            if(log != null) {
                                FileChooser fileChooser = new FileChooser();
                                fileChooser.setTitle("Save Reply");
                                File file = fileChooser.showSaveDialog(primaryStage);
                                if(file != null) {
                                    boolean ifSavede = log.renameTo(file);
                                    if(ifSavede) controller.instruction.setText("Save succeed!");
                                    else controller.instruction.setText("Save to log succeed");
                                }
                                else controller.instruction.setText("Cancle : Save!");
                            }
                            else controller.instruction.setText("No log to save!");
                        }
                        else controller.instruction.setText("No log to save!");
                    }
                    else {
                        String text = field.isFighting ? "is fighting!" : "is replaying!";
                        controller.instruction.setText("Wait, "+text);
                    }
                }
            }
        });
        primaryStage.setTitle("FightingGame");
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                field.terminateAll();
                field.closeFile();
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}