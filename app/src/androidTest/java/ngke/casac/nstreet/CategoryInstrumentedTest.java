package ngke.casac.nstreet;


import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import ngke.casac.nstreet.model.Category;

@RunWith(AndroidJUnit4.class)
public class CategoryInstrumentedTest {

    @Test
    public void constructorTest() {
        Category category = new Category("Ballroom Dances");

    }

}
