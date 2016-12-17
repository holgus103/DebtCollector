package debtcollector.holgus103.debtcollector.dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import debtcollector.holgus103.debtcollector.R;

/**
 * Created by Kuba on 16/12/2016.
 */
public class DebtSettledDialog extends DialogFragment {
    public interface IDebtSettledDialogListener {
        public void onSuccess(DialogInterface dialog, int which);
    }

    private IDebtSettledDialogListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);
        AlertDialog.Builder builder = new AlertDialog.Builder(this.getActivity());
        builder.setMessage(R.string.debt_settled_message)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DebtSettledDialog.this.listener.onSuccess(dialog, which);
                    }
                })
                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DebtSettledDialog.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.listener = (IDebtSettledDialogListener) activity;
    }
}
