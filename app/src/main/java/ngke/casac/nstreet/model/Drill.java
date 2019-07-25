package ngke.casac.nstreet.model;

import android.database.Cursor;

import java.util.Date;

import ngke.casac.nstreet.model.template.DanceSubItem;
import ngke.casac.nstreet.model.template.SubItemContractTemplate;

public class Drill extends DanceSubItem {

    public Drill(Cursor cursor) {
        id = cursor.getLong(cursor.getColumnIndexOrThrow(Contract._ID));

    }

    public static final String TYPE = "DRILL";

    public String instructions;
    public int completionCount;
    public int estimatedTime;
    public Date lastCompleted;

    // DATABASE

    public static class Contract extends SubItemContractTemplate {
        public static String TABLE_NAME = "drill_table";
        public static String COL_INSTRUCTIONS = "instructions";
        public static String COL_COMPLETION_CNT = "completions";
        public static String COL_ESTIMATED_TIME = "estimatedtime";
        public static String COL_LAST_COMPLETED = "lastcomplete";

        public static String getInitSQL() {
            return getCreateTableSQL(TABLE_NAME, COL_INSTRUCTIONS + " TEXT, " +
                    COL_COMPLETION_CNT + " INTEGER, " +
                    COL_ESTIMATED_TIME + " INTEGER, " +
                    COL_LAST_COMPLETED + " INTEGER, ");
        }

        public static String getDestroySQL() {
            return getDeleteTableSQL(TABLE_NAME);
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
}
