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

public class Archive extends DebtCollectorActivity implements AdapterView.OnItemClickListener {

    private SimpleCursorAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_archive);
        ListView listView = (ListView)this.findViewById(R.id.archivedTransactionsListView);
        this.adapter = new SimpleCursorAdapter(this,
                R.layout.simple_list_item,
                TransactionDao.getResolvedTransactions(database),
                new String[] {TransactionTable.TITLE, TransactionTable.AMOUNT},
                new int[] {R.id.nameView, R.id.balanceView}
        );
        listView.setAdapter(this.adapter);

        listView.setOnItemClickListener(this);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Cursor cursor = (Cursor)this.adapter.getItem(position);
        this.startActivity(TransactionDetails.class, cursor.getInt(cursor.getColumnIndex("_id")));
    }
}
