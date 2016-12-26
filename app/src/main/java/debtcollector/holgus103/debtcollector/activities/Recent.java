package debtcollector.holgus103.debtcollector.activities;

import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import java.util.HashSet;
import java.util.Set;

import debtcollector.holgus103.debtcollector.R;
import debtcollector.holgus103.debtcollector.db.dao.TransactionDao;
import debtcollector.holgus103.debtcollector.db.tables.ContactsTable;
import debtcollector.holgus103.debtcollector.db.tables.TransactionTable;
import debtcollector.holgus103.debtcollector.dialogs.DebtSettledDialog;

public class Recent extends DebtCollectorMenuActivity implements DebtSettledDialog.IDebtSettledDialogListener, AdapterView.OnItemClickListener {

    // save settled transactions to avoid countless reloads
    Set<Integer> settledTransactions = new HashSet<>();
    private Integer clickedTransactionID;
    private SimpleCursorAdapter adapter;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recent);
        this.listView  = (ListView)this.findViewById(R.id.recentTransactionsListView);
        this.adapter = new SimpleCursorAdapter(this,
                R.layout.simple_list_item,
                TransactionDao.getRecentTransactions(),
                new String[] {TransactionTable.TITLE, TransactionTable.AMOUNT},
                new int[] {R.id.nameView, R.id.balanceView}
        );
        this.listView.setAdapter(this.adapter);
        listView.setOnItemClickListener(this);
    }

    protected void loadData(){
        Cursor cursor = this.adapter.swapCursor(TransactionDao.getRecentTransactions());
        this.adapter.notifyDataSetChanged();
        cursor.close();
    }

    @Override
    public void onSuccess(DialogInterface dialog, int which) {
        TransactionDao transaction = new TransactionDao(this.clickedTransactionID);
        transaction.markAsSettled();
        this.settledTransactions.add(this.clickedTransactionID);
        this.loadData();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Cursor cursor = (Cursor)this.adapter.getItem(position);
        this.clickedTransactionID = cursor.getInt(cursor.getColumnIndex("_id"));
        if(TransactionDao.isSettled(cursor) || this.settledTransactions.contains(this.clickedTransactionID)) {
            return;
        }
        DialogFragment fragment = new DebtSettledDialog();
        fragment.show(this.getFragmentManager(), "RANDOMSTUFF");

    }
}
