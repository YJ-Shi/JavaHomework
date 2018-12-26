package formation;

import annotation.Author;
import ground.*;
import creature.*;

@Author
public class Formation {
    private FormationType type;
    private int direction;
    private Ground space;
    private int midX;
    private int midY;
    private Creature[] creatures;
    public Formation() {
        type = null;
        direction = 0;
        space = null;
        creatures = null;
        midX = -1;
        midY = -1;
    }
    public Formation(FormationType type, int direction, Ground room, Creature[] creatures, int X, int Y) {
        this.type = type;
        space = room;
        this.direction = direction;
        this.creatures = creatures;
        midX = X;
        midY = Y;
    }
    private int HeYiFormation() {
        creatures[0].gotoPlace(space, midX, midY);
        int step = 1;
        boolean flag;
        int len = creatures.length;
        for(int i = 1; i < creatures.length; i++) {
            if(i % 2 == 1) {
                flag = creatures[i].gotoPlace(space, midX + step * direction, midY + step);
                if(!flag) len--;
            }
            else {
                flag = creatures[i].gotoPlace(space, midX + step*direction, midY - step);
                if(!flag) len--;
                step++;
            }
        }
        return len;
    }
    private int YanXingFormation() {
        //creatures[0].gotoPlace(space, midX, midY);
        //int step = 0;
        int len = creatures.length;
        boolean flag;
        for(int i = 0, step = 0; i < creatures.length; i++) {
            flag = creatures[i].gotoPlace(space, midX + step*direction, midY + step);
            if(!flag) len--;
            step++;
        }
        return len;
    }
    private int HengEFormation() {
        int len = creatures.length;
        boolean flag;
        creatures[0].gotoPlace(space, midX, midY);
        for(int i = 1; i < creatures.length; i++) {
            if(i % 2 == 1) {
                flag = creatures[i].gotoPlace(space, midX - 1, midY - 1 - i / 2 * 2);
                if(!flag) len--;
            }
            else {
                flag = creatures[i].gotoPlace(space, midX, midY - i / 2 * 2);
                if(!flag) len--;
            }
        }
        return len;
    }
    private int ChangSheFormation() {
        //creatures[0].gotoPlace(space, midX, midY);
        //int step = 0;
        int len = creatures.length;
        boolean flag;
        for(int i = 0, step= 0; i < creatures.length; i++) {
            flag = creatures[i].gotoPlace(space, midX, midY + step);
            if(!flag) len--;
            step++;
        }
        return len;
    }
    private void YuLinFormation() {
        if(creatures.length < 10)
            return;
        creatures[0].gotoPlace(space, midX, midY);
        creatures[1].gotoPlace(space, midX + direction, midY - 1);
        creatures[2].gotoPlace(space, midX + 2*direction, midY - 2);
        creatures[3].gotoPlace(space, midX + 2*direction, midY);
        creatures[4].gotoPlace(space, midX + 2*direction, midY + 2);
        creatures[5].gotoPlace(space, midX + 3*direction, midY - 3);
        creatures[6].gotoPlace(space, midX + 3*direction, midY -1);
        creatures[7].gotoPlace(space, midX + 3*direction, midY + 1);
        creatures[8].gotoPlace(space, midX + 3*direction, midY + 3);
        creatures[9].gotoPlace(space, midX + 4*direction, midY);
    }
    private void FangMenFormation() {
        if(creatures.length < 8)
            return;
        creatures[0].gotoPlace(space, midX, midY);
        creatures[1].gotoPlace(space, midX + 1*direction, midY + 1);
        creatures[2].gotoPlace(space, midX + 1*direction, midY - 1);
        creatures[3].gotoPlace(space, midX + 2*direction, midY + 2);
        creatures[4].gotoPlace(space, midX + 2*direction, midY - 2);
        creatures[5].gotoPlace(space, midX + 3*direction, midY + 1);
        creatures[6].gotoPlace(space, midX + 3*direction, midY - 1);
        creatures[7].gotoPlace(space, midX + 4*direction, midY);
    }
    private void YanYueFormation() {
        if(creatures.length < 19)
            return;
        creatures[0].gotoPlace(space, midX, midY);
        creatures[1].gotoPlace(space, midX, midY + 1);
        creatures[2].gotoPlace(space, midX, midY - 1);
        creatures[3].gotoPlace(space, midX + direction, midY);
        creatures[4].gotoPlace(space, midX + direction, midY + 1);
        creatures[5].gotoPlace(space, midX + direction, midY - 1);
        creatures[6].gotoPlace(space, midX + 2*direction, midY + 2);
        creatures[7].gotoPlace(space, midX + 2*direction, midY + 1);
        creatures[8].gotoPlace(space, midX + 2*direction, midY);
        creatures[9].gotoPlace(space, midX + 2*direction, midY - 1);
        creatures[10].gotoPlace(space, midX + 2*direction, midY - 2);
        creatures[11].gotoPlace(space, midX + 3*direction, midY + 2);
        creatures[12].gotoPlace(space, midX + 3*direction, midY + 3);
        creatures[13].gotoPlace(space, midX + 3*direction, midY - 2);
        creatures[14].gotoPlace(space, midX + 3*direction, midY - 3);
        creatures[15].gotoPlace(space, midX + 4*direction, midY + 3);
        creatures[16].gotoPlace(space, midX + 4*direction, midY - 3);
        creatures[17].gotoPlace(space, midX + 5*direction, midY + 4);
        creatures[18].gotoPlace(space, midX + 5*direction, midY - 4);
    }
    private void FengShiFormation() {
        if(creatures.length < 12)
            return;
        creatures[0].gotoPlace(space, midX, midY);
        creatures[1].gotoPlace(space, midX + 1*direction, midY + 1);
        creatures[2].gotoPlace(space, midX + 1*direction, midY);
        creatures[3].gotoPlace(space, midX + 1*direction, midY - 1);
        creatures[4].gotoPlace(space, midX + 2*direction, midY + 2);
        creatures[5].gotoPlace(space, midX + 2*direction, midY);
        creatures[6].gotoPlace(space, midX + 2*direction, midY - 2);
        creatures[7].gotoPlace(space, midX + 3*direction, midY - 3);
        creatures[8].gotoPlace(space, midX + 3*direction, midY);
        creatures[9].gotoPlace(space, midX + 3*direction, midY + 3);
        creatures[10].gotoPlace(space, midX + 4*direction, midY);
        creatures[11].gotoPlace(space, midX + 5*direction, midY);
    }
    public int DealFormation() {
        int length = creatures.length;
        switch (type) {
            case HeYi: length = HeYiFormation(); break;
            case HengE: length = HengEFormation(); break;
            case YuLin: YuLinFormation(); length = 10; break;
            case YanYue: YanYueFormation(); length = 19; break;
            case FangMen: FangMenFormation(); length =  8; break;
            case FengShi: FengShiFormation(); length =  12; break;
            case ChangShe: length = ChangSheFormation(); break;
            case YanXing: length = YanXingFormation(); break;
        }
        return length;
    }
    public void SetDirection(int direction) {
        this.direction = direction;
    }
    public void SetFormation(FormationType type) {
        this.type = type;
    }
    public void SetGound(Ground space) {
        this.space = space;
    }
    public void SetCreature(Creature[] creatures) {
        this.creatures = creatures;
    }
    public void SetMidPoint(int X, int Y) {
        this.midX = X;
        this.midY = Y;
    }
}

