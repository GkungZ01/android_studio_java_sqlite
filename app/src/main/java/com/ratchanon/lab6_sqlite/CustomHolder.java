package com.ratchanon.lab6_sqlite;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class CustomHolder extends RecyclerView.ViewHolder {

    public TextView HolderTxt1;
    public TextView HolderTxt2;
    public TextView HolderTxt3;

    public CustomHolder(View view) {
        super(view);
        HolderTxt1 = view.findViewById(R.id.ShowTxt1);
        HolderTxt2 = view.findViewById(R.id.ShowTxt2);
        HolderTxt3 = view.findViewById(R.id.ShowTxt3);
    }
}