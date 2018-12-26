package creature;

import annotation.Author;

@Author
public enum CalabashBrother {
    One("红", "大娃", 1), Two("橙","二娃", 2), Three("黄","三娃", 3), Four("绿","四娃", 4), Five("青","五娃", 5), Six("蓝","六娃", 6), Seven("紫","七娃", 7);
    private String color;
    private String myName;
    private int rank;
    CalabashBrother(String color, String myName, int rank) {
        this.color = color;
        this.myName = myName;
        this.rank = rank;
    }
    public void reportColor() {
        System.out.print(color);
    }
    public void reportName() {
        System.out.print(myName);
    }
    public int getRank() {
        return rank;
    }
    public String getMyName() {
        return myName;
    }
    public int compareByRank(int otherRank) {
        if(rank < otherRank)
            return 1;
        else
            return 0;
    }
    public void reportSwap(int oldPlace, int newPlace) {
        System.out.println(myName + ":" + (oldPlace+1) + "->" + (newPlace+1));
    }
}
