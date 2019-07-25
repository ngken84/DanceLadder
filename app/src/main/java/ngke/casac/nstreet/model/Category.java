package ngke.casac.nstreet.model;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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

    @Override
    public String getTableName() {
        return Contract.TABLE_NAME;
    }

    // DATABASE FUNCTIONS

    public Category(SQLiteDatabase db, long id) throws DanceObjectException {
        if(!isReadableDatabase(db)) {
            throw new DanceObjectException(DanceObjectException.ERR_INVALID_DB);
        }
        String[] projection = Contract.getProjection();
        String selection = Contract._ID + " = ?";
        String selectionArgs[] = {Long.toString(id)};

        Cursor cursor = db.query(Contract.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null);

        if(cursor.moveToNext()) {
            this.id = id;
            name = cursor.getString(cursor.getColumnIndexOrThrow(Contract.COL_NAME));
            dateCreated = new Date(cursor.getLong(cursor.getColumnIndexOrThrow(Contract.COL_DATE_CREATED)));
            dateModified = new Date(cursor.getLong(cursor.getColumnIndexOrThrow(Contract.COL_DATE_MODIFIED)));
            starred = cursor.getInt(cursor.getColumnIndexOrThrow(Contract.COL_STARRED)) == 1;
            cursor.close();
        } else {
            cursor.close();
            throw new DanceObjectException(DanceObjectException.ERR_NOT_FOUND);
        }
    }

    public static Map<Long, Category> getCategoryMap(SQLiteDatabase db) throws DanceObjectException {
        if(!isReadableDatabase(db)) {
            throw new DanceObjectException(DanceObjectException.ERR_INVALID_DB);
        }
        String[] projection = Contract.getProjection();
        Cursor cursor = db.query(Contract.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null);
        Map<Long, Category> retMap = new HashMap<>();
        while(cursor.moveToNext()) {
            Category category = new Category(cursor);
            retMap.put(category.getId(), category);
        }
        cursor.close();
        return retMap;
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
