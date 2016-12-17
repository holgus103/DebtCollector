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
    Short settled;


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

    public final void markAsSettled(SQLiteDatabase db){
        db.execSQL("UPDATE " + TransactionTable.class.getSimpleName() +
        " SET " + TransactionTable.SETTLED + " = 1 " +
        " WHERE " + TransactionTable.TRANSACTION_ID + " = " + this.transactionID);
        ContactsDao.adjustBalance(db, this.amount, ContactsDao.BalanceAdjustment.Add);
    }

    public TransactionDao(String contactID, Double amount, Long dateAdded, Long dateClosed, String title, String description){
        this.contactID = contactID;
        this.amount = amount;
        this.dateAdded = dateAdded;
        this.dateClosed = dateClosed;
        this.title = title;
        this.description = description;

    }

    public TransactionDao(SQLiteDatabase db, int transactionID){
        this.transactionID = transactionID;
        Cursor cursor = db.rawQuery("SELECT * FROM " + TransactionTable.class.getSimpleName() +
                " WHERE " + TransactionTable.TRANSACTION_ID + " = " + this.transactionID, null);
        if(cursor.moveToFirst()){
            this.contactID = cursor.getString(cursor.getColumnIndex(TransactionTable.CONTACT_ID));
            this.amount = cursor.getDouble(cursor.getColumnIndex(TransactionTable.AMOUNT));
            this.dateAdded = cursor.getLong(cursor.getColumnIndex(TransactionTable.DATE_ADDED));
            this.dateClosed = cursor.getLong(cursor.getColumnIndex(TransactionTable.DATE_CLOSED));
            this.title = cursor.getString(cursor.getColumnIndex(TransactionTable.TITLE));
            this.description = cursor.getString(cursor.getColumnIndex(TransactionTable.DESCRIPTION));
            this.settled = cursor.getShort(cursor.getColumnIndex(TransactionTable.SETTLED));
        }
    }

    public final void insert(SQLiteDatabase db){
        ContentValues values = new ContentValues();
        values.put(TransactionTable.CONTACT_ID, this.contactID);
        values.put(TransactionTable.AMOUNT, this.amount);
        values.put(TransactionTable.DATE_ADDED, this.dateAdded);
        values.put(TransactionTable.DATE_CLOSED, this.dateClosed);
        values.put(TransactionTable.TITLE, this.title);
        values.put(TransactionTable.DESCRIPTION, this.description);
        values.put(TransactionTable.SETTLED, 0);
        long rowID = db.insert(TransactionTable.class.getSimpleName(), null, values);
        ContactsDao.adjustBalance(db, this.amount, ContactsDao.BalanceAdjustment.Substract);
    }
}
