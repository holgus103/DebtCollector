package debtcollector.holgus103.debtcollector.activities;

import android.os.Bundle;
import android.widget.Adapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import debtcollector.holgus103.debtcollector.R;
import debtcollector.holgus103.debtcollector.db.dao.TransactionDao;
import debtcollector.holgus103.debtcollector.db.tables.TransactionTable;

public class Archive extends DebtCollectorActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_archive);
        ListView listView = (ListView)this.findViewById(R.id.archivedTransactionsListView);
        ListAdapter adapter = new SimpleCursorAdapter(this,
                R.layout.simple_list_item,
                TransactionDao.getResolvedTransactions(database),
                new String[] {TransactionTable.TITLE, TransactionTable.AMOUNT},
                new int[] {R.id.nameView, R.id.balanceView}
        );
        listView.setAdapter(adapter);


    }
}
