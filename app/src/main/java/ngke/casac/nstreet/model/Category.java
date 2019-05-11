package ngke.casac.nstreet.model;


import ngke.casac.nstreet.model.template.ContractTemplate;
import ngke.casac.nstreet.model.template.DanceObject;

public class Category extends DanceObject {

    public Category(String category) {
        super(category);
    }

    public class CategoryContract extends ContractTemplate {
        public static final String TABLE_NAME = "category_table";

        @Override
        protected String getTableName() {
            return TABLE_NAME;
        }

        @Override
        protected String getNewColumnSQLString() {
            return "";
        }

    }

}
