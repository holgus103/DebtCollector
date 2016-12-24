package debtcollector.holgus103.debtcollector.activities;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import debtcollector.holgus103.debtcollector.R;
import debtcollector.holgus103.debtcollector.db.dao.ContactsDao;
import debtcollector.holgus103.debtcollector.db.tables.ContactsTable;

public class Contacts extends DebtCollectorActivity implements AdapterView.OnItemClickListener {
    static final int PICK_CONTACT = 1;
    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        this.listView  = (ListView) findViewById(R.id.listView);
        this.loadContacts();
        this.addListeners();
    }

    private void loadContacts() {
        Cursor cursor = ContactsDao.getContacts(database);
        listView.setAdapter( new SimpleCursorAdapter(this,
                R.layout.simple_list_item,
                cursor,
                new String[] {ContactsTable.DISPLAY_NAME, ContactsTable.BALANCE},
                new int[] {R.id.nameView, R.id.balanceView}
        ));
        ((SimpleCursorAdapter)listView.getAdapter()).notifyDataSetChanged();
    }

    protected void addContact(){
        startActivityForResult(new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI), PICK_CONTACT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == Contacts.PICK_CONTACT){
            if(resultCode == RESULT_OK){
                Cursor cursor = getContentResolver().query(data.getData(), null, null, null, null);
                if(cursor.moveToFirst()){
                    ContentValues values = new ContentValues();
                    values.put(ContactsTable.CONTACT_ID, cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID)));
                    values.put(ContactsTable.DISPLAY_NAME, cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)));
                    database.insert(ContactsTable.class.getSimpleName(), null, values);

                }
                this.loadContacts();
            }
        }
    }

    private void addListeners(){
        Button btn = (Button)this.findViewById(R.id.add_contact_btn);
        btn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                addContact();
            }
        });
        this.listView.setOnItemClickListener(this);

    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Cursor cursor = (Cursor)this.listView
                .getAdapter()
                .getItem(position);
        this.startActivity(ContactDetails.class,
                cursor.getString(cursor.getColumnIndex("_id"))
        );
    }
}
