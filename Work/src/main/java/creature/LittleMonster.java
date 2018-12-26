package creature;

import ground.BattleField;

public class LittleMonster extends Monster{
    public LittleMonster(BattleField field) {
        super(field,"image/littlemonster.png");
        name = "喽啰";
        attackPercent = currentPercent = 10;
    }
}
