package debtcollector.holgus103.debtcollector.fragments;

import android.app.Fragment;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import debtcollector.holgus103.debtcollector.R;
import debtcollector.holgus103.debtcollector.db.dao.TransactionDao;
import debtcollector.holgus103.debtcollector.db.tables.TransactionTable;

/**
 * Created by Kuba on 24/12/2016.
 */
public class TransactionsView extends Fragment {
    private ListAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.transactions_view, container, false);
    }

    public void setCursor(Cursor cursor){
        this.adapter = new SimpleCursorAdapter(getActivity(),
                R.layout.simple_list_item,
                cursor,
                new String[] {TransactionTable.TITLE, TransactionTable.AMOUNT},
                new int[] {R.id.nameView, R.id.balanceView}
        );

        ListView listview = (ListView) this.getView().findViewById(R.id.transactionsView);
        listview.setAdapter(this.adapter);
    }
}
