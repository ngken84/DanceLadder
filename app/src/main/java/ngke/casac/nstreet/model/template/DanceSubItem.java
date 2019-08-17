package ngke.casac.nstreet.model.template;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.Comparator;
import java.util.Map;

import ngke.casac.nstreet.model.Category;
import ngke.casac.nstreet.model.Dance;
import ngke.casac.nstreet.model.DanceObjectException;

public abstract class DanceSubItem extends DanceObject {

    public DanceSubItem() {}

    public DanceSubItem(SQLiteDatabase db, Map<Long, Dance> danceMap, Map<Long, Category> categoryMap, Cursor cursor) throws DanceObjectException {
        super(cursor);
        checkReadableDatabase(db);
        long danceId = cursor.getLong(cursor.getColumnIndexOrThrow(SubItemContractTemplate.COL_DANCE_ID));

        if(danceId != 0) {
            if(danceMap != null && danceMap.containsKey(danceId)) {
                dance = danceMap.get(danceId);
            } else {
                Dance d = Dance.getDanceById(db, categoryMap, danceId);
                if(danceMap != null) {
                    danceMap.put(danceId, d);
                }
                dance = d;
            }
        }
        rating = cursor.getInt(cursor.getColumnIndexOrThrow(SubItemContractTemplate.COL_RATING));
        orderNumber = cursor.getInt(cursor.getColumnIndexOrThrow(SubItemContractTemplate.COL_ORDER_NO));
    }

    protected int orderNumber;
    protected int rating;
    protected Dance dance;

    public int getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public Dance getDance() {
        return dance;
    }

    public void setDance(Dance dance) {
        this.dance = dance;
    }

    public static class DanceSubItemOrderComparator implements Comparator<DanceSubItem> {

        @Override
        public int compare(DanceSubItem t0, DanceSubItem t1) {
            return t0.getOrderNumber() - t1.getOrderNumber();
        }

        @Override
        public boolean equals(Object o) {
            return false;
        }
    }

    // Database Functions

    @Override
    protected void updateContentValues(ContentValues cv) {
        cv.put(SubItemContractTemplate.COL_ORDER_NO, orderNumber);
        cv.put(SubItemContractTemplate.COL_RATING, rating);

        if(dance != null) {
            cv.put(SubItemContractTemplate.COL_DANCE_ID, dance.getId());
        }
        updateContentValuesForSubItem(cv);
    }

    protected abstract void updateContentValuesForSubItem(ContentValues cv);

    protected int getRatingFromCursor(Cursor cursor) {
        return cursor.getInt(cursor.getColumnIndexOrThrow(SubItemContractTemplate.COL_RATING));
    }

    protected int getOrderNumberFromCursor(Cursor cursor) {
        return cursor.getInt(cursor.getColumnIndexOrThrow(SubItemContractTemplate.COL_ORDER_NO));
    }

    protected Dance getDanceFromCursor(SQLiteDatabase db, Map<Long, Dance> danceMap, Map<Long, Category> categoryMap, Cursor cursor) throws DanceObjectException {
        long danceId = cursor.getLong(cursor.getColumnIndexOrThrow(SubItemContractTemplate.COL_DANCE_ID));
        if(danceMap != null && danceMap.containsKey(danceId)) {
            return danceMap.get(danceId);
        } else {
            checkReadableDatabase(db);
            Dance d = Dance.getDanceById(db, categoryMap, danceId);
            if(danceMap != null) {
                danceMap.put(danceId, d);
            }
            return d;
        }
    }
}
