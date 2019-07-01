package ngke.casac.nstreet.database.async;

import android.content.Context;
import android.os.AsyncTask;

public abstract class DBTaskTemplate<R, S, T> extends AsyncTask<R, S, T> {

    public static final String ERROR_NULL_VALUE_PASSED = "Null value passed";
    public static final String ERROR_NULL_NAME_VALUE_PASSED = "Null name value passed";
    public static final String ERROR_INSERT_ALREADY_EXISTS = "Already exists";

    private final Context context;

    public DBTaskTemplate(Context context) {
        this.context = context;
    }

    public Context getContext() {
        return context;
    }

}
