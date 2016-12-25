package debtcollector.holgus103.debtcollector.activities;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import debtcollector.holgus103.debtcollector.R;
import debtcollector.holgus103.debtcollector.db.dao.TransactionDao;
import debtcollector.holgus103.debtcollector.db.tables.TransactionTable;
import debtcollector.holgus103.debtcollector.fragments.TransactionsView;

public class Archive extends DebtCollectorActivity  {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_archive);


        ((TransactionsView)this.getFragmentManager().findFragmentById(R.id.transactionsViewFragment))
                .setCursor(
                        TransactionDao.getResolvedTransactions()
                );



    }

}
