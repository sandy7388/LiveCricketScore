package com.sandy.wayzon_android.livecricketscore.activity;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.sandy.wayzon_android.livecricketscore.R;
import com.sandy.wayzon_android.livecricketscore.httpHelper.URLs;
import com.sandy.wayzon_android.livecricketscore.volley.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class TossSelectionActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener, View.OnClickListener {

    private String stringMatchKey,stringShortName,stringSeasonName,
                    stringDate,stringTime,stringDateTime,stringFirstName,stringSecondName;
    private TextView textViewFirstName,textViewSecondName,textViewDate,textViewSeasonName;
    private RadioGroup radioGroupTeamName,radioGroupBattingTeamName,radioGroupBowlingTeamName;
    private RadioButton radioButtonFirstTeam,radioButtonSecondTeam,
                        radioButtonFirstBattingTeam,radioButtonSecondBattingTeam,
                        radioButtonFirstBowlingTeam,radioButtonSecondBowlingTeam;
    private String[] stringTeamArray;
    private String tossWonBy = "";
    private String battingBy = "";
    private String bowlingBy = "";
    private Button buttonNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toss_selection);

        textViewDate = findViewById(R.id.textDateToss);
        textViewFirstName = findViewById(R.id.textFirstTeamToss);
        textViewSecondName = findViewById(R.id.textSecondTeamToss);
        textViewSeasonName = findViewById(R.id.textSeasonNameToss);
        buttonNext = findViewById(R.id.buttonTosssNext);


        radioGroupTeamName = findViewById(R.id.radioGroupTeamName);
        radioGroupBattingTeamName = findViewById(R.id.radioGroupBattingTeamName);
        radioGroupBowlingTeamName = findViewById(R.id.radioGroupBowlingTeamName);
        radioButtonFirstBattingTeam = findViewById(R.id.radioButtonFirstBattingTeam);
        radioButtonSecondBattingTeam = findViewById(R.id.radioButtonSecondBattingTeam);
        radioButtonFirstTeam = findViewById(R.id.radioButtonFirstTeam);
        radioButtonSecondTeam = findViewById(R.id.radioButtonSecondTeam);
        radioButtonFirstBowlingTeam = findViewById(R.id.radioButtonFirstBowlingTeam);
        radioButtonSecondBowlingTeam = findViewById(R.id.radioButtonSecondBowlingTeam);


        Intent intent = this.getIntent();
        stringMatchKey = intent.getExtras().getString("MATCH_KEY");
        stringDate = intent.getExtras().getString("DATE_KEY");
        stringTime = intent.getExtras().getString("TIME_KEY");

        stringDateTime = stringDate + " "+ stringTime;

        Log.d("stringDateTime" , stringDateTime);

        radioGroupTeamName.setOnCheckedChangeListener(this);
        radioGroupBattingTeamName.setOnCheckedChangeListener(this);
        radioGroupBowlingTeamName.setOnCheckedChangeListener(this);
        getMatchDetails();

        buttonNext.setOnClickListener(this);
    }

    private void getMatchDetails()
    {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.MATCH_DETAILS_URL,
                new Response.Listener<String>() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getString("success").equals("1"))
                            {
                                //stringDate = jsonObject.getString("start_date");
                                stringShortName = jsonObject.getString("short_name");
                                stringSeasonName = jsonObject.getString("season_name");
                                //stringDate = jsonObject.getString("");

                                stringTeamArray = stringShortName.split("vs");

                                for (int j=0;j<stringTeamArray.length;j++){

                                    stringFirstName = stringTeamArray[0];
                                    stringSecondName = stringTeamArray[1];

                                    Log.d("stringFirstNamegggg",stringFirstName);

                                    Log.d("stringSecondTeam",stringSecondName);

                                    for (int i = 0; i < radioGroupTeamName.getChildCount(); i++) {
                                        ((RadioButton) radioGroupTeamName.getChildAt(i)).setText(String.valueOf(i));

                                        radioButtonFirstTeam.setText(stringFirstName);
                                        radioButtonSecondTeam.setText(stringSecondName);
                                    }
                                    for (int i = 0; i < radioGroupBattingTeamName.getChildCount(); i++) {
                                        ((RadioButton) radioGroupBattingTeamName.getChildAt(i)).setText(String.valueOf(i));

                                        radioButtonFirstBattingTeam.setText(stringFirstName);
                                        radioButtonSecondBattingTeam.setText(stringSecondName);
                                    }
                                    for (int i = 0; i < radioGroupBowlingTeamName.getChildCount(); i++) {
                                        ((RadioButton) radioGroupBowlingTeamName.getChildAt(i)).setText(String.valueOf(i));

                                        radioButtonFirstBowlingTeam.setText(stringFirstName);
                                        radioButtonSecondBowlingTeam.setText(stringSecondName);
                                    }
                                }

                                Date date = new Date();
                                SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
                                String strDate = formatter.format(date);
                                System.out.println("Date Format with MM/dd/yyyy : "+strDate);

                                textViewDate.setText(stringDateTime);
                                textViewFirstName.setText(stringFirstName);
                                textViewSecondName.setText(stringSecondName);
                                textViewSeasonName.setText(stringSeasonName);

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();

            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("match_key",stringMatchKey);
                //params.put("password",stringPassword);
                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {

        RadioButton checkedRadioButton = group.findViewById(checkedId);
        // This puts the value (true/false) into the variable
        boolean isChecked = checkedRadioButton.isChecked();
        // If the radiobutton that has changed in check state is now checked...

        switch (group.getId())
        {
            case R.id.radioGroupTeamName:

                if (checkedId == R.id.radioButtonFirstTeam){
                    tossWonBy = checkedRadioButton.getText().toString();
                    System.out.println("tossWonBy:" + tossWonBy);

                    if (radioButtonFirstTeam.isChecked())
                    {
                        radioButtonFirstBattingTeam.setChecked(true);
                        radioButtonSecondBowlingTeam.setChecked(true);
                    }
                }
                if (checkedId == R.id.radioButtonSecondTeam){
                    tossWonBy = checkedRadioButton.getText().toString();
                    System.out.println("tossWonBy:" + tossWonBy);

                    if (radioButtonSecondTeam.isChecked())
                    {
                        radioButtonSecondBattingTeam.setChecked(true);
                        radioButtonFirstBowlingTeam.setChecked(true);
                    }
                }

                break;

            case R.id.radioGroupBattingTeamName:

                if (checkedId == R.id.radioButtonFirstBattingTeam){
                    battingBy = checkedRadioButton.getText().toString();
                    System.out.println("battingBy:" + battingBy);
                    if (radioButtonFirstBattingTeam.isChecked())
                    {
                        radioButtonSecondBowlingTeam.setChecked(true);
                    }

                }
                if (checkedId == R.id.radioButtonSecondBattingTeam){
                    battingBy = checkedRadioButton.getText().toString();
                    System.out.println("battingBy:" + battingBy);

                    if (radioButtonSecondBattingTeam.isChecked())
                    {
                        radioButtonFirstBowlingTeam.setChecked(true);
                    }
                }

                break;

            case R.id.radioGroupBowlingTeamName:

                if (checkedId == R.id.radioButtonFirstBowlingTeam){
                    bowlingBy = checkedRadioButton.getText().toString();
                    System.out.println("bowlingBy:" + bowlingBy);

                    if (radioButtonFirstBowlingTeam.isChecked())
                    {
                        radioButtonSecondBattingTeam.setChecked(true);
                    }
                }
                if (checkedId == R.id.radioButtonSecondBowlingTeam){
                    bowlingBy = checkedRadioButton.getText().toString();
                    System.out.println("bowlingBy:" + bowlingBy);

                    if (radioButtonSecondBowlingTeam.isChecked())
                    {
                        radioButtonFirstBattingTeam.setChecked(true);
                    }
                }

                break;

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.buttonTosssNext:

                sendTossDetails();
        }
    }

    private void sendTossDetails() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.TOSS_DETAILS_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            if (jsonObject.getString("success").equals("1"))
                            {
                                Intent intent = new Intent(TossSelectionActivity.this, PlayersActivity.class);

                                intent.putExtra("MATCH_KEY",stringMatchKey);
                                intent.putExtra("TEAM_KEY",tossWonBy);
                                intent.putExtra("BATTING_KEY",battingBy);
                                intent.putExtra("BOWLING_KEY",bowlingBy);
                                startActivity(intent);

                                finish();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                error.printStackTrace();

            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("match_key",stringMatchKey);
                params.put("team_name",tossWonBy);
                params.put("batting",battingBy);
                params.put("bowling",bowlingBy);
                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }


    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }
}
