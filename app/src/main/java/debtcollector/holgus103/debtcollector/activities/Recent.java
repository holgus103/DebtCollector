package debtcollector.holgus103.debtcollector.activities;

import android.app.DialogFragment;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import debtcollector.holgus103.debtcollector.R;
import debtcollector.holgus103.debtcollector.db.dao.TransactionDao;
import debtcollector.holgus103.debtcollector.db.tables.ContactsTable;
import debtcollector.holgus103.debtcollector.db.tables.TransactionTable;
import debtcollector.holgus103.debtcollector.dialogs.DebtSettledDialog;

public class Recent extends DebtCollectorActivity implements DebtSettledDialog.IDebtSettledDialogListener, AdapterView.OnItemClickListener {

    private Integer clickedTransactionID;
    private SimpleCursorAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        this.
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recent);

        ListView listView = (ListView)this.findViewById(R.id.recentTransactionsListView);
        this.adapter = new SimpleCursorAdapter(this,
                R.layout.simple_list_item,
                TransactionDao.getRecentTransactions(database),
                new String[] {TransactionTable.TITLE, TransactionTable.AMOUNT},
                new int[] {R.id.nameView, R.id.balanceView}
        );
        listView.setAdapter(adapter);


        listView.setOnItemClickListener(this);
    }

    @Override
    public void onSuccess(DialogInterface dialog, int which) {
        TransactionDao transaction = new TransactionDao(database, this.clickedTransactionID);
        transaction.markAsSettled(database);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Cursor cursor = (Cursor)this.adapter.getItem(position);
        this.clickedTransactionID = cursor.getInt(cursor.getColumnIndex("_id"));
        DialogFragment fragment = new DebtSettledDialog();
        fragment.show(this.getFragmentManager(),"JUDEPOWER");

    }
}
