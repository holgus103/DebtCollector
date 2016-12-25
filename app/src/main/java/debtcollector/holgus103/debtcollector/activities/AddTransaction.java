package debtcollector.holgus103.debtcollector.activities;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;

import java.util.Date;

import debtcollector.holgus103.debtcollector.R;
import debtcollector.holgus103.debtcollector.db.dao.ContactsDao;
import debtcollector.holgus103.debtcollector.db.dao.TransactionDao;
import debtcollector.holgus103.debtcollector.db.tables.ContactsTable;

public class AddTransaction extends DebtCollectorActivity {

    private SimpleCursorAdapter adapter;
    private String contactID;

    //    private String selected
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transaction);
        this.addActionListeners();
        this.adapter = new SimpleCursorAdapter(
                this,
                R.layout.dropdown_item,
                ContactsDao.getContacts(),
                new String[] {ContactsTable.DISPLAY_NAME},
                new int[] {R.id.nameView}
        );

        this.adapter.setCursorToStringConverter(new ContactsDao.CursorToStringConverter());

        AutoCompleteTextView autoTV = (AutoCompleteTextView)this.findViewById(R.id.contactSelectAutoComplete);
        autoTV.setAdapter(adapter);

    }

    private void addActionListeners() {

        AutoCompleteTextView autoTV = (AutoCompleteTextView)this.findViewById(R.id.contactSelectAutoComplete);
        autoTV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor obj = (Cursor)adapter.getItem(position);
                AddTransaction.this.contactID = obj.getString(obj.getColumnIndex("_id"));
            }

        });

        ((Button)this.findViewById(R.id.saveBtn)).setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                double amount;
                try {
                     amount = Double.parseDouble(AddTransaction.this.getStringFromView(R.id.amountEditText));
                }
                catch(NumberFormatException e){
                    //TODO: no amount specified
                    return;
                }
                String title = AddTransaction.this.getStringFromView(R.id.titleEditText);
                if(title == ""){
                    //TODO: no title specified
                }
                TransactionDao transaction = new TransactionDao(
                        AddTransaction.this.contactID,
                        amount,
                        title,
                        AddTransaction.this.getStringFromView(R.id.descriptionTextEdit)
                );
                transaction.insert();
                AddTransaction.this.startActivity(Recent.class, R.id.recent);
            }
        });
    }

}
