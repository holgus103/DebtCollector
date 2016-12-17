package debtcollector.holgus103.debtcollector.db.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.SimpleCursorAdapter;

import debtcollector.holgus103.debtcollector.db.tables.ContactsTable;
import debtcollector.holgus103.debtcollector.db.tables.TransactionTable;

/**
 * Created by Kuba on 13/12/2016.
 */
public final class ContactsDao {
    private String contactID;
    private Double balance;
    private String displayName;

    public enum BalanceAdjustment{
        Substract,
        Add

    }

    public static class CursorToStringConverter implements SimpleCursorAdapter.CursorToStringConverter {
        @Override
        public CharSequence convertToString(Cursor cursor) {
            return cursor.getString(cursor.getColumnIndex(ContactsTable.DISPLAY_NAME));
        }
    }

    public final void adjustBalance(SQLiteDatabase db, double amount, ContactsDao.BalanceAdjustment mode){

        if(mode == BalanceAdjustment.Add)
            this.balance += amount;
        else
            this.balance -= amount;
    }


    public void update(SQLiteDatabase db){
        db.execSQL("UPDATE " + ContactsTable.class.getSimpleName() + " SET " +
                ContactsTable.BALANCE + " = " + this.balance + " " +
                " WHERE " + ContactsTable.CONTACT_ID + " = " + this.contactID);

    }
    public ContactsDao(SQLiteDatabase db, String contactID){
        this.contactID = contactID;
        Cursor cursor = db.rawQuery("SELECT * FROM " + ContactsTable.class.getSimpleName() + " WHERE " + ContactsTable.CONTACT_ID + " = " + this.contactID, null);
        if(cursor.moveToFirst()){
            this.balance = cursor.getDouble(cursor.getColumnIndex(ContactsTable.BALANCE));
            this.displayName = cursor.getString(cursor.getColumnIndex(ContactsTable.DISPLAY_NAME));
        }
    }


    public final static Cursor getContacts(SQLiteDatabase db){
        return db.rawQuery("SELECT " + ContactsTable.CONTACT_ID + " AS _id, " +
        ContactsTable.BALANCE + ", " +
        ContactsTable.DISPLAY_NAME + " FROM " + ContactsTable.class.getSimpleName(),
                null);

    }
}
