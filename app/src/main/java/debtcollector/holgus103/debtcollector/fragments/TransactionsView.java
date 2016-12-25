package debtcollector.holgus103.debtcollector.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import debtcollector.holgus103.debtcollector.R;
import debtcollector.holgus103.debtcollector.activities.DebtCollectorActivity;
import debtcollector.holgus103.debtcollector.activities.TransactionDetails;
import debtcollector.holgus103.debtcollector.db.dao.TransactionDao;
import debtcollector.holgus103.debtcollector.db.tables.TransactionTable;

/**
 * Created by Kuba on 24/12/2016.
 */
public class TransactionsView extends Fragment implements AdapterView.OnItemClickListener {
    private ListAdapter adapter;
    private DebtCollectorActivity parent;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.transactions_view, container, false);
    }

    @Override
    public void onAttach(Activity context) {
        super.onAttach(context);
        try{
            this.parent = (DebtCollectorActivity) context;
        }
        catch(ClassCastException e){

        }
    }

    public void setCursor(Cursor cursor){
        this.adapter = new SimpleCursorAdapter(getActivity(),
                R.layout.simple_list_item,
                cursor,
                new String[] {TransactionTable.TITLE, TransactionTable.AMOUNT},
                new int[] {R.id.nameView, R.id.balanceView}
        );

        ListView listView = (ListView) this.getView().findViewById(R.id.transactionsView);
        listView.setAdapter(this.adapter);
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Cursor cursor = (Cursor)this.adapter.getItem(position);
        if(this.parent != null)
            this.parent.startActivity(TransactionDetails.class, cursor.getInt(cursor.getColumnIndex("_id")));
    }
}
