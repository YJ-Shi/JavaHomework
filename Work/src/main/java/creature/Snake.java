package creature;

import java.util.Random;
import ground.*;
import formation.*;

public class Snake extends Monster{
    private int[] midPoint;
    private Formation monFormation;
    public Snake(BattleField field) {
        super(field,"image/snake.png");
        name = "蛇精";
        monFormation = new Formation();
        midPoint = new int[2];
        midPoint[0] = -1;
        midPoint[1] = -1;
        attackPercent = currentPercent = 45;
    }
    public void setMidPoint(int X, int Y) {
        midPoint[0] = X;
        midPoint[1] = Y;
    }
    public void chooseUnit(Ground space) {
        Random rand = new Random();
        int bound = space.showBound();
        int randX = -1;
        int randY = -1;
        do {
            randX = rand.nextInt(bound - midPoint[0] - 1) + midPoint[0];
            randY = rand.nextInt(bound);
        }while(!gotoPlace(space, randX, randY));
    }
    public void initializeFormation(int direction, Ground space, Creature[] creatures) {
        monFormation.SetCreature(creatures);
        monFormation.SetDirection(direction);
        monFormation.SetGound(space);
    }
    public int commandMonsters(FormationType type) {
        monFormation.SetFormation(type);
        monFormation.SetMidPoint(midPoint[0], midPoint[1]);
        return monFormation.DealFormation();
    }
    public void waitNewLocation(Monster[] monsters, Ground space) {
        for(int i = 0; i < monsters.length; i++)
            monsters[i].leavePlace(space);
        leavePlace(space);
    }
    public FormationType generateFormation() {
        int test = new Random().nextInt(8);
        FormationType type = FormationType.HengE;
        switch (test) {
            case 0: type = FormationType.HeYi; break;
            case 1: type = FormationType.YanXing; break;
            case 2: type = FormationType.HengE; break;
            case 3: type = FormationType.ChangShe; break;
            case 4: type = FormationType.YuLin; break;
            case 5: type = FormationType.FangMen; break;
            case 6: type = FormationType.YanYue; break;
            case 7: type = FormationType.FengShi; break;
        }
        return type;
    }
}