package debtcollector.holgus103.debtcollector.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import debtcollector.holgus103.debtcollector.R;

/**
 * Created by Kuba on 13/12/2016.
 */
public abstract class DebtCollectorActivity extends AppCompatActivity implements MenuItem.OnMenuItemClickListener{
    private static final String SELECTED_MENU_ITEM_ID = "SELECTED_MENU_ITEM_ID";
    private int selectedMenuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            this.selectedMenuItem = bundle.getInt(DebtCollectorActivity.SELECTED_MENU_ITEM_ID);
        }
        else{
            this.selectedMenuItem = R.id.contacts;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuItem[] items = new MenuItem[5];
        items[0] = menu.add(0, R.id.add_contact, 0, R.string.add_contact);
        items[1] = menu.add(0, R.id.add_transaction, 0, R.string.add_transaction);
        items[2] = menu.add(0, R.id.contacts, 0, R.string.contacts);
        items[3] = menu.add(0, R.id.recent, 0, R.string.recent);
        items[4] = menu.add(0, R.id.archive, 0, R.string.archive);

        for(MenuItem item:items){
            item.setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
            item.setOnMenuItemClickListener(this);
            if(item.getItemId() == this.selectedMenuItem)
                item.setEnabled(false);
        }


        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onMenuItemClick(MenuItem item) {
        int id = item.getItemId();
        switch(id){
            case R.id.add_contact:
                this.startActivity(AddContact.class, id);
                break;
            case R.id.add_transaction:
                this.startActivity(AddTransaction.class, id);
                break;
            case R.id.archive:
                this.startActivity(Archive.class, id);
                break;
            case R.id.contacts:
                this.startActivity(Contacts.class, id);
                break;
            case R.id.recent:
                this.startActivity(Recent.class, id);
                break;
        }
        return true;
    }

    private void startActivity(Class<?> cls, int itemId) {
        Intent intent = new Intent(this, cls);
        intent.putExtra(DebtCollectorActivity.SELECTED_MENU_ITEM_ID, itemId);
        this.startActivity(intent);
    }
}
