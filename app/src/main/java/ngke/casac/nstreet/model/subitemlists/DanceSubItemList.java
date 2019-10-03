package ngke.casac.nstreet.model.subitemlists;

import android.database.sqlite.SQLiteDatabase;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ngke.casac.nstreet.model.Category;
import ngke.casac.nstreet.model.Dance;
import ngke.casac.nstreet.model.DanceObjectException;
import ngke.casac.nstreet.model.Drill;
import ngke.casac.nstreet.model.Note;
import ngke.casac.nstreet.model.template.DanceObject;

public class DanceSubItemList {

    public DanceSubItemList(SQLiteDatabase db, DanceObject object) throws DanceObjectException {
        // Get a list of the relations
        Map<String, List<DanceObjRelation>> map = DanceObjRelation.getDanceObjRelationMapByObject(db, object);

        // for each table name get the list of objects from the database
        Map<Long, Dance> danceMap = new HashMap<>();
        Map<Long, Category> catMap = new HashMap<>();


        for(String tableName : map.keySet()) {
            switch(tableName) {
                case Note.Contract.TABLE_NAME:
                    Note.populateDanceObjRelList(db, map.get(tableName));
                    break;
                case Drill.Contract.TABLE_NAME:
                    Drill.populateDanceObjRelList(db, danceMap, catMap, map.get(tableName));
                    break;
            }
        }

        // combine them all and sort them by order number
        


    }


    long parentId;
    long parentTableName;

    List<DanceObjRelation> subItemList;




}
