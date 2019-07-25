package ngke.casac.nstreet.model.template;

import android.database.sqlite.SQLiteDatabase;

public class BaseObject {

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
