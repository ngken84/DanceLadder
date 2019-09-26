package ngke.casac.nstreet.model.subitemlists;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import ngke.casac.nstreet.model.DanceObjectException;
import ngke.casac.nstreet.model.template.BaseObject;
import ngke.casac.nstreet.model.template.DanceObject;

public class DanceObjRelation extends BaseObject {

    public DanceObjRelation(DanceObject owner, DanceObject child) {
        if(owner != null) {
            parentId = owner.getId();
            parentTableName = owner.getTableName();
        }
        if(child != null) {
            childId = child.getId();
            childTableName = child.getTableName();
        }
    }

    public DanceObjRelation(Cursor cursor) {
        id = cursor.getLong(cursor.getColumnIndexOrThrow(Contract.COL_PARENT_ID));
        childId = cursor.getLong(cursor.getColumnIndexOrThrow(Contract.COL_CHILD_ID));
        childTableName = cursor.getString(cursor.getColumnIndexOrThrow(Contract.COL_CHILD_TABLE_NAME));
        parentId = cursor.getLong(cursor.getColumnIndexOrThrow(Contract.COL_PARENT_ID));
        parentTableName = cursor.getString(cursor.getColumnIndexOrThrow(Contract.COL_PARENT_TABLE_NAME));
    }

    private long id;
    private long parentId;
    private String parentTableName;
    private long childId;
    private String childTableName;
    private int orderVal;
    private DanceObject object;

    public static class Contract implements BaseColumns {
        public static final String TABLE_NAME = "dance_orj_relationship";
        public static final String COL_PARENT_ID = "parent_id";
        public static final String COL_PARENT_TABLE_NAME = "parent_table_name";
        public static final String COL_CHILD_ID = "child_id";
        public static final String COL_CHILD_TABLE_NAME = "child_table_name";
        public static final String COL_ORDER_VALUE = "order_val";

        public static String[] getProjection() {
            String[] retval = {
                    _ID,
                    COL_PARENT_ID,
                    COL_PARENT_TABLE_NAME,
                    COL_CHILD_ID,
                    COL_CHILD_TABLE_NAME,
                    COL_ORDER_VALUE
            };
            return retval;

        }

        public static String getInitSQL() {
            return "CREATE TABLE " + TABLE_NAME + " (" +
                    _ID + " INTEGER PRIMARY KEY, " +
                    COL_PARENT_ID + " INTEGER, " +
                    COL_PARENT_TABLE_NAME + " TEXT, " +
                    COL_CHILD_ID + " INTEGER, " +
                    COL_CHILD_TABLE_NAME + " TEXT, " +
                    COL_ORDER_VALUE + " INTEGER)";
        }

        public static String getDestroySQL() {
            return "DROP TABLE IF EXISTS " + TABLE_NAME;
        }
    }

    public void dbInsert(SQLiteDatabase db) throws DanceObjectException {
        checkWriteDatabase(db);

        if(!isValid()) {
            throw new DanceObjectException(DanceObjectException.ERR_INVALID_OBJECT);
        }

        ContentValues cv = new ContentValues();
        cv.put(Contract.COL_PARENT_ID, parentId);
        cv.put(Contract.COL_PARENT_TABLE_NAME, parentTableName);
        cv.put(Contract.COL_CHILD_ID, childId);
        cv.put(Contract.COL_CHILD_TABLE_NAME, childTableName);
        cv.put(Contract.COL_ORDER_VALUE, orderVal);

        id = db.insert(Contract.TABLE_NAME, null, cv);
    }

    /**
     * Retrieves a Map with Key being table name and values being a list of dance objects
     * @param db readable database
     * @param object
     * @return Map of List of Dance Object Relations By Table Name
     * @throws DanceObjectException if db sent is not readable
     */
    public static Map<String, List<DanceObjRelation>> getDanceObjRelationMapByObject(SQLiteDatabase db, DanceObject object) throws DanceObjectException {
        checkReadableDatabase(db);

        Map<String, List<DanceObjRelation>> retMap = new HashMap<>();

        String[] projection = Contract.getProjection();
        String selection = Contract.COL_PARENT_ID + " = ? AND " +
                Contract.COL_PARENT_TABLE_NAME + " = ? ";
        String[] selectionArgs = {Long.toString(object.getId()),
            object.getTableName()};

        Cursor cursor = db.query(Contract.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                Contract.COL_ORDER_VALUE);

        while(cursor.moveToNext()) {
            DanceObjRelation obj = new DanceObjRelation(cursor);
            if(retMap.containsKey(obj.getParentTableName())) {
                retMap.get(obj.getParentTableName()).add(obj);
            } else {
                List<DanceObjRelation> list = new LinkedList<>();
                list.add(obj);
                retMap.put(obj.getParentTableName(), list);
            }
        }
        return retMap;
    }

    private boolean isValid() {
        if(parentId == 0) {
            return false;
        }
        if(parentTableName == null || parentTableName.isEmpty()) {
            return false;
        }
        if(childId == 0) {
            return false;
        }

        if(childTableName == null || childTableName.isEmpty()) {
            return false;
        }

        return true;

    }

    public long getParentId() {
        return parentId;
    }

    public void setParentId(long parentId) {
        this.parentId = parentId;
    }

    public String getParentTableName() {
        return parentTableName;
    }

    public void setParentTableName(String parentTableName) {
        this.parentTableName = parentTableName;
    }

    public int getOrderVal() {
        return orderVal;
    }

    public void setOrderVal(int orderVal) {
        this.orderVal = orderVal;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getChildId() {
        return childId;
    }

    public void setChildId(long childId) {
        this.childId = childId;
    }

    public String getChildTableName() {
        return childTableName;
    }

    public void setChildTableName(String childTableName) {
        this.childTableName = childTableName;
    }

    public DanceObject getObject() {
        return object;
    }

    public void setObject(DanceObject object) {
        this.object = object;
    }
}
