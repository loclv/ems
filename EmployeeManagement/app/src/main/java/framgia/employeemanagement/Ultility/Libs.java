package framgia.employeemanagement.Ultility;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import framgia.employeemanagement.DisplayEmployeeDetailActivity;
import framgia.employeemanagement.R;

/**
 * Created by FRAMGIA\luu.vinh.loc on 15/10/2015.
 */
public class Libs {
    private Context c;
    private static final int EDITMODE = 3;

    public Libs(Context context) {
        c = context;
    }

    public void longClickAlert(final long employeeID) {
        AlertDialog.Builder builder = new AlertDialog.Builder(c);
        builder
            .setMessage(c.getString(R.string.question_edit_employee))
            .setIcon(android.R.drawable.ic_dialog_alert)
            .setPositiveButton(c.getString(R.string.yes),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //Yes button clicked, do something
                        Intent intent =
                            new Intent(c, DisplayEmployeeDetailActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putInt(c.getString(R.string.display_mode), EDITMODE);
                        bundle.putLong(DisplayEmployeeDetailActivity.EMPLOYEE_ID, employeeID);
                        intent.putExtras(bundle);
                        c.startActivity(intent);
                    }
                })
            .setNegativeButton(c.getString(R.string.no), null)
            .show();
    }
}
