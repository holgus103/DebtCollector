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

    private final static String TRANSACTION_SETTLED = "Settled";
    private final static String TRANSACTION_NOT_SETTLED = "Unsettled";
    private String contactID;
    private Integer transactionID;
    private Double amount;
    private Long dateAdded;
    private Long dateClosed;
    private String title;
    private String description;
    private Short settled;


    public static final Cursor getRecentTransactions(SQLiteDatabase db){
        return db.rawQuery("SELECT " +
                TransactionTable.TRANSACTION_ID + " AS _id, " +
                TransactionTable.AMOUNT + ", " +
                TransactionTable.TITLE + ", " +
                TransactionTable.SETTLED +
                " FROM " + TransactionTable.class.getSimpleName() +
                " WHERE " + TransactionTable.SETTLED + " != 1 " +
                " LIMIT 30",
                null
        );
    }

    public static final Cursor getResolvedTransactions(SQLiteDatabase db){
        return db.rawQuery("SELECT " +
                        TransactionTable.TRANSACTION_ID + " AS _id, " +
                        TransactionTable.AMOUNT + ", " +
                        TransactionTable.TITLE + ", " +
                        TransactionTable.SETTLED +
                        " FROM " + TransactionTable.class.getSimpleName() +
                        " WHERE " + TransactionTable.SETTLED + " = 1 " +
                        " LIMIT 30",
                null
        );
    }

    public static final boolean isSettled(Cursor cursor){
        try{
            return 1 == cursor.getShort(cursor.getColumnIndex(TransactionTable.SETTLED));

        }
        catch(Exception e){
            return true;
        }
    }

    public final void markAsSettled(SQLiteDatabase db){
        this.settled = 1;
        this.dateClosed = System.currentTimeMillis()/1000L;
        db.execSQL("UPDATE " + TransactionTable.class.getSimpleName() +
        " SET " + TransactionTable.SETTLED + " = " + this.settled + ", " +
                TransactionTable.DATE_CLOSED + " = " + this.dateClosed +
        " WHERE " + TransactionTable.TRANSACTION_ID + " = " + this.transactionID);
        ContactsDao contact = new ContactsDao(db, this.contactID);
        contact.adjustBalance(db, this.amount, ContactsDao.BalanceAdjustment.Add);
        contact.update(db);

    }

    public TransactionDao(String contactID, Double amount, String title, String description){
        this.contactID = contactID;
        this.amount = amount;
        this.dateAdded = System.currentTimeMillis()/1000L;
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
        ContactsDao contact = new ContactsDao(db, this.contactID);
        contact.adjustBalance(db, this.amount, ContactsDao.BalanceAdjustment.Substract);
        contact.update(db);
    }

    public String getSettled() {
         return settled == 1 ? TransactionDao.TRANSACTION_SETTLED : TransactionDao.TRANSACTION_NOT_SETTLED;
    }

    public String getContactID() {
        return contactID;
    }

    public Integer getTransactionID() {
        return transactionID;
    }

    public Double getAmount() {
        return amount;
    }

    public Long getDateAdded() {
        return dateAdded;
    }

    public Long getDateClosed() {
        return dateClosed;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}
