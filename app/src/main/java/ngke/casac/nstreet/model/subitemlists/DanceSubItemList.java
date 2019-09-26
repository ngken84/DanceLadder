package ngke.casac.nstreet.model.subitemlists;

import android.database.sqlite.SQLiteDatabase;

import java.util.List;
import java.util.Map;

import ngke.casac.nstreet.model.DanceObjectException;
import ngke.casac.nstreet.model.template.DanceObject;

public class DanceSubItemList {

    public DanceSubItemList(SQLiteDatabase db, DanceObject object) throws DanceObjectException {
        // Get a list of the relations
        Map<String, List<DanceObjRelation>> map = DanceObjRelation.getDanceObjRelationMapByObject(db, object);

        // for each table name get the list of objects from the database

        // combine them all and sort them by order number
        


    }


    long parentId;
    long parentTableName;

    List<DanceObjRelation> subItemList;




}
