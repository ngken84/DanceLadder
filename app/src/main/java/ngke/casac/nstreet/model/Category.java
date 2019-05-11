package ngke.casac.nstreet.model;


import ngke.casac.nstreet.model.template.ContractTemplate;
import ngke.casac.nstreet.model.template.DanceObject;

public class Category extends DanceObject {

    public Category(String category) {
        super(category);
    }

    public static class Contract extends ContractTemplate {
        public static final String TABLE_NAME = "category_table";

        public static String getInitSQL() {
            return getCreateTableSQL(TABLE_NAME, "");
        }

        public static String getDestroySQL() {
            return getDeleteTableSQL(TABLE_NAME);
        }

    }

}
