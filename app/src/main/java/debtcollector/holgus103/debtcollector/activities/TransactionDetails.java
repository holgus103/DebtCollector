package debtcollector.holgus103.debtcollector.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.text.SimpleDateFormat;
import java.util.Date;

import debtcollector.holgus103.debtcollector.R;
import debtcollector.holgus103.debtcollector.db.dao.ContactsDao;
import debtcollector.holgus103.debtcollector.db.dao.TransactionDao;

public class TransactionDetails extends DebtCollectorActivity implements View.OnClickListener {

    private TransactionDao model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_details);
        Bundle bundle = getIntent().getExtras();
        int id = bundle.getInt(DebtCollectorActivity.ITEM_ID);
        this.model = new TransactionDao(id);

        this.fillViewWithData(model);
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
                format.format(
                    new Date(model.getDateClosed() * 1000L)
                )
        );

        if(model.isSettled()) {
            this.disableButton();
        }

    }


    @Override
    public void onClick(View v) {
        this.model.markAsSettled();

    }

    private void disableButton(){
            ((Button)this.findViewById(R.id.markAsSettledButton))
                    .setEnabled(false);
    }
}
