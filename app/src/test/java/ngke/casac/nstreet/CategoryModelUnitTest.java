package ngke.casac.nstreet;

import org.junit.Test;

import ngke.casac.nstreet.model.Category;

import static org.junit.Assert.assertEquals;

public class CategoryModelUnitTest {

    @Test
    public void constructor_works() {
        String categoryName = "Swing";
        Category category = new Category(categoryName);

        assertEquals(categoryName  , category.getName());
    }
}
