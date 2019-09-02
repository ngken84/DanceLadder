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

import static junit.framework.TestCase.assertTrue;
import static ngke.casac.nstreet.DanceInstrumentedTest.compareDanceObjects;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

@RunWith(AndroidJUnit4.class)
public class CategoryInstrumentedTest {

    @Test
    public void constructorTest() {
        SQLiteDatabase writeDB = getCleanDatabase();

        try {
            Category category = new Category("Ballroom Dances");
            category.dbInsert(writeDB);

            assertNotEquals(category.getId(), 0);

            Category dbCat = Category.getCategoryById(writeDB, category.getId());

            compareCategory(category, dbCat);
        } catch (DanceObjectException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void constructorDoubleInsertFails() {
        SQLiteDatabase db = getCleanDatabase();

        Category category = new Category("Ballroom");
        try {
            category.dbInsert(db);
        } catch (DanceObjectException e) {
            assertTrue(false);
        }

        try {
            category.dbInsert(db);
            assertTrue(false);
        } catch (DanceObjectException e) {

        }

        Category category2 = new Category("Ballroom");
        try {
            category2.dbInsert(db);
            assertTrue(false);
        } catch (DanceObjectException e) {
            e.printStackTrace();
        }


    }

    private SQLiteDatabase getCleanDatabase() {
        Context appContext = InstrumentationRegistry.getTargetContext();
        DanceSQLHelper danceSQLHelper = new DanceSQLHelper(appContext);
        SQLiteDatabase writeDB = danceSQLHelper.getWritableDatabase();

        try {
            Category.deleteAllCategories(writeDB);
        } catch (DanceObjectException e) {
            e.printStackTrace();
        }
        return writeDB;

    }

    public static void compareCategory(Category c1, Category c2) {
        if(c1 == null && c2 == null) {
            return;
        }

        if(c1 == null && c2 != null) {
            assertEquals("C1 is null", "");
            return;
        }
        if(c2 == null && c1 != null) {
            assertEquals("C2 is null", "");
            return;
        }
        compareDanceObjects(c1, c2);
    }
}
