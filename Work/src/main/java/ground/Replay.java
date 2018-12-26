package ground;

import annotation.Author;
import creature.*;
import formation.FormationType;
import javafx.application.Platform;
import javafx.scene.image.Image;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

@Author
public class Replay implements Runnable{
    private BattleField field;
    private Scanner scanner;
    private String fileName;
    public Replay(BattleField field, Scanner sc, String fileName) {
        this.field = field;
        this.scanner = sc;
        this.fileName = fileName;
    }
    public boolean initialReplay() {
        field.reset();
        Grandpa grandpa = new Grandpa(field);
        Snake snake = new Snake(field);
        HuluWa[] brothers = new HuluWa[7];
        Monster[] monsters = new Monster[20];
        for(int i = 0; i < 7; i++)
            brothers[i] = new HuluWa(i + 1, field);
        monsters[0] = new Scorpio(field);
        for(int i = 1; i < 20; i++) {
            monsters[i] = new LittleMonster(field);
        }
        grandpa.initializeFormation(-1,field.viewBattleField(),brothers);
        snake.initializeFormation(1,field.viewBattleField(),monsters);
        grandpa.standInLine(brothers);
        grandpa.standByOrder(brothers);
        grandpa.setMidPoint(2,2);
        snake.setMidPoint(5,5);

        try {
            String text = scanner.nextLine();
            if(!text.equals("HuluWaFightReplay")) {
                field.setTxet("Corrupted File!");
                return false;
            }
            grandpa.commandHuluWa(FormationType.ChangShe);
            text = scanner.nextLine();
            String[] arr = text.split(" ");
            grandpa.gotoPlace(field.viewBattleField(), Integer.parseInt(arr[0]), Integer.parseInt(arr[1]));
            text = scanner.nextLine();
            arr = text.split(" ");
            snake.gotoPlace(field.viewBattleField(), Integer.parseInt(arr[0]), Integer.parseInt(arr[1]));
            text = scanner.nextLine();
            arr = text.split(" ");
            FormationType type = FormationType.values()[Integer.parseInt(arr[0])];
            int len = snake.commandMonsters(type);

            field.Views.add(grandpa.getImageView());
            field.Views.add(snake.getImageView());
            for (int i = 0; i < 7; i++)
                field.Views.add(brothers[i].getImageView());
            for (int i = 0; i < len; i++)
                field.Views.add(monsters[i].getImageView());
        }catch (Exception e) {
            field.setTxet("Corrupted File!");
            return false;
        }

        field.isReFighting = true;
        return true;
    }
    public void run() {
        //initialReplay();
        while(!Thread.interrupted()) {
            boolean nEnd = scanner.hasNextLine();
            if(nEnd) {
                String text = scanner.nextLine();
                String[] arr = text.split(" ");
                int index = Integer.parseInt(arr[1]);
                if(arr[0].equals("m")) {
                    field.Views.get(index).reLocate(Integer.parseInt(arr[2]),Integer.parseInt(arr[3]));
                }
                else field.Views.get(index).reSetImage(new Image("image/die.png"));
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        field.drwaView(index);
                    }
                });
            }
            else {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        field.setTxet(fileName+":End");
                    }
                });
            }
            try{
                if(nEnd) {
                    TimeUnit.MILLISECONDS.sleep(200);
                }
                else{
                    field.isReFighting = false;
                    field.stopReplay();
                }
            }catch (Exception e) {
                System.out.println("非正常停止 : 回放中");
                return ;
            }
        }
    }
}
