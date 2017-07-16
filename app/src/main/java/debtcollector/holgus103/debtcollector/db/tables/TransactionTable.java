package debtcollector.holgus103.debtcollector.db.tables;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Kuba on 15/12/2016.
 */
public class TransactionTable {
    public static final String TRANSACTION_ID = "TRANSACTION_ID";
    public static final String CONTACT_ID = ContactsTable.CONTACT_ID;
    /**
     * 0 > contact is in debt, 0 < contact has credit
     */
    public static final String AMOUNT = "AMOUNT";
    public static final String DATE_ADDED = "DATE_ADDED";
    public static final String DATE_CLOSED = "DATE_CLOSED";
    public static final String TITLE = "TITLE";
    public static final String DESCRIPTION = "DESCRIPTION";
    public static final String SETTLED = "SETTLED";

    public final static void create(SQLiteDatabase db){
        db.execSQL("CREATE TABLE " + TransactionTable.class.getSimpleName() + "(" +
            TRANSACTION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            CONTACT_ID + " TEXT, " +
            AMOUNT + " REAL, " +
            DATE_ADDED + " INTEGER, " +
            DATE_CLOSED + " INTEGER, " +
            TITLE + " TEXT, " +
            DESCRIPTION + " TEXT, " +
            SETTLED + " BIT, " +
            "FOREIGN KEY(" + CONTACT_ID + ") REFERENCES " + ContactsTable.class.getSimpleName() + "(" + ContactsTable.CONTACT_ID + ")" +
                ");"

        );
    }
    public final static void drop(SQLiteDatabase db){
        db.execSQL("DROP TABLE " + TransactionTable.class.getSimpleName());
    }

}
