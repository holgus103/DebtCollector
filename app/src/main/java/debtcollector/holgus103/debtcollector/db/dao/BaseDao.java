package debtcollector.holgus103.debtcollector.db.dao;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import debtcollector.holgus103.debtcollector.DebtCollectorContext;
import debtcollector.holgus103.debtcollector.db.DebtCollectorDBHelper;

/**
 * Created by Kuba on 24/12/2016.
 */
public class BaseDao {
    private static SQLiteDatabase database = null;

    protected static SQLiteDatabase getDatabase(){
        if(database == null){
            database = new DebtCollectorDBHelper(DebtCollectorContext.getContext()).getWritableDatabase();
        }
        return database;
    }
}
