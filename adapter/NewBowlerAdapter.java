package com.sandy.wayzon_android.livecricketscore.adapter;

import android.content.Context;
import android.database.DataSetObserver;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.sandy.wayzon_android.livecricketscore.R;
import com.sandy.wayzon_android.livecricketscore.model.BowlerDetails;

import java.util.ArrayList;

public class NewBowlerAdapter extends RecyclerView.Adapter<NewBowlerAdapter.NewBowlerHolder> {

    private ArrayList<BowlerDetails> bowlerDetailsArrayList;
    private Context context;

    public NewBowlerAdapter(ArrayList<BowlerDetails> bowlerDetailsArrayList, Context context) {
        this.bowlerDetailsArrayList = bowlerDetailsArrayList;
        this.context = context;
    }

    @Override
    public NewBowlerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_bowler_details,parent,false);

        return new NewBowlerHolder(view);
    }

    @Override
    public void onBindViewHolder(NewBowlerHolder holder, int position) {

        BowlerDetails bowlerDetails = bowlerDetailsArrayList.get(position);

        holder.checkBoxNewBowler.setText(bowlerDetails.getStrBowlerName());

    }

    @Override
    public int getItemCount() {
        return bowlerDetailsArrayList.size();
    }



    public class NewBowlerHolder extends RecyclerView.ViewHolder {

        private TextView textViewNewBowler;

        private CheckBox checkBoxNewBowler;
        public NewBowlerHolder(View itemView) {
            super(itemView);

            textViewNewBowler = itemView.findViewById(R.id.textViewNewBowler);

            checkBoxNewBowler = itemView.findViewById(R.id.checkBoxNewBowler);
        }
    }
}
