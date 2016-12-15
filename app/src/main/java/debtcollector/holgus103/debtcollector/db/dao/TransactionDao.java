package debtcollector.holgus103.debtcollector.db.dao;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import debtcollector.holgus103.debtcollector.db.tables.ContactsTable;
import debtcollector.holgus103.debtcollector.db.tables.TransactionTable;

/**
 * Created by Kuba on 15/12/2016.
 */
public class TransactionDao {
    String contactID;
    Integer transactionID;
    Double amount;
    Long dateAdded;
    Long dateClosed;
    String title;
    String description;

    private final void adjustBalance(SQLiteDatabase db){
        db.execSQL("UPDATE " + ContactsTable.class.getSimpleName() +
        " SET " + ContactsTable.BALANCE + " = " + ContactsTable.BALANCE + " - " + this.amount);
    }

    public static final Cursor getRecentTransactions(SQLiteDatabase db){
        return db.rawQuery("SELECT " +
                TransactionTable.TRANSACTION_ID + " AS _id, " +
                TransactionTable.AMOUNT + ", " +
                TransactionTable.TITLE +
                " FROM " + TransactionTable.class.getSimpleName() +
                " LIMIT 30",
                null
        );
    }

    public TransactionDao(String contactID, Double amount, Long dateAdded, Long dateClosed, String title, String description){
        this.contactID = contactID;
        this.amount = amount;
        this.dateAdded = dateAdded;
        this.dateClosed = dateClosed;
        this.title = title;
        this.description = description;

    }
    public final void insert(SQLiteDatabase db){
        ContentValues values = new ContentValues();
        values.put(TransactionTable.CONTACT_ID, this.contactID);
        values.put(TransactionTable.AMOUNT, this.amount);
        values.put(TransactionTable.DATE_ADDED, this.dateAdded);
        values.put(TransactionTable.DATE_CLOSED, this.dateClosed);
        values.put(TransactionTable.TITLE, this.title);
        values.put(TransactionTable.DESCRIPTION, this.description);
        long rowID = db.insert(TransactionTable.class.getSimpleName(), null, values);
        adjustBalance(db);
    }
}
