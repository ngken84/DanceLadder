package ngke.casac.nstreet.model;


import android.content.ContentValues;
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
        super(cursor);
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

    public static Category getCategoryById(SQLiteDatabase db, long id) {
        try {
            checkReadableDatabase(db);

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
            try {
                if (cursor.moveToNext()) {
                    return new Category(cursor);
                } else {
                    throw new DanceObjectException(DanceObjectException.ERR_NOT_FOUND);
                }
            } finally {
                cursor.close();
            }
        } catch (DanceObjectException ex) {

        }
        return null;
    }

    @Override
    protected void isInsertReady(SQLiteDatabase db) throws DanceObjectException {
        if (!isNameValid()) {
            throw new DanceObjectException(DanceObjectException.ERR_INVALID_OBJECT);
        }
        if (doesCategoryExist(db)) {
            throw new DanceObjectException(DanceObjectException.ERR_ALREADY_EXISTS);
        }
    }

    @Override
    protected void updateContentValuesForInsert(ContentValues cv) {
    }

    @Override
    public String getObjectName() {
        return "Category";
    }

    public boolean doesCategoryExist(SQLiteDatabase db) throws DanceObjectException {
        if(!isReadableDatabase(db)) {
            throw new DanceObjectException(DanceObjectException.ERR_INVALID_DB);
        }

        String[] projection = {Contract._ID};
        String selection = "UPPER(" + Contract.COL_NAME + ") = ?";
        String[] selectionArgs = {name};

        Cursor cursor = db.query(Contract.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null);

        if(cursor.moveToNext()) {
            cursor.close();
            return true;
        }
        cursor.close();
        return false;
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

    public static void deleteAllCategories(SQLiteDatabase db) throws DanceObjectException {
        if(!isWriteDatabase(db)) {
            throw new DanceObjectException(DanceObjectException.ERR_INVALID_DB);
        }
        db.delete(Contract.TABLE_NAME, null, null);
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
