package creature;

import ground.BattleField;

public class Scorpio extends Monster{
    public Scorpio(BattleField field) {
        super(field,"image/scorpio.png");
        name = "蝎精";
        attackPercent = currentPercent = 20;
    }
}