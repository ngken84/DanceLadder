package ngke.casac.nstreet;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import ngke.casac.nstreet.database.DanceSQLHelper;
import ngke.casac.nstreet.model.Category;
import ngke.casac.nstreet.model.DanceObjectException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

@RunWith(AndroidJUnit4.class)
public class CategoryInstrumentedTest {

    @Test
    public void constructorTest() {
        Context appContext = InstrumentationRegistry.getTargetContext();
        DanceSQLHelper danceSQLHelper = new DanceSQLHelper(appContext);
        SQLiteDatabase writeDB = danceSQLHelper.getWritableDatabase();

        try {
            Category.deleteAllCategories(writeDB);

            Category category = new Category("Ballroom Dances");
            category.insertCategory(writeDB);

            assertNotEquals(category.getId(), 0);

            Category dbCat = new Category(writeDB, category.getId());

            assertEquals(dbCat.getId(), category.getId());
            assertEquals(dbCat.getName(), category.getName());
        } catch (DanceObjectException e) {
            e.printStackTrace();
        }
    }

}
