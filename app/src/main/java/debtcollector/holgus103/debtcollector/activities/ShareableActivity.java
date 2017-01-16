package debtcollector.holgus103.debtcollector.activities;

import android.content.Intent;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.ShareActionProvider;
import android.view.Menu;
import android.view.MenuItem;

import debtcollector.holgus103.debtcollector.R;

/**
 * Created by Kuba on 16/01/2017.
 */

public abstract class ShareableActivity extends DebtCollectorActivity {
    private ShareActionProvider shareActionProvider;


    private void setShareIntent() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
            this.getShareActionProvider().setShareIntent(this.populateShareIntent(intent));
    }

    protected abstract Intent populateShareIntent(Intent intent);

    @Override
    public boolean onCreateOptionsMenu(Menu menu){

        MenuItem item = menu.add(0, R.id.share, 0, R.string.share);
//        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        this.shareActionProvider = this.getShareActionProvider();
        MenuItemCompat.setActionProvider(item, this.shareActionProvider);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void loadData() {
        this.setShareIntent();
    }

    public ShareActionProvider getShareActionProvider() {
        return shareActionProvider == null ? new ShareActionProvider(this) : this.shareActionProvider;
    }
}
