package ngke.casac.nstreet.model.template;

import android.database.sqlite.SQLiteDatabase;

import ngke.casac.nstreet.model.DanceObjectException;

public class BaseObject {

    protected static void checkReadableDatabase(SQLiteDatabase db) throws DanceObjectException {
        if(!isReadableDatabase(db)) {
            throw new DanceObjectException(DanceObjectException.ERR_INVALID_DB);
        }
    }

    protected static void checkWriteDatabase(SQLiteDatabase db) throws DanceObjectException {
        if(!isWriteDatabase(db)) {
            throw new DanceObjectException(DanceObjectException.ERR_INVALID_DB);
        }
    }

    protected static boolean isReadableDatabase(SQLiteDatabase db) {
        if(db == null || !db.isOpen()) {
            return false;
        }
        return true;
    }

    protected static boolean isWriteDatabase(SQLiteDatabase db) {
        if(db == null || !db.isOpen()) {
            return false;
        }
        return !db.isReadOnly();
    }
}
