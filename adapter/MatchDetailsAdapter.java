package com.sandy.wayzon_android.livecricketscore.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sandy.wayzon_android.livecricketscore.R;
import com.sandy.wayzon_android.livecricketscore.activity.TossSelectionActivity;
import com.sandy.wayzon_android.livecricketscore.model.MatchDetails;

import java.util.ArrayList;

public class MatchDetailsAdapter
        extends RecyclerView.Adapter<MatchDetailsAdapter.MatchDetailsHolder>
{

    private ArrayList<MatchDetails> matchDetailsArrayList;
    private Context context;

    public MatchDetailsAdapter(ArrayList<MatchDetails> matchDetailsArrayList, Context context) {
        this.matchDetailsArrayList = matchDetailsArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public MatchDetailsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_match_details,parent,false);
        return new MatchDetailsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MatchDetailsHolder holder, final int position) {

        MatchDetails matchDetails = matchDetailsArrayList.get(position);
        holder.textViewFirstTeam.setText(matchDetails.getFirst_team_name());
        holder.textViewSecondTeam.setText(matchDetails.getSecond_team_name());
        holder.textViewDate.setText(matchDetails.getDate());
        holder.textViewTime.setText(matchDetails.getTime());
        holder.textViewVenue.setText(matchDetails.getVenue());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTossActivity(position);
                ((Activity)context).finish();
            }
        });
    }



    @Override
    public int getItemCount() {
        return matchDetailsArrayList.size();
    }



    public class MatchDetailsHolder extends RecyclerView.ViewHolder {

        private TextView textViewFirstTeam,textViewSecondTeam,textViewDate,
                textViewTime,textViewVenue;
        public MatchDetailsHolder(View itemView) {
            super(itemView);

            textViewFirstTeam = itemView.findViewById(R.id.textFirstTeam);
            textViewSecondTeam = itemView.findViewById(R.id.textSecondTeam);
            textViewVenue = itemView.findViewById(R.id.textVenue);
            textViewDate = itemView.findViewById(R.id.textDate);
            textViewTime = itemView.findViewById(R.id.textTime);
        }
    }


    private void openTossActivity(int position) {

        MatchDetails matchDetails = new MatchDetails();

        matchDetails = matchDetailsArrayList.get(position);

        Intent intent = new Intent(context, TossSelectionActivity.class);

        intent.putExtra("MATCH_KEY",matchDetails.getMatch_key());
        intent.putExtra("DATE_KEY",matchDetails.getDate());
        intent.putExtra("TIME_KEY",matchDetails.getTime());
        context.startActivity(intent);

        ((Activity)context).finish();


    }
}
