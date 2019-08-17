package ngke.casac.nstreet.model.template;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.Date;

import ngke.casac.nstreet.model.ActivityLog;
import ngke.casac.nstreet.model.DanceObjectException;

public abstract class DanceObject extends BaseObject {

    protected DanceObject(Cursor cursor) {
        id = getIdFromCursor(cursor);
        name = getNameFromCursor(cursor);
        starred = getStarredFromCursor(cursor);
        dateModified = getModifiedDateFromCursor(cursor);
        dateCreated = getCreatedDateFromCursor(cursor);
    }

    protected long id;
    protected String name;
    protected boolean starred;
    protected Date dateCreated;
    protected Date dateModified;

    public DanceObject() {
        dateCreated = new Date();
        dateModified = new Date();
    }

    public boolean isNameValid() {
        return isStringValid(name);
    }

    protected boolean isStringValid(String string) {
        if(string == null || string.trim().length() == 0) {
            return false;
        }
        return true;
    }

    // DATABASE FUNCTIONS

    public void dbInsert(SQLiteDatabase db) throws DanceObjectException {
        checkWriteDatabase(db);

        if(id > 0) {
            throw new DanceObjectException(DanceObjectException.ERR_ALREADY_EXISTS);
        }

        isInsertReady(db);

        long time = System.currentTimeMillis();

        dateCreated = new Date(time);
        dateModified = new Date(time);

        ContentValues cv = getContentValues();
        updateContentValues(cv);

        id = db.insert(getTableName(), null, cv);

        if(id > 0) {
            ActivityLog log = new ActivityLog(this, getInsertActivityLogMsg());
            log.insertActivity(db);
        }
    }

    public void dbUpdate(SQLiteDatabase db) throws DanceObjectException {
        checkWriteDatabase(db);

        // If has not been put into database, just insert it into the database
        if(id == 0) {
            dbInsert(db);
            return;
        }

        isUpdateReady(db);

        dateModified = new Date(System.currentTimeMillis());
        ContentValues cv = getContentValues();
        updateContentValues(cv);

        String selection = ContractTemplate._ID + " = ?";
        String[] selectionArgs = {Long.toString(id)};

        db.update(getTableName(),
                cv,
                selection,
                selectionArgs);

        ActivityLog log = new ActivityLog(this, ActivityLog.ActivityTag.MODIFIED);
        log.insertActivity(db);

    }

    public abstract void isUpdateReady(SQLiteDatabase db) throws DanceObjectException;

    private ContentValues getContentValues() {
        ContentValues cv = new ContentValues();
        cv.put(ContractTemplate.COL_NAME, name);
        cv.put(ContractTemplate.COL_DATE_CREATED, dateCreated.getTime());
        cv.put(ContractTemplate.COL_DATE_MODIFIED, dateModified.getTime());
        cv.put(ContractTemplate.COL_STARRED, starred ? 1 : 0);
        return cv;
    }


    protected abstract void isInsertReady(SQLiteDatabase db) throws DanceObjectException;

    protected abstract void updateContentValues(ContentValues cv);

    public void updateStarred(SQLiteDatabase db) throws DanceObjectException {
        checkWriteDatabase(db);

        if(id < 1) {
            throw new DanceObjectException(DanceObjectException.ERR_INVALID_OBJECT);
        }

        long time = System.currentTimeMillis();

        ContentValues cv = new ContentValues();
        cv.put(ContractTemplate.COL_STARRED, starred ? 1 : 0);
        cv.put(ContractTemplate.COL_DATE_MODIFIED, time);

        dateModified = new Date(time);

        String selection = ContractTemplate._ID + " = ?";
        String[] selectionArgs = {Long.toString(id)};

        db.update(getTableName(), cv, selection, selectionArgs);
    }

    public String getInsertActivityLogMsg() {
        return getObjectName() + " " + name + " created.";
    }

    public DanceObject(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean getStarred() { return starred; }

    public void setStarred(boolean value) { starred = value; }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Date getDateModified() {
        return dateModified;
    }

    public void setDateModified(Date dateModified) {
        this.dateModified = dateModified;
    }

    protected Date getDateFromLong(long time) {
        if(time == 0) {
            return null;
        }
        return new Date(time);
    }

    public abstract String getType();

    public abstract String getTableName();

    public abstract String getObjectName();

    protected Date getModifiedDateFromCursor(Cursor cursor) {
        return getDateFromCursor(cursor, ContractTemplate.COL_DATE_MODIFIED);
    }

    protected Date getCreatedDateFromCursor(Cursor cursor) {
        return getDateFromCursor(cursor, ContractTemplate.COL_DATE_CREATED);
    }

    protected Date getDateFromCursor(Cursor cursor, String columnName) {
        long time = cursor.getLong(cursor.getColumnIndexOrThrow(columnName));
        if(time != 0) {
            return new Date(time);
        }
        return null;
    }

    protected boolean getStarredFromCursor(Cursor cursor) {
        return cursor.getInt(cursor.getColumnIndexOrThrow(ContractTemplate.COL_STARRED)) == 1;
    }

    protected String getNameFromCursor(Cursor cursor) {
        return cursor.getString(cursor.getColumnIndexOrThrow(ContractTemplate.COL_NAME));
    }

    protected long getIdFromCursor(Cursor cursor) {
        return cursor.getLong(cursor.getColumnIndexOrThrow(ContractTemplate._ID));
    }
}
