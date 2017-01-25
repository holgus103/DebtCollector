package debtcollector.holgus103.debtcollector.activities;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import debtcollector.holgus103.debtcollector.R;

/**
 * Created by Kuba on 26/12/2016.
 */
public abstract class DebtCollectorMenuActivity extends DebtCollectorActivity {
    private int selectedMenuItem;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();

        if(bundle != null) {
            this.selectedMenuItem = bundle.getInt(DebtCollectorActivity.ITEM_ID);
        }
        if(this.selectedMenuItem == 0){
            this.selectedMenuItem = R.id.contacts;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // super builds menu
        boolean res = super.onCreateOptionsMenu(menu);
        if(res) {
            MenuItem item = menu.findItem(this.selectedMenuItem);
            if(item != null){
                item.setEnabled(false);
            }
        }
        return res;
    }
}
