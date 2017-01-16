package debtcollector.holgus103.debtcollector.activities;

import android.app.Fragment;
import android.content.Intent;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ShareActionProvider;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import debtcollector.holgus103.debtcollector.R;
import debtcollector.holgus103.debtcollector.db.dao.ContactsDao;
import debtcollector.holgus103.debtcollector.db.dao.TransactionDao;
import debtcollector.holgus103.debtcollector.db.tables.TransactionTable;
import debtcollector.holgus103.debtcollector.enums.TransactionDirection;
import debtcollector.holgus103.debtcollector.fragments.TransactionsView;

public class ContactDetails extends ShareableActivity implements View.OnClickListener {

    private String contactID;
    private TransactionDirection currentTab;
    private ShareActionProvider shareActionProvider;
    private ContactsDao model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_details);
        this.setStringForView(R.id.debtBtn, TransactionDirection.TheyOweMe.toString());
        this.setStringForView(R.id.creditBtn, TransactionDirection.IOweThem.toString());
        Bundle bundle = this.getIntent().getExtras();
        this.contactID = bundle.getString(DebtCollectorActivity.ITEM_ID);
        this.currentTab = TransactionDirection.TheyOweMe;
        this.loadData();
        this.assignListeners();
    }

    private void assignListeners() {
        ((Button) this.findViewById(R.id.debtBtn)).setOnClickListener(this);
        ((Button) this.findViewById(R.id.creditBtn)).setOnClickListener(this);
    }


    private void fillViewWithData(ContactsDao model) {
        this.fillTextView(R.id.balanceTextView, model.getBalance().toString());
        this.fillTextView(R.id.contactNameTextView, model.getDisplayName().toString());
    }


    @Override
    protected Intent populateShareIntent(Intent intent) {
        intent.putExtra(Intent.EXTRA_TEXT, getString(R.string.contact_details_share_message) + this.model.getBalance());
        intent.setType("text/plain");
        return intent;
    }


    private void reloadTransactions(){
        ((TransactionsView)getFragmentManager().findFragmentById(R.id.transactionsViewFragment))
                .setCursor(
                        TransactionDao.getUnsettledTransactionsForContactID(this.model.getContactID(), this.currentTab)
                );
    }
    @Override
    protected void loadData(){
        this.model = new ContactsDao(this.contactID);
        this.fillViewWithData(this.model);
        this.reloadTransactions();
        super.loadData();
    }


    @Override
    public void startActivity(Class<?> cls, int itemId) {
        Intent intent = new Intent(this, cls);
        if(cls == AddTransaction.class || cls == Archive.class){
            intent.putExtra(DebtCollectorActivity.CONTACT_ID, this.contactID);
        }
        intent.putExtra(DebtCollectorActivity.ITEM_ID, itemId);
        this.startActivity(intent);
    }




    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.debtBtn:
                this.currentTab = TransactionDirection.TheyOweMe;
                this.reloadTransactions();
                break;
            case R.id.creditBtn:
                this.currentTab = TransactionDirection.IOweThem;
                this.reloadTransactions();
                break;

        }
    }
}
