package com.ratchanon.lab6_sqlite;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomHolder> {

    private Context mContext;
    private List<CustomItem> mItems;

    public CustomAdapter(Context context, List<CustomItem> items) {
        mContext = context;
        mItems = items;
    }

    public interface OnItemClickListener {
        void onItemClick(View item, int position);
    }

    private OnItemClickListener mListener;
    public void setOnClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    @NonNull
    @Override
    public CustomHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);

        final View view = inflater.inflate(R.layout.component_card, parent, false);
        final CustomHolder viewHolder = new CustomHolder(view);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null) {
//                    int pos = viewHolder.getAdapterPosition();
//                    String str = viewHolder.textView1.getText().toString();
//                    str += "  :  " + viewHolder.textView2.getText().toString();
//                    if(pos != RecyclerView.NO_POSITION) {
//                        Toast.makeText(mContext, str, Toast.LENGTH_SHORT).show();
//                    }
                    int pos = viewHolder.getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {
                        mListener.onItemClick(view, pos);
                    }
                }

            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CustomHolder viewHolder, int position) {
        CustomItem item = mItems.get(position);
        viewHolder.HolderTxt1.setText(item.ReceiveTxt1);
        viewHolder.HolderTxt2.setText(item.ReceiveTxt2);
        viewHolder.HolderTxt3.setText(item.ReceiveTxt3);
    }

}