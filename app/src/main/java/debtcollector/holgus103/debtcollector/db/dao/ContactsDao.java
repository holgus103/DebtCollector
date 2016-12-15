package debtcollector.holgus103.debtcollector.db.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.SimpleCursorAdapter;

import debtcollector.holgus103.debtcollector.db.tables.ContactsTable;

/**
 * Created by Kuba on 13/12/2016.
 */
public final class ContactsDao {
    public static class CursorToStringConverter implements SimpleCursorAdapter.CursorToStringConverter {
        @Override
        public CharSequence convertToString(Cursor cursor) {
            return cursor.getString(cursor.getColumnIndex(ContactsTable.DISPLAY_NAME));
        }
    }

    public final static Cursor getContacts(SQLiteDatabase db){
        return db.rawQuery("SELECT " + ContactsTable.CONTACT_ID + " AS _id, " +
        ContactsTable.BALANCE + ", " +
        ContactsTable.DISPLAY_NAME + " FROM " + ContactsTable.class.getSimpleName(),
                null);

    }
}
