package creature;

import annotation.Author;
import ground.*;
import imageView.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import ground.BattleField;
import javafx.application.Platform;
import javafx.scene.image.Image;

@Author
public class Creature implements Runnable{
    protected String name;
    private int locationX;
    private int locationY;
    private cImageView imageView;
    protected int attackPercent;
    protected int currentPercent;
    protected BattleField field;
    protected int threadID;
    public boolean isAlive;
    public boolean isJustice;
    public Creature(BattleField field, String imagepath) {
        name = "unknown";
        locationX = -1;
        locationY = -1;
        attackPercent = 0;
        currentPercent = 0;
        this.field = field;
        threadID = 0;
        isAlive = true;
        isJustice = true;
        imageView = new cImageView(locationX,locationY,new Image(imagepath));
    }
    public Creature(String creatureName) {
        name = creatureName;
        locationX = -1;
        locationY = -1;
    }
    public void tellMyName() {
        System.out.print(name);
    }
    public void setID(int id) {
        threadID = id;
    }
    public int tellPlaceX() {return locationX;}
    public int tellPlaceY() {return locationY;}
    public int getID() {return threadID;}
    private int calculateDistance(Creature aCreature) {
        return Math.abs(locationX - aCreature.tellPlaceX()) + Math.abs(locationY - aCreature.tellPlaceY());
    }
    public cImageView getImageView() {return imageView;}
    public boolean gotoPlace(Ground space, int X, int Y) {
        if(space.isEmpty(X,Y)) {
            space.leaveUnit(locationX, locationY);
            locationX = X;
            locationY = Y;
            space.gotoUnit(X,Y,this);
            if(imageView != null)
                this.imageView.reLocate(X,Y);
            return true;
        }
        else {
            //System.out.println("error");
            return false;
        }
    }
    public void Fight(Creature aCreature) {
        synchronized (field) {
            if (this.isAlive && aCreature.isAlive) {
                int whole = currentPercent + aCreature.currentPercent;

            /*if(currentPercent < aCreature.currentPercent) {
                int test = new Random().nextInt(currentPercent);
                if(test < (aCreature.currentPercent - currentPercent)) {

                }
            }*/
                //System.out.println(whole);
                int test = new Random().nextInt(whole);
                if (test < currentPercent) {
                    aCreature.isAlive = false;
                    currentPercent -= 10;
                    if (currentPercent <= 0) currentPercent = 1;
                    aCreature.leavePlace(field.viewBattleField());
                    field.Views.get(aCreature.threadID).reSetImage(new Image("image/die.png"));
                    field.writeFight(aCreature);
                } else {
                    this.isAlive = false;
                    aCreature.currentPercent -= 10;
                    if (currentPercent <= 0) currentPercent = 1;
                    this.leavePlace(field.viewBattleField());
                    field.Views.get(threadID).reSetImage(new Image("image/die.png"));
                    field.writeFight(this);
                }
            }
        }
    }
    public boolean moveFofFight(Creature enemy) {
        synchronized (field) {
            int test = new Random().nextInt(10);
            if (test == 0) return false;
            int dx = enemy.tellPlaceX() - locationX;
            int dy = enemy.tellPlaceY() - locationY;
            int stepX = 0, stepY = 0;
            if (dx != 0) stepX = (dx > 0) ? 1 : -1;
            if (dy != 0) stepY = (dy > 0) ? 1 : -1;

            if (dx != 0 && dy != 0) {
                int direction = new Random().nextInt(2);
                if (direction == 0)
                    return gotoPlace(field.viewBattleField(), locationX + stepX, locationY);
                else
                    return gotoPlace(field.viewBattleField(), locationX, locationY + stepY);
            } else if (dx == 0 && dy != 0)
                return gotoPlace(field.viewBattleField(), locationX, locationY + stepY);
            else if (dx != 0 && dy == 0)
                return gotoPlace(field.viewBattleField(), locationX + stepX, locationY);
            return false;
        }
    }
    public void leavePlace(Ground space) {
        if(space.testBound(locationX, locationY)) {
            space.leaveUnit(locationX, locationY);
            locationX = -1;
            locationY = -1;
        }
    }

    private Creature getEnemy() {
        int distance = 1000;
        Creature myEnemy = null;
        ArrayList<Creature> enemys = isJustice ? field.Monsters : field.Hulus;
        for(int i = 0; i < enemys.size(); i++) {
            Creature aCreature = enemys.get(i);
            if(!aCreature.isAlive) continue;
            int tempDis = Math.abs(locationX - aCreature.tellPlaceX()) + Math.abs(locationY - aCreature.tellPlaceY());
            if(tempDis < distance) {
                distance = tempDis;
                myEnemy = aCreature;
            }
        }
        return myEnemy;
    }
    public void run() {
        while(!Thread.interrupted()) {
            if(!isAlive) return;

            Creature myEnemy = getEnemy();
            if(myEnemy == null) {
                field.result = isJustice ? 1 : -1;
                //field.stopThread(this.threadID);
            }
            else {
                int distance = calculateDistance(myEnemy);
                if(distance == 1) this.Fight(myEnemy);
                else {
                    currentPercent += 5;
                    if(currentPercent > attackPercent) currentPercent = attackPercent;
                    if(this.moveFofFight(myEnemy)) {
                        field.writeMove(this);
                    }
                }
            }

            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    field.drwaView(threadID);
                    if(field.result != 0)
                        field.setResult();
                }
            });

            try{
                //TimeUnit.MILLISECONDS.sleep(new Random().nextInt(500) + 300);
                if(field.result == 0)
                    TimeUnit.MILLISECONDS.sleep(new Random().nextInt(500) + 500);
                else {
                    field.stopThread(this.threadID);
                    field.isFighting = false;
                }
            }catch (InterruptedException e) {
                System.out.println("非正常停止 : 战斗中");
                return ;
            }
        }
    }
}

