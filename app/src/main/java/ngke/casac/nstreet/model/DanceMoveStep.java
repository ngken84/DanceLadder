package ngke.casac.nstreet.model;

import android.database.Cursor;
import android.provider.BaseColumns;

import java.util.Comparator;

public class DanceMoveStep {

    public DanceMoveStep(Cursor cursor) {
        id = cursor.getLong(cursor.getColumnIndexOrThrow(Contract._ID));
        order = cursor.getInt(cursor.getColumnIndexOrThrow(Contract.COL_ORDER));
        moveId = cursor.getLong(cursor.getColumnIndexOrThrow(Contract.COL_MOVE_ID));
        description = cursor.getString(cursor.getColumnIndexOrThrow(Contract.COL_DESC));

        leaderFootwork = new MoveStepInstruction(cursor.getInt(cursor.getColumnIndexOrThrow(Contract.COL_LEAD_START_FOOT)),
                cursor.getInt(cursor.getColumnIndexOrThrow(Contract.COL_LEAD_END_FOOT)),
                cursor.getString(cursor.getColumnIndexOrThrow(Contract.COL_LEAD_INSTRUCTIONS)));

        followerFootwork = new MoveStepInstruction(cursor.getInt(cursor.getColumnIndexOrThrow(Contract.COL_FLW_START_FOOT)),
                cursor.getInt(cursor.getColumnIndexOrThrow(Contract.COL_FLW_END_FOOT)),
                cursor.getString(cursor.getColumnIndexOrThrow(Contract.COL_FLW_INSTRUCTIONS)));

    }

    private long id;
    private int order;
    private long moveId;
    private String description;

    private MoveStepInstruction leaderFootwork;
    private MoveStepInstruction followerFootwork;

    public static class Contract implements BaseColumns {
        public static final String TABLE_NAME = "dance_move_step_tbl";
        public static final String COL_ORDER = "move_order";
        public static final String COL_MOVE_ID = "move_id";
        public static final String COL_DESC = "description";
        public static final String COL_LEAD_START_FOOT = "lead_start_foot";
        public static final String COL_LEAD_END_FOOT = "lead_end_foot";
        public static final String COL_LEAD_INSTRUCTIONS = "lead_instruction";
        public static final String COL_FLW_START_FOOT = "follow_start_foot";
        public static final String COL_FLW_END_FOOT = "follow_end_foot";
        public static final String COL_FLW_INSTRUCTIONS = "follow_instructions";

        public static String getInitSQL() {
            return "CREATE TABLE " + TABLE_NAME + " (" +
                    _ID + " INTEGER PRIMARY KEY, " +
                    COL_ORDER + " INTEGER NOT NULL, " +
                    COL_MOVE_ID + " INTEGER NOT NULL, " +
                    COL_DESC + " TEXT, " +
                    COL_LEAD_START_FOOT + " INTEGER, " +
                    COL_LEAD_END_FOOT + " INTEGER, " +
                    COL_LEAD_INSTRUCTIONS + " TEXT, " +
                    COL_FLW_START_FOOT + " INTEGER, " +
                    COL_FLW_END_FOOT + " INTEGER, " +
                    COL_FLW_INSTRUCTIONS + " TEXT)";
        }

        public static String getDestroySQL() {
            return "DROP TABLE IF EXISTS " + TABLE_NAME;
        }

        public static String[] getProjection() {
            String[] projection = {
                    _ID,
                    COL_ORDER,
                    COL_MOVE_ID,
                    COL_DESC,
                    COL_LEAD_START_FOOT,
                    COL_LEAD_END_FOOT,
                    COL_LEAD_INSTRUCTIONS,
                    COL_FLW_START_FOOT,
                    COL_FLW_END_FOOT,
                    COL_FLW_INSTRUCTIONS
            };
            return projection;
        }
    }



    // GETTERS & SETTERS

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public long getMoveId() {
        return moveId;
    }

    public void setMoveId(long moveId) {
        this.moveId = moveId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public MoveStepInstruction getLeaderFootwork() {
        return leaderFootwork;
    }

    public void setLeaderFootwork(MoveStepInstruction leaderFootwork) {
        this.leaderFootwork = leaderFootwork;
    }

    public MoveStepInstruction getFollowerFootwork() {
        return followerFootwork;
    }

    public void setFollowerFootwork(MoveStepInstruction followerFootwork) {
        this.followerFootwork = followerFootwork;
    }

    public static class DanceMoveStepComparator implements Comparator<DanceMoveStep> {

        @Override
        public int compare(DanceMoveStep t0, DanceMoveStep t1) {
            return t0.getOrder() - t1.getOrder();
        }

        @Override
        public boolean equals(Object o) {
            return false;
        }
    }
}
