package ngke.casac.nstreet.model;


import android.database.Cursor;

import java.util.Date;

import ngke.casac.nstreet.model.template.ContractTemplate;
import ngke.casac.nstreet.model.template.DanceObject;

public class Category extends DanceObject {

    public static final String TYPE = "CATEGORY";

    public Category(String category) {
        super(category);
    }

    public Category(Cursor cursor) {
        super(cursor.getString(cursor.getColumnIndexOrThrow(Contract.COL_NAME)));
        id = cursor.getInt(cursor.getColumnIndexOrThrow(Contract._ID));
        dateCreated = new Date(cursor.getLong(cursor.getColumnIndexOrThrow(Contract.COL_DATE_CREATED)));
        dateModified = new Date(cursor.getLong(cursor.getColumnIndexOrThrow(Contract.COL_DATE_MODIFIED)));
        starred = cursor.getInt(cursor.getColumnIndexOrThrow(Contract.COL_STARRED)) == 1;
    }

    @Override
    public String getType() {
        return TYPE;
    }

    public static class Contract extends ContractTemplate {
        public static final String TABLE_NAME = "category_table";

        public static String getInitSQL() {
            return getCreateTableSQL(TABLE_NAME, "");
        }

        public static String getDestroySQL() {
            return getDeleteTableSQL(TABLE_NAME);
        }

        public static String[] getProjection() {
            String[] projection = {
                    _ID,
                    COL_NAME,
                    COL_DATE_CREATED,
                    COL_DATE_MODIFIED,
                    COL_STARRED
            };
            return projection;
        }

    }

}
