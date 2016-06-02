package Utitilies;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import net.grapesoft.www.telcel.R;

/**
 * Created by Mugauli on 01/06/2016.
 */

public class AlertDialogManager {

    public void showAlertDialog(Context context, String title, String message,
                                Boolean status) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();

        // Setting Dialog Title
        alertDialog.setTitle(title);

        // Setting Dialog Message
        alertDialog.setMessage(message);

        if(status != null)
            // Setting alert dialog icon
            //alertDialog.setIcon((status) ? R.drawable.success : R.drawable.fail);

        // Setting OK Button
        alertDialog.setButton("Entendido", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }

    public void showAlertDialog2(Context context, String title, String message,
                                 Boolean status) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();

        // Setting Dialog Title
        alertDialog.setTitle(title);

        // Setting Dialog Message
        alertDialog.setMessage(message);

        if(status != null)
            // Setting alert dialog icon
           // alertDialog.setIcon((status) ? R.drawable.success : R.drawable.fail);

        // Setting OK Button

        // Showing Alert Message
        alertDialog.show();
    }

}
