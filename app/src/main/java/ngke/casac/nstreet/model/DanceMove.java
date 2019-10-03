package ngke.casac.nstreet.model;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import java.util.List;
import java.util.Map;

import ngke.casac.nstreet.model.template.ContractTemplate;
import ngke.casac.nstreet.model.template.DanceSubItem;
import ngke.casac.nstreet.model.template.SubItemContractTemplate;

public class DanceMove extends DanceSubItem {

    public DanceMove(SQLiteDatabase db, Map<Long, Dance> danceMap, Map<Long, Category> categoryMap, Cursor cursor) throws DanceObjectException {
        super(db, danceMap, categoryMap, cursor);
        parentMoveId = cursor.getLong(cursor.getColumnIndexOrThrow(Contract.COL_PARENT_MOVE_ID));
        description = cursor.getString(cursor.getColumnIndexOrThrow(Contract.COL_DESC));

    }

    public static final String TYPE = "DANCE_MOVE";

    private String description;
    private long parentMoveId;

    private List<DanceMoveStep> moveSteps;

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
        return "Dance Move";
    }

    // DATABASE FUNCTIONS

    public static DanceMove getDanceMoveById(SQLiteDatabase db, Map<Long, Dance> danceMap,
                                             Map<Long, Category> categoryMap, long id) {
        try {
            checkReadableDatabase(db);

            String[] projection = Contract.getProjection();
            String selection = Contract.COL_NAME + " = ?";
            String[] selectionArgs = {Long.toString(id)};

            Cursor cursor = db.query(Contract.TABLE_NAME,
                    projection,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    null);

            if(cursor.moveToNext()) {
                DanceMove retval = new DanceMove(db, danceMap, categoryMap, cursor);
                cursor.close();
                return retval;
            } else {
                cursor.close();
            }
        } catch (DanceObjectException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void updateContentValuesForSubItem(ContentValues cv) {
        if(parentMoveId > 0) {
            cv.put(Contract.COL_PARENT_MOVE_ID, parentMoveId);
        }
        cv.put(Contract.COL_DESC, description);
    }

    @Override
    protected void isInsertReady(SQLiteDatabase db) throws DanceObjectException {
        throw new DanceObjectException("NOT IMPLEMENTED");
    }

    @Override
    public void isUpdateReady(SQLiteDatabase db) throws DanceObjectException {
        throw new DanceObjectException("NOT IMPLEMENTED");
    }

    public static class Contract extends SubItemContractTemplate {

        public static final String TABLE_NAME = "dance_move_template";
        public static final String COL_PARENT_MOVE_ID = "parent_move_id";
        public static final String COL_DESC = "description";

        public static String getInitSQL() {
            return getCreateTableSQL(TABLE_NAME, COL_PARENT_MOVE_ID + " INTEGER, " +
                    COL_DESC + " TEXT, ");
        }

        public static String getDestroySQL() {
            return getDeleteTableSQL(TABLE_NAME);
        }

        public static String[] getProjection() {
            String[] projection = {
                    _ID,
                    COL_DANCE_ID,
                    COL_ORDER_NO,
                    COL_RATING,
                    COL_NAME,
                    COL_PARENT_MOVE_ID,
                    COL_DESC,
                    COL_DATE_CREATED,
                    COL_STARRED,
                    COL_DATE_MODIFIED
            };
            return projection;
        }
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getParentMoveId() {
        return parentMoveId;
    }

    public void setParentMoveId(long parentMoveId) {
        this.parentMoveId = parentMoveId;
    }
}
