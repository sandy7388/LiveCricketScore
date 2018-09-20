package com.sandy.wayzon_android.livecricketscore.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.sandy.wayzon_android.livecricketscore.R;
import com.sandy.wayzon_android.livecricketscore.adapter.MatchDetailsAdapter;
import com.sandy.wayzon_android.livecricketscore.httpHelper.URLs;
import com.sandy.wayzon_android.livecricketscore.model.MatchDetails;
import com.sandy.wayzon_android.livecricketscore.session.SessionManager;
import com.sandy.wayzon_android.livecricketscore.volley.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class MainActivity extends RuntimePermissionActivity {

    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private ArrayList<MatchDetails> matchDetailsArrayList;
    private MatchDetailsAdapter matchDetailsAdapter;
    private SessionManager session;
    private String stringFristTeam,stringSecondTeam,stringDate,stringTime,stringMatchName,
                    stringMatchKey,stringMatcheId,stringVenue,stringFormatedDate;
    private String[] stringTeamArray;
    private static final int REQUEST_PERMISSIONS = 20;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerViewMatchDetails);
        session = new SessionManager(this);
        recyclerview();
        getMatchDetails();
        permission();

        if (!SessionManager.getInstance(this).isLoggedIn())
        {
            startActivity(new Intent(this,LoginActivity.class));

            finish();
        }
    }

    // Runtime Permission
    @Override
    public void onPermissionsGranted(final int requestCode) {
        Toast.makeText(this, "Permissions Received.", Toast.LENGTH_LONG).show();
    }

    void permission() {
        MainActivity.super.requestAppPermissions(new
                        String[]{Manifest.permission.READ_CONTACTS,
                        Manifest.permission.READ_PHONE_STATE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.ACCESS_FINE_LOCATION}, R.string
                        .runtime_permissions_txt
                , REQUEST_PERMISSIONS);
    }


    void recyclerview() {
        matchDetailsArrayList = new ArrayList<>();
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        matchDetailsAdapter = new MatchDetailsAdapter(matchDetailsArrayList,this);
        recyclerView.setAdapter(matchDetailsAdapter);
    }

    void getMatchDetails()
    {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.MATCH_LIST_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            if (jsonObject.getString("success").equals("1"))
                            {
                                JSONArray jsonArray = jsonObject.getJSONArray("match_name");

                                for (int i=0;i<jsonArray.length();i++)
                                {
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    stringMatchName = object.getString("match_name");
                                    stringMatcheId = object.getString("match_id");
                                    stringMatchKey = object.getString("match_key");
                                    stringDate = object.getString("date");
                                    stringTime = object.getString("time");
                                    stringVenue = object.getString("venue");


                                    stringTeamArray = stringMatchName.split("vs");

                                    for (int j=i;j<=i;j++){

                                        stringFristTeam = stringTeamArray[0];
                                        stringSecondTeam = stringTeamArray[1];

                                        Log.d("stringFristTeam",stringFristTeam);

                                        Log.d("stringSecondTeam",stringSecondTeam);
                                    }

                                    Date date = new Date();
                                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

                                    date = simpleDateFormat.parse(stringDate);

                                    SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");

                                    stringFormatedDate = format.format(date);

                                    MatchDetails matchDetails = new MatchDetails();
                                    matchDetails.setFirst_team_name(stringFristTeam);
                                    matchDetails.setSecond_team_name(stringSecondTeam);
                                    matchDetails.setMatch_id(stringMatcheId);
                                    matchDetails.setMatch_key(stringMatchKey);
                                    matchDetails.setDate(stringFormatedDate);
                                    matchDetails.setTime(stringTime);
                                    matchDetails.setVenue(stringVenue);

                                    matchDetailsArrayList.add(matchDetails);
                                    matchDetailsAdapter.notifyDataSetChanged();

                                }


                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("operator_id",session.getUserID());
                //params.put("password",stringPassword);
                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }


    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        MainActivity.this.finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();

    }
}
