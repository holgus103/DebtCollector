package debtcollector.holgus103.debtcollector.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import debtcollector.holgus103.debtcollector.R;
import debtcollector.holgus103.debtcollector.db.dao.ContactsDao;
import debtcollector.holgus103.debtcollector.db.dao.TransactionDao;
import debtcollector.holgus103.debtcollector.db.tables.TransactionTable;

public class ContactDetails extends DebtCollectorActivity {

    private ListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_details);
        Bundle bundle = this.getIntent().getExtras();
        String id = bundle.getString(DebtCollectorActivity.ITEM_ID);
        ContactsDao model = new ContactsDao(database, id);
        this.fillViewWithData(model);
        
        this.adapter = new SimpleCursorAdapter(this,
                R.layout.simple_list_item,
                TransactionDao.getUnsettledTransactionsForContactID(database, model.getContactID()),
                new String[] {TransactionTable.TITLE, TransactionTable.AMOUNT},
                new int[] {R.id.nameView, R.id.balanceView}
        );
        ((ListView) this.findViewById(R.id.contactTransactionsListView)).setAdapter(this.adapter);
    }

    private void fillViewWithData(ContactsDao model) {
        this.fillTextView(R.id.balanceTextView, model.getBalance().toString());
        this.fillTextView(R.id.contactNameTextView, model.getDisplayName().toString());
    }
}
