package ngke.casac.nstreet.model.template;

public class SubItemContractTemplate extends ContractTemplate {

    public static final String COL_DANCE_ID = "dance_id";
    public static final String COL_ORDER_NO = "object_order";
    public static final String COL_RATING = "rating";

    protected static String getCreateTableSQL(String tableName, String extraSQL) {
        return ContractTemplate.getCreateTableSQL(tableName,
                COL_DANCE_ID + " INTEGER, " +
                COL_ORDER_NO + " INTEGER, " +
                COL_RATING + " INTEGER, " +
                extraSQL);

    }
}
