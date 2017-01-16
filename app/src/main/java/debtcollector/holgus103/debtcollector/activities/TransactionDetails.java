package debtcollector.holgus103.debtcollector.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.ShareActionProvider;
import android.view.View;
import android.widget.Button;

import java.text.SimpleDateFormat;
import java.util.Date;

import debtcollector.holgus103.debtcollector.R;
import debtcollector.holgus103.debtcollector.db.dao.ContactsDao;
import debtcollector.holgus103.debtcollector.db.dao.TransactionDao;

public class TransactionDetails extends ShareableActivity implements View.OnClickListener {

    private TransactionDao model;
    private int transactionID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_details);
        Bundle bundle = getIntent().getExtras();
        this.transactionID = bundle.getInt(DebtCollectorActivity.ITEM_ID);
        this.loadData();
        ((Button)this.findViewById(R.id.markAsSettledButton)).setOnClickListener(this);

    }

    private void fillViewWithData(TransactionDao model){
        SimpleDateFormat format = new SimpleDateFormat(getString(R.string.date_format));

        this.fillTextView(R.id.transactionTitleTextView, model.getTitle());
        ContactsDao contact = new ContactsDao(model.getContactID());
        this.fillTextView(R.id.contactTextView, contact.getDisplayName());
        this.fillTextView(R.id.transactionAmountTextView, model.getAmount().toString());
        this.fillTextView(R.id.descriptionTextView, model.getDescription());
        this.fillTextView(R.id.statusTextView, model.getSettled());
        // convert Unix timestamp to date
        this.fillTextView(R.id.addedTextView,
                format.format(
                        new Date(model.getDateAdded() * 1000L)
                )
        );

        this.fillTextView(R.id.closedTextView,
                model.isSettled() ?
                format.format(
                    new Date( model.getDateClosed() * 1000L )
                ) : ""

        );

        if(model.isSettled()) {
            this.disableButton();
        }

    }


    @Override
    public void onClick(View v) {
        if(!this.model.isSettled()) {
            this.model.markAsSettled();
            this.disableButton();
            this.fillTextView(R.id.statusTextView, model.getSettled());
        }
    }

    private void disableButton(){
            Button btn = (Button)this.findViewById(R.id.markAsSettledButton);
            if(btn != null){
                btn.setEnabled(false);
            }
    }

    @Override
    protected Intent populateShareIntent(Intent intent) {
        intent.putExtra(Intent.EXTRA_TEXT, getString(R.string.transaction_details_share_message) + this.model.getAmount() + " - " + this.model.getTitle());
        intent.setType("text/plain");
        return intent;
    }

    @Override
    protected void loadData() {
        this.model = new TransactionDao(this.transactionID);
        this.fillViewWithData(model);
        super.loadData();
    }
}
