package ground;

import annotation.Author;
import creature.*;

@Author
public class Unit {
    private Creature onGroundCreature;
    public Unit() {
        onGroundCreature = null;
    }
    public boolean isEmpty() {
        if(onGroundCreature == null)
            return true;
        else return false;
    }
    public void Come(Creature creature) {
        onGroundCreature = creature;
    }
    public void Leave() {
        onGroundCreature = null;
    }
    public Creature showCreature() {
        return onGroundCreature;
    }
}