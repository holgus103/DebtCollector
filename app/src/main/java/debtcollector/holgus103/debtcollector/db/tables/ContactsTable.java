package debtcollector.holgus103.debtcollector.db.tables;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Kuba on 13/12/2016.
 */
public final class ContactsTable {
    public static final String CONTACT_ID = "CONTACT_ID";
    public static final String BALANCE = "BALANCE";
    public static final String DISPLAY_NAME = "DISPLAY_NAME";

    public final static void create(SQLiteDatabase db){
        db.execSQL("CREATE TABLE " + ContactsTable.class.getSimpleName() + "(" +
                CONTACT_ID + " TEXT, " +
                BALANCE + " INT, " +
                DISPLAY_NAME + " TEXT" +
                ");"
        );
    }

    public final static void drop(SQLiteDatabase db) {
        db.execSQL("DROP TABLE " + ContactsTable.class.getSimpleName());
    }

    public final static String[] getAllColumns(){
        return new String[]{ CONTACT_ID, BALANCE, DISPLAY_NAME };

    }
}
