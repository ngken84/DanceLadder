package ngke.casac.nstreet.model;

import android.provider.BaseColumns;

import java.util.Comparator;

public class DanceMoveStep {

    private int id;
    private int order;
    private int moveId;
    private String description;

    private MoveStepInstruction LeaderFootwork;
    private MoveStepInstruction FollowerFootwork;

    public static class DanceMoveStepContract implements BaseColumns {
        public static final String TABLE_NAME = "dance_move_step_tbl";
        public static final String COL_ORDER = "order";
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

        public static String getDeleteTableSQL() {
            return "DROP TABLE IF EXISTS " + TABLE_NAME;
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public int getMoveId() {
        return moveId;
    }

    public void setMoveId(int moveId) {
        this.moveId = moveId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public MoveStepInstruction getLeaderFootwork() {
        return LeaderFootwork;
    }

    public void setLeaderFootwork(MoveStepInstruction leaderFootwork) {
        LeaderFootwork = leaderFootwork;
    }

    public MoveStepInstruction getFollowerFootwork() {
        return FollowerFootwork;
    }

    public void setFollowerFootwork(MoveStepInstruction followerFootwork) {
        FollowerFootwork = followerFootwork;
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
