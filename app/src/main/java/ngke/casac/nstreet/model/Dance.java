package ngke.casac.nstreet.model;


import android.database.Cursor;

import java.util.Date;
import java.util.Map;

import ngke.casac.nstreet.model.template.ContractTemplate;
import ngke.casac.nstreet.model.template.DanceObject;

public class Dance extends DanceObject {

    public static final String TYPE = "DANCE";

    // Parameters
    private Category category;
    private String description;

    //Constructors
    public Dance(String danceName) {
        super(danceName);
    }

    public Dance(Cursor cursor, Map<Integer, Category> categoryMap) {
        super(cursor.getString(cursor.getColumnIndexOrThrow(Contract.COL_NAME)));
        id = cursor.getInt(cursor.getColumnIndexOrThrow(Contract._ID));
        description = cursor.getString(cursor.getColumnIndexOrThrow(Contract.COL_DESCRIPTION));
        dateModified = new Date(cursor.getLong(cursor.getColumnIndexOrThrow(Contract.COL_DATE_MODIFIED)));
        dateCreated = new Date(cursor.getLong(cursor.getColumnIndexOrThrow(Contract.COL_DATE_CREATED)));
        starred = cursor.getInt(cursor.getColumnIndexOrThrow(Contract.COL_STARRED)) == 1;
        category = categoryMap.get(cursor.getInt(cursor.getColumnIndexOrThrow(Contract.COL_CATEGORY_ID)));
    }

    @Override
    public String getType() {
        return TYPE;
    }

    // DanceContract

    public static class Contract extends ContractTemplate {
        public static final String TABLE_NAME = "dance_table";
        public static final String COL_CATEGORY_ID = "category";
        public static final String COL_DESCRIPTION = "description";

        public static String getInitSQL() {
            return getCreateTableSQL(TABLE_NAME, COL_CATEGORY_ID + " INTEGER, " +
                    COL_DESCRIPTION + " TEXT, ");
        }

        public static String getDestroySQL() {
            return getDeleteTableSQL(TABLE_NAME);
        }

        public static String[] getProjection() {
            String[] projection = {
                    _ID,
                    COL_NAME,
                    COL_DESCRIPTION,
                    COL_CATEGORY_ID,
                    COL_DATE_CREATED,
                    COL_DATE_MODIFIED,
                    COL_STARRED
            };
            return projection;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
