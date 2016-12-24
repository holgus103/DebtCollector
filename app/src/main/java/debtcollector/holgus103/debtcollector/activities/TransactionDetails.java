package debtcollector.holgus103.debtcollector.activities;

import android.os.Bundle;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

import debtcollector.holgus103.debtcollector.R;
import debtcollector.holgus103.debtcollector.db.dao.ContactsDao;
import debtcollector.holgus103.debtcollector.db.dao.TransactionDao;

public class TransactionDetails extends DebtCollectorActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_details);
        Bundle bundle = getIntent().getExtras();
        int id = bundle.getInt(DebtCollectorActivity.ITEM_ID);
        TransactionDao model = new TransactionDao(database, id);

        this.fillViewWithData(model);
    }

    private void fillViewWithData(TransactionDao model){
        SimpleDateFormat format = new SimpleDateFormat(getString(R.string.date_format));

        this.fillTextView(R.id.transactionTitleTextView, model.getTitle());
        ContactsDao contact = new ContactsDao(database, model.getContactID());
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


    }


}
