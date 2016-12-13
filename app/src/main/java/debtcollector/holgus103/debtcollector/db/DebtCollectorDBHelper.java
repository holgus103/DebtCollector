package debtcollector.holgus103.debtcollector.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import debtcollector.holgus103.debtcollector.db.tables.ContactsTable;

/**
 * Created by Kuba on 13/12/2016.
 */
public class DebtCollectorDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "debtCollector.db" ;
    private static final int DATABASE_VERSION = 1 ;

    public DebtCollectorDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        ContactsTable.create(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        ContactsTable.drop(db);
    }
}
