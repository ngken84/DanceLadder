package ngke.casac.nstreet;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import ngke.casac.nstreet.model.Dance;

import static org.junit.Assert.assertNotNull;

@RunWith(AndroidJUnit4.class)
public class DanceInstrumentedTest {

    @Test
    public void constructorWorks() {
        Dance dance = new Dance("Tango");

        assertNotNull(dance);
    }

}
