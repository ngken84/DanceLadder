package ngke.casac.nstreet.model.template;

import android.provider.BaseColumns;

public abstract class ContractTemplate implements BaseColumns {

    public static final String COL_NAME = "name";
    public static final String COL_STARRED = "starred";
    public static final String COL_DATE_CREATED = "date_created";
    public static final String COL_DATE_MODIFIED = "date_modified";

    protected static String getCreateTableSQL(String tableName, String extraSQL) {
        return "CREATE TABLE " + tableName + " (" +
                _ID + " INTEGER PRIMARY KEY, " +
                COL_NAME + " TEXT, " +
                extraSQL +
                COL_STARRED + " INTEGER, " +
                COL_DATE_CREATED + " INTEGER, " +
                COL_DATE_MODIFIED + " INTEGER)";
    }

    protected static String getDeleteTableSQL(String tableName) {
        return "DROP TABLE IF EXISTS " + tableName;
    }
}
