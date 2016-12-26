package debtcollector.holgus103.debtcollector.activities;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import debtcollector.holgus103.debtcollector.R;
import debtcollector.holgus103.debtcollector.db.dao.ContactsDao;
import debtcollector.holgus103.debtcollector.db.tables.ContactsTable;

public class Contacts extends DebtCollectorMenuActivity implements AdapterView.OnItemClickListener, View.OnClickListener {
    static final int PICK_CONTACT = 1;
    private ListView listView;
    private CursorAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        this.listView  = (ListView) findViewById(R.id.listView);
        this.adapter =  new SimpleCursorAdapter(this,
                R.layout.simple_list_item,
                ContactsDao.getContacts(),
                new String[] {ContactsTable.DISPLAY_NAME, ContactsTable.BALANCE},
                new int[] {R.id.nameView, R.id.balanceView}
        );
        this.listView.setAdapter(this.adapter);
        this.addListeners();
    }

    @Override
    protected void loadData() {
        Cursor cursor = this.adapter.swapCursor(ContactsDao.getContacts());
        this.adapter.notifyDataSetChanged();
        cursor.close();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == Contacts.PICK_CONTACT){
            if(resultCode == RESULT_OK){
                ContentResolver resolver = getContentResolver();
                if(resolver == null){
                    return;
                }
                Cursor cursor = resolver.query(data.getData(), null, null, null, null);
                if(cursor == null){
                    return;
                }
                if(cursor.moveToFirst()){
                    ContactsDao model = new ContactsDao(
                            cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID)),
                            cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
                    );
                    model.insert();
                }
                cursor.close();
                this.loadData();
            }
        }
    }

    private void addListeners(){
        Button btn = (Button)this.findViewById(R.id.add_contact_btn);
        if(btn != null) {
            btn.setOnClickListener(this);
        }
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

    @Override
    public void onClick(View v) {
        startActivityForResult(new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI), PICK_CONTACT);
    }
}
