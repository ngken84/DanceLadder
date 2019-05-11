package ngke.casac.nstreet;

import org.junit.Test;

import ngke.casac.nstreet.model.Dance;

import static org.junit.Assert.assertEquals;

public class DanceModelUnitTest {

    @Test
    public void constructor_works() {
        String danceName = "West Coast Swing";
        Dance dance = new Dance(danceName);

        assertEquals(danceName  , dance.getName());
    }


}
