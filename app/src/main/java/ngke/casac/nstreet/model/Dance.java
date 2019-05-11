package ngke.casac.nstreet.model;


import ngke.casac.nstreet.model.template.ContractTemplate;
import ngke.casac.nstreet.model.template.DanceObject;

public class Dance extends DanceObject {

    // Parameters
    private Category category;

    //Constructors
    public Dance(String danceName) {
        super(danceName);
    }

    // DanceContract

    public static class DanceContract extends ContractTemplate {
        public static final String TABLE_NAME = "dance_table";
        public static final String COL_CATEGORY_ID = "category";

        @Override
        protected String getTableName() {
            return TABLE_NAME;
        }

        @Override
        protected String getNewColumnSQLString() {
            return COL_CATEGORY_ID + " INTEGER, ";
        }


    }

    // GETTERS & SETTERS

    public void setCategory(String catName) {
        category = new Category(catName);
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
