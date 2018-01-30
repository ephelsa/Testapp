package com.example.leonardo.testapplication.fragment;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.leonardo.testapplication.R;
import com.example.leonardo.testapplication.SQLite.InfoContract.InfoEntry;
import com.example.leonardo.testapplication.SQLite.InfoContract.InfoDbHelper;
import com.example.leonardo.testapplication.dialog.InfoDialog;

import me.toptas.fancyshowcase.FancyShowCaseQueue;
import me.toptas.fancyshowcase.FancyShowCaseView;
import me.toptas.fancyshowcase.FocusShape;
import me.toptas.fancyshowcase.OnCompleteListener;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link InfoFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link InfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InfoFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    /* CONST */
    private static final String DB_TAG = "Info_SQLite";
    private static final String DBHELPER_SERIAL = "dbhelper_sqlite";


    /* */
    private InfoDbHelper dbHelper;


    /* View Components */
    private View view;

    private EditText agregarET, fechaET;
    private Button agregarBtn, verBtn, borrarBtn;



    public InfoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment InfoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static InfoFragment newInstance(String param1, String param2) {
        InfoFragment fragment = new InfoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_info, container, false);

        agregarET = (EditText) view.findViewById(R.id.agregar_et);
        fechaET = (EditText) view.findViewById(R.id.fecha_et);
        agregarBtn = (Button) view.findViewById(R.id.agregar_btn);
        verBtn = (Button) view.findViewById(R.id.ver_btn);
        borrarBtn = (Button) view.findViewById(R.id.borrar_btn);

        dbHelper = new InfoDbHelper(getContext());

        agregarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String agregarString = agregarET.getText().toString();
                String fechaString = fechaET.getText().toString();

                agregarET.setText("");
                fechaET.setText("");

                writeOnDatabase(agregarString, fechaString);
            }
        });

        verBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isDatabaseVoid()) {
                    InfoDialog infoDialog = new InfoDialog();
                    infoDialog.show(getFragmentManager(), "INFO_DIALOG");

                    readOnDatabase();

                } else {
                    Toast.makeText(getContext(), "SQLite is void", Toast.LENGTH_SHORT).show();
                }
            }
        });

        borrarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


        return view;
    }

    private void writeOnDatabase(String data, String long_fecha) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();

        if (!data.isEmpty() && !long_fecha.isEmpty()) {
            values.put(InfoEntry.DATA, data);
            values.put(InfoEntry.FECHA, long_fecha);

            db.insertWithOnConflict(InfoEntry.TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_IGNORE);

            Log.i(DB_TAG, "Write");
            Toast.makeText(getContext(), "Data saved", Toast.LENGTH_SHORT).show();
        }
    }

    private void readOnDatabase() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String consultaSQL = "SELECT * FROM " + InfoEntry.TABLE_NAME;

        Cursor cursor = db.rawQuery(consultaSQL, null);
        Log.i(DB_TAG, "Read");

        while (cursor.moveToNext()) {
            Log.i(DB_TAG, "ID: " + cursor.getString(cursor.getColumnIndex(InfoEntry.ID)));

            Log.i(DB_TAG, "DATA: " + cursor.getString(cursor.getColumnIndex(InfoEntry.DATA)));
            Log.i(DB_TAG, "FECHA: " + cursor.getString(cursor.getColumnIndex(InfoEntry.FECHA)));
        }
    }

    private boolean isDatabaseVoid() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String consultaSQL = "SELECT * FROM " + InfoEntry.TABLE_NAME;

        Cursor cursor = db.rawQuery(consultaSQL, null);

        return !cursor.moveToNext();
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
