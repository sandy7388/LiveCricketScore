package com.sandy.wayzon_android.livecricketscore.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;

import com.sandy.wayzon_android.livecricketscore.R;
import com.sandy.wayzon_android.livecricketscore.model.BowlerDetails;

import java.util.ArrayList;

public class SpinnerAdapter extends BaseAdapter {
    Context context;
    private LayoutInflater mInflater;


    private ArrayList<BowlerDetails> bowlerDetailsArrayList;

    public SpinnerAdapter(Context context, ArrayList<BowlerDetails> bowlerDetailsArrayList) {
        this.context = context;
        this.bowlerDetailsArrayList = bowlerDetailsArrayList;
    }

    @Override
    public int getCount() {
        return bowlerDetailsArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ListContent holder;
        View v = convertView;
        if (v == null) {
            mInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            v = mInflater.inflate(R.layout.adapter_match_details, null);
            holder = new ListContent();
            holder.text = (CheckBox) v.findViewById(R.id.checkBoxNewBowler);

            v.setTag(holder);
        } else {
            holder = (ListContent) v.getTag();
        }

        holder.text.setText((CharSequence) bowlerDetailsArrayList.get(position));

        return v;
    }
}

class ListContent {
    CheckBox text;
}