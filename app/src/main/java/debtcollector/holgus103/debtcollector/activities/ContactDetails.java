package debtcollector.holgus103.debtcollector.activities;

import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import debtcollector.holgus103.debtcollector.R;
import debtcollector.holgus103.debtcollector.db.dao.ContactsDao;
import debtcollector.holgus103.debtcollector.db.dao.TransactionDao;
import debtcollector.holgus103.debtcollector.db.tables.TransactionTable;
import debtcollector.holgus103.debtcollector.fragments.TransactionsView;

public class ContactDetails extends DebtCollectorActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_details);
        Bundle bundle = this.getIntent().getExtras();
        String id = bundle.getString(DebtCollectorActivity.ITEM_ID);
        ContactsDao model = new ContactsDao(id);
        this.fillViewWithData(model);


        ((TransactionsView)getFragmentManager().findFragmentById(R.id.transactionsViewFragment))
                .setCursor(
                    TransactionDao.getUnsettledTransactionsForContactID(model.getContactID())
                );

    }

    private void fillViewWithData(ContactsDao model) {
        this.fillTextView(R.id.balanceTextView, model.getBalance().toString());
        this.fillTextView(R.id.contactNameTextView, model.getDisplayName().toString());
    }
}
