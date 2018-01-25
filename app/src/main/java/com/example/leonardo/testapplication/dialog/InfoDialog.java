package com.example.leonardo.testapplication.dialog;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import com.example.leonardo.testapplication.SQLite.InfoContract;
import com.example.leonardo.testapplication.receiver.AlarmReceiver;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by leonardo on 24/01/18.
 */

public class InfoDialog extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        /* AlertDialog */
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("Tabla SQLite");
        builder.setItems(getItems(), new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                sendNotification(Integer.parseInt(getFecha(i)), getData(i));
            }
        });


        return builder.create();
    }

    private CharSequence[] getItems() {
        InfoContract.InfoDbHelper dbHelper = new InfoContract.InfoDbHelper(getContext());
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String consultaSQL = "SELECT * FROM " + InfoContract.InfoEntry.TABLE_NAME;
        Cursor cursor = db.rawQuery(consultaSQL, null);

        CharSequence[] items = new CharSequence[cursor.getCount()];

        for (int i = 0; cursor.moveToNext(); i++) {
            items[i] = cursor.getString(cursor.getColumnIndex(InfoContract.InfoEntry.DATA)) + " - "
                    + cursor.getString(cursor.getColumnIndex(InfoContract.InfoEntry.FECHA));
        }

        return items;
    }

    private String getFecha(int fecha) {
        InfoContract.InfoDbHelper dbHelper = new InfoContract.InfoDbHelper(getContext());
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String consultaSQL = "SELECT " + InfoContract.InfoEntry.FECHA + " FROM "
                + InfoContract.InfoEntry.TABLE_NAME;
        Cursor cursor = db.rawQuery(consultaSQL, null);

        String[] items = new String[cursor.getCount()];

        for (int i = 0; cursor.moveToNext(); i++) {
            items[i] = cursor.getString(cursor.getColumnIndex(InfoContract.InfoEntry.FECHA));
        }

        return items[fecha];
    }

    private String getData(int data) {
        InfoContract.InfoDbHelper dbHelper = new InfoContract.InfoDbHelper(getContext());
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String consultaSQL = "SELECT " + InfoContract.InfoEntry.DATA + " FROM "
                + InfoContract.InfoEntry.TABLE_NAME;
        Cursor cursor = db.rawQuery(consultaSQL, null);

        String[] items = new String[cursor.getCount()];

        for (int i = 0; cursor.moveToNext(); i++) {
            items[i] = cursor.getString(cursor.getColumnIndex(InfoContract.InfoEntry.DATA));
        }

        return items[data];
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void sendNotification(int time, String data) {
        AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);

        Intent notificationIntent = new Intent(getContext(), AlarmReceiver.class);
        notificationIntent.putExtra("time", time);
        notificationIntent.putExtra("data", data);

        PendingIntent broadcast = PendingIntent.getBroadcast(getContext(), 100, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.SECOND, time);

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), broadcast);
    }
}
