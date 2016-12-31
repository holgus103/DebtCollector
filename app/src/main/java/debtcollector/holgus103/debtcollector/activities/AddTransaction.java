package debtcollector.holgus103.debtcollector.activities;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.SimpleCursorAdapter;

import debtcollector.holgus103.debtcollector.R;
import debtcollector.holgus103.debtcollector.db.dao.ContactsDao;
import debtcollector.holgus103.debtcollector.db.dao.TransactionDao;
import debtcollector.holgus103.debtcollector.db.tables.ContactsTable;

public class AddTransaction extends DebtCollectorMenuActivity implements AdapterView.OnItemClickListener, View.OnClickListener {

    private SimpleCursorAdapter adapter;
    private String contactID;
    private AutoCompleteTextView autoTextView;


    private void addActionListeners() {

        this.autoTextView.setOnItemClickListener(this);
        Button btn = (Button)this.findViewById(R.id.saveBtn);
        if(btn != null){
            btn.setOnClickListener(this);
        }
    }

    @Override
    protected void loadData() {
        Cursor cursor = this.adapter.swapCursor(ContactsDao.getContacts());
        this.adapter.notifyDataSetChanged();
        cursor.close();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transaction);
        Bundle bundle = this.getIntent().getExtras();
        this.autoTextView = (AutoCompleteTextView)this.findViewById(R.id.contactSelectAutoComplete);
        this.addActionListeners();

        this.adapter = new SimpleCursorAdapter(
                this,
                R.layout.dropdown_item,
                ContactsDao.getContacts(),
                new String[] {ContactsTable.DISPLAY_NAME},
                new int[] {R.id.nameView}
        );

        this.adapter.setCursorToStringConverter(new ContactsDao.CursorToStringConverter());
        this.autoTextView.setAdapter(adapter);

        String contactID = bundle.getString(DebtCollectorActivity.CONTACT_ID);
        if(contactID != null){
            ContactsDao contact = new ContactsDao(contactID);
            this.autoTextView.setText(contact.getDisplayName());
            this.contactID = contactID;
        }

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Cursor obj = (Cursor)adapter.getItem(position);
        AddTransaction.this.contactID = obj.getString(obj.getColumnIndex("_id"));
    }

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

}
