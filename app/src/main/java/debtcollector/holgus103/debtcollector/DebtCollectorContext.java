package debtcollector.holgus103.debtcollector;

import android.app.Application;
import android.content.Context;

import debtcollector.holgus103.debtcollector.activities.DebtCollectorActivity;

/**
 * Created by Kuba on 24/12/2016.
 */
public class DebtCollectorContext extends Application{
    public static Context ctx;

    @Override
    public void onCreate() {
        super.onCreate();
        ctx = getApplicationContext();
    }

    public static Context getContext(){
        return ctx;
    }
}
