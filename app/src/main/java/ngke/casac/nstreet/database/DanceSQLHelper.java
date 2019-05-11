package ngke.casac.nstreet.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import ngke.casac.nstreet.model.Category;
import ngke.casac.nstreet.model.Dance;

public class DanceSQLHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "dance.db";

    public DanceSQLHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(Dance.Contract.getInitSQL());
        sqLiteDatabase.execSQL(Category.Contract.getInitSQL());
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //Dance
        sqLiteDatabase.execSQL(Dance.Contract.getDestroySQL());
        sqLiteDatabase.execSQL(Dance.Contract.getInitSQL());

        //Category
        sqLiteDatabase.execSQL(Category.Contract.getDestroySQL());
        sqLiteDatabase.execSQL(Category.Contract.getInitSQL());
    }
}
