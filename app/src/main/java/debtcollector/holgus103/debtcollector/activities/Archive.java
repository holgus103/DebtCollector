package debtcollector.holgus103.debtcollector.activities;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import debtcollector.holgus103.debtcollector.R;
import debtcollector.holgus103.debtcollector.db.dao.ContactsDao;
import debtcollector.holgus103.debtcollector.db.dao.TransactionDao;
import debtcollector.holgus103.debtcollector.db.tables.TransactionTable;
import debtcollector.holgus103.debtcollector.fragments.TransactionsView;

public class Archive extends DebtCollectorMenuActivity {


    private String contactID;
    private ContactsDao model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_archive);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            this.contactID = bundle.getString(DebtCollectorActivity.CONTACT_ID);
            if (this.contactID != null) {
                this.model = new ContactsDao(this.contactID);
                this.setStringForView(R.id.contactNameLbl, this.getString(R.string.history_for) + model.getDisplayName());
            }
            else {
                this.setStringForView(R.id.contactNameLbl, this.getClass().getSimpleName());
            }
        }

        this.loadData();
    }

    @Override
    protected void loadData() {
        ((TransactionsView) this.getFragmentManager().findFragmentById(R.id.transactionsViewFragment))
                .setCursor(
                        TransactionDao.getResolvedTransactions(this.contactID)
                );
    }
}
