package debtcollector.holgus103.debtcollector.activities;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import debtcollector.holgus103.debtcollector.R;
import debtcollector.holgus103.debtcollector.db.dao.TransactionDao;
import debtcollector.holgus103.debtcollector.db.tables.ContactsTable;
import debtcollector.holgus103.debtcollector.db.tables.TransactionTable;

public class Recent extends DebtCollectorActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        this.
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recent);

        ListView listView = (ListView)this.findViewById(R.id.recentTransactionsListView);
        listView.setAdapter(new SimpleCursorAdapter(this,
                R.layout.simple_list_item,
                TransactionDao.getTransactions(database),
                new String[] {TransactionTable.TITLE, TransactionTable.AMOUNT},
                new int[] {R.id.nameView, R.id.balanceView}
        ));
    }
}
