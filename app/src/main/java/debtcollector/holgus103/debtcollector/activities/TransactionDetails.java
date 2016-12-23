package debtcollector.holgus103.debtcollector.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

import debtcollector.holgus103.debtcollector.R;
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
        this.fillTextView(R.id.transactionTitleTextView, model.getTitle());

        this.fillTextView(R.id.contactTextView, model.getContactID());
        this.fillTextView(R.id.transactionAmountTextView, model.getAmount().toString());
        this.fillTextView(R.id.descriptionTextView, model.getDescription());
        this.fillTextView(R.id.addedTextView, model.getDateAdded().toString());
        this.fillTextView(R.id.closedTextView, model.getDateClosed().toString());
        this.fillTextView(R.id.statusTextView, model.getSettled());
    }

    private void fillTextView(int id, String text){
        ((TextView) this.findViewById(id)).setText(text);
    }
}
