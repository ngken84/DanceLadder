package ngke.casac.nstreet.model.template;

import android.provider.BaseColumns;

public abstract class ContractTemplate implements BaseColumns {

    public static final String COL_NAME = "name";
    public static final String COL_STARRED = "starred";
    public static final String COL_DATE_CREATED = "date_created";
    public static final String COL_DATE_MODIFIED = "date_modified";

    protected abstract String getTableName();

    protected abstract String getNewColumnSQLString();

    public String getCreateTableSQL() {
        return "CREATE TABLE " + getTableName() + " (" +
                _ID + " INTEGER PRIMARY KEY, " +
                COL_NAME + " TEXT, " +
                getNewColumnSQLString() +
                COL_STARRED + " INTEGER, " +
                COL_DATE_CREATED + " INTEGER, " +
                COL_DATE_MODIFIED + " INTEGER)";
    }



    public String getDeleteTableSQL() {
        return "DROP TABLE IF EXISTS " + getTableName();
    }
}
