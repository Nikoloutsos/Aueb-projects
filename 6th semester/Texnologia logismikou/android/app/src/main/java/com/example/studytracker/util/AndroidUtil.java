package com.example.studytracker.util;

import android.content.Context;
import android.content.DialogInterface;

import androidx.appcompat.app.AlertDialog;

import static android.content.DialogInterface.BUTTON_POSITIVE;

/**
 * <p>Class containing android related methods.
 * By using it we follow DRY principle (Don't Repeat Yourself)</p>
 */
public class AndroidUtil {

    public static void showDialog(final Context context, String title, String description, String buttonTxt, final Runnable runnable) {
        AlertDialog alertDialog1 = new AlertDialog.Builder(
                context).create();

        // Setting Dialog Title
        alertDialog1.setTitle(title);

        // Setting Dialog Message
        alertDialog1.setMessage(description);
        alertDialog1.setButton(BUTTON_POSITIVE, buttonTxt, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        // Setting OK Button
        alertDialog1.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                runnable.run();
            }
        });
        alertDialog1.setCanceledOnTouchOutside(false);

        // Showing Alert Message
        alertDialog1.show();
    }


}
