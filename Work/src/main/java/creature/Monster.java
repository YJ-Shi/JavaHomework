package creature;

import ground.BattleField;

public class Monster extends Creature {
    public Monster(BattleField field, String path) {
        super(field,path);
        name = "妖怪";
        isJustice = false;
    }
}
