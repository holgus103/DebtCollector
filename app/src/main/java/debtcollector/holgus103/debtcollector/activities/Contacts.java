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
                new int[] {R.id.nameView, R.id.balanceView},
                0
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



    private void addListeners(){
        this.listView.setOnItemClickListener(this);
        ((Button)this.findViewById(R.id.add_transaction_btn)).setOnClickListener(this);

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
        this.startActivity(AddTransaction.class, R.id.add_transaction);
    }
}
