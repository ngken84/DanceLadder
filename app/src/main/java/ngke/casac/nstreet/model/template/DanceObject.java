package ngke.casac.nstreet.model.template;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import java.util.Date;

import ngke.casac.nstreet.model.DanceObjectException;

public abstract class DanceObject extends BaseObject {

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
        if(name == null || name.trim().length() == 0) {
            return false;
        }
        return true;
    }

    // DATABASE FUNCTIONS
    public void updateStarred(SQLiteDatabase db) throws DanceObjectException {
        if(!isWriteDatabase(db)) {
            throw new DanceObjectException(DanceObjectException.ERR_INVALID_DB);
        }

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

    public abstract String getType();

    public abstract String getTableName();
}
