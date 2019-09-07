package ngke.casac.nstreet.model;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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

    public Dance(SQLiteDatabase db, Map<Long, Category> categoryMap, Cursor cursor) throws DanceObjectException {
        super(cursor.getString(cursor.getColumnIndexOrThrow(Contract.COL_NAME)));
        id = cursor.getInt(cursor.getColumnIndexOrThrow(Contract._ID));
        description = cursor.getString(cursor.getColumnIndexOrThrow(Contract.COL_DESCRIPTION));
        dateModified = getDateFromLong(cursor.getLong(cursor.getColumnIndexOrThrow(Contract.COL_DATE_MODIFIED)));
        dateCreated = getDateFromLong(cursor.getLong(cursor.getColumnIndexOrThrow(Contract.COL_DATE_CREATED)));
        starred = cursor.getInt(cursor.getColumnIndexOrThrow(Contract.COL_STARRED)) == 1;

        long categoryId = cursor.getLong(cursor.getColumnIndexOrThrow(Contract.COL_CATEGORY_ID));

        if(categoryMap != null && categoryMap.containsKey(categoryId)) {
            category = categoryMap.get(categoryId);
        } else {
            Category c = Category.getCategoryById(db, categoryId);
            if (categoryMap != null && c != null) {
                categoryMap.put(categoryId, c);
            }
            category = c;

        }
    }

    @Override
    public String getType() {
        return TYPE;
    }

    @Override
    public String getTableName() {
        return Contract.TABLE_NAME;
    }

    @Override
    public String getObjectName() {
        return "Dance";
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

    // DATABASE FUNCTIONS

    public static Dance getDanceById(SQLiteDatabase db, Map<Long, Category> categoryMap, long id) {
        try {
            if (!isReadableDatabase(db)) {
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
            try {
                if (cursor.moveToNext()) {
                    return new Dance(db, categoryMap, cursor);
                }
            } finally {
                cursor.close();
            }
        } catch (DanceObjectException ex) {

        }
        return null;
    }

    public static Dance getDanceByName(SQLiteDatabase db, Map<Long, Category> categoryMap, String danceName) {
        if(danceName == null || danceName.trim().isEmpty()) {
            return null;
        }

        try {
            checkReadableDatabase(db);

            String[] projection = Contract.getProjection();
            String selection = "UPPER(" + Contract.COL_NAME + ") = UPPER(?)";
            String[] selectionArgs = {danceName.trim()};

            Cursor cursor = db.query(Contract.TABLE_NAME,
                    projection,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    null);

            try {
                if(cursor.moveToNext()) {
                    return new Dance(db, categoryMap, cursor);
                }
            } finally {
                cursor.close();
            }

        } catch (DanceObjectException ex) {

        }
        return null;
    }

    @Override
    public void isUpdateReady(SQLiteDatabase db) throws DanceObjectException {
        Dance dance = Dance.getDanceByName(db, null, name);
        if(dance != null) {
            if(dance.getId() != id) {
                throw new DanceObjectException("Can't rename dance to match another dance's name.");
            }
        }
    }

    @Override
    protected void isInsertReady(SQLiteDatabase db) throws DanceObjectException {
        if(!isNameValid()) {
            throw new DanceObjectException(DanceObjectException.ERR_INVALID_OBJECT);
        }

        if(isDanceInDatabase(db)) {
            throw new DanceObjectException(DanceObjectException.ERR_ALREADY_EXISTS);
        }
    }

    @Override
    protected void updateContentValues(ContentValues cv) {
        if(category != null) {
            cv.put(Contract.COL_CATEGORY_ID, category.getId());
        }
        cv.put(Contract.COL_DESCRIPTION, description);
    }

    public boolean isDanceInDatabase(SQLiteDatabase db) throws DanceObjectException {
        if(!isReadableDatabase(db)) {
            throw new DanceObjectException(DanceObjectException.ERR_INVALID_DB);
        }

        if(name == null || name.trim().length() == 0) {
            return false;
        }

        String[] projection = {Dance.Contract._ID};
        String selection = "UPPER(" + Dance.Contract.COL_NAME + ") = UPPER(?)";
        String[] selectionArgs = {name.trim()};

        Cursor cursor = db.query(Dance.Contract.TABLE_NAME,
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

    public static List<Dance> getAllDances(SQLiteDatabase db) throws DanceObjectException {
        if(!isReadableDatabase(db)) {
            throw new DanceObjectException(DanceObjectException.ERR_INVALID_DB);
        }
        Map<Long, Category> catMap = Category.getCategoryMap(db);

        String[] projection = Contract.getProjection();
        String orderBy = Contract.COL_NAME;

        Cursor cursor = db.query(Contract.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                orderBy);

        List<Dance> retList = new ArrayList<>();
        try {
            while(cursor.moveToNext()) {
                Dance dance = new Dance(db, catMap, cursor);
                retList.add(dance);
            }
        } finally {
            cursor.close();
        }

        return retList;
    }

    public static void deleteAllDances(SQLiteDatabase db) throws DanceObjectException {
        checkWriteDatabase(db);
        db.delete(Contract.TABLE_NAME, null, null);

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
