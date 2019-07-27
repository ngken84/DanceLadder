package ngke.casac.nstreet.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import ngke.casac.nstreet.model.ActivityLog;
import ngke.casac.nstreet.model.Category;
import ngke.casac.nstreet.model.Dance;
import ngke.casac.nstreet.model.DanceMove;
import ngke.casac.nstreet.model.DanceMoveStep;
import ngke.casac.nstreet.model.Lesson;
import ngke.casac.nstreet.model.Location;
import ngke.casac.nstreet.model.Note;
import ngke.casac.nstreet.model.Teacher;

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
        sqLiteDatabase.execSQL(DanceMove.Contract.getInitSQL());
        sqLiteDatabase.execSQL(DanceMoveStep.Contract.getInitSQL());
        sqLiteDatabase.execSQL(Note.Contract.getInitSQL());
        sqLiteDatabase.execSQL(Lesson.Contract.getInitSQL());
        sqLiteDatabase.execSQL(Teacher.Contract.getInitSQL());
        sqLiteDatabase.execSQL(Location.Contract.getInitSQL());
        sqLiteDatabase.execSQL(ActivityLog.Contract.getInitSQL());
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //Dance
        sqLiteDatabase.execSQL(Dance.Contract.getDestroySQL());
        sqLiteDatabase.execSQL(Dance.Contract.getInitSQL());

        //Category
        sqLiteDatabase.execSQL(Category.Contract.getDestroySQL());
        sqLiteDatabase.execSQL(Category.Contract.getInitSQL());

        //Dance Move
        sqLiteDatabase.execSQL(DanceMove.Contract.getDestroySQL());
        sqLiteDatabase.execSQL(DanceMove.Contract.getInitSQL());

        //Dance Move Step
        sqLiteDatabase.execSQL(DanceMoveStep.Contract.getDestroySQL());
        sqLiteDatabase.execSQL(DanceMoveStep.Contract.getInitSQL());

        //Note
        sqLiteDatabase.execSQL(Note.Contract.getDestroySQL());
        sqLiteDatabase.execSQL(Note.Contract.getInitSQL());

        //Lessons
        sqLiteDatabase.execSQL(Lesson.Contract.getDestroySQL());
        sqLiteDatabase.execSQL(Lesson.Contract.getInitSQL());

        //Teacher
        sqLiteDatabase.execSQL(Teacher.Contract.getDestroySQL());
        sqLiteDatabase.execSQL(Teacher.Contract.getInitSQL());

        //Location
        sqLiteDatabase.execSQL(Location.Contract.getDestroySQL());
        sqLiteDatabase.execSQL(Location.Contract.getInitSQL());

        //ActivityLog
        sqLiteDatabase.execSQL(ActivityLog.Contract.getDestroySQL());
        sqLiteDatabase.execSQL(ActivityLog.Contract.getInitSQL());

    }
}
