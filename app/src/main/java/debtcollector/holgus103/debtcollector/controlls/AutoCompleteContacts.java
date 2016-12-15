package debtcollector.holgus103.debtcollector.controlls;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.AutoCompleteTextView;

/**
 * Created by Kuba on 15/12/2016.
 */
public class AutoCompleteContacts extends AutoCompleteTextView {
    public AutoCompleteContacts(Context context) {
        super(context);
    }

    @Override
    protected void replaceText(CharSequence text) {
        super.replaceText(text);
    }
}
