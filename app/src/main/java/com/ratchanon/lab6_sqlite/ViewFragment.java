package com.ratchanon.lab6_sqlite;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;


public class ViewFragment extends Fragment {

    RecyclerView rcv;
    Cursor mCursor;
    View view;

    @SuppressLint("Recycle")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_view, container, false);
        DatabaseHelper databaseHelper = new DatabaseHelper(getContext());

        SQLiteDatabase mDb = databaseHelper.getReadableDatabase();

//        databaseHelper.logAllTables();

        try {
            mCursor = mDb.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_NAME, null);
        } catch (android.database.sqlite.SQLiteException e) {
            databaseHelper.importDatabase();
            mCursor = mDb.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_NAME, null);
        }


        final ArrayList<CustomItem> items = new ArrayList<>();
        mCursor.moveToFirst();
        while (!mCursor.isAfterLast()) {
            items.add(new CustomItem(
                    "ชื่อ " +
                    mCursor.getString(mCursor.getColumnIndex(DatabaseHelper.COL_NAME)) + " "
                            + mCursor.getString(mCursor.getColumnIndex(DatabaseHelper.COL_LASTNAME)),
                    "ชั้น "+ mCursor.getString(mCursor.getColumnIndex(DatabaseHelper.COL_YEAR)),
                    mCursor.getString(mCursor.getColumnIndex(DatabaseHelper.COL_ID))
            ));
            mCursor.moveToNext();
        }


        CustomAdapter adapter = new CustomAdapter(getContext(), items);
        rcv = view.findViewById(R.id.list_view);
        rcv.setAdapter(adapter);
        rcv.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter.setOnClickListener(new CustomAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(View item, int position) {
                String str = items.get(position).ReceiveTxt1 + " - " + items.get(position).ReceiveTxt2;
                Toast.makeText(view.getContext(), str, Toast.LENGTH_SHORT).show();

                String id = items.get(position).ReceiveTxt3;
                String name = items.get(position).ReceiveTxt1;
                String lastname = items.get(position).ReceiveTxt2;
                Log.i("TAG", "Click");


//                Intent intent = new Intent(getApplicationContext(), Act_Edit.class);
//
//                intent.putExtra("ID", id);
//                intent.putExtra("NAME", name);
//                intent.putExtra("LASTNAME", lastname);
//                intent.putExtra("YEAR", id);
//                intent.putExtra("SEX", id);
//                intent.putExtra("BOOK", id);
//                intent.putExtra("SWIMMING", id);


//                startActivity(intent);
            }
        });

        // Inflate the layout for this fragment
        return view;
    }
}