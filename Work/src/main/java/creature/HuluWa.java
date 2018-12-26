package creature;

import ground.BattleField;

public class HuluWa extends Creature{
    private CalabashBrother brother;
    public HuluWa(int rank, BattleField field) {
        super(field,"image/"+rank+".png");
        String path = "";
        switch (rank) {
            case 1:brother = CalabashBrother.One; path = "image/1.png"; break;
            case 2:brother = CalabashBrother.Two; path = "image/2.png";break;
            case 3:brother = CalabashBrother.Three; path = "image/3.png";break;
            case 4:brother = CalabashBrother.Four; path = "image/4.png";break;
            case 5:brother = CalabashBrother.Five; path = "image/5.png";break;
            case 6:brother = CalabashBrother.Six; path = "image/6.png";break;
            case 7:brother = CalabashBrother.Seven; path = "image/7.png";break;
        }
        name = brother.getMyName();
        attackPercent = currentPercent = 30;
    }
    public int getRank() {
        return brother.getRank();
    }
    public int compareByRank(int otherRank) {
        return brother.compareByRank(otherRank);
    }
}
