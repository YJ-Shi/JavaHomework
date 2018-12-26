package ground;

import annotation.Author;
import creature.*;
import formation.FormationType;
import gui.Controller;
import imageView.cImageView;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

@Author
public class BattleField
{
    private Ground space;
    private Controller control;
    public ArrayList<Creature> Hulus;
    public ArrayList<Creature> Monsters;
    public ArrayList<cImageView> Views;
    private ArrayList<Thread> threads;
    public int result;
    public boolean isFighting;
    public boolean isReFighting;
    private File fightLog;
    private FileWriter fileWriter;
    private Thread rep;
    //private File file;
    //private FileWriter fileWriter;
    public BattleField(Controller controller) {
        space = new Ground(12);
        Hulus = null;
        Monsters = null;
        Views = null;
        threads = null;
        rep = null;
        result = 0;
        this.control = controller;
        isFighting = isReFighting = false;
    }
    public void setResult() {
        control.setResult(result);
    }
    public void setTxet(String text) {
        control.setInstruction(text);
    }
    public synchronized void stopThread(int id) {
        threads.get(id).interrupt();
    }
    public synchronized void drwaView(int id) {control.paintOneView(id);}
    public Ground viewBattleField() {
        return space;
    }
    public void reset() {
        space = new Ground(12);
        result = 0;

        Hulus = new ArrayList<Creature>();
        Monsters = new ArrayList<Creature>();
        threads = new ArrayList<Thread>();
        Views = new ArrayList<cImageView>();
    }
    private void initALL() {
        reset();
        Grandpa grandpa = new Grandpa(this);
        Hulus.add(grandpa);
        HuluWa[] brothers = new HuluWa[7];
        for(int i = 0; i < 7; i++) {
            brothers[i] = new HuluWa(i + 1, this);
            Hulus.add(brothers[i]);
        }
        Snake snake = new Snake(this);
        Monsters.add(snake);
        Monster[] monsters = new Monster[20];
        monsters[0] = new Scorpio(this);
        Monsters.add(monsters[0]);
        for(int i = 1; i < 20; i++) {
            monsters[i] = new LittleMonster(this);
            //Monsters.add(monsters[i]);
        }
        grandpa.initializeFormation(-1,space,brothers);
        snake.initializeFormation(1,space,monsters);
        grandpa.standInLine(brothers);
        grandpa.standByOrder(brothers);
        grandpa.setMidPoint(2,2);
        snake.setMidPoint(5,5);

        grandpa.commandHuluWa(FormationType.ChangShe);
        grandpa.chooseUnit(space);

        FormationType type = snake.generateFormation();
        int len = snake.commandMonsters(type);
        snake.chooseUnit(space);
        for(int i = 1; i < len; i++)
            Monsters.add(monsters[i]);

        writeInitial(type,grandpa,snake);

        grandpa.setID(0);
        threads.add(new Thread(grandpa));
        Views.add(grandpa.getImageView());
        snake.setID(1);
        threads.add(new Thread(snake));
        Views.add(snake.getImageView());
        for(int i = 0; i < 7; i++) {
            brothers[i].setID(i+2);
            threads.add(new Thread(brothers[i]));
            Views.add(brothers[i].getImageView());
        }
        for(int i = 0; i < len; i++) {
            monsters[i].setID(i + 9);
            threads.add(new Thread(monsters[i]));
            Views.add(monsters[i].getImageView());
        }
    }
    public void startBattle() {
        boolean flag = creatLog();
        if(!flag) return;
        initALL();
        control.paintViews();
        isFighting = true;
        for(Thread element : threads)
            element.start();
    }

    private boolean creatLog() {
        try {
            fightLog = new File("fightlog");
            fightLog.createNewFile();
            fileWriter = new FileWriter(fightLog);
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    public FileWriter getLogWriter() {
        return fileWriter;
    }
    public File getLog() {
        return fightLog;
    }
    public void writeInitial(FormationType type, Creature grandpa, Creature snake) {
        if(fileWriter == null) return ;
        try{
            fileWriter.write("HuluWaFightReplay\n");
            fileWriter.write(grandpa.tellPlaceX()+" "+grandpa.tellPlaceY()+"\n");
            fileWriter.write(snake.tellPlaceX()+" "+snake.tellPlaceY()+"\n");
            fileWriter.write(type.ordinal()+"\n");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public synchronized void writeMove(Creature aCreature) {
        try{
            fileWriter.write("m "+aCreature.getID()+" "+aCreature.tellPlaceX()+" "+aCreature.tellPlaceY()+"\n");
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    public synchronized void writeFight(Creature aCreature) {
        try{
            fileWriter.write("f "+aCreature.getID()+"\n");
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    public boolean closeFile() {
        if(fileWriter == null) return false;
        try {
            fileWriter.close();
            fileWriter = null;
            return true;
        }catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    public void terminateAll() {
        if(threads == null) return ;
        for(Thread element : threads)
            element.interrupt();
    }
    public void replayBattle(File file) {
        Scanner scanner;
        try{
            scanner = new Scanner(file);
        }catch (Exception e) {
            e.printStackTrace();
            return ;
        }
        Replay replay = new Replay(this,scanner,file.getName());
        boolean test = replay.initialReplay();
        if(!test) return ;
        control.paintViews();
        rep = new Thread(replay);
        rep.start();
    }
    public void stopReplay() {
        rep.interrupt();
        rep = null;
    }
}

