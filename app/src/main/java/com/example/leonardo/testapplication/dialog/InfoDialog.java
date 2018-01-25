package com.example.leonardo.testapplication.dialog;

import android.app.Dialog;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

/**
 * Created by leonardo on 24/01/18.
 */

public class InfoDialog extends DialogFragment {

    private static final String CURSOR_SERIAL = "cursor_sqlite";


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Cursor CURSOR = (Cursor) getArguments().getSerializable(CURSOR_SERIAL);

        /* AlertDialog */
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("Tabla SQLite");


        return super.onCreateDialog(savedInstanceState);
    }

    private CharSequence[] getItems(Cursor cursor) {
        return null;
    }
}
