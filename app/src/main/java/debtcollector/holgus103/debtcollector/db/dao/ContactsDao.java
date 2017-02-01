package debtcollector.holgus103.debtcollector.db.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.SimpleCursorAdapter;

import debtcollector.holgus103.debtcollector.db.tables.ContactsTable;
import debtcollector.holgus103.debtcollector.db.tables.TransactionTable;

/**
 * Created by Kuba on 13/12/2016.
 */
public final class ContactsDao extends BaseDao{
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

    public final void adjustBalance(double amount, ContactsDao.BalanceAdjustment mode){

        if(mode == BalanceAdjustment.Add)
            this.balance += amount;
        else
            this.balance -= amount;
    }


    public void update(){
        getDatabase().execSQL("UPDATE " + ContactsTable.class.getSimpleName() + " SET " +
                ContactsTable.BALANCE + " = " + this.balance + " " +
                " WHERE " + ContactsTable.CONTACT_ID + " = " + this.contactID);

    }
    public ContactsDao(String contactID){
        this.contactID = contactID;
        Cursor cursor = getDatabase().rawQuery("SELECT * FROM " + ContactsTable.class.getSimpleName() + " WHERE " + ContactsTable.CONTACT_ID + " = " + this.contactID, null);
        if(cursor.moveToFirst()){
            this.balance = cursor.getDouble(cursor.getColumnIndex(ContactsTable.BALANCE));
            this.displayName = cursor.getString(cursor.getColumnIndex(ContactsTable.DISPLAY_NAME));
        }
        cursor.close();
    }

    public ContactsDao(String contactID, String displayName){
        this.displayName = displayName;
        this.contactID = contactID;
        this.balance = 0.0;
    }

    public static boolean checkIfExists(String contactID) {
        return getDatabase()
                .rawQuery("SELECT * FROM " + ContactsTable.class.getSimpleName() + " WHERE " + ContactsTable.CONTACT_ID + " = " + contactID, null)
                .getCount() > 0;
    }

    public final static Cursor getContacts(){
        return getDatabase().rawQuery("SELECT " + ContactsTable.CONTACT_ID + " AS _id, " +
        ContactsTable.BALANCE + ", " +
        ContactsTable.DISPLAY_NAME + " FROM " + ContactsTable.class.getSimpleName(),
                null);

    }

    public void insert() {
        ContentValues values = new ContentValues();
        values.put(ContactsTable.CONTACT_ID, this.contactID);
        values.put(ContactsTable.BALANCE, this.balance);
        values.put(ContactsTable.DISPLAY_NAME, this.displayName);
        getDatabase().insert(ContactsTable.class.getSimpleName(), null, values);
    }


    public String getContactID() {
        return contactID;
    }

    public Double getBalance() {
        return balance;
    }

    public String getDisplayName() {
        return displayName;
    }
}
