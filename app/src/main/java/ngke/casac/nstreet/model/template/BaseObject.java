package ngke.casac.nstreet.model.template;

import android.database.sqlite.SQLiteDatabase;

import java.util.List;
import java.util.Map;

import ngke.casac.nstreet.model.DanceObjectException;
import ngke.casac.nstreet.model.subitemlists.DanceObjRelation;

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

    public static String[] getSelectionArgsForDorList(SQLiteDatabase db, List<DanceObjRelation> list,
                                                   Map<Long, DanceObjRelation> map, StringBuilder selection) throws DanceObjectException {
        checkReadableDatabase(db);

        if (list == null || list.size() == 0) {
            return null;
        }
        selection.append(ContractTemplate._ID);
        selection.append(" IN (?");
        map.put(list.get(0).getChildId(), list.get(0));
        String[] selectionArgs = new String[list.size()];
        selectionArgs[0] = Long.toString(list.get(0).getChildId());
        for (int i = 1, x = list.size(); i < x; ++i) {
            selection.append(",?");
            selectionArgs[i] = Long.toString(list.get(i).getChildId());
            map.put(list.get(i).getChildId(), list.get(i));
        }
        selection.append(")");
        return selectionArgs;
    }

}
