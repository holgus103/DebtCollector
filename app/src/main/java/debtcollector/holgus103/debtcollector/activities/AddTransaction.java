package debtcollector.holgus103.debtcollector.activities;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.SimpleCursorAdapter;

import debtcollector.holgus103.debtcollector.R;
import debtcollector.holgus103.debtcollector.db.dao.ContactsDao;
import debtcollector.holgus103.debtcollector.db.tables.ContactsTable;

public class AddTransaction extends DebtCollectorActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transaction);
        this.addActionListeners();
        final SimpleCursorAdapter adapter = new SimpleCursorAdapter(
                this,
                R.layout.dropdown_item,
                ContactsDao.getContacts(database),
                new String[] {ContactsTable.DISPLAY_NAME},
                new int[] {R.id.nameView}
        );

        adapter.setCursorToStringConverter(new SimpleCursorAdapter.CursorToStringConverter() {
            @Override
            public CharSequence convertToString(Cursor cursor) {
                return cursor.getString(cursor.getColumnIndex(ContactsTable.DISPLAY_NAME));
            }
        });

        AutoCompleteTextView autoTV = (AutoCompleteTextView)this.findViewById(R.id.contactSelectAutoComplete);
        autoTV.setAdapter(adapter);
    }

    private void addActionListeners() {
        ((Button)this.findViewById(R.id.saveBtn)).setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
//                database.execSQL();
            }
        });
    }

}
