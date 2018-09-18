package com.sandy.wayzon_android.livecricketscore.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.sandy.wayzon_android.livecricketscore.R;
import com.sandy.wayzon_android.livecricketscore.httpHelper.URLs;
import com.sandy.wayzon_android.livecricketscore.volley.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayersActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    private Spinner spinnerStrikePlayer,spinnerNonStrikePlsyer,spinnerBowler;
    private String stringMatchKey,stringTeamKey,stringBattingKey,stringBowlingKey;
    private List<String> strikePlayer,nonStrikePlayer,bowler;
    private String stringTeamName,stringPlayerId,stringPlayerName,stringShortName;
    private JSONArray jsonArrayPlayerList,jsonArrayPlayerName;
    private JSONObject objectPlayerList,playersObject,jsonObjectResponse;
    private String stringSpaceRemove;

    private String stringSpinnerPlayerNameStrk,stringSpinnerPlayerIdStrk,
            stringSpinnerPlayerNameNStrk,stringSpinnerPlayerIdNStrk,
            stringSpinnerPlayerNameBwlr,stringSpinnerPlayerIdBwlr;

    private Button buttonNext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_players);

        Intent intent = this.getIntent();
        stringMatchKey = intent.getExtras().getString("MATCH_KEY");
        stringTeamKey = intent.getExtras().getString("TEAM_KEY");
        stringBattingKey = intent.getExtras().getString("BATTING_KEY");
        stringBowlingKey = intent.getExtras().getString("BOWLING_KEY");

        spinnerStrikePlayer = findViewById(R.id.spinnerStrikerPlayer);
        spinnerNonStrikePlsyer = findViewById(R.id.spinnerNonStrikerPlayer);
        spinnerBowler = findViewById(R.id.spinnerBowler);
        buttonNext = findViewById(R.id.buttonPlayerNext);
        strikePlayer = new ArrayList<>();
        nonStrikePlayer = new ArrayList<>();
        bowler = new ArrayList<>();

        spinnerStrikePlayer.setOnItemSelectedListener(this);

        spinnerNonStrikePlsyer.setOnItemSelectedListener(this);

        spinnerBowler.setOnItemSelectedListener(this);

        buttonNext.setOnClickListener(this);

        getPlayerList();

    }

    private void getPlayerList() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.PLAYER_DETAILS_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            jsonObjectResponse = new JSONObject(response);

                            if (jsonObjectResponse.getString("success").equals("1"))
                            {
                                jsonArrayPlayerList = jsonObjectResponse.getJSONArray("player_list");

                                for (int i=0;i<jsonArrayPlayerList.length();i++)
                                {
                                    objectPlayerList = jsonArrayPlayerList.getJSONObject(i);

                                   stringTeamName = objectPlayerList.getString("team_name");

                                    stringSpaceRemove = stringBattingKey.replaceAll(" ", "");

                                    System.out.println(stringSpaceRemove);

                                    Log.d("TeamName111",stringTeamName);

                                    Log.d("TeamName222",stringSpaceRemove);

                                    if (stringTeamName.equals(stringSpaceRemove)){

                                        jsonArrayPlayerName = objectPlayerList.getJSONArray("players");

                                        for (int j=0;j<jsonArrayPlayerName.length();j++) {
                                            playersObject = jsonArrayPlayerName.getJSONObject(j);

                                            stringPlayerId = playersObject.getString("player_id");
                                            stringPlayerName = playersObject.getString("fullname");
                                            stringShortName = playersObject.getString("short_name");

//                                            if (stringTeamName.equals(stringShortName))
//                                            {
                                                strikePlayer.add(stringPlayerName);
                                            //}


                                        }
                                        Log.d("ddddddd","equeal");
                                        spinnerStrikePlayer.setAdapter(new ArrayAdapter<String>(PlayersActivity.this, R.layout.spinner_layout, strikePlayer));
                                        spinnerNonStrikePlsyer.setAdapter(new ArrayAdapter<String>(PlayersActivity.this, R.layout.spinner_layout, strikePlayer));

                                    }

                                    else {
                                        Log.d("ddddddd","not equeal");

                                        jsonArrayPlayerName = objectPlayerList.getJSONArray("players");

                                        for (int j=0;j<jsonArrayPlayerName.length();j++) {
                                            playersObject = jsonArrayPlayerName.getJSONObject(j);

                                            stringPlayerId = playersObject.getString("player_id");

                                            stringPlayerName = playersObject.getString("fullname");
                                            stringShortName = playersObject.getString("short_name");


                                            bowler.add(stringPlayerName);


                                        }
                                        spinnerBowler.setAdapter(new ArrayAdapter<String>(PlayersActivity.this, R.layout.spinner_layout, bowler));

                                    }

                                }
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

                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Spinner spinner = (Spinner) parent;
        switch (spinner.getId()) {
            case R.id.spinnerStrikerPlayer:
                stringSpinnerPlayerNameStrk = spinner.getAdapter().getItem(position).toString();
                try {

                    jsonArrayPlayerList = jsonObjectResponse.getJSONArray("player_list");

                    for (int x=0;x<jsonArrayPlayerList.length();x++) {
                        objectPlayerList = jsonArrayPlayerList.getJSONObject(x);
                        stringTeamName = objectPlayerList.getString("team_name");
                        if (stringTeamName.equals(stringSpaceRemove)) {

                            jsonArrayPlayerName = objectPlayerList.getJSONArray("players");

                            for (int k = 0; k < jsonArrayPlayerName.length(); k++) {

                                playersObject = jsonArrayPlayerName.getJSONObject(k);

                                stringPlayerId = playersObject.getString("player_id");

                                stringPlayerName = playersObject.getString("fullname");

                                if (stringSpinnerPlayerNameStrk.equals(stringPlayerName)) {
                                    stringSpinnerPlayerIdStrk = stringPlayerId;

                                    System.out.println("stringSpinnerPlayerIdStrk" + stringSpinnerPlayerIdStrk);
                                }

                            }

                        }
                    }

                }catch (JSONException e) {
                    e.printStackTrace();
                }

                break;

            case R.id.spinnerNonStrikerPlayer:
                stringSpinnerPlayerNameNStrk = spinner.getAdapter().getItem(position).toString();
                try {
                jsonArrayPlayerList = jsonObjectResponse.getJSONArray("player_list");

                for (int y=0;y<jsonArrayPlayerList.length();y++) {
                    objectPlayerList = jsonArrayPlayerList.getJSONObject(y);
                    stringTeamName = objectPlayerList.getString("team_name");

                if (stringTeamName.equals(stringSpaceRemove)) {

                        jsonArrayPlayerName = objectPlayerList.getJSONArray("players");
                        for (int p = 0; p < jsonArrayPlayerName.length(); p++) {
                            playersObject = jsonArrayPlayerName.getJSONObject(p);

                            stringPlayerId = playersObject.getString("player_id");

                            stringPlayerName = playersObject.getString("fullname");

                            if (stringSpinnerPlayerNameNStrk.equals(stringPlayerName)) {
                                stringSpinnerPlayerIdNStrk = stringPlayerId;

                                System.out.println("stringSpinnerPlayerIdNStrk" + stringSpinnerPlayerIdNStrk);
                            }
                        }
                    }
                }

                }catch (JSONException e) {
                    e.printStackTrace();
                }

                break;

            case R.id.spinnerBowler:

                stringSpinnerPlayerNameBwlr = spinner.getAdapter().getItem(position).toString();
                try {
                    jsonArrayPlayerList = jsonObjectResponse.getJSONArray("player_list");

                    for (int z = 0; z < jsonArrayPlayerList.length(); z++) {
                        objectPlayerList = jsonArrayPlayerList.getJSONObject(z);
                        stringTeamName = objectPlayerList.getString("team_name");
                    //}
                    if (!stringTeamName.equals(stringSpaceRemove)) {
                        jsonArrayPlayerName = objectPlayerList.getJSONArray("players");

                        for (int q = 0; q < jsonArrayPlayerName.length(); q++) {

                            playersObject = jsonArrayPlayerName.getJSONObject(q);

                            stringPlayerId = playersObject.getString("player_id");

                            stringPlayerName = playersObject.getString("fullname");

                            if (stringSpinnerPlayerNameBwlr.equals(stringPlayerName)) {
                                stringSpinnerPlayerIdBwlr = stringPlayerId;

                                System.out.println("stringSpinnerPlayerIdBwlr" + stringSpinnerPlayerIdBwlr);
                            }
                        }
                    }

                }

                }catch (JSONException e) {
                    e.printStackTrace();
                }

                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.buttonPlayerNext:
                sendOpeningPlayers();
        }

    }

    private void sendOpeningPlayers() {
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, "",
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//            }
//        })
//        {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> params = new HashMap<>();
//                params.put("match_key",stringMatchKey);
//
//                return params;
//            }
//        };
//
//        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);

        if (spinnerStrikePlayer.getSelectedItem().equals(spinnerNonStrikePlsyer.getSelectedItem()))
        {
            Toast.makeText(this, "Both Players could not be same", Toast.LENGTH_SHORT).show();

            return;
        }

        else {
            Intent intent = new Intent(PlayersActivity.this, MatchPlayActivity.class);
            intent.putExtra("MATCH_KEY", stringMatchKey);
            //intent.putExtra("TEAM_KEY",tossWonBy);
            intent.putExtra("BATTING_KEY", stringBattingKey);
            intent.putExtra("BOWLING_KEY", stringBowlingKey);

            intent.putExtra("BATTING_FIRST_ID_KEY", stringSpinnerPlayerIdStrk);
            intent.putExtra("BATTING_FIRST_NAME_KEY", stringSpinnerPlayerNameStrk);

            intent.putExtra("BATTING_SECONDT_ID_KEY", stringSpinnerPlayerIdNStrk);
            intent.putExtra("BATTING_SECOND_NAME_KEY", stringSpinnerPlayerNameNStrk);

            intent.putExtra("BOWLING_ID_KEY", stringSpinnerPlayerIdBwlr);
            intent.putExtra("BOWLING_NAME_KEY", stringSpinnerPlayerNameBwlr);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }
}
