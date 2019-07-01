package ngke.casac.nstreet.database.async;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import ngke.casac.nstreet.database.DanceSQLHelper;
import ngke.casac.nstreet.model.Category;
import ngke.casac.nstreet.model.Dance;

public class DanceDBTasks {

    private DanceDBTasks() {}

    public static void getAllDances(Context context, final DanceConsumer<List<Dance>> danceListConsumer) {

        class GetDanceTask extends DBTaskTemplate<Void, Void, List<Dance>> {

            public GetDanceTask(Context context) {
                super(context);
            }

            @Override
            protected List<Dance> doInBackground(Void... voids) {
                DanceSQLHelper helper = new DanceSQLHelper(getContext());
                SQLiteDatabase db = helper.getReadableDatabase();

                Map<Integer, Category> categoryMap = getAllCategories(db);

                String[] projection = Dance.Contract.getProjection();

                String sortOrder = Dance.Contract.COL_NAME + " ASC";
                Cursor cursor = db.query(Dance.Contract.TABLE_NAME,
                        projection,
                        null,
                        null,
                        null,
                        null,
                        sortOrder);

                List<Dance> retList = new LinkedList<>();
                while(cursor.moveToNext()) {
                    Dance dance = new Dance(cursor, categoryMap);
                    retList.add(dance);
                }
                return retList;
            }

            @Override
            protected void onPostExecute(List<Dance> dances) {
                danceListConsumer.consume(dances);
            }
        }

        GetDanceTask task = new GetDanceTask(context);
        task.execute();
    }

    private static Map<Integer, Category> getAllCategories(SQLiteDatabase db) {
        if (db != null && db.isOpen()) {
            Map<Integer, Category> retMap = new HashMap<>();

            String[] projection = Category.Contract.getProjection();

            Cursor cursor = db.query(Category.Contract.TABLE_NAME,
                    projection,
                    null,
                    null,
                    null,
                    null,
                    null);

            while(cursor.moveToNext()) {
                Category category = new Category(cursor);
                retMap.put(category.getId(), category);
            }
            return retMap;
        }
        return null;
    }

}
