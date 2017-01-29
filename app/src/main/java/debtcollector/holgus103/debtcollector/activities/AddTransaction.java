package debtcollector.holgus103.debtcollector.activities;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.FilterQueryProvider;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import debtcollector.holgus103.debtcollector.R;
import debtcollector.holgus103.debtcollector.db.dao.ContactsDao;
import debtcollector.holgus103.debtcollector.db.dao.TransactionDao;
import debtcollector.holgus103.debtcollector.db.tables.ContactsTable;
import debtcollector.holgus103.debtcollector.enums.TransactionDirection;

public class AddTransaction extends DebtCollectorMenuActivity implements AdapterView.OnItemClickListener, View.OnClickListener {

    private Spinner directionSpinner;


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
        this.directionSpinner =  (Spinner)this.findViewById(R.id.direction_spinner);
        this.populateSpinner();
        this.adapter = new SimpleCursorAdapter(
                this,
                R.layout.dropdown_item,
                ContactsDao.getContacts(),
                new String[] {ContactsTable.DISPLAY_NAME},
                new int[] {R.id.nameView},
                0
        );

        this.adapter.setCursorToStringConverter(new ContactsDao.CursorToStringConverter());
        String contactID = bundle.getString(DebtCollectorActivity.CONTACT_ID);

        if(contactID != null){

            ContactsDao contact = new ContactsDao(contactID);
            this.autoTextView.setText(contact.getDisplayName());
            this.contactID = contactID;
        }
        this.autoTextView.setAdapter(adapter);
    }

    private void populateSpinner() {
        ArrayList<String> spinnerValues = new ArrayList<String>();
        for(TransactionDirection val:TransactionDirection.values()){
            if(val != TransactionDirection.Both) {
                spinnerValues.add(val.toString());
            }
        }

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, R.layout.simple_spinner_item, spinnerValues);
        directionSpinner.setAdapter(spinnerAdapter);
        spinnerAdapter.setDropDownViewResource(R.layout.simple_spinner_item);
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
            amount = Math.abs(Double.parseDouble(AddTransaction.this.getStringFromView(R.id.amountEditText)));
        }
        catch(NumberFormatException e) {
            Toast toast = Toast.makeText(this, R.string.no_amount, Toast.LENGTH_LONG);
            toast.show();
            return;
        }

        TransactionDirection direction = TransactionDirection
                .getEnum((String)this.directionSpinner.getSelectedItem());
        // if user is the one taking the debt, increase the contacts credit
        if(direction == TransactionDirection.IOweThem){
            amount = (-1) * amount;
        }
        String title = AddTransaction.this.getStringFromView(R.id.titleEditText);
        if(title == null || title.length() == 0){
            Toast toast = Toast.makeText(this, R.string.no_title, Toast.LENGTH_LONG);
            toast.show();
            return;
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
