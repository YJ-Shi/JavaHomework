import org.junit.Test;
import static org.junit.Assert.*;

import creature.*;
import ground.*;

import java.util.Random;

public class GroundTest {
    @Test
    public void testBound(){
        Ground ground = new Ground(12);
        Creature creature = new Creature("aCreature");
        for(int i = 0; i < 40; i++) {
            Random random = new Random();
            int x = random.nextInt(40) - 20;
            int y = random.nextInt(40) - 20;
            if(x >= 0 && x < 12 && y >= 0 && y < 12) {
                    assertEquals(true, creature.gotoPlace(ground,x,y));
            }
            else
                assertEquals(false,creature.gotoPlace(ground,x,y));
            creature.leavePlace(ground);
        }
    }
    @Test
    public void testCollision() {
        Ground ground = new Ground(12);
        Creature creature = new Creature("aCreature");
        for(int i = 0; i < 40; i++) {
            Random random = new Random();
            int x = random.nextInt(12);
            int y = random.nextInt(12);
            creature.gotoPlace(ground,x,y);
            assertEquals(false, creature.gotoPlace(ground,x,y));
            creature.leavePlace(ground);
        }
    }
}
