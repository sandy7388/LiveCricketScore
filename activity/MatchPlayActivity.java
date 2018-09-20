package com.sandy.wayzon_android.livecricketscore.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.sandy.wayzon_android.livecricketscore.R;
import com.sandy.wayzon_android.livecricketscore.adapter.NewBowlerAdapter;
import com.sandy.wayzon_android.livecricketscore.adapter.SpinnerAdapter;
import com.sandy.wayzon_android.livecricketscore.httpHelper.URLs;
import com.sandy.wayzon_android.livecricketscore.model.BowlerDetails;
import com.sandy.wayzon_android.livecricketscore.session.SessionManager;
import com.sandy.wayzon_android.livecricketscore.volley.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MatchPlayActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    // Ball Operation
    private TextView textViewZero, textViewOne, textViewTwo, textViewThree, textViewFour, textViewFive,
            textViewSix, textViewSeven, textViewWicket, textViewNoBall, textViewUndo, textViewWide,
            textViewLegBy, textViewBy, textViewDot;

    ArrayList<String> spinnerNewBowler, spinnerNewBatsman, spinnerCatcher;

    private String stringWicketType;
    private Button yes, no;

    private Spinner spinner, spinnerBatsman, spinnerCatcherName;
    // Playing Players
    //private RadioGroup radioGroupPlayers;

    private String bBowler;

    // Playing Players
    private RadioButton radioButtonFirstPlayer, radioButtonSecondPlayer,radioButtonFirst,radioButtonSecond;

    // Pop up option for other than number
    private PopupMenu popupMenuWide, popupMenuNoBall, popupMenuByes, popupMenuLegBy,
            popupMenuUndo, popupMenuWicket, popupMenuDot;

    // Intent data string
    private String stringFristPlayerName, stringSecondPlayerName, stringBowlerName,
            stringFristPlayerId, stringSecondPlayerId, stringBowlerId,
            stringMatchKey, stringBattingTeam, stringBowlingTeam;

    // Players details
    private TextView textViewPlayingTeamName, textViewPlayingTeamRun, textViewPlayingTeamWicket,
            textViewPlayingTeamOvers, textViewFirstPlayerRun, textViewFirstPlayerBall,
            textViewSecondPlayerRun, textViewSecondPlayerBall, textViewBowlerName, textViewBowlerRun,
            textViewBowlerWicket, textViewBowlerBall, textViewCurrentOver, textViewExtraRun,
            textViewCurrentRunRate, textViewRequiredRunRate;

    // String values for Players details
    private String stringPlayingTeamRun, stringPlayingTeamWicket, stringPlayingTeamOvers,
            stringFirstPlayerRun, stringFirstPlayerBall, stringSecondPlayerRun,
            stringSecondPlayerBall, stringBowlerRun, stringBowlerWicket,
            stringBowlerBall, stringCurrentOver, stringExtraRun,
            stringCurrentRunRate, stringRequiredRunRate;

    // int Values for players details
    private int playingTeamRum, playingTeamWicket, playingTeamOvers, firstPlayerRun,
            firstPlayerBall, secondPlayerRun, secondPlayerBall, bowlerRun, bowlerWicket,
            bowlerBall, currentOver, extraRun, currentRunRate, requiredRunRate, editTextRuns1;

    private float playingTeamOversFloat, bowlerOversFloat;

    CustomDialogClass customDialogClass;

    Dialog dialog;

    private EditText editTextRuns;

    private String stringNewBowlerName, stringNewBowlerId, stringSpaceRemove, stringBowlerName1, stringBowlerId1,
            stringNewTeamName, stringNewBatsmanName, stringNewBatsmanId, stringBatsmanName1, stringBatsmanId1,
            stringCatcherName, stringCatcherId, stringCatcherName1, stringCatcherId1;

    private JSONObject jsonObject, jsonObjectBatsman;

    private String stringTeamRuns, stringTeamOvers, stringFirstPlayerRuns, stringFirstPlayerBalls,
            stringSecondPlayerRuns, stringSecondPlayerBalls, stringBowlerBalls,
            stringBowlerRuns, stringExtraRuns, stringTeamWickets, stringBowlerWickets, stringBallType,
            stringBallType1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_play);
        initialization();
        playerDetailsInitialization();
        getIntentData();
        intentDataSetter();
        //getBowlerDetails();
        radioButtonFirstPlayer.setChecked(true);
        spinnerNewBowler = new ArrayList<>();
        spinnerCatcher = new ArrayList<>();
        spinnerNewBatsman = new ArrayList<>();
        customDialogClass = new CustomDialogClass(MatchPlayActivity.this);

    }

    private void initialization() {

        textViewZero = findViewById(R.id.textViewZero);
        textViewOne = findViewById(R.id.textViewOne);
        textViewTwo = findViewById(R.id.textViewTwo);
        textViewThree = findViewById(R.id.textViewThree);
        textViewFour = findViewById(R.id.textViewFour);
        textViewFive = findViewById(R.id.textViewFive);
        textViewSix = findViewById(R.id.textViewSix);
        textViewSeven = findViewById(R.id.textViewSeven);
        textViewWicket = findViewById(R.id.textViewW);
        textViewNoBall = findViewById(R.id.textViewNB);
        textViewUndo = findViewById(R.id.textViewUndo);
        textViewWide = findViewById(R.id.textViewWD);
        textViewLegBy = findViewById(R.id.textViewLB);
        textViewBy = findViewById(R.id.textViewB);
        textViewDot = findViewById(R.id.textViewDot);

        radioButtonFirstPlayer = findViewById(R.id.radioButtonFirstPlayer);
        radioButtonSecondPlayer = findViewById(R.id.radioButtonSecondPlayer);

        onClickListener();

    }

    private void onClickListener() {
        textViewZero.setOnClickListener(this);
        textViewOne.setOnClickListener(this);
        textViewTwo.setOnClickListener(this);
        textViewThree.setOnClickListener(this);
        textViewFour.setOnClickListener(this);
        textViewFive.setOnClickListener(this);
        textViewSix.setOnClickListener(this);
        textViewSeven.setOnClickListener(this);
        textViewWicket.setOnClickListener(this);
        textViewNoBall.setOnClickListener(this);
        textViewUndo.setOnClickListener(this);
        textViewWide.setOnClickListener(this);
        textViewLegBy.setOnClickListener(this);
        textViewBy.setOnClickListener(this);
        textViewDot.setOnClickListener(this);

    }

    void getIntentData() {

        Intent intent = this.getIntent();

        stringFristPlayerId = intent.getExtras().getString("BATTING_FIRST_ID_KEY");
        stringFristPlayerName = intent.getExtras().getString("BATTING_FIRST_NAME_KEY");
        stringSecondPlayerId = intent.getExtras().getString("BATTING_SECONDT_ID_KEY");
        stringSecondPlayerName = intent.getExtras().getString("BATTING_SECOND_NAME_KEY");
        stringBowlerId = intent.getExtras().getString("BOWLING_ID_KEY");
        stringBowlerName = intent.getExtras().getString("BOWLING_NAME_KEY");
        stringMatchKey = intent.getExtras().getString("MATCH_KEY");
        stringBattingTeam = intent.getExtras().getString("BATTING_KEY");
        stringBowlingTeam = intent.getExtras().getString("BOWLING_KEY");
    }

    void playerDetailsInitialization() {

        textViewPlayingTeamName = findViewById(R.id.textViewPlayingTeam);
        textViewPlayingTeamRun = findViewById(R.id.textViewPlayingTeamRun);
        textViewPlayingTeamWicket = findViewById(R.id.textViewPlayingTeamWicket);
        textViewPlayingTeamOvers = findViewById(R.id.textViewPlayingTeamOver);
        textViewBowlerName = findViewById(R.id.textViewBowlerName);
        textViewFirstPlayerRun = findViewById(R.id.textViewFirstPlayerRun);
        textViewFirstPlayerBall = findViewById(R.id.textViewFirstPlayerBall);
        textViewSecondPlayerRun = findViewById(R.id.textViewSecondPlayerRun);
        textViewSecondPlayerBall = findViewById(R.id.textViewSecondPlayerBall);
        textViewBowlerRun = findViewById(R.id.textViewBowlerRun);
        textViewBowlerWicket = findViewById(R.id.textViewBowlerWicket);
        textViewBowlerBall = findViewById(R.id.textViewBowlerBall);
        textViewCurrentOver = findViewById(R.id.textViewCurrentOverBall);
        textViewExtraRun = findViewById(R.id.textViewExtraRun);
        textViewCurrentRunRate = findViewById(R.id.textViewCurrentRR);
        textViewRequiredRunRate = findViewById(R.id.textViewRequiredRR);

    }


    void intentDataSetter() {
        textViewPlayingTeamName.setText(stringBattingTeam);
        textViewBowlerName.setText(stringBowlerName);
        radioButtonFirstPlayer.setText(stringFristPlayerName);
        radioButtonSecondPlayer.setText(stringSecondPlayerName);
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.textViewZero:
                zeroToSevenMethod(0);
                break;

            case R.id.textViewOne:
                zeroToSevenMethod(1);
                break;

            case R.id.textViewTwo:
                zeroToSevenMethod(2);
                break;

            case R.id.textViewThree:
                zeroToSevenMethod(3);
                break;

            case R.id.textViewFour:
                zeroToSevenMethod(4);
                break;

            case R.id.textViewFive:
                zeroToSevenMethod(5);
                break;

            case R.id.textViewSix:
                zeroToSevenMethod(6);
                break;

            case R.id.textViewSeven:
                zeroToSevenMethod(7);
                break;

            case R.id.textViewW:
                extraRuns("Wicket");
                break;

            case R.id.textViewNB:
                extraRuns("NoBall");
                break;

            case R.id.textViewWD:
                extraRuns("Wide");
                break;

            case R.id.textViewLB:
                extraRuns("LegByes");
                break;

            case R.id.textViewB:
                extraRuns("Byes");
                break;

            case R.id.textViewDot:
                dotPopUp();
                break;

            case R.id.textViewUndo:
                undoPopUp();
                break;


        }

    }

    void wideBallPopUp() {
        popupMenuWide = new PopupMenu(MatchPlayActivity.this, textViewWide);

        popupMenuWide.getMenuInflater().inflate(R.menu.wide_layout, popupMenuWide.getMenu());

        popupMenuWide.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                stringBallType = item.getTitle().toString();
                final String str1 = String.valueOf(stringBallType.charAt(0));
                stringBallType1 = stringBallType.toString().substring(Math.max(stringBallType.length() - 2, 0));

                int numberWide = Integer.parseInt(str1);
                Log.d("strfdwide", str1 + " " + stringBallType1);

                if (numberWide % 2 == 0) {

                    if (radioButtonFirstPlayer.isChecked()) {
                        stringPlayingTeamRun = textViewPlayingTeamRun.getText().toString();

                        playingTeamRum = Integer.parseInt(stringPlayingTeamRun);

                        textViewPlayingTeamRun.setText(String.valueOf(playingTeamRum + numberWide + 1));

                        stringPlayingTeamOvers = textViewPlayingTeamOvers.getText().toString();

                        playingTeamOversFloat = Float.parseFloat(stringPlayingTeamOvers);

                        reusableFunctionPlayer();
                        stringFirstPlayerRun = textViewFirstPlayerRun.getText().toString();

                        firstPlayerRun = Integer.parseInt(stringFirstPlayerRun);


                        stringFirstPlayerBall = textViewFirstPlayerBall.getText().toString();

                        firstPlayerBall = Integer.parseInt(stringFirstPlayerBall);


                        stringBowlerRun = textViewBowlerRun.getText().toString();

                        bowlerRun = Integer.parseInt(stringBowlerRun);

                        textViewBowlerRun.setText(String.valueOf(bowlerRun + numberWide + 1));


                        stringBowlerBall = textViewBowlerBall.getText().toString();

                        bowlerOversFloat = Float.parseFloat(stringBowlerBall);

                        textViewCurrentOver.setText(textViewCurrentOver.getText() + " " + str1 + "wd");

                        stringExtraRun = textViewExtraRun.getText().toString();

                        extraRun = Integer.parseInt(stringExtraRun);

                        textViewExtraRun.setText(String.valueOf(extraRun + numberWide + 1));

                        reusableFunctionBowler();

                    } else {

                        stringPlayingTeamRun = textViewPlayingTeamRun.getText().toString();

                        playingTeamRum = Integer.parseInt(stringPlayingTeamRun);

                        textViewPlayingTeamRun.setText(String.valueOf(playingTeamRum + numberWide + 1));


                        stringPlayingTeamOvers = textViewPlayingTeamOvers.getText().toString();

                        playingTeamOversFloat = Float.parseFloat(stringPlayingTeamOvers);

                        reusableFunctionPlayer();
                        stringSecondPlayerRun = textViewSecondPlayerRun.getText().toString();

                        secondPlayerRun = Integer.parseInt(stringSecondPlayerRun);


                        stringSecondPlayerBall = textViewSecondPlayerBall.getText().toString();

                        secondPlayerBall = Integer.parseInt(stringSecondPlayerBall);

                        stringBowlerRun = textViewBowlerRun.getText().toString();

                        bowlerRun = Integer.parseInt(stringBowlerRun);

                        textViewBowlerRun.setText(String.valueOf(bowlerRun + numberWide + 1));


                        stringBowlerBall = textViewBowlerBall.getText().toString();

                        bowlerOversFloat = Float.parseFloat(stringBowlerBall);

                        textViewCurrentOver.setText(textViewCurrentOver.getText() + " " + str1 + "wd");
                        stringExtraRun = textViewExtraRun.getText().toString();

                        extraRun = Integer.parseInt(stringExtraRun);

                        textViewExtraRun.setText(String.valueOf(extraRun + numberWide + 1));
                        reusableFunctionBowler();
                    }

                    runsServerCall(numberWide);
                } else {

                    if (radioButtonFirstPlayer.isChecked()) {
                        stringPlayingTeamRun = textViewPlayingTeamRun.getText().toString();

                        playingTeamRum = Integer.parseInt(stringPlayingTeamRun);

                        textViewPlayingTeamRun.setText(String.valueOf(playingTeamRum + numberWide + 1));

                        stringPlayingTeamOvers = textViewPlayingTeamOvers.getText().toString();

                        playingTeamOversFloat = Float.parseFloat(stringPlayingTeamOvers);

                        reusableFunctionPlayer();
                        stringFirstPlayerRun = textViewFirstPlayerRun.getText().toString();

                        firstPlayerRun = Integer.parseInt(stringFirstPlayerRun);


                        stringFirstPlayerBall = textViewFirstPlayerBall.getText().toString();

                        firstPlayerBall = Integer.parseInt(stringFirstPlayerBall);


                        stringBowlerRun = textViewBowlerRun.getText().toString();

                        bowlerRun = Integer.parseInt(stringBowlerRun);

                        textViewBowlerRun.setText(String.valueOf(bowlerRun + numberWide + 1));


                        stringBowlerBall = textViewBowlerBall.getText().toString();

                        bowlerOversFloat = Float.parseFloat(stringBowlerBall);

                        textViewCurrentOver.setText(textViewCurrentOver.getText() + " " + str1 + "wd");

                        stringExtraRun = textViewExtraRun.getText().toString();

                        extraRun = Integer.parseInt(stringExtraRun);

                        textViewExtraRun.setText(String.valueOf(extraRun + numberWide + 1));

                        reusableFunctionBowler();

                    } else {

                        stringPlayingTeamRun = textViewPlayingTeamRun.getText().toString();

                        playingTeamRum = Integer.parseInt(stringPlayingTeamRun);

                        textViewPlayingTeamRun.setText(String.valueOf(playingTeamRum + numberWide + 1));


                        stringPlayingTeamOvers = textViewPlayingTeamOvers.getText().toString();

                        playingTeamOversFloat = Float.parseFloat(stringPlayingTeamOvers);

                        reusableFunctionPlayer();
                        stringSecondPlayerRun = textViewSecondPlayerRun.getText().toString();

                        secondPlayerRun = Integer.parseInt(stringSecondPlayerRun);


                        stringSecondPlayerBall = textViewSecondPlayerBall.getText().toString();

                        secondPlayerBall = Integer.parseInt(stringSecondPlayerBall);

                        stringBowlerRun = textViewBowlerRun.getText().toString();

                        bowlerRun = Integer.parseInt(stringBowlerRun);

                        textViewBowlerRun.setText(String.valueOf(bowlerRun + numberWide + 1));


                        stringBowlerBall = textViewBowlerBall.getText().toString();

                        bowlerOversFloat = Float.parseFloat(stringBowlerBall);

                        textViewCurrentOver.setText(textViewCurrentOver.getText() + " " + str1 + "wd");
                        stringExtraRun = textViewExtraRun.getText().toString();

                        extraRun = Integer.parseInt(stringExtraRun);

                        textViewExtraRun.setText(String.valueOf(extraRun + numberWide + 1));
                        reusableFunctionBowler();

                    }

                    runsServerCall(numberWide);
                }

                return true;
            }
        });
        popupMenuWide.show();
    }

    void byesPopUp() {
        popupMenuByes = new PopupMenu(MatchPlayActivity.this, textViewBy);

        popupMenuByes.getMenuInflater().inflate(R.menu.byes_layout, popupMenuByes.getMenu());

        popupMenuByes.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                stringBallType = item.getTitle().toString();
                final String str1 = String.valueOf(stringBallType.charAt(0));
                stringBallType1 = stringBallType.toString().substring(Math.max(stringBallType.length() - 1, 0));
                Log.d("strfdwide", str1 + " " + stringBallType1);
                int numberByes = Integer.parseInt(str1);
                if (numberByes % 2 == 0) {
                    if (radioButtonFirstPlayer.isChecked()) {
                        stringPlayingTeamRun = textViewPlayingTeamRun.getText().toString();

                        playingTeamRum = Integer.parseInt(stringPlayingTeamRun);

                        textViewPlayingTeamRun.setText(String.valueOf(playingTeamRum + numberByes + 1));

                        stringPlayingTeamOvers = textViewPlayingTeamOvers.getText().toString();

                        playingTeamOversFloat = Float.parseFloat(stringPlayingTeamOvers);

                        textViewPlayingTeamOvers.setText(new DecimalFormat("##.#").format((playingTeamOversFloat + 0.1)));

                        reusableFunctionPlayer();
                        stringFirstPlayerRun = textViewFirstPlayerRun.getText().toString();

                        firstPlayerRun = Integer.parseInt(stringFirstPlayerRun);


                        stringFirstPlayerBall = textViewFirstPlayerBall.getText().toString();

                        firstPlayerBall = Integer.parseInt(stringFirstPlayerBall);

                        textViewFirstPlayerBall.setText(String.valueOf(firstPlayerBall + 1));


                        stringBowlerRun = textViewBowlerRun.getText().toString();

                        bowlerRun = Integer.parseInt(stringBowlerRun);

                        stringBowlerBall = textViewBowlerBall.getText().toString();

                        bowlerOversFloat = Float.parseFloat(stringBowlerBall);

                        textViewBowlerBall.setText(new DecimalFormat("##.#").format(bowlerOversFloat + 0.1));

                        textViewCurrentOver.setText(textViewCurrentOver.getText() + " " + str1 + "b");

                        stringExtraRun = textViewExtraRun.getText().toString();

                        extraRun = Integer.parseInt(stringExtraRun);

                        textViewExtraRun.setText(String.valueOf(extraRun + numberByes + 1));

                        reusableFunctionBowler();

                    } else {

                        stringPlayingTeamRun = textViewPlayingTeamRun.getText().toString();

                        playingTeamRum = Integer.parseInt(stringPlayingTeamRun);

                        textViewPlayingTeamRun.setText(String.valueOf(playingTeamRum + numberByes + 1));


                        stringPlayingTeamOvers = textViewPlayingTeamOvers.getText().toString();

                        playingTeamOversFloat = Float.parseFloat(stringPlayingTeamOvers);

                        textViewPlayingTeamOvers.setText(new DecimalFormat("##.#").format((playingTeamOversFloat + 0.1)));

                        reusableFunctionPlayer();
                        stringSecondPlayerRun = textViewSecondPlayerRun.getText().toString();

                        secondPlayerRun = Integer.parseInt(stringSecondPlayerRun);


                        stringSecondPlayerBall = textViewSecondPlayerBall.getText().toString();

                        secondPlayerBall = Integer.parseInt(stringSecondPlayerBall);

                        textViewSecondPlayerBall.setText(String.valueOf(secondPlayerBall + 1));

                        stringBowlerRun = textViewBowlerRun.getText().toString();

                        bowlerRun = Integer.parseInt(stringBowlerRun);


                        stringBowlerBall = textViewBowlerBall.getText().toString();

                        bowlerOversFloat = Float.parseFloat(stringBowlerBall);

                        textViewBowlerBall.setText(new DecimalFormat("##.#").format((bowlerOversFloat + 0.1)));

                        textViewCurrentOver.setText(textViewCurrentOver.getText() + " " + str1 + "b");
                        stringExtraRun = textViewExtraRun.getText().toString();

                        extraRun = Integer.parseInt(stringExtraRun);

                        textViewExtraRun.setText(String.valueOf(extraRun + numberByes + 1));
                        reusableFunctionBowler();

                    }

                    runsServerCall(numberByes);
                } else {

                    if (radioButtonFirstPlayer.isChecked()) {
                        stringPlayingTeamRun = textViewPlayingTeamRun.getText().toString();

                        playingTeamRum = Integer.parseInt(stringPlayingTeamRun);

                        textViewPlayingTeamRun.setText(String.valueOf(playingTeamRum + numberByes + 1));

                        stringPlayingTeamOvers = textViewPlayingTeamOvers.getText().toString();

                        playingTeamOversFloat = Float.parseFloat(stringPlayingTeamOvers);

                        textViewPlayingTeamOvers.setText(new DecimalFormat("##.#").format((playingTeamOversFloat + 0.1)));

                        reusableFunctionPlayer();
                        stringFirstPlayerRun = textViewFirstPlayerRun.getText().toString();

                        firstPlayerRun = Integer.parseInt(stringFirstPlayerRun);


                        stringFirstPlayerBall = textViewFirstPlayerBall.getText().toString();

                        firstPlayerBall = Integer.parseInt(stringFirstPlayerBall);

                        textViewFirstPlayerBall.setText(String.valueOf(firstPlayerBall + 1));


                        stringBowlerRun = textViewBowlerRun.getText().toString();

                        bowlerRun = Integer.parseInt(stringBowlerRun);

                        stringBowlerBall = textViewBowlerBall.getText().toString();

                        bowlerOversFloat = Float.parseFloat(stringBowlerBall);

                        textViewBowlerBall.setText(new DecimalFormat("##.#").format(bowlerOversFloat + 0.1));

                        textViewCurrentOver.setText(textViewCurrentOver.getText() + " " + str1 + "b");

                        stringExtraRun = textViewExtraRun.getText().toString();

                        extraRun = Integer.parseInt(stringExtraRun);

                        textViewExtraRun.setText(String.valueOf(extraRun + numberByes + 1));

                        reusableFunctionBowler();

                    } else {

                        stringPlayingTeamRun = textViewPlayingTeamRun.getText().toString();

                        playingTeamRum = Integer.parseInt(stringPlayingTeamRun);

                        textViewPlayingTeamRun.setText(String.valueOf(playingTeamRum + numberByes + 1));


                        stringPlayingTeamOvers = textViewPlayingTeamOvers.getText().toString();

                        playingTeamOversFloat = Float.parseFloat(stringPlayingTeamOvers);

                        textViewPlayingTeamOvers.setText(new DecimalFormat("##.#").format((playingTeamOversFloat + 0.1)));

                        reusableFunctionPlayer();
                        stringSecondPlayerRun = textViewSecondPlayerRun.getText().toString();

                        secondPlayerRun = Integer.parseInt(stringSecondPlayerRun);


                        stringSecondPlayerBall = textViewSecondPlayerBall.getText().toString();

                        secondPlayerBall = Integer.parseInt(stringSecondPlayerBall);

                        textViewSecondPlayerBall.setText(String.valueOf(secondPlayerBall + 1));

                        stringBowlerRun = textViewBowlerRun.getText().toString();

                        bowlerRun = Integer.parseInt(stringBowlerRun);


                        stringBowlerBall = textViewBowlerBall.getText().toString();

                        bowlerOversFloat = Float.parseFloat(stringBowlerBall);

                        textViewBowlerBall.setText(new DecimalFormat("##.#").format((bowlerOversFloat + 0.1)));

                        textViewCurrentOver.setText(textViewCurrentOver.getText() + " " + str1 + "b");
                        stringExtraRun = textViewExtraRun.getText().toString();

                        extraRun = Integer.parseInt(stringExtraRun);

                        textViewExtraRun.setText(String.valueOf(extraRun + numberByes + 1));
                        reusableFunctionBowler();

                    }

                    runsServerCall(numberByes);
                }


                return true;
            }
        });
        popupMenuByes.show();

    }

    void legByPopUp() {
        popupMenuLegBy = new PopupMenu(MatchPlayActivity.this, textViewLegBy);

        popupMenuLegBy.getMenuInflater().inflate(R.menu.leg_by_layout, popupMenuLegBy.getMenu());

        popupMenuLegBy.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                stringBallType = item.getTitle().toString();
                final String str1 = String.valueOf(stringBallType.charAt(0));
                stringBallType1 = stringBallType.toString().substring(Math.max(stringBallType.length() - 2, 0));
                Log.d("strfdwide", str1 + " " + stringBallType1);
                int numberLegByes = Integer.parseInt(str1);

                if (numberLegByes % 2 == 0) {
                    if (radioButtonFirstPlayer.isChecked()) {
                        stringPlayingTeamRun = textViewPlayingTeamRun.getText().toString();

                        playingTeamRum = Integer.parseInt(stringPlayingTeamRun);

                        textViewPlayingTeamRun.setText(String.valueOf(playingTeamRum + numberLegByes + 1));

                        stringPlayingTeamOvers = textViewPlayingTeamOvers.getText().toString();

                        playingTeamOversFloat = Float.parseFloat(stringPlayingTeamOvers);

                        textViewPlayingTeamOvers.setText(new DecimalFormat("##.#").format((playingTeamOversFloat + 0.1)));

                        reusableFunctionPlayer();
                        stringFirstPlayerRun = textViewFirstPlayerRun.getText().toString();

                        firstPlayerRun = Integer.parseInt(stringFirstPlayerRun);

                        //textViewFirstPlayerRun.setText(String.valueOf(firstPlayerRun + 1));


                        stringFirstPlayerBall = textViewFirstPlayerBall.getText().toString();

                        firstPlayerBall = Integer.parseInt(stringFirstPlayerBall);

                        textViewFirstPlayerBall.setText(String.valueOf(firstPlayerBall + 1));


                        stringBowlerRun = textViewBowlerRun.getText().toString();

                        bowlerRun = Integer.parseInt(stringBowlerRun);

                        //textViewBowlerRun.setText(String.valueOf(bowlerRun + 1));


                        stringBowlerBall = textViewBowlerBall.getText().toString();

                        bowlerOversFloat = Float.parseFloat(stringBowlerBall);

                        textViewBowlerBall.setText(new DecimalFormat("##.#").format(bowlerOversFloat + 0.1));

                        textViewCurrentOver.setText(textViewCurrentOver.getText() + " " + str1 + "lb");

                        stringExtraRun = textViewExtraRun.getText().toString();

                        extraRun = Integer.parseInt(stringExtraRun);

                        textViewExtraRun.setText(String.valueOf(extraRun + numberLegByes + 1));

                        reusableFunctionBowler();


                    } else {

                        stringPlayingTeamRun = textViewPlayingTeamRun.getText().toString();

                        playingTeamRum = Integer.parseInt(stringPlayingTeamRun);

                        textViewPlayingTeamRun.setText(String.valueOf(playingTeamRum + numberLegByes + 1));


                        stringPlayingTeamOvers = textViewPlayingTeamOvers.getText().toString();

                        playingTeamOversFloat = Float.parseFloat(stringPlayingTeamOvers);

                        textViewPlayingTeamOvers.setText(new DecimalFormat("##.#").format((playingTeamOversFloat + 0.1)));

                        reusableFunctionPlayer();
                        stringSecondPlayerRun = textViewSecondPlayerRun.getText().toString();

                        secondPlayerRun = Integer.parseInt(stringSecondPlayerRun);

                        //textViewSecondPlayerRun.setText(String.valueOf(secondPlayerRun + 1));


                        stringSecondPlayerBall = textViewSecondPlayerBall.getText().toString();

                        secondPlayerBall = Integer.parseInt(stringSecondPlayerBall);

                        textViewSecondPlayerBall.setText(String.valueOf(secondPlayerBall + 1));

                        stringBowlerRun = textViewBowlerRun.getText().toString();

                        bowlerRun = Integer.parseInt(stringBowlerRun);

                        //textViewBowlerRun.setText(String.valueOf(bowlerRun + 1));


                        stringBowlerBall = textViewBowlerBall.getText().toString();

                        bowlerOversFloat = Float.parseFloat(stringBowlerBall);

                        textViewBowlerBall.setText(new DecimalFormat("##.#").format((bowlerOversFloat + 0.1)));

                        textViewCurrentOver.setText(textViewCurrentOver.getText() + " " + str1 + "lb");
                        stringExtraRun = textViewExtraRun.getText().toString();

                        extraRun = Integer.parseInt(stringExtraRun);

                        textViewExtraRun.setText(String.valueOf(extraRun + numberLegByes + 1));
                        reusableFunctionBowler();

                    }

                    runsServerCall(numberLegByes);
                } else {
                    if (radioButtonFirstPlayer.isChecked()) {
                        stringPlayingTeamRun = textViewPlayingTeamRun.getText().toString();

                        playingTeamRum = Integer.parseInt(stringPlayingTeamRun);

                        textViewPlayingTeamRun.setText(String.valueOf(playingTeamRum + numberLegByes + 1));

                        stringPlayingTeamOvers = textViewPlayingTeamOvers.getText().toString();

                        playingTeamOversFloat = Float.parseFloat(stringPlayingTeamOvers);

                        textViewPlayingTeamOvers.setText(new DecimalFormat("##.#").format((playingTeamOversFloat + 0.1)));

                        reusableFunctionPlayer();
                        stringFirstPlayerRun = textViewFirstPlayerRun.getText().toString();

                        firstPlayerRun = Integer.parseInt(stringFirstPlayerRun);

                        //textViewFirstPlayerRun.setText(String.valueOf(firstPlayerRun + 1));


                        stringFirstPlayerBall = textViewFirstPlayerBall.getText().toString();

                        firstPlayerBall = Integer.parseInt(stringFirstPlayerBall);

                        textViewFirstPlayerBall.setText(String.valueOf(firstPlayerBall + 1));


                        stringBowlerRun = textViewBowlerRun.getText().toString();

                        bowlerRun = Integer.parseInt(stringBowlerRun);

                        //textViewBowlerRun.setText(String.valueOf(bowlerRun + 1));


                        stringBowlerBall = textViewBowlerBall.getText().toString();

                        bowlerOversFloat = Float.parseFloat(stringBowlerBall);

                        textViewBowlerBall.setText(new DecimalFormat("##.#").format(bowlerOversFloat + 0.1));

                        textViewCurrentOver.setText(textViewCurrentOver.getText() + " " + str1 + "lb");

                        stringExtraRun = textViewExtraRun.getText().toString();

                        extraRun = Integer.parseInt(stringExtraRun);

                        textViewExtraRun.setText(String.valueOf(extraRun + numberLegByes + 1));

                        reusableFunctionBowler();

                    } else {

                        stringPlayingTeamRun = textViewPlayingTeamRun.getText().toString();

                        playingTeamRum = Integer.parseInt(stringPlayingTeamRun);

                        textViewPlayingTeamRun.setText(String.valueOf(playingTeamRum + numberLegByes + 1));


                        stringPlayingTeamOvers = textViewPlayingTeamOvers.getText().toString();

                        playingTeamOversFloat = Float.parseFloat(stringPlayingTeamOvers);

                        textViewPlayingTeamOvers.setText(new DecimalFormat("##.#").format((playingTeamOversFloat + 0.1)));

                        reusableFunctionPlayer();
                        stringSecondPlayerRun = textViewSecondPlayerRun.getText().toString();

                        secondPlayerRun = Integer.parseInt(stringSecondPlayerRun);

                        //textViewSecondPlayerRun.setText(String.valueOf(secondPlayerRun + 1));


                        stringSecondPlayerBall = textViewSecondPlayerBall.getText().toString();

                        secondPlayerBall = Integer.parseInt(stringSecondPlayerBall);

                        textViewSecondPlayerBall.setText(String.valueOf(secondPlayerBall + 1));

                        stringBowlerRun = textViewBowlerRun.getText().toString();

                        bowlerRun = Integer.parseInt(stringBowlerRun);

                        //textViewBowlerRun.setText(String.valueOf(bowlerRun + 1));


                        stringBowlerBall = textViewBowlerBall.getText().toString();

                        bowlerOversFloat = Float.parseFloat(stringBowlerBall);

                        textViewBowlerBall.setText(new DecimalFormat("##.#").format((bowlerOversFloat + 0.1)));

                        textViewCurrentOver.setText(textViewCurrentOver.getText() + " " + str1 + "lb");
                        stringExtraRun = textViewExtraRun.getText().toString();

                        extraRun = Integer.parseInt(stringExtraRun);

                        textViewExtraRun.setText(String.valueOf(extraRun + numberLegByes + 1));
                        reusableFunctionBowler();
                    }

                    runsServerCall(numberLegByes);
                }


                return true;
            }
        });

        popupMenuLegBy.show();
    }

    void noBallPopUp() {
        popupMenuNoBall = new PopupMenu(MatchPlayActivity.this, textViewNoBall);

        popupMenuNoBall.getMenuInflater().inflate(R.menu.no_ball_layout, popupMenuNoBall.getMenu());

        popupMenuNoBall.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                stringBallType = item.getTitle().toString();
                final String str1 = String.valueOf(stringBallType.charAt(0));
                stringBallType1 = stringBallType.toString().substring(Math.max(stringBallType.length() - 2, 0));
                Log.d("strfdwide", str1 + " " + stringBallType1);
                final int numberNoBall = Integer.parseInt(str1);


                if (str1.equals("0")) {
                    if (radioButtonFirstPlayer.isChecked()) {
                        stringPlayingTeamRun = textViewPlayingTeamRun.getText().toString();

                        playingTeamRum = Integer.parseInt(stringPlayingTeamRun);

                        textViewPlayingTeamRun.setText(String.valueOf(playingTeamRum + 1));

                        stringPlayingTeamOvers = textViewPlayingTeamOvers.getText().toString();

                        playingTeamOversFloat = Float.parseFloat(stringPlayingTeamOvers);

                        //textViewPlayingTeamOvers.setText(new DecimalFormat("##.#").format((playingTeamOversFloat + 0.1)));

                        reusableFunctionPlayer();
                        stringFirstPlayerRun = textViewFirstPlayerRun.getText().toString();

                        firstPlayerRun = Integer.parseInt(stringFirstPlayerRun);

                        //textViewFirstPlayerRun.setText(String.valueOf(firstPlayerRun + 1));


                        stringFirstPlayerBall = textViewFirstPlayerBall.getText().toString();

                        firstPlayerBall = Integer.parseInt(stringFirstPlayerBall);

                        //textViewFirstPlayerBall.setText(String.valueOf(firstPlayerBall + 1));


                        stringBowlerRun = textViewBowlerRun.getText().toString();

                        bowlerRun = Integer.parseInt(stringBowlerRun);

                        textViewBowlerRun.setText(String.valueOf(bowlerRun + 1));


                        stringBowlerBall = textViewBowlerBall.getText().toString();

                        bowlerOversFloat = Float.parseFloat(stringBowlerBall);

                        //textViewBowlerBall.setText(new DecimalFormat("##.#").format(bowlerOversFloat + 0.1));

                        textViewCurrentOver.setText(textViewCurrentOver.getText() + " " + "0nb");

                        stringExtraRun = textViewExtraRun.getText().toString();

                        extraRun = Integer.parseInt(stringExtraRun);

                        textViewExtraRun.setText(String.valueOf(extraRun + 1));

                        reusableFunctionBowler();
                        runsServerCall(numberNoBall);
                    } else {

                        stringPlayingTeamRun = textViewPlayingTeamRun.getText().toString();

                        playingTeamRum = Integer.parseInt(stringPlayingTeamRun);

                        textViewPlayingTeamRun.setText(String.valueOf(playingTeamRum + 1));


                        stringPlayingTeamOvers = textViewPlayingTeamOvers.getText().toString();

                        playingTeamOversFloat = Float.parseFloat(stringPlayingTeamOvers);

                        //textViewPlayingTeamOvers.setText(new DecimalFormat("##.#").format((playingTeamOversFloat + 0.1)));

                        reusableFunctionPlayer();
                        stringSecondPlayerRun = textViewSecondPlayerRun.getText().toString();

                        secondPlayerRun = Integer.parseInt(stringSecondPlayerRun);

                        //textViewSecondPlayerRun.setText(String.valueOf(secondPlayerRun + 1));


                        stringSecondPlayerBall = textViewSecondPlayerBall.getText().toString();

                        secondPlayerBall = Integer.parseInt(stringSecondPlayerBall);

                        //textViewSecondPlayerBall.setText(String.valueOf(secondPlayerBall + 1));

                        stringBowlerRun = textViewBowlerRun.getText().toString();

                        bowlerRun = Integer.parseInt(stringBowlerRun);

                        textViewBowlerRun.setText(String.valueOf(bowlerRun + 1));


                        stringBowlerBall = textViewBowlerBall.getText().toString();

                        bowlerOversFloat = Float.parseFloat(stringBowlerBall);

                        //textViewBowlerBall.setText(new DecimalFormat("##.#").format((bowlerOversFloat + 0.1)));

                        textViewCurrentOver.setText(textViewCurrentOver.getText() + " " + "0nb");
                        stringExtraRun = textViewExtraRun.getText().toString();

                        extraRun = Integer.parseInt(stringExtraRun);

                        textViewExtraRun.setText(String.valueOf(extraRun + 1));
                        reusableFunctionBowler();

                        runsServerCall(numberNoBall);

                    }

                } else {

                    if (numberNoBall % 2 == 0) {

                        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(MatchPlayActivity.this);
                        LayoutInflater inflater = MatchPlayActivity.this.getLayoutInflater();
                        final View dialogView = inflater.inflate(R.layout.layout_no_ball, null);
                        dialogBuilder.setView(dialogView);
                        dialogBuilder.setCancelable(true);

                        TextView textViewByes = dialogView.findViewById(R.id.textViewByesNoBall);

                        TextView textViewLegByes = dialogView.findViewById(R.id.textViewLegByesNoBall);

                        TextView textViewNone = dialogView.findViewById(R.id.textViewNoneNoBall);

                        textViewByes.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                if (radioButtonFirstPlayer.isChecked()) {
                                    stringPlayingTeamRun = textViewPlayingTeamRun.getText().toString();

                                    playingTeamRum = Integer.parseInt(stringPlayingTeamRun);

                                    textViewPlayingTeamRun.setText(String.valueOf(playingTeamRum + numberNoBall + 1));

                                    stringPlayingTeamOvers = textViewPlayingTeamOvers.getText().toString();

                                    playingTeamOversFloat = Float.parseFloat(stringPlayingTeamOvers);

                                    //textViewPlayingTeamOvers.setText(new DecimalFormat("##.#").format((playingTeamOversFloat + 0.1)));

                                    reusableFunctionPlayer();
                                    stringFirstPlayerRun = textViewFirstPlayerRun.getText().toString();

                                    firstPlayerRun = Integer.parseInt(stringFirstPlayerRun);

                                    //textViewFirstPlayerRun.setText(String.valueOf(firstPlayerRun + 1));


                                    stringFirstPlayerBall = textViewFirstPlayerBall.getText().toString();

                                    firstPlayerBall = Integer.parseInt(stringFirstPlayerBall);

                                    //textViewFirstPlayerBall.setText(String.valueOf(firstPlayerBall + 1));


                                    stringBowlerRun = textViewBowlerRun.getText().toString();

                                    bowlerRun = Integer.parseInt(stringBowlerRun);

                                    textViewBowlerRun.setText(String.valueOf(bowlerRun + 1));


                                    stringBowlerBall = textViewBowlerBall.getText().toString();

                                    bowlerOversFloat = Float.parseFloat(stringBowlerBall);

                                    //textViewBowlerBall.setText(new DecimalFormat("##.#").format(bowlerOversFloat + 0.1));

                                    textViewCurrentOver.setText(textViewCurrentOver.getText() + " " + str1 + "nb");

                                    stringExtraRun = textViewExtraRun.getText().toString();

                                    extraRun = Integer.parseInt(stringExtraRun);

                                    textViewExtraRun.setText(String.valueOf(extraRun + numberNoBall + 1));

                                    reusableFunctionBowler();


                                } else {

                                    stringPlayingTeamRun = textViewPlayingTeamRun.getText().toString();

                                    playingTeamRum = Integer.parseInt(stringPlayingTeamRun);

                                    textViewPlayingTeamRun.setText(String.valueOf(playingTeamRum + numberNoBall + 1));


                                    stringPlayingTeamOvers = textViewPlayingTeamOvers.getText().toString();

                                    playingTeamOversFloat = Float.parseFloat(stringPlayingTeamOvers);

                                    //textViewPlayingTeamOvers.setText(new DecimalFormat("##.#").format((playingTeamOversFloat + 0.1)));

                                    reusableFunctionPlayer();
                                    stringSecondPlayerRun = textViewSecondPlayerRun.getText().toString();

                                    secondPlayerRun = Integer.parseInt(stringSecondPlayerRun);

                                    //textViewSecondPlayerRun.setText(String.valueOf(secondPlayerRun + 1));


                                    stringSecondPlayerBall = textViewSecondPlayerBall.getText().toString();

                                    secondPlayerBall = Integer.parseInt(stringSecondPlayerBall);

                                    //textViewSecondPlayerBall.setText(String.valueOf(secondPlayerBall + 1));

                                    stringBowlerRun = textViewBowlerRun.getText().toString();

                                    bowlerRun = Integer.parseInt(stringBowlerRun);

                                    textViewBowlerRun.setText(String.valueOf(bowlerRun + 1));


                                    stringBowlerBall = textViewBowlerBall.getText().toString();

                                    bowlerOversFloat = Float.parseFloat(stringBowlerBall);

                                    //textViewBowlerBall.setText(new DecimalFormat("##.#").format((bowlerOversFloat + 0.1)));

                                    textViewCurrentOver.setText(textViewCurrentOver.getText() + " " + str1 + "nb");
                                    stringExtraRun = textViewExtraRun.getText().toString();

                                    extraRun = Integer.parseInt(stringExtraRun);

                                    textViewExtraRun.setText(String.valueOf(extraRun + numberNoBall + 1));
                                    reusableFunctionBowler();

                                }

                                runsServerCall(numberNoBall);

                                dialog.dismiss();
                            }
                        });


                        textViewLegByes.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                if (radioButtonFirstPlayer.isChecked()) {
                                    stringPlayingTeamRun = textViewPlayingTeamRun.getText().toString();

                                    playingTeamRum = Integer.parseInt(stringPlayingTeamRun);

                                    textViewPlayingTeamRun.setText(String.valueOf(playingTeamRum + numberNoBall + 1));

                                    stringPlayingTeamOvers = textViewPlayingTeamOvers.getText().toString();

                                    playingTeamOversFloat = Float.parseFloat(stringPlayingTeamOvers);

                                    //textViewPlayingTeamOvers.setText(new DecimalFormat("##.#").format((playingTeamOversFloat + 0.1)));

                                    reusableFunctionPlayer();
                                    stringFirstPlayerRun = textViewFirstPlayerRun.getText().toString();

                                    firstPlayerRun = Integer.parseInt(stringFirstPlayerRun);

                                    //textViewFirstPlayerRun.setText(String.valueOf(firstPlayerRun + 1));


                                    stringFirstPlayerBall = textViewFirstPlayerBall.getText().toString();

                                    firstPlayerBall = Integer.parseInt(stringFirstPlayerBall);

                                    //textViewFirstPlayerBall.setText(String.valueOf(firstPlayerBall + 1));


                                    stringBowlerRun = textViewBowlerRun.getText().toString();

                                    bowlerRun = Integer.parseInt(stringBowlerRun);

                                    textViewBowlerRun.setText(String.valueOf(bowlerRun + 1));


                                    stringBowlerBall = textViewBowlerBall.getText().toString();

                                    bowlerOversFloat = Float.parseFloat(stringBowlerBall);

                                    //textViewBowlerBall.setText(new DecimalFormat("##.#").format(bowlerOversFloat + 0.1));

                                    textViewCurrentOver.setText(textViewCurrentOver.getText() + " " + str1 + "nb");

                                    stringExtraRun = textViewExtraRun.getText().toString();

                                    extraRun = Integer.parseInt(stringExtraRun);

                                    textViewExtraRun.setText(String.valueOf(extraRun + numberNoBall + 1));

                                    reusableFunctionBowler();

                                } else {

                                    stringPlayingTeamRun = textViewPlayingTeamRun.getText().toString();

                                    playingTeamRum = Integer.parseInt(stringPlayingTeamRun);

                                    textViewPlayingTeamRun.setText(String.valueOf(playingTeamRum + numberNoBall + 1));


                                    stringPlayingTeamOvers = textViewPlayingTeamOvers.getText().toString();

                                    playingTeamOversFloat = Float.parseFloat(stringPlayingTeamOvers);

                                    //textViewPlayingTeamOvers.setText(new DecimalFormat("##.#").format((playingTeamOversFloat + 0.1)));

                                    reusableFunctionPlayer();
                                    stringSecondPlayerRun = textViewSecondPlayerRun.getText().toString();

                                    secondPlayerRun = Integer.parseInt(stringSecondPlayerRun);

                                    //textViewSecondPlayerRun.setText(String.valueOf(secondPlayerRun + 1));


                                    stringSecondPlayerBall = textViewSecondPlayerBall.getText().toString();

                                    secondPlayerBall = Integer.parseInt(stringSecondPlayerBall);

                                    //textViewSecondPlayerBall.setText(String.valueOf(secondPlayerBall + 1));

                                    stringBowlerRun = textViewBowlerRun.getText().toString();

                                    bowlerRun = Integer.parseInt(stringBowlerRun);

                                    textViewBowlerRun.setText(String.valueOf(bowlerRun + 1));


                                    stringBowlerBall = textViewBowlerBall.getText().toString();

                                    bowlerOversFloat = Float.parseFloat(stringBowlerBall);

                                    //textViewBowlerBall.setText(new DecimalFormat("##.#").format((bowlerOversFloat + 0.1)));

                                    textViewCurrentOver.setText(textViewCurrentOver.getText() + " " + str1 + "nb");
                                    stringExtraRun = textViewExtraRun.getText().toString();

                                    extraRun = Integer.parseInt(stringExtraRun);

                                    textViewExtraRun.setText(String.valueOf(extraRun + numberNoBall + 1));
                                    reusableFunctionBowler();

                                }

                                runsServerCall(numberNoBall);
                                dialog.dismiss();
                            }
                        });

                        textViewNone.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                if (radioButtonFirstPlayer.isChecked()) {
                                    stringPlayingTeamRun = textViewPlayingTeamRun.getText().toString();

                                    playingTeamRum = Integer.parseInt(stringPlayingTeamRun);

                                    textViewPlayingTeamRun.setText(String.valueOf(playingTeamRum + numberNoBall + 1));

                                    stringPlayingTeamOvers = textViewPlayingTeamOvers.getText().toString();

                                    playingTeamOversFloat = Float.parseFloat(stringPlayingTeamOvers);

                                    //textViewPlayingTeamOvers.setText(new DecimalFormat("##.#").format((playingTeamOversFloat + 0.1)));

                                    reusableFunctionPlayer();
                                    stringFirstPlayerRun = textViewFirstPlayerRun.getText().toString();

                                    firstPlayerRun = Integer.parseInt(stringFirstPlayerRun);

                                    textViewFirstPlayerRun.setText(String.valueOf(firstPlayerRun + 1));


                                    stringFirstPlayerBall = textViewFirstPlayerBall.getText().toString();

                                    firstPlayerBall = Integer.parseInt(stringFirstPlayerBall);

                                    textViewFirstPlayerBall.setText(String.valueOf(firstPlayerBall + 1));


                                    stringBowlerRun = textViewBowlerRun.getText().toString();

                                    bowlerRun = Integer.parseInt(stringBowlerRun);

                                    textViewBowlerRun.setText(String.valueOf(bowlerRun + 2));


                                    stringBowlerBall = textViewBowlerBall.getText().toString();

                                    bowlerOversFloat = Float.parseFloat(stringBowlerBall);

                                    //textViewBowlerBall.setText(new DecimalFormat("##.#").format(bowlerOversFloat + 0.1));

                                    textViewCurrentOver.setText(textViewCurrentOver.getText() + " " + str1 + "nb");

                                    stringExtraRun = textViewExtraRun.getText().toString();

                                    extraRun = Integer.parseInt(stringExtraRun);

                                    textViewExtraRun.setText(String.valueOf(extraRun + 1));

                                    reusableFunctionBowler();

                                } else {

                                    stringPlayingTeamRun = textViewPlayingTeamRun.getText().toString();

                                    playingTeamRum = Integer.parseInt(stringPlayingTeamRun);

                                    textViewPlayingTeamRun.setText(String.valueOf(playingTeamRum + numberNoBall + 1));


                                    stringPlayingTeamOvers = textViewPlayingTeamOvers.getText().toString();

                                    playingTeamOversFloat = Float.parseFloat(stringPlayingTeamOvers);

                                    //textViewPlayingTeamOvers.setText(new DecimalFormat("##.#").format((playingTeamOversFloat + 0.1)));

                                    reusableFunctionPlayer();
                                    stringSecondPlayerRun = textViewSecondPlayerRun.getText().toString();

                                    secondPlayerRun = Integer.parseInt(stringSecondPlayerRun);

                                    textViewSecondPlayerRun.setText(String.valueOf(secondPlayerRun + 1));


                                    stringSecondPlayerBall = textViewSecondPlayerBall.getText().toString();

                                    secondPlayerBall = Integer.parseInt(stringSecondPlayerBall);

                                    textViewSecondPlayerBall.setText(String.valueOf(secondPlayerBall + 1));

                                    stringBowlerRun = textViewBowlerRun.getText().toString();

                                    bowlerRun = Integer.parseInt(stringBowlerRun);

                                    textViewBowlerRun.setText(String.valueOf(bowlerRun + 2));


                                    stringBowlerBall = textViewBowlerBall.getText().toString();

                                    bowlerOversFloat = Float.parseFloat(stringBowlerBall);

                                    //textViewBowlerBall.setText(new DecimalFormat("##.#").format((bowlerOversFloat + 0.1)));

                                    textViewCurrentOver.setText(textViewCurrentOver.getText() + " " + str1 + "nb");
                                    stringExtraRun = textViewExtraRun.getText().toString();

                                    extraRun = Integer.parseInt(stringExtraRun);

                                    textViewExtraRun.setText(String.valueOf(extraRun + 1));
                                    reusableFunctionBowler();

                                }

                                runsServerCall(numberNoBall);
                                dialog.dismiss();

                            }
                        });

                        dialog = dialogBuilder.create();
                        dialog.show();


                    } else {

                        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(MatchPlayActivity.this);
                        LayoutInflater inflater = MatchPlayActivity.this.getLayoutInflater();
                        final View dialogView = inflater.inflate(R.layout.layout_no_ball, null);
                        dialogBuilder.setView(dialogView);
                        dialogBuilder.setCancelable(true);

                        TextView textViewByes = dialogView.findViewById(R.id.textViewByesNoBall);

                        TextView textViewLegByes = dialogView.findViewById(R.id.textViewLegByesNoBall);

                        TextView textViewNone = dialogView.findViewById(R.id.textViewNoneNoBall);

                        textViewByes.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                if (radioButtonFirstPlayer.isChecked()) {
                                    stringPlayingTeamRun = textViewPlayingTeamRun.getText().toString();

                                    playingTeamRum = Integer.parseInt(stringPlayingTeamRun);

                                    textViewPlayingTeamRun.setText(String.valueOf(playingTeamRum + numberNoBall + 1));

                                    stringPlayingTeamOvers = textViewPlayingTeamOvers.getText().toString();

                                    playingTeamOversFloat = Float.parseFloat(stringPlayingTeamOvers);

                                    //textViewPlayingTeamOvers.setText(new DecimalFormat("##.#").format((playingTeamOversFloat + 0.1)));

                                    reusableFunctionPlayer();
                                    stringFirstPlayerRun = textViewFirstPlayerRun.getText().toString();

                                    firstPlayerRun = Integer.parseInt(stringFirstPlayerRun);

                                    //textViewFirstPlayerRun.setText(String.valueOf(firstPlayerRun + 1));


                                    stringFirstPlayerBall = textViewFirstPlayerBall.getText().toString();

                                    firstPlayerBall = Integer.parseInt(stringFirstPlayerBall);

                                    //textViewFirstPlayerBall.setText(String.valueOf(firstPlayerBall + 1));


                                    stringBowlerRun = textViewBowlerRun.getText().toString();

                                    bowlerRun = Integer.parseInt(stringBowlerRun);

                                    textViewBowlerRun.setText(String.valueOf(bowlerRun + 1));


                                    stringBowlerBall = textViewBowlerBall.getText().toString();

                                    bowlerOversFloat = Float.parseFloat(stringBowlerBall);

                                    //textViewBowlerBall.setText(new DecimalFormat("##.#").format(bowlerOversFloat + 0.1));

                                    textViewCurrentOver.setText(textViewCurrentOver.getText() + " " + str1 + "nb");

                                    stringExtraRun = textViewExtraRun.getText().toString();

                                    extraRun = Integer.parseInt(stringExtraRun);

                                    textViewExtraRun.setText(String.valueOf(extraRun + numberNoBall + 1));

                                    reusableFunctionBowler();


                                } else {

                                    stringPlayingTeamRun = textViewPlayingTeamRun.getText().toString();

                                    playingTeamRum = Integer.parseInt(stringPlayingTeamRun);

                                    textViewPlayingTeamRun.setText(String.valueOf(playingTeamRum + numberNoBall + 1));


                                    stringPlayingTeamOvers = textViewPlayingTeamOvers.getText().toString();

                                    playingTeamOversFloat = Float.parseFloat(stringPlayingTeamOvers);

                                    //textViewPlayingTeamOvers.setText(new DecimalFormat("##.#").format((playingTeamOversFloat + 0.1)));

                                    reusableFunctionPlayer();
                                    stringSecondPlayerRun = textViewSecondPlayerRun.getText().toString();

                                    secondPlayerRun = Integer.parseInt(stringSecondPlayerRun);

                                    //textViewSecondPlayerRun.setText(String.valueOf(secondPlayerRun + 1));


                                    stringSecondPlayerBall = textViewSecondPlayerBall.getText().toString();

                                    secondPlayerBall = Integer.parseInt(stringSecondPlayerBall);

                                    //textViewSecondPlayerBall.setText(String.valueOf(secondPlayerBall + 1));

                                    stringBowlerRun = textViewBowlerRun.getText().toString();

                                    bowlerRun = Integer.parseInt(stringBowlerRun);

                                    textViewBowlerRun.setText(String.valueOf(bowlerRun + 1));


                                    stringBowlerBall = textViewBowlerBall.getText().toString();

                                    bowlerOversFloat = Float.parseFloat(stringBowlerBall);

                                    //textViewBowlerBall.setText(new DecimalFormat("##.#").format((bowlerOversFloat + 0.1)));

                                    textViewCurrentOver.setText(textViewCurrentOver.getText() + " " + str1 + "nb");
                                    stringExtraRun = textViewExtraRun.getText().toString();

                                    extraRun = Integer.parseInt(stringExtraRun);

                                    textViewExtraRun.setText(String.valueOf(extraRun + numberNoBall + 1));
                                    reusableFunctionBowler();

                                }

                                runsServerCall(numberNoBall);

                                dialog.dismiss();
                            }
                        });


                        textViewLegByes.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                if (radioButtonFirstPlayer.isChecked()) {
                                    stringPlayingTeamRun = textViewPlayingTeamRun.getText().toString();

                                    playingTeamRum = Integer.parseInt(stringPlayingTeamRun);

                                    textViewPlayingTeamRun.setText(String.valueOf(playingTeamRum + numberNoBall + 1));

                                    stringPlayingTeamOvers = textViewPlayingTeamOvers.getText().toString();

                                    playingTeamOversFloat = Float.parseFloat(stringPlayingTeamOvers);

                                    //textViewPlayingTeamOvers.setText(new DecimalFormat("##.#").format((playingTeamOversFloat + 0.1)));

                                    reusableFunctionPlayer();
                                    stringFirstPlayerRun = textViewFirstPlayerRun.getText().toString();

                                    firstPlayerRun = Integer.parseInt(stringFirstPlayerRun);

                                    //textViewFirstPlayerRun.setText(String.valueOf(firstPlayerRun + 1));


                                    stringFirstPlayerBall = textViewFirstPlayerBall.getText().toString();

                                    firstPlayerBall = Integer.parseInt(stringFirstPlayerBall);

                                    //textViewFirstPlayerBall.setText(String.valueOf(firstPlayerBall + 1));


                                    stringBowlerRun = textViewBowlerRun.getText().toString();

                                    bowlerRun = Integer.parseInt(stringBowlerRun);

                                    textViewBowlerRun.setText(String.valueOf(bowlerRun + 1));


                                    stringBowlerBall = textViewBowlerBall.getText().toString();

                                    bowlerOversFloat = Float.parseFloat(stringBowlerBall);

                                    //textViewBowlerBall.setText(new DecimalFormat("##.#").format(bowlerOversFloat + 0.1));

                                    textViewCurrentOver.setText(textViewCurrentOver.getText() + " " + str1 + "nb");

                                    stringExtraRun = textViewExtraRun.getText().toString();

                                    extraRun = Integer.parseInt(stringExtraRun);

                                    textViewExtraRun.setText(String.valueOf(extraRun + numberNoBall + 1));

                                    reusableFunctionBowler();

                                } else {

                                    stringPlayingTeamRun = textViewPlayingTeamRun.getText().toString();

                                    playingTeamRum = Integer.parseInt(stringPlayingTeamRun);

                                    textViewPlayingTeamRun.setText(String.valueOf(playingTeamRum + numberNoBall + 1));


                                    stringPlayingTeamOvers = textViewPlayingTeamOvers.getText().toString();

                                    playingTeamOversFloat = Float.parseFloat(stringPlayingTeamOvers);

                                    //textViewPlayingTeamOvers.setText(new DecimalFormat("##.#").format((playingTeamOversFloat + 0.1)));

                                    reusableFunctionPlayer();
                                    stringSecondPlayerRun = textViewSecondPlayerRun.getText().toString();

                                    secondPlayerRun = Integer.parseInt(stringSecondPlayerRun);

                                    //textViewSecondPlayerRun.setText(String.valueOf(secondPlayerRun + 1));


                                    stringSecondPlayerBall = textViewSecondPlayerBall.getText().toString();

                                    secondPlayerBall = Integer.parseInt(stringSecondPlayerBall);

                                    //textViewSecondPlayerBall.setText(String.valueOf(secondPlayerBall + 1));

                                    stringBowlerRun = textViewBowlerRun.getText().toString();

                                    bowlerRun = Integer.parseInt(stringBowlerRun);

                                    textViewBowlerRun.setText(String.valueOf(bowlerRun + 1));


                                    stringBowlerBall = textViewBowlerBall.getText().toString();

                                    bowlerOversFloat = Float.parseFloat(stringBowlerBall);

                                    //textViewBowlerBall.setText(new DecimalFormat("##.#").format((bowlerOversFloat + 0.1)));

                                    textViewCurrentOver.setText(textViewCurrentOver.getText() + " " + str1 + "nb");
                                    stringExtraRun = textViewExtraRun.getText().toString();

                                    extraRun = Integer.parseInt(stringExtraRun);

                                    textViewExtraRun.setText(String.valueOf(extraRun + numberNoBall + 1));
                                    reusableFunctionBowler();

                                }

                                runsServerCall(numberNoBall);
                                dialog.dismiss();
                            }
                        });

                        textViewNone.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                if (radioButtonFirstPlayer.isChecked()) {
                                    stringPlayingTeamRun = textViewPlayingTeamRun.getText().toString();

                                    playingTeamRum = Integer.parseInt(stringPlayingTeamRun);

                                    textViewPlayingTeamRun.setText(String.valueOf(playingTeamRum + numberNoBall + 1));

                                    stringPlayingTeamOvers = textViewPlayingTeamOvers.getText().toString();

                                    playingTeamOversFloat = Float.parseFloat(stringPlayingTeamOvers);

                                    //textViewPlayingTeamOvers.setText(new DecimalFormat("##.#").format((playingTeamOversFloat + 0.1)));

                                    reusableFunctionPlayer();
                                    stringFirstPlayerRun = textViewFirstPlayerRun.getText().toString();

                                    firstPlayerRun = Integer.parseInt(stringFirstPlayerRun);

                                    textViewFirstPlayerRun.setText(String.valueOf(firstPlayerRun + 1));


                                    stringFirstPlayerBall = textViewFirstPlayerBall.getText().toString();

                                    firstPlayerBall = Integer.parseInt(stringFirstPlayerBall);

                                    textViewFirstPlayerBall.setText(String.valueOf(firstPlayerBall + 1));


                                    stringBowlerRun = textViewBowlerRun.getText().toString();

                                    bowlerRun = Integer.parseInt(stringBowlerRun);

                                    textViewBowlerRun.setText(String.valueOf(bowlerRun + 2));


                                    stringBowlerBall = textViewBowlerBall.getText().toString();

                                    bowlerOversFloat = Float.parseFloat(stringBowlerBall);

                                    //textViewBowlerBall.setText(new DecimalFormat("##.#").format(bowlerOversFloat + 0.1));

                                    textViewCurrentOver.setText(textViewCurrentOver.getText() + " " + str1 + "nb");

                                    stringExtraRun = textViewExtraRun.getText().toString();

                                    extraRun = Integer.parseInt(stringExtraRun);

                                    textViewExtraRun.setText(String.valueOf(extraRun + 1));

                                    reusableFunctionBowler();

                                } else {

                                    stringPlayingTeamRun = textViewPlayingTeamRun.getText().toString();

                                    playingTeamRum = Integer.parseInt(stringPlayingTeamRun);

                                    textViewPlayingTeamRun.setText(String.valueOf(playingTeamRum + numberNoBall + 1));


                                    stringPlayingTeamOvers = textViewPlayingTeamOvers.getText().toString();

                                    playingTeamOversFloat = Float.parseFloat(stringPlayingTeamOvers);

                                    //textViewPlayingTeamOvers.setText(new DecimalFormat("##.#").format((playingTeamOversFloat + 0.1)));

                                    reusableFunctionPlayer();
                                    stringSecondPlayerRun = textViewSecondPlayerRun.getText().toString();

                                    secondPlayerRun = Integer.parseInt(stringSecondPlayerRun);

                                    textViewSecondPlayerRun.setText(String.valueOf(secondPlayerRun + 1));


                                    stringSecondPlayerBall = textViewSecondPlayerBall.getText().toString();

                                    secondPlayerBall = Integer.parseInt(stringSecondPlayerBall);

                                    textViewSecondPlayerBall.setText(String.valueOf(secondPlayerBall + 1));

                                    stringBowlerRun = textViewBowlerRun.getText().toString();

                                    bowlerRun = Integer.parseInt(stringBowlerRun);

                                    textViewBowlerRun.setText(String.valueOf(bowlerRun + 2));


                                    stringBowlerBall = textViewBowlerBall.getText().toString();

                                    bowlerOversFloat = Float.parseFloat(stringBowlerBall);

                                    //textViewBowlerBall.setText(new DecimalFormat("##.#").format((bowlerOversFloat + 0.1)));

                                    textViewCurrentOver.setText(textViewCurrentOver.getText() + " " + str1 + "nb");
                                    stringExtraRun = textViewExtraRun.getText().toString();

                                    extraRun = Integer.parseInt(stringExtraRun);

                                    textViewExtraRun.setText(String.valueOf(extraRun + 1));
                                    reusableFunctionBowler();

                                }

                                runsServerCall(numberNoBall);
                                dialog.dismiss();

                            }
                        });

                        dialog = dialogBuilder.create();
                        dialog.show();
                    }

                }


                if (item.getTitle().equals("nb + wkt")) {
                    Toast.makeText(MatchPlayActivity.this, "No Ball And Wicket", Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });
        popupMenuNoBall.show();

    }

    void wicketPopUp() {
        popupMenuWicket = new PopupMenu(MatchPlayActivity.this, textViewWicket);

        popupMenuWicket.getMenuInflater().inflate(R.menu.wicket_layout, popupMenuWicket.getMenu());

        popupMenuWicket.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(final MenuItem item) {

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MatchPlayActivity.this);
                alertDialogBuilder.setMessage("Are you sure, You wanted to make decision");
                alertDialogBuilder.setPositiveButton("yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {

                                if (item.getTitle().equals("Bowled")) {
                                    stringWicketType = item.getTitle().toString();
                                    DialogFragment firstDialog = new FirstDialog();
                                    firstDialog.show(getFragmentManager(), "fgggggggg");
                                    getBatsmanDetails();
                                    //wicketServerCall();
                                }

                                if (item.getTitle().equals("LBW")) {
                                    stringWicketType = item.getTitle().toString();
                                    DialogFragment firstDialog = new FirstDialog();
                                    firstDialog.show(getFragmentManager(), "fgggggggg");
                                    getBatsmanDetails();
                                }

                                if (item.getTitle().equals("Caught & Bowled")) {
                                    stringWicketType = item.getTitle().toString();
                                    DialogFragment firstDialog = new FirstDialog();
                                    firstDialog.show(getFragmentManager(), "fgggggggg");
                                    getBatsmanDetails();
                                }


                                if (item.getTitle().equals("Retired")) {
                                    stringWicketType = item.getTitle().toString();
                                    DialogFragment firstDialog = new FirstDialog();
                                    firstDialog.show(getFragmentManager(), "fgggggggg");
                                    getBatsmanDetails();
                                }

                                if (item.getTitle().equals("Obstructing The Field")) {
                                    stringWicketType = item.getTitle().toString();
                                    DialogFragment firstDialog = new FirstDialog();
                                    firstDialog.show(getFragmentManager(), "fgggggggg");
                                    getBatsmanDetails();
                                }

                                if (item.getTitle().equals("Handling The Ball")) {
                                    stringWicketType = item.getTitle().toString();
                                    DialogFragment firstDialog = new FirstDialog();
                                    firstDialog.show(getFragmentManager(), "fgggggggg");
                                    getBatsmanDetails();
                                }

                                if (item.getTitle().equals("Caught")) {
                                    stringWicketType = item.getTitle().toString();
                                    //Toast.makeText(MatchPlayActivity.this, "Caught", Toast.LENGTH_SHORT).show();
                                    DialogFragment firstDialogCaught = new FirstDialogCaught();
                                    firstDialogCaught.show(getFragmentManager(), "fgggggggg");
                                    getBatsmanDetails();

                                }

                                if (item.getTitle().equals("Stumped")) {
                                    stringWicketType = item.getTitle().toString();
                                    //Toast.makeText(MatchPlayActivity.this, "Stumped", Toast.LENGTH_SHORT).show();
                                    DialogFragment firstDialogStumped = new FirstDialogStumped();
                                    firstDialogStumped.show(getFragmentManager(), "fgggggggg");

                                    getBatsmanDetails();
                                }

                                if (item.getTitle().equals("Hit Wicket")) {
                                    stringWicketType = item.getTitle().toString();
                                    //Toast.makeText(MatchPlayActivity.this, "Hit Wicket", Toast.LENGTH_SHORT).show();
                                    DialogFragment firstDialogStumped = new FirstDialogHitWicket();
                                    firstDialogStumped.show(getFragmentManager(), "fgggggggg");
                                    getBatsmanDetails();
                                }

                                if (item.getTitle().equals("Run Out")) {
                                    stringWicketType = item.getTitle().toString();
                                    //Toast.makeText(MatchPlayActivity.this, "Run Out", Toast.LENGTH_SHORT).show();
                                    DialogFragment firstDialogRunOut = new FirstDialogRunOut();
                                    firstDialogRunOut.show(getFragmentManager(), "fgggggggg");
                                    getBatsmanDetails();

                                }

                            }
                        });

                alertDialogBuilder.setNegativeButton("No",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();


                return true;
            }
        });
        popupMenuWicket.show();
    }

    void undoPopUp() {
        popupMenuUndo = new PopupMenu(MatchPlayActivity.this, textViewUndo);

        popupMenuUndo.getMenuInflater().inflate(R.menu.undo_layout, popupMenuUndo.getMenu());

        popupMenuUndo.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                Toast.makeText(MatchPlayActivity.this, "You Clicked : " + item.getTitle(), Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        popupMenuUndo.show();
    }

    void dotPopUp() {
        popupMenuDot = new PopupMenu(MatchPlayActivity.this, textViewDot);

        popupMenuDot.getMenuInflater().inflate(R.menu.dot_layout, popupMenuDot.getMenu());

        popupMenuDot.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                Toast.makeText(MatchPlayActivity.this, "You Clicked : " + item.getTitle(), Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        popupMenuDot.show();

    }


    void reusableFunctionBowler() {
        String stringBowler = textViewBowlerBall.getText().toString();

        double testBowler = Double.parseDouble(stringBowler);

        String[] tokBowler = stringBowler.split("\\.");

        String aBowler = tokBowler[0];
        bBowler = tokBowler[1];


        if (bBowler.equals("6")) {

            textViewBowlerBall.setText(new DecimalFormat("##.#").format(testBowler + 0.4));

            //selectOtherBowler();
            setAddress();
            //getBowlerDetails();

        }
    }

    void reusableFunctionPlayer() {
        String stringPlayer = textViewPlayingTeamOvers.getText().toString();

        double testPlayer = Double.parseDouble(stringPlayer);

        String[] tokPlayer = stringPlayer.split("\\.");

        String aPlayer = tokPlayer[0];
        String bZeroPlayer = tokPlayer[1];


        if (bZeroPlayer.equals("6")) {
            textViewPlayingTeamOvers.setText(new DecimalFormat("##.#").format(testPlayer + 0.4));
        }
    }

    public void setAddress() {


        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(MatchPlayActivity.this);
        LayoutInflater inflater = MatchPlayActivity.this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.layout_bowler_select, null);
        dialogBuilder.setTitle("Select New Bowler");
        dialogBuilder.setView(dialogView);
        dialogBuilder.setCancelable(false);

        spinner = (Spinner) dialogView.findViewById(R.id.spinnerNewBowler);
        getBowlerDetails();
        //Button buttonSelect = dialogView.findViewById(R.id.buttonNewBowlerSelect);

        //Button buttonReset = dialogView.findViewById(R.id.buttonNewBowlerReset);

        dialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                stringBowlerName1 = spinner.getSelectedItem().toString();
                String str1 = spinner.getSelectedItem().toString();

                Log.d("spinner", stringBowlerName1);

                //if ()
                textViewBowlerName.setText(stringBowlerName1);
                textViewBowlerBall.setText("0.0");
                textViewBowlerRun.setText("0");
                textViewCurrentOver.setText("");

                try {

                    if (jsonObject.getString("success").equals("1")) {
                        JSONArray jsonArray = jsonObject.getJSONArray("player_list");

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);

                            stringNewTeamName = object.getString("team_name");

                            stringSpaceRemove = stringBowlingTeam.replaceAll(" ", "");

                            if (stringSpaceRemove.equals(stringNewTeamName)) {
                                JSONArray array = object.getJSONArray("players");

                                for (int j = 0; j < array.length(); j++) {
                                    JSONObject obj = array.getJSONObject(j);

                                    stringNewBowlerName = obj.getString("fullname");
                                    stringNewBowlerId = obj.getString("player_id");

                                    if (stringBowlerName1.equals(stringNewBowlerName)) {
                                        stringBowlerId1 = stringNewBowlerId;
                                        //System.out.println("stringSpinnerPlayerIdStrk" + stringBowlerId1);
                                    }

                                }

                            }
                        }
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

                dialog.dismiss();
            }
        });

        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        dialog = dialogBuilder.create();
        dialog.show();
    }

    void getBowlerDetails() {


        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.PLAYER_DETAILS_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            jsonObject = new JSONObject(response);

                            if (jsonObject.getString("success").equals("1")) {
                                JSONArray jsonArray = jsonObject.getJSONArray("player_list");

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject object = jsonArray.getJSONObject(i);

                                    stringNewTeamName = object.getString("team_name");

                                    stringSpaceRemove = stringBowlingTeam.replaceAll(" ", "");

                                    System.out.println(stringSpaceRemove);

                                    Log.d("TeamName111", stringNewTeamName);

                                    Log.d("TeamName222", stringSpaceRemove);


                                    if (stringSpaceRemove.equals(stringNewTeamName)) {
                                        JSONArray array = object.getJSONArray("players");

                                        for (int j = 0; j < array.length(); j++) {
                                            JSONObject obj = array.getJSONObject(j);

                                            stringNewBowlerName = obj.getString("fullname");
                                            stringNewBowlerId = obj.getString("player_id");

                                            spinnerNewBowler.add(stringNewBowlerName);

                                        }

                                        spinner.setAdapter(new ArrayAdapter<String>(MatchPlayActivity.this, R.layout.spinner_layout, spinnerNewBowler));

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
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("match_key", stringMatchKey);

                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }

    void getBatsmanDetails() {


        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.PLAYER_DETAILS_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            jsonObjectBatsman = new JSONObject(response);

                            if (jsonObjectBatsman.getString("success").equals("1")) {
                                JSONArray jsonArray = jsonObjectBatsman.getJSONArray("player_list");

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject object = jsonArray.getJSONObject(i);

                                    stringNewTeamName = object.getString("team_name");

                                    stringSpaceRemove = stringBattingTeam.replaceAll(" ", "");

                                    System.out.println(stringSpaceRemove);

                                    Log.d("TeamName111", stringNewTeamName);

                                    Log.d("TeamName222", stringSpaceRemove);


                                    if (stringSpaceRemove.equals(stringNewTeamName)) {
                                        JSONArray array = object.getJSONArray("players");

                                        for (int j = 0; j < array.length(); j++) {
                                            JSONObject obj = array.getJSONObject(j);

                                            stringNewBatsmanName = obj.getString("fullname");
                                            stringNewBatsmanId = obj.getString("player_id");

                                            spinnerNewBatsman.add(stringNewBatsmanName);

                                        }

                                        spinnerBatsman.setAdapter(new ArrayAdapter<String>(MatchPlayActivity.this, R.layout.spinner_layout, spinnerNewBatsman));

                                    } else {
                                        JSONArray array = object.getJSONArray("players");

                                        for (int j = 0; j < array.length(); j++) {
                                            JSONObject obj = array.getJSONObject(j);

                                            stringCatcherName = obj.getString("fullname");
                                            stringCatcherId = obj.getString("player_id");

                                            spinnerCatcher.add(stringCatcherName);

                                        }

                                        spinnerCatcherName.setAdapter(new ArrayAdapter<String>(MatchPlayActivity.this, R.layout.spinner_layout, spinnerCatcher));

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
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("match_key", stringMatchKey);

                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Spinner spinner = (Spinner) parent;

        switch (view.getId()) {
            case R.id.spinnerNewBatsman:


                stringBatsmanName1 = spinner.getAdapter().getItem(position).toString();

                try {


                    if (jsonObjectBatsman.getString("success").equals("1")) {
                        JSONArray jsonArray = jsonObjectBatsman.getJSONArray("player_list");

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);

                            stringNewTeamName = object.getString("team_name");

                            stringSpaceRemove = stringBowlingTeam.replaceAll(" ", "");

                            if (stringSpaceRemove.equals(stringNewTeamName)) {
                                JSONArray array = object.getJSONArray("players");

                                for (int j = 0; j < array.length(); j++) {
                                    JSONObject obj = array.getJSONObject(j);

                                    stringNewBatsmanName = obj.getString("fullname");
                                    stringNewBatsmanId = obj.getString("player_id");

                                    if (stringBatsmanName1.equals(stringNewBatsmanName)) {
                                        stringBatsmanId1 = stringNewBatsmanId;
                                    }

                                }


                            }
                        }
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

                break;
            case R.id.spinnerCatcherName:

                stringCatcherName1 = spinner.getAdapter().getItem(position).toString();

                try {

                    if (jsonObjectBatsman.getString("success").equals("1")) {
                        JSONArray jsonArray = jsonObjectBatsman.getJSONArray("player_list");

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);

                            stringNewTeamName = object.getString("team_name");

                            stringSpaceRemove = stringBowlingTeam.replaceAll(" ", "");

                            if (!stringSpaceRemove.equals(stringNewTeamName)) {
                                JSONArray array = object.getJSONArray("players");

                                for (int j = 0; j < array.length(); j++) {
                                    JSONObject obj = array.getJSONObject(j);

                                    stringCatcherName = obj.getString("fullname");
                                    stringCatcherId = obj.getString("player_id");


                                    if (stringCatcherName1.equals(stringCatcherName)) {
                                        stringCatcherId1 = stringCatcherId;


                                    }

                                }


                            }
                        }
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

                break;
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    @SuppressLint("ValidFragment")
    public class FirstDialog extends DialogFragment {
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.layout_new_batsman, container, true);
            Button buttonReset = (Button) view.findViewById(R.id.buttonNewBatsmanReset);
            spinnerBatsman = (Spinner) view.findViewById(R.id.spinnerNewBatsman);
            spinnerCatcherName = (Spinner) view.findViewById(R.id.spinnerCatcherName);
            Button buttonSelect = (Button) view.findViewById(R.id.buttonNewBatsmanSelect);

            buttonSelect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (radioButtonFirstPlayer.isChecked()) {

                        stringBatsmanName1 = spinnerBatsman.getSelectedItem().toString();

                        try {

                            if (jsonObjectBatsman.getString("success").equals("1")) {
                                JSONArray jsonArray = jsonObjectBatsman.getJSONArray("player_list");

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject object = jsonArray.getJSONObject(i);

                                    stringNewTeamName = object.getString("team_name");

                                    stringSpaceRemove = stringBattingTeam.replaceAll(" ", "");

                                    if (stringSpaceRemove.equals(stringNewTeamName)) {
                                        JSONArray array = object.getJSONArray("players");

                                        for (int j = 0; j < array.length(); j++) {
                                            JSONObject obj = array.getJSONObject(j);

                                            stringNewBatsmanName = obj.getString("fullname");
                                            stringNewBatsmanId = obj.getString("player_id");

                                            if (stringBatsmanName1.equals(stringNewBatsmanName)) {
                                                stringBatsmanId1 = stringNewBatsmanId;
                                            }

                                        }


                                    }
                                }
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        stringPlayingTeamRun = textViewPlayingTeamRun.getText().toString();

                        playingTeamRum = Integer.parseInt(stringPlayingTeamRun);

                        //textViewPlayingTeamRun.setText(String.valueOf(playingTeamRum + 0));

                        stringPlayingTeamWicket = textViewPlayingTeamWicket.getText().toString();

                        playingTeamWicket = Integer.parseInt(stringPlayingTeamWicket);

                        textViewPlayingTeamWicket.setText(String.valueOf(playingTeamWicket + 1));

                        stringPlayingTeamOvers = textViewPlayingTeamOvers.getText().toString();

                        playingTeamOversFloat = Float.parseFloat(stringPlayingTeamOvers);

                        textViewPlayingTeamOvers.setText(new DecimalFormat("##.#").format(playingTeamOversFloat + 0.1));

                        reusableFunctionPlayer();

                        stringFirstPlayerRun = textViewFirstPlayerRun.getText().toString();

                        firstPlayerRun = Integer.parseInt(stringFirstPlayerRun);

                        //textViewFirstPlayerRun.setText(String.valueOf(firstPlayerRun + 0));
                        stringFirstPlayerBall = textViewFirstPlayerBall.getText().toString();

                        firstPlayerBall = Integer.parseInt(stringFirstPlayerBall);

                        textViewFirstPlayerBall.setText(String.valueOf(firstPlayerBall + 1));


                        stringBowlerRun = textViewBowlerRun.getText().toString();

                        bowlerRun = Integer.parseInt(stringBowlerRun);

                        //textViewBowlerRun.setText(String.valueOf(bowlerRun + 0));

                        stringBowlerWicket = textViewBowlerWicket.getText().toString();

                        bowlerWicket = Integer.parseInt(stringBowlerWicket);

                        textViewBowlerWicket.setText(String.valueOf(bowlerWicket + 1));

                        stringBowlerBall = textViewBowlerBall.getText().toString();

                        bowlerOversFloat = Float.parseFloat(stringBowlerBall);

                        textViewBowlerBall.setText(new DecimalFormat("##.#").format(bowlerOversFloat + 0.1));

                        textViewCurrentOver.setText(textViewCurrentOver.getText() + " " + "w");
                        reusableFunctionBowler();

                        wicketServerCall();


                    } else {

                        stringBatsmanName1 = spinnerBatsman.getSelectedItem().toString();

                        try {


                            if (jsonObjectBatsman.getString("success").equals("1")) {
                                JSONArray jsonArray = jsonObjectBatsman.getJSONArray("player_list");

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject object = jsonArray.getJSONObject(i);

                                    stringNewTeamName = object.getString("team_name");

                                    stringSpaceRemove = stringBattingTeam.replaceAll(" ", "");

                                    if (stringSpaceRemove.equals(stringNewTeamName)) {
                                        JSONArray array = object.getJSONArray("players");

                                        for (int j = 0; j < array.length(); j++) {
                                            JSONObject obj = array.getJSONObject(j);

                                            stringNewBatsmanName = obj.getString("fullname");
                                            stringNewBatsmanId = obj.getString("player_id");

                                            if (stringBatsmanName1.equals(stringNewBatsmanName)) {
                                                stringBatsmanId1 = stringNewBatsmanId;
                                            }

                                        }


                                    }
                                }
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        stringPlayingTeamRun = textViewPlayingTeamRun.getText().toString();

                        playingTeamRum = Integer.parseInt(stringPlayingTeamRun);

                        //textViewPlayingTeamRun.setText(String.valueOf(playingTeamRum + 0));

                        stringPlayingTeamWicket = textViewPlayingTeamWicket.getText().toString();

                        playingTeamWicket = Integer.parseInt(stringPlayingTeamWicket);

                        textViewPlayingTeamWicket.setText(String.valueOf(playingTeamWicket + 1));

                        stringPlayingTeamOvers = textViewPlayingTeamOvers.getText().toString();

                        playingTeamOversFloat = Float.parseFloat(stringPlayingTeamOvers);

                        textViewPlayingTeamOvers.setText(new DecimalFormat("##.#").format(playingTeamOversFloat + 0.1));

                        reusableFunctionPlayer();

                        stringSecondPlayerRun = textViewSecondPlayerRun.getText().toString();

                        secondPlayerRun = Integer.parseInt(stringSecondPlayerRun);

                        //textViewSecondPlayerRun.setText(String.valueOf(secondPlayerRun + 0));


                        stringSecondPlayerBall = textViewSecondPlayerBall.getText().toString();

                        secondPlayerBall = Integer.parseInt(stringSecondPlayerBall);

                        textViewSecondPlayerBall.setText(String.valueOf(secondPlayerBall + 1));


                        stringBowlerRun = textViewBowlerRun.getText().toString();

                        bowlerRun = Integer.parseInt(stringBowlerRun);

                        //textViewBowlerRun.setText(String.valueOf(bowlerRun + 0));

                        stringBowlerWicket = textViewBowlerWicket.getText().toString();

                        bowlerWicket = Integer.parseInt(stringBowlerWicket);

                        textViewBowlerWicket.setText(String.valueOf(bowlerWicket + 1));

                        stringBowlerBall = textViewBowlerBall.getText().toString();

                        bowlerOversFloat = Float.parseFloat(stringBowlerBall);

                        textViewBowlerBall.setText(new DecimalFormat("##.#").format(bowlerOversFloat + 0.1));

                        textViewCurrentOver.setText(textViewCurrentOver.getText() + " " + "w");
                        reusableFunctionBowler();

                        wicketServerCall();

                    }

                    dismiss();

                }
            });

            buttonReset.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });


            return view;
        }

        @Override
        public void onResume() {
            super.onResume();

            getDialog().getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        }
    }


    @SuppressLint("ValidFragment")
    public class FirstDialogCaught extends DialogFragment {
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.layout_new_batsman_caught, container, true);
            Button buttonReset = (Button) view.findViewById(R.id.buttonNewBatsmanReset);
            spinnerBatsman = (Spinner) view.findViewById(R.id.spinnerNewBatsman);
            spinnerCatcherName = (Spinner) view.findViewById(R.id.spinnerCatcherName);
            Button buttonSelect = (Button) view.findViewById(R.id.buttonNewBatsmanSelect);

            buttonSelect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (radioButtonFirstPlayer.isChecked()) {

                        stringBatsmanName1 = spinnerBatsman.getSelectedItem().toString();
                        stringCatcherName1 = spinnerCatcherName.getSelectedItem().toString();


                        try {

                            if (jsonObjectBatsman.getString("success").equals("1")) {
                                JSONArray jsonArray = jsonObjectBatsman.getJSONArray("player_list");

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject object = jsonArray.getJSONObject(i);

                                    stringNewTeamName = object.getString("team_name");

                                    stringSpaceRemove = stringBattingTeam.replaceAll(" ", "");

                                    if (stringSpaceRemove.equals(stringNewTeamName)) {
                                        JSONArray array = object.getJSONArray("players");

                                        for (int j = 0; j < array.length(); j++) {
                                            JSONObject obj = array.getJSONObject(j);

                                            stringNewBatsmanName = obj.getString("fullname");
                                            stringNewBatsmanId = obj.getString("player_id");

                                            if (stringBatsmanName1.equals(stringNewBatsmanName)) {
                                                stringBatsmanId1 = stringNewBatsmanId;
                                            }

                                        }


                                    }
                                    else {
                                        JSONArray array = object.getJSONArray("players");

                                        for (int j = 0; j < array.length(); j++) {
                                            JSONObject obj = array.getJSONObject(j);

                                            stringCatcherName = obj.getString("fullname");
                                            stringCatcherId = obj.getString("player_id");


                                            if (stringCatcherName1.equals(stringCatcherName)) {
                                                stringCatcherId1 = stringCatcherId;

                                                Log.d("catcherName",stringCatcherId1);
                                            }

                                        }
                                    }
                                }
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        stringPlayingTeamRun = textViewPlayingTeamRun.getText().toString();

                        playingTeamRum = Integer.parseInt(stringPlayingTeamRun);

                        //textViewPlayingTeamRun.setText(String.valueOf(playingTeamRum + 0));

                        stringPlayingTeamWicket = textViewPlayingTeamWicket.getText().toString();

                        playingTeamWicket = Integer.parseInt(stringPlayingTeamWicket);

                        textViewPlayingTeamWicket.setText(String.valueOf(playingTeamWicket + 1));

                        stringPlayingTeamOvers = textViewPlayingTeamOvers.getText().toString();

                        playingTeamOversFloat = Float.parseFloat(stringPlayingTeamOvers);

                        textViewPlayingTeamOvers.setText(new DecimalFormat("##.#").format(playingTeamOversFloat + 0.1));

                        reusableFunctionPlayer();

                        stringFirstPlayerRun = textViewFirstPlayerRun.getText().toString();

                        firstPlayerRun = Integer.parseInt(stringFirstPlayerRun);

                        //textViewFirstPlayerRun.setText(String.valueOf(firstPlayerRun + 0));
                        stringFirstPlayerBall = textViewFirstPlayerBall.getText().toString();

                        firstPlayerBall = Integer.parseInt(stringFirstPlayerBall);

                        textViewFirstPlayerBall.setText(String.valueOf(firstPlayerBall + 1));


                        stringBowlerRun = textViewBowlerRun.getText().toString();

                        bowlerRun = Integer.parseInt(stringBowlerRun);

                        //textViewBowlerRun.setText(String.valueOf(bowlerRun + 0));

                        stringBowlerWicket = textViewBowlerWicket.getText().toString();

                        bowlerWicket = Integer.parseInt(stringBowlerWicket);

                        textViewBowlerWicket.setText(String.valueOf(bowlerWicket + 1));

                        stringBowlerBall = textViewBowlerBall.getText().toString();

                        bowlerOversFloat = Float.parseFloat(stringBowlerBall);

                        textViewBowlerBall.setText(new DecimalFormat("##.#").format(bowlerOversFloat + 0.1));

                        textViewCurrentOver.setText(textViewCurrentOver.getText() + " " + "W");
                        reusableFunctionBowler();

                        wicketServerCall();
                    } else {

                        stringBatsmanName1 = spinnerBatsman.getSelectedItem().toString();
                        stringCatcherName1 = spinnerCatcherName.getSelectedItem().toString();


                        try {

                            if (jsonObjectBatsman.getString("success").equals("1")) {
                                JSONArray jsonArray = jsonObjectBatsman.getJSONArray("player_list");

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject object = jsonArray.getJSONObject(i);

                                    stringNewTeamName = object.getString("team_name");

                                    stringSpaceRemove = stringBattingTeam.replaceAll(" ", "");

                                    if (stringSpaceRemove.equals(stringNewTeamName)) {
                                        JSONArray array = object.getJSONArray("players");

                                        for (int j = 0; j < array.length(); j++) {
                                            JSONObject obj = array.getJSONObject(j);

                                            stringNewBatsmanName = obj.getString("fullname");
                                            stringNewBatsmanId = obj.getString("player_id");

                                            if (stringBatsmanName1.equals(stringNewBatsmanName)) {
                                                stringBatsmanId1 = stringNewBatsmanId;
                                            }

                                        }


                                    }
                                    else {
                                        JSONArray array = object.getJSONArray("players");

                                        for (int j = 0; j < array.length(); j++) {
                                            JSONObject obj = array.getJSONObject(j);

                                            stringCatcherName = obj.getString("fullname");
                                            stringCatcherId = obj.getString("player_id");


                                            if (stringCatcherName1.equals(stringCatcherName)) {
                                                stringCatcherId1 = stringCatcherId;

                                                Log.d("catcherName",stringCatcherId1);
                                            }

                                        }
                                    }
                                }
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                        stringPlayingTeamRun = textViewPlayingTeamRun.getText().toString();

                        playingTeamRum = Integer.parseInt(stringPlayingTeamRun);

                        //textViewPlayingTeamRun.setText(String.valueOf(playingTeamRum + 0));

                        stringPlayingTeamWicket = textViewPlayingTeamWicket.getText().toString();

                        playingTeamWicket = Integer.parseInt(stringPlayingTeamWicket);

                        textViewPlayingTeamWicket.setText(String.valueOf(playingTeamWicket + 1));

                        stringPlayingTeamOvers = textViewPlayingTeamOvers.getText().toString();

                        playingTeamOversFloat = Float.parseFloat(stringPlayingTeamOvers);

                        textViewPlayingTeamOvers.setText(new DecimalFormat("##.#").format(playingTeamOversFloat + 0.1));

                        reusableFunctionPlayer();

                        stringSecondPlayerRun = textViewSecondPlayerRun.getText().toString();

                        secondPlayerRun = Integer.parseInt(stringSecondPlayerRun);

                        //textViewSecondPlayerRun.setText(String.valueOf(secondPlayerRun + 0));


                        stringSecondPlayerBall = textViewSecondPlayerBall.getText().toString();

                        secondPlayerBall = Integer.parseInt(stringSecondPlayerBall);

                        textViewSecondPlayerBall.setText(String.valueOf(secondPlayerBall + 1));


                        stringBowlerRun = textViewBowlerRun.getText().toString();

                        bowlerRun = Integer.parseInt(stringBowlerRun);

                        //textViewBowlerRun.setText(String.valueOf(bowlerRun + 0));

                        stringBowlerWicket = textViewBowlerWicket.getText().toString();

                        bowlerWicket = Integer.parseInt(stringBowlerWicket);

                        textViewBowlerWicket.setText(String.valueOf(bowlerWicket + 1));

                        stringBowlerBall = textViewBowlerBall.getText().toString();

                        bowlerOversFloat = Float.parseFloat(stringBowlerBall);

                        textViewBowlerBall.setText(new DecimalFormat("##.#").format(bowlerOversFloat + 0.1));

                        textViewCurrentOver.setText(textViewCurrentOver.getText() + " " + "W");
                        reusableFunctionBowler();
                        wicketServerCall();
                    }

                    dismiss();

                }
            });

            buttonReset.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });


            return view;
        }

        @Override
        public void onResume() {
            super.onResume();

            getDialog().getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        }
    }


    @SuppressLint("ValidFragment")
    public class FirstDialogStumped extends DialogFragment {
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.layout_new_batsman_stumped, container, true);
            Button buttonReset = (Button) view.findViewById(R.id.buttonNewBatsmanReset);
            spinnerBatsman = (Spinner) view.findViewById(R.id.spinnerNewBatsman);
            spinnerCatcherName = (Spinner) view.findViewById(R.id.spinnerCatcherNameStumped);
            Button buttonSelect = (Button) view.findViewById(R.id.buttonNewBatsmanSelect);
            final RadioButton radioButtonWide = view.findViewById(R.id.radioButtonWide);
            final RadioButton radioButtonNo = view.findViewById(R.id.radioButtonNo);
            final RadioButton radioButtonNor = view.findViewById(R.id.radioButtonNor);

            buttonSelect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (radioButtonWide.isChecked()) {

                        if (radioButtonFirstPlayer.isChecked()) {

                            stringBatsmanName1 = spinnerBatsman.getSelectedItem().toString();
                            stringCatcherName1 = spinnerCatcherName.getSelectedItem().toString();


                            try {

                                if (jsonObjectBatsman.getString("success").equals("1")) {
                                    JSONArray jsonArray = jsonObjectBatsman.getJSONArray("player_list");

                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject object = jsonArray.getJSONObject(i);

                                        stringNewTeamName = object.getString("team_name");

                                        stringSpaceRemove = stringBattingTeam.replaceAll(" ", "");

                                        if (stringSpaceRemove.equals(stringNewTeamName)) {
                                            JSONArray array = object.getJSONArray("players");

                                            for (int j = 0; j < array.length(); j++) {
                                                JSONObject obj = array.getJSONObject(j);

                                                stringNewBatsmanName = obj.getString("fullname");
                                                stringNewBatsmanId = obj.getString("player_id");

                                                if (stringBatsmanName1.equals(stringNewBatsmanName)) {
                                                    stringBatsmanId1 = stringNewBatsmanId;
                                                }

                                            }


                                        }
                                        else {
                                            JSONArray array = object.getJSONArray("players");

                                            for (int j = 0; j < array.length(); j++) {
                                                JSONObject obj = array.getJSONObject(j);

                                                stringCatcherName = obj.getString("fullname");
                                                stringCatcherId = obj.getString("player_id");


                                                if (stringCatcherName1.equals(stringCatcherName)) {
                                                    stringCatcherId1 = stringCatcherId;

                                                    Log.d("catcherName",stringCatcherId1);
                                                }

                                            }
                                        }
                                    }
                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            stringPlayingTeamRun = textViewPlayingTeamRun.getText().toString();

                            playingTeamRum = Integer.parseInt(stringPlayingTeamRun);

                            textViewPlayingTeamRun.setText(String.valueOf(playingTeamRum + 1));

                            stringPlayingTeamWicket = textViewPlayingTeamWicket.getText().toString();

                            playingTeamWicket = Integer.parseInt(stringPlayingTeamWicket);

                            textViewPlayingTeamWicket.setText(String.valueOf(playingTeamWicket + 1));

                            stringPlayingTeamOvers = textViewPlayingTeamOvers.getText().toString();

                            playingTeamOversFloat = Float.parseFloat(stringPlayingTeamOvers);

                            //textViewPlayingTeamOvers.setText(new DecimalFormat("##.#").format(playingTeamOversFloat + 0.1));

                            reusableFunctionPlayer();

                            stringFirstPlayerRun = textViewFirstPlayerRun.getText().toString();

                            firstPlayerRun = Integer.parseInt(stringFirstPlayerRun);

                            //textViewFirstPlayerRun.setText(String.valueOf(firstPlayerRun + 0));
                            stringFirstPlayerBall = textViewFirstPlayerBall.getText().toString();

                            firstPlayerBall = Integer.parseInt(stringFirstPlayerBall);

                            //textViewFirstPlayerBall.setText(String.valueOf(firstPlayerBall + 1));


                            stringBowlerRun = textViewBowlerRun.getText().toString();

                            bowlerRun = Integer.parseInt(stringBowlerRun);

                            //textViewBowlerRun.setText(String.valueOf(bowlerRun + 0));

                            stringBowlerWicket = textViewBowlerWicket.getText().toString();

                            bowlerWicket = Integer.parseInt(stringBowlerWicket);

                            textViewBowlerWicket.setText(String.valueOf(bowlerWicket + 1));

                            stringBowlerBall = textViewBowlerBall.getText().toString();

                            bowlerOversFloat = Float.parseFloat(stringBowlerBall);

                            //textViewBowlerBall.setText(new DecimalFormat("##.#").format(bowlerOversFloat + 0.1));

                            textViewCurrentOver.setText(textViewCurrentOver.getText() + " " + "wd+w");
                            stringExtraRun = textViewExtraRun.getText().toString();

                            extraRun = Integer.parseInt(stringExtraRun);

                            textViewExtraRun.setText(String.valueOf(extraRun + 1));
                            reusableFunctionBowler();


                        } else {

                            stringBatsmanName1 = spinnerBatsman.getSelectedItem().toString();
                            stringCatcherName1 = spinnerCatcherName.getSelectedItem().toString();


                            try {

                                if (jsonObjectBatsman.getString("success").equals("1")) {
                                    JSONArray jsonArray = jsonObjectBatsman.getJSONArray("player_list");

                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject object = jsonArray.getJSONObject(i);

                                        stringNewTeamName = object.getString("team_name");

                                        stringSpaceRemove = stringBattingTeam.replaceAll(" ", "");

                                        if (stringSpaceRemove.equals(stringNewTeamName)) {
                                            JSONArray array = object.getJSONArray("players");

                                            for (int j = 0; j < array.length(); j++) {
                                                JSONObject obj = array.getJSONObject(j);

                                                stringNewBatsmanName = obj.getString("fullname");
                                                stringNewBatsmanId = obj.getString("player_id");

                                                if (stringBatsmanName1.equals(stringNewBatsmanName)) {
                                                    stringBatsmanId1 = stringNewBatsmanId;
                                                }

                                            }


                                        }
                                        else {
                                            JSONArray array = object.getJSONArray("players");

                                            for (int j = 0; j < array.length(); j++) {
                                                JSONObject obj = array.getJSONObject(j);

                                                stringCatcherName = obj.getString("fullname");
                                                stringCatcherId = obj.getString("player_id");


                                                if (stringCatcherName1.equals(stringCatcherName)) {
                                                    stringCatcherId1 = stringCatcherId;

                                                    Log.d("catcherName",stringCatcherId1);
                                                }

                                            }
                                        }
                                    }
                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            stringPlayingTeamRun = textViewPlayingTeamRun.getText().toString();

                            playingTeamRum = Integer.parseInt(stringPlayingTeamRun);

                            textViewPlayingTeamRun.setText(String.valueOf(playingTeamRum + 1));

                            stringPlayingTeamWicket = textViewPlayingTeamWicket.getText().toString();

                            playingTeamWicket = Integer.parseInt(stringPlayingTeamWicket);

                            textViewPlayingTeamWicket.setText(String.valueOf(playingTeamWicket + 1));

                            stringPlayingTeamOvers = textViewPlayingTeamOvers.getText().toString();

                            playingTeamOversFloat = Float.parseFloat(stringPlayingTeamOvers);

                            //textViewPlayingTeamOvers.setText(new DecimalFormat("##.#").format(playingTeamOversFloat + 0.1));

                            reusableFunctionPlayer();

                            stringSecondPlayerRun = textViewSecondPlayerRun.getText().toString();

                            secondPlayerRun = Integer.parseInt(stringSecondPlayerRun);

                            textViewSecondPlayerRun.setText(String.valueOf(secondPlayerRun + 0));


                            stringSecondPlayerBall = textViewSecondPlayerBall.getText().toString();

                            secondPlayerBall = Integer.parseInt(stringSecondPlayerBall);

                            //textViewSecondPlayerBall.setText(String.valueOf(secondPlayerBall + 1));


                            stringBowlerRun = textViewBowlerRun.getText().toString();

                            bowlerRun = Integer.parseInt(stringBowlerRun);

                            //textViewBowlerRun.setText(String.valueOf(bowlerRun + 0));

                            stringBowlerWicket = textViewBowlerWicket.getText().toString();

                            bowlerWicket = Integer.parseInt(stringBowlerWicket);

                            textViewBowlerWicket.setText(String.valueOf(bowlerWicket + 1));

                            stringBowlerBall = textViewBowlerBall.getText().toString();

                            bowlerOversFloat = Float.parseFloat(stringBowlerBall);

                            //textViewBowlerBall.setText(new DecimalFormat("##.#").format(bowlerOversFloat + 0.1));

                            textViewCurrentOver.setText(textViewCurrentOver.getText() + " " + "wd+w");
                            stringExtraRun = textViewExtraRun.getText().toString();

                            extraRun = Integer.parseInt(stringExtraRun);

                            textViewExtraRun.setText(String.valueOf(extraRun + 1));
                            reusableFunctionBowler();

                        }
                        wicketServerCall();
                        dismiss();

                    }

                    if (radioButtonNo.isChecked()) {
                        if (radioButtonFirstPlayer.isChecked()) {
                            stringBatsmanName1 = spinnerBatsman.getSelectedItem().toString();
                            stringCatcherName1 = spinnerCatcherName.getSelectedItem().toString();


                            try {

                                if (jsonObjectBatsman.getString("success").equals("1")) {
                                    JSONArray jsonArray = jsonObjectBatsman.getJSONArray("player_list");

                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject object = jsonArray.getJSONObject(i);

                                        stringNewTeamName = object.getString("team_name");

                                        stringSpaceRemove = stringBattingTeam.replaceAll(" ", "");

                                        if (stringSpaceRemove.equals(stringNewTeamName)) {
                                            JSONArray array = object.getJSONArray("players");

                                            for (int j = 0; j < array.length(); j++) {
                                                JSONObject obj = array.getJSONObject(j);

                                                stringNewBatsmanName = obj.getString("fullname");
                                                stringNewBatsmanId = obj.getString("player_id");

                                                if (stringBatsmanName1.equals(stringNewBatsmanName)) {
                                                    stringBatsmanId1 = stringNewBatsmanId;
                                                }

                                            }


                                        }
                                        else {
                                            JSONArray array = object.getJSONArray("players");

                                            for (int j = 0; j < array.length(); j++) {
                                                JSONObject obj = array.getJSONObject(j);

                                                stringCatcherName = obj.getString("fullname");
                                                stringCatcherId = obj.getString("player_id");


                                                if (stringCatcherName1.equals(stringCatcherName)) {
                                                    stringCatcherId1 = stringCatcherId;

                                                    Log.d("catcherName",stringCatcherId1);
                                                }

                                            }
                                        }
                                    }
                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            stringPlayingTeamRun = textViewPlayingTeamRun.getText().toString();

                            playingTeamRum = Integer.parseInt(stringPlayingTeamRun);

                            textViewPlayingTeamRun.setText(String.valueOf(playingTeamRum + 1));

                            stringPlayingTeamWicket = textViewPlayingTeamWicket.getText().toString();

                            playingTeamWicket = Integer.parseInt(stringPlayingTeamWicket);

                            textViewPlayingTeamWicket.setText(String.valueOf(playingTeamWicket + 1));

                            stringPlayingTeamOvers = textViewPlayingTeamOvers.getText().toString();

                            playingTeamOversFloat = Float.parseFloat(stringPlayingTeamOvers);

                            //textViewPlayingTeamOvers.setText(new DecimalFormat("##.#").format(playingTeamOversFloat + 0.1));

                            reusableFunctionPlayer();

                            stringFirstPlayerRun = textViewFirstPlayerRun.getText().toString();

                            firstPlayerRun = Integer.parseInt(stringFirstPlayerRun);

                            textViewFirstPlayerRun.setText(String.valueOf(firstPlayerRun + 0));
                            stringFirstPlayerBall = textViewFirstPlayerBall.getText().toString();

                            firstPlayerBall = Integer.parseInt(stringFirstPlayerBall);

                            //textViewFirstPlayerBall.setText(String.valueOf(firstPlayerBall + 1));


                            stringBowlerRun = textViewBowlerRun.getText().toString();

                            bowlerRun = Integer.parseInt(stringBowlerRun);

                            //textViewBowlerRun.setText(String.valueOf(bowlerRun + 0));

                            stringBowlerWicket = textViewBowlerWicket.getText().toString();

                            bowlerWicket = Integer.parseInt(stringBowlerWicket);

                            textViewBowlerWicket.setText(String.valueOf(bowlerWicket + 1));

                            stringBowlerBall = textViewBowlerBall.getText().toString();

                            bowlerOversFloat = Float.parseFloat(stringBowlerBall);

                            //textViewBowlerBall.setText(new DecimalFormat("##.#").format(bowlerOversFloat + 0.1));

                            textViewCurrentOver.setText(textViewCurrentOver.getText() + " " + "wd+w");

                            stringExtraRun = textViewExtraRun.getText().toString();

                            extraRun = Integer.parseInt(stringExtraRun);

                            textViewExtraRun.setText(String.valueOf(extraRun + 1));

                            reusableFunctionBowler();


                        } else {

                            stringBatsmanName1 = spinnerBatsman.getSelectedItem().toString();
                            stringCatcherName1 = spinnerCatcherName.getSelectedItem().toString();


                            try {

                                if (jsonObjectBatsman.getString("success").equals("1")) {
                                    JSONArray jsonArray = jsonObjectBatsman.getJSONArray("player_list");

                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject object = jsonArray.getJSONObject(i);

                                        stringNewTeamName = object.getString("team_name");

                                        stringSpaceRemove = stringBattingTeam.replaceAll(" ", "");

                                        if (stringSpaceRemove.equals(stringNewTeamName)) {
                                            JSONArray array = object.getJSONArray("players");

                                            for (int j = 0; j < array.length(); j++) {
                                                JSONObject obj = array.getJSONObject(j);

                                                stringNewBatsmanName = obj.getString("fullname");
                                                stringNewBatsmanId = obj.getString("player_id");

                                                if (stringBatsmanName1.equals(stringNewBatsmanName)) {
                                                    stringBatsmanId1 = stringNewBatsmanId;
                                                }

                                            }


                                        }
                                        else {
                                            JSONArray array = object.getJSONArray("players");

                                            for (int j = 0; j < array.length(); j++) {
                                                JSONObject obj = array.getJSONObject(j);

                                                stringCatcherName = obj.getString("fullname");
                                                stringCatcherId = obj.getString("player_id");


                                                if (stringCatcherName1.equals(stringCatcherName)) {
                                                    stringCatcherId1 = stringCatcherId;

                                                    Log.d("catcherName",stringCatcherId1);
                                                }

                                            }
                                        }
                                    }
                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            stringPlayingTeamRun = textViewPlayingTeamRun.getText().toString();

                            playingTeamRum = Integer.parseInt(stringPlayingTeamRun);

                            textViewPlayingTeamRun.setText(String.valueOf(playingTeamRum + 1));

                            stringPlayingTeamWicket = textViewPlayingTeamWicket.getText().toString();

                            playingTeamWicket = Integer.parseInt(stringPlayingTeamWicket);

                            textViewPlayingTeamWicket.setText(String.valueOf(playingTeamWicket + 1));

                            stringPlayingTeamOvers = textViewPlayingTeamOvers.getText().toString();

                            playingTeamOversFloat = Float.parseFloat(stringPlayingTeamOvers);

                            //textViewPlayingTeamOvers.setText(new DecimalFormat("##.#").format(playingTeamOversFloat + 0.1));

                            reusableFunctionPlayer();

                            stringSecondPlayerRun = textViewSecondPlayerRun.getText().toString();

                            secondPlayerRun = Integer.parseInt(stringSecondPlayerRun);

                            //textViewSecondPlayerRun.setText(String.valueOf(secondPlayerRun + 0));


                            stringSecondPlayerBall = textViewSecondPlayerBall.getText().toString();

                            secondPlayerBall = Integer.parseInt(stringSecondPlayerBall);

                            //textViewSecondPlayerBall.setText(String.valueOf(secondPlayerBall + 1));


                            stringBowlerRun = textViewBowlerRun.getText().toString();

                            bowlerRun = Integer.parseInt(stringBowlerRun);

                            //textViewBowlerRun.setText(String.valueOf(bowlerRun + 0));

                            stringBowlerWicket = textViewBowlerWicket.getText().toString();

                            bowlerWicket = Integer.parseInt(stringBowlerWicket);

                            textViewBowlerWicket.setText(String.valueOf(bowlerWicket + 1));

                            stringBowlerBall = textViewBowlerBall.getText().toString();

                            bowlerOversFloat = Float.parseFloat(stringBowlerBall);

                            //textViewBowlerBall.setText(new DecimalFormat("##.#").format(bowlerOversFloat + 0.1));

                            textViewCurrentOver.setText(textViewCurrentOver.getText() + " " + "wd+w");

                            stringExtraRun = textViewExtraRun.getText().toString();

                            extraRun = Integer.parseInt(stringExtraRun);

                            textViewExtraRun.setText(String.valueOf(extraRun + 1));
                            reusableFunctionBowler();

                        }
                        wicketServerCall();
                        dismiss();

                    }



                    if (radioButtonNor.isChecked()) {
                        if (radioButtonFirstPlayer.isChecked()) {
                            stringBatsmanName1 = spinnerBatsman.getSelectedItem().toString();
                            stringCatcherName1 = spinnerCatcherName.getSelectedItem().toString();


                            try {

                                if (jsonObjectBatsman.getString("success").equals("1")) {
                                    JSONArray jsonArray = jsonObjectBatsman.getJSONArray("player_list");

                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject object = jsonArray.getJSONObject(i);

                                        stringNewTeamName = object.getString("team_name");

                                        stringSpaceRemove = stringBattingTeam.replaceAll(" ", "");

                                        if (stringSpaceRemove.equals(stringNewTeamName)) {
                                            JSONArray array = object.getJSONArray("players");

                                            for (int j = 0; j < array.length(); j++) {
                                                JSONObject obj = array.getJSONObject(j);

                                                stringNewBatsmanName = obj.getString("fullname");
                                                stringNewBatsmanId = obj.getString("player_id");

                                                if (stringBatsmanName1.equals(stringNewBatsmanName)) {
                                                    stringBatsmanId1 = stringNewBatsmanId;
                                                }

                                            }


                                        }
                                        else {
                                            JSONArray array = object.getJSONArray("players");

                                            for (int j = 0; j < array.length(); j++) {
                                                JSONObject obj = array.getJSONObject(j);

                                                stringCatcherName = obj.getString("fullname");
                                                stringCatcherId = obj.getString("player_id");


                                                if (stringCatcherName1.equals(stringCatcherName)) {
                                                    stringCatcherId1 = stringCatcherId;

                                                    Log.d("catcherName",stringCatcherId1);
                                                }

                                            }
                                        }
                                    }
                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            stringPlayingTeamRun = textViewPlayingTeamRun.getText().toString();

                            playingTeamRum = Integer.parseInt(stringPlayingTeamRun);

                            //textViewPlayingTeamRun.setText(String.valueOf(playingTeamRum + 0));

                            stringPlayingTeamWicket = textViewPlayingTeamWicket.getText().toString();

                            playingTeamWicket = Integer.parseInt(stringPlayingTeamWicket);

                            textViewPlayingTeamWicket.setText(String.valueOf(playingTeamWicket + 1));

                            stringPlayingTeamOvers = textViewPlayingTeamOvers.getText().toString();

                            playingTeamOversFloat = Float.parseFloat(stringPlayingTeamOvers);

                            textViewPlayingTeamOvers.setText(new DecimalFormat("##.#").format(playingTeamOversFloat + 0.1));

                            reusableFunctionPlayer();

                            stringFirstPlayerRun = textViewFirstPlayerRun.getText().toString();

                            firstPlayerRun = Integer.parseInt(stringFirstPlayerRun);

                            //textViewFirstPlayerRun.setText(String.valueOf(firstPlayerRun + 0));
                            stringFirstPlayerBall = textViewFirstPlayerBall.getText().toString();

                            firstPlayerBall = Integer.parseInt(stringFirstPlayerBall);

                            textViewFirstPlayerBall.setText(String.valueOf(firstPlayerBall + 1));


                            stringBowlerRun = textViewBowlerRun.getText().toString();

                            bowlerRun = Integer.parseInt(stringBowlerRun);

                            //textViewBowlerRun.setText(String.valueOf(bowlerRun + 0));

                            stringBowlerWicket = textViewBowlerWicket.getText().toString();

                            bowlerWicket = Integer.parseInt(stringBowlerWicket);

                            textViewBowlerWicket.setText(String.valueOf(bowlerWicket + 1));

                            stringBowlerBall = textViewBowlerBall.getText().toString();

                            bowlerOversFloat = Float.parseFloat(stringBowlerBall);

                            textViewBowlerBall.setText(new DecimalFormat("##.#").format(bowlerOversFloat + 0.1));

                            textViewCurrentOver.setText(textViewCurrentOver.getText() + " " + "w");

                            stringExtraRun = textViewExtraRun.getText().toString();

                            extraRun = Integer.parseInt(stringExtraRun);

                            //textViewExtraRun.setText(String.valueOf(extraRun + numberByes + 1));
                            reusableFunctionBowler();


                        } else {

                            stringBatsmanName1 = spinnerBatsman.getSelectedItem().toString();
                            stringCatcherName1 = spinnerCatcherName.getSelectedItem().toString();


                            try {

                                if (jsonObjectBatsman.getString("success").equals("1")) {
                                    JSONArray jsonArray = jsonObjectBatsman.getJSONArray("player_list");

                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject object = jsonArray.getJSONObject(i);

                                        stringNewTeamName = object.getString("team_name");

                                        stringSpaceRemove = stringBattingTeam.replaceAll(" ", "");

                                        if (stringSpaceRemove.equals(stringNewTeamName)) {
                                            JSONArray array = object.getJSONArray("players");

                                            for (int j = 0; j < array.length(); j++) {
                                                JSONObject obj = array.getJSONObject(j);

                                                stringNewBatsmanName = obj.getString("fullname");
                                                stringNewBatsmanId = obj.getString("player_id");

                                                if (stringBatsmanName1.equals(stringNewBatsmanName)) {
                                                    stringBatsmanId1 = stringNewBatsmanId;
                                                }

                                            }


                                        }
                                        else {
                                            JSONArray array = object.getJSONArray("players");

                                            for (int j = 0; j < array.length(); j++) {
                                                JSONObject obj = array.getJSONObject(j);

                                                stringCatcherName = obj.getString("fullname");
                                                stringCatcherId = obj.getString("player_id");


                                                if (stringCatcherName1.equals(stringCatcherName)) {
                                                    stringCatcherId1 = stringCatcherId;

                                                    Log.d("catcherName",stringCatcherId1);
                                                }

                                            }
                                        }
                                    }
                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            stringPlayingTeamRun = textViewPlayingTeamRun.getText().toString();

                            playingTeamRum = Integer.parseInt(stringPlayingTeamRun);

                            //textViewPlayingTeamRun.setText(String.valueOf(playingTeamRum + 0));

                            stringPlayingTeamWicket = textViewPlayingTeamWicket.getText().toString();

                            playingTeamWicket = Integer.parseInt(stringPlayingTeamWicket);

                            textViewPlayingTeamWicket.setText(String.valueOf(playingTeamWicket + 1));

                            stringPlayingTeamOvers = textViewPlayingTeamOvers.getText().toString();

                            playingTeamOversFloat = Float.parseFloat(stringPlayingTeamOvers);

                            textViewPlayingTeamOvers.setText(new DecimalFormat("##.#").format(playingTeamOversFloat + 0.1));

                            reusableFunctionPlayer();

                            stringSecondPlayerRun = textViewSecondPlayerRun.getText().toString();

                            secondPlayerRun = Integer.parseInt(stringSecondPlayerRun);

                            //textViewSecondPlayerRun.setText(String.valueOf(secondPlayerRun + 0));


                            stringSecondPlayerBall = textViewSecondPlayerBall.getText().toString();

                            secondPlayerBall = Integer.parseInt(stringSecondPlayerBall);

                            textViewSecondPlayerBall.setText(String.valueOf(secondPlayerBall + 1));


                            stringBowlerRun = textViewBowlerRun.getText().toString();

                            bowlerRun = Integer.parseInt(stringBowlerRun);

                            //textViewBowlerRun.setText(String.valueOf(bowlerRun + 0));

                            stringBowlerWicket = textViewBowlerWicket.getText().toString();

                            bowlerWicket = Integer.parseInt(stringBowlerWicket);

                            textViewBowlerWicket.setText(String.valueOf(bowlerWicket + 1));

                            stringBowlerBall = textViewBowlerBall.getText().toString();

                            bowlerOversFloat = Float.parseFloat(stringBowlerBall);

                            textViewBowlerBall.setText(new DecimalFormat("##.#").format(bowlerOversFloat + 0.1));

                            textViewCurrentOver.setText(textViewCurrentOver.getText() + " " + "w");
                            stringExtraRun = textViewExtraRun.getText().toString();

                            extraRun = Integer.parseInt(stringExtraRun);

                            //textViewExtraRun.setText(String.valueOf(extraRun + numberByes + 1));
                            reusableFunctionBowler();

                        }
                        wicketServerCall();
                        dismiss();
                    }

                }
            });

            buttonReset.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });


            return view;
        }

        @Override
        public void onResume() {
            super.onResume();

            getDialog().getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        }
    }

    @SuppressLint("ValidFragment")
    public class FirstDialogHitWicket extends DialogFragment {
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.layout_new_batsman_hit_wicket, container, true);
            Button buttonReset = (Button) view.findViewById(R.id.buttonNewBatsmanReset);
            spinnerBatsman = (Spinner) view.findViewById(R.id.spinnerNewBatsman);
            spinnerCatcherName = (Spinner) view.findViewById(R.id.spinnerCatcherNameStumped);
            Button buttonSelect = (Button) view.findViewById(R.id.buttonNewBatsmanSelect);
            final RadioButton radioButtonWide = view.findViewById(R.id.radioButtonWide);
            final RadioButton radioButtonNo = view.findViewById(R.id.radioButtonNo);
            final RadioButton radioButtonNor = view.findViewById(R.id.radioButtonNor);

            buttonSelect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (radioButtonWide.isChecked()) {

                        if (radioButtonFirstPlayer.isChecked()) {

                            stringBatsmanName1 = spinnerBatsman.getSelectedItem().toString();
                            //stringCatcherName1 = spinnerCatcherName.getSelectedItem().toString();


                            try {

                                if (jsonObjectBatsman.getString("success").equals("1")) {
                                    JSONArray jsonArray = jsonObjectBatsman.getJSONArray("player_list");

                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject object = jsonArray.getJSONObject(i);

                                        stringNewTeamName = object.getString("team_name");

                                        stringSpaceRemove = stringBattingTeam.replaceAll(" ", "");

                                        if (stringSpaceRemove.equals(stringNewTeamName)) {
                                            JSONArray array = object.getJSONArray("players");

                                            for (int j = 0; j < array.length(); j++) {
                                                JSONObject obj = array.getJSONObject(j);

                                                stringNewBatsmanName = obj.getString("fullname");
                                                stringNewBatsmanId = obj.getString("player_id");

                                                if (stringBatsmanName1.equals(stringNewBatsmanName)) {
                                                    stringBatsmanId1 = stringNewBatsmanId;
                                                }

                                            }


                                        }
                                    }
                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            stringPlayingTeamRun = textViewPlayingTeamRun.getText().toString();

                            playingTeamRum = Integer.parseInt(stringPlayingTeamRun);

                            textViewPlayingTeamRun.setText(String.valueOf(playingTeamRum + 1));

                            stringPlayingTeamWicket = textViewPlayingTeamWicket.getText().toString();

                            playingTeamWicket = Integer.parseInt(stringPlayingTeamWicket);

                            textViewPlayingTeamWicket.setText(String.valueOf(playingTeamWicket + 1));

                            stringPlayingTeamOvers = textViewPlayingTeamOvers.getText().toString();

                            playingTeamOversFloat = Float.parseFloat(stringPlayingTeamOvers);

                            //textViewPlayingTeamOvers.setText(new DecimalFormat("##.#").format(playingTeamOversFloat + 0.1));

                            reusableFunctionPlayer();

                            stringFirstPlayerRun = textViewFirstPlayerRun.getText().toString();

                            firstPlayerRun = Integer.parseInt(stringFirstPlayerRun);

                            //textViewFirstPlayerRun.setText(String.valueOf(firstPlayerRun + 0));
                            stringFirstPlayerBall = textViewFirstPlayerBall.getText().toString();

                            firstPlayerBall = Integer.parseInt(stringFirstPlayerBall);

                            //textViewFirstPlayerBall.setText(String.valueOf(firstPlayerBall + 1));


                            stringBowlerRun = textViewBowlerRun.getText().toString();

                            bowlerRun = Integer.parseInt(stringBowlerRun);

                            //textViewBowlerRun.setText(String.valueOf(bowlerRun + 0));

                            stringBowlerWicket = textViewBowlerWicket.getText().toString();

                            bowlerWicket = Integer.parseInt(stringBowlerWicket);

                            textViewBowlerWicket.setText(String.valueOf(bowlerWicket + 1));

                            stringBowlerBall = textViewBowlerBall.getText().toString();

                            bowlerOversFloat = Float.parseFloat(stringBowlerBall);

                            //textViewBowlerBall.setText(new DecimalFormat("##.#").format(bowlerOversFloat + 0.1));

                            textViewCurrentOver.setText(textViewCurrentOver.getText() + " " + "wd+w");
                            stringExtraRun = textViewExtraRun.getText().toString();

                            extraRun = Integer.parseInt(stringExtraRun);

                            textViewExtraRun.setText(String.valueOf(extraRun + 1));
                            reusableFunctionBowler();


                        } else {

                            stringBatsmanName1 = spinnerBatsman.getSelectedItem().toString();
                            //stringCatcherName1 = spinnerCatcherName.getSelectedItem().toString();


                            try {

                                if (jsonObjectBatsman.getString("success").equals("1")) {
                                    JSONArray jsonArray = jsonObjectBatsman.getJSONArray("player_list");

                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject object = jsonArray.getJSONObject(i);

                                        stringNewTeamName = object.getString("team_name");

                                        stringSpaceRemove = stringBattingTeam.replaceAll(" ", "");

                                        if (stringSpaceRemove.equals(stringNewTeamName)) {
                                            JSONArray array = object.getJSONArray("players");

                                            for (int j = 0; j < array.length(); j++) {
                                                JSONObject obj = array.getJSONObject(j);

                                                stringNewBatsmanName = obj.getString("fullname");
                                                stringNewBatsmanId = obj.getString("player_id");

                                                if (stringBatsmanName1.equals(stringNewBatsmanName)) {
                                                    stringBatsmanId1 = stringNewBatsmanId;
                                                }

                                            }


                                        }
                                    }
                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            stringPlayingTeamRun = textViewPlayingTeamRun.getText().toString();

                            playingTeamRum = Integer.parseInt(stringPlayingTeamRun);

                            textViewPlayingTeamRun.setText(String.valueOf(playingTeamRum + 1));

                            stringPlayingTeamWicket = textViewPlayingTeamWicket.getText().toString();

                            playingTeamWicket = Integer.parseInt(stringPlayingTeamWicket);

                            textViewPlayingTeamWicket.setText(String.valueOf(playingTeamWicket + 1));

                            stringPlayingTeamOvers = textViewPlayingTeamOvers.getText().toString();

                            playingTeamOversFloat = Float.parseFloat(stringPlayingTeamOvers);

                            //textViewPlayingTeamOvers.setText(new DecimalFormat("##.#").format(playingTeamOversFloat + 0.1));

                            reusableFunctionPlayer();

                            stringSecondPlayerRun = textViewSecondPlayerRun.getText().toString();

                            secondPlayerRun = Integer.parseInt(stringSecondPlayerRun);

                            textViewSecondPlayerRun.setText(String.valueOf(secondPlayerRun + 0));


                            stringSecondPlayerBall = textViewSecondPlayerBall.getText().toString();

                            secondPlayerBall = Integer.parseInt(stringSecondPlayerBall);

                            //textViewSecondPlayerBall.setText(String.valueOf(secondPlayerBall + 1));


                            stringBowlerRun = textViewBowlerRun.getText().toString();

                            bowlerRun = Integer.parseInt(stringBowlerRun);

                            //textViewBowlerRun.setText(String.valueOf(bowlerRun + 0));

                            stringBowlerWicket = textViewBowlerWicket.getText().toString();

                            bowlerWicket = Integer.parseInt(stringBowlerWicket);

                            textViewBowlerWicket.setText(String.valueOf(bowlerWicket + 1));

                            stringBowlerBall = textViewBowlerBall.getText().toString();

                            bowlerOversFloat = Float.parseFloat(stringBowlerBall);

                            //textViewBowlerBall.setText(new DecimalFormat("##.#").format(bowlerOversFloat + 0.1));

                            textViewCurrentOver.setText(textViewCurrentOver.getText() + " " + "wd+w");
                            stringExtraRun = textViewExtraRun.getText().toString();

                            extraRun = Integer.parseInt(stringExtraRun);

                            textViewExtraRun.setText(String.valueOf(extraRun + 1));
                            reusableFunctionBowler();

                        }
                        wicketServerCall();
                        dismiss();

                    }

                    if (radioButtonNo.isChecked()) {
                        if (radioButtonFirstPlayer.isChecked()) {
                            stringBatsmanName1 = spinnerBatsman.getSelectedItem().toString();
                            stringCatcherName1 = spinnerCatcherName.getSelectedItem().toString();


                            try {

                                if (jsonObjectBatsman.getString("success").equals("1")) {
                                    JSONArray jsonArray = jsonObjectBatsman.getJSONArray("player_list");

                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject object = jsonArray.getJSONObject(i);

                                        stringNewTeamName = object.getString("team_name");

                                        stringSpaceRemove = stringBattingTeam.replaceAll(" ", "");

                                        if (stringSpaceRemove.equals(stringNewTeamName)) {
                                            JSONArray array = object.getJSONArray("players");

                                            for (int j = 0; j < array.length(); j++) {
                                                JSONObject obj = array.getJSONObject(j);

                                                stringNewBatsmanName = obj.getString("fullname");
                                                stringNewBatsmanId = obj.getString("player_id");

                                                if (stringBatsmanName1.equals(stringNewBatsmanName)) {
                                                    stringBatsmanId1 = stringNewBatsmanId;
                                                }

                                            }


                                        }
                                        else {
                                            JSONArray array = object.getJSONArray("players");

                                            for (int j = 0; j < array.length(); j++) {
                                                JSONObject obj = array.getJSONObject(j);

                                                stringCatcherName = obj.getString("fullname");
                                                stringCatcherId = obj.getString("player_id");


                                                if (stringCatcherName1.equals(stringCatcherName)) {
                                                    stringCatcherId1 = stringCatcherId;

                                                    Log.d("catcherName",stringCatcherId1);
                                                }

                                            }
                                        }
                                    }
                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            stringPlayingTeamRun = textViewPlayingTeamRun.getText().toString();

                            playingTeamRum = Integer.parseInt(stringPlayingTeamRun);

                            textViewPlayingTeamRun.setText(String.valueOf(playingTeamRum + 1));

                            stringPlayingTeamWicket = textViewPlayingTeamWicket.getText().toString();

                            playingTeamWicket = Integer.parseInt(stringPlayingTeamWicket);

                            textViewPlayingTeamWicket.setText(String.valueOf(playingTeamWicket + 1));

                            stringPlayingTeamOvers = textViewPlayingTeamOvers.getText().toString();

                            playingTeamOversFloat = Float.parseFloat(stringPlayingTeamOvers);

                            //textViewPlayingTeamOvers.setText(new DecimalFormat("##.#").format(playingTeamOversFloat + 0.1));

                            reusableFunctionPlayer();

                            stringFirstPlayerRun = textViewFirstPlayerRun.getText().toString();

                            firstPlayerRun = Integer.parseInt(stringFirstPlayerRun);

                            textViewFirstPlayerRun.setText(String.valueOf(firstPlayerRun + 0));
                            stringFirstPlayerBall = textViewFirstPlayerBall.getText().toString();

                            firstPlayerBall = Integer.parseInt(stringFirstPlayerBall);

                            //textViewFirstPlayerBall.setText(String.valueOf(firstPlayerBall + 1));


                            stringBowlerRun = textViewBowlerRun.getText().toString();

                            bowlerRun = Integer.parseInt(stringBowlerRun);

                            //textViewBowlerRun.setText(String.valueOf(bowlerRun + 0));

                            stringBowlerWicket = textViewBowlerWicket.getText().toString();

                            bowlerWicket = Integer.parseInt(stringBowlerWicket);

                            textViewBowlerWicket.setText(String.valueOf(bowlerWicket + 1));

                            stringBowlerBall = textViewBowlerBall.getText().toString();

                            bowlerOversFloat = Float.parseFloat(stringBowlerBall);

                            //textViewBowlerBall.setText(new DecimalFormat("##.#").format(bowlerOversFloat + 0.1));

                            textViewCurrentOver.setText(textViewCurrentOver.getText() + " " + "wd+w");

                            stringExtraRun = textViewExtraRun.getText().toString();

                            extraRun = Integer.parseInt(stringExtraRun);

                            textViewExtraRun.setText(String.valueOf(extraRun + 1));

                            reusableFunctionBowler();


                        } else {

                            stringBatsmanName1 = spinnerBatsman.getSelectedItem().toString();
                            stringCatcherName1 = spinnerCatcherName.getSelectedItem().toString();


                            try {

                                if (jsonObjectBatsman.getString("success").equals("1")) {
                                    JSONArray jsonArray = jsonObjectBatsman.getJSONArray("player_list");

                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject object = jsonArray.getJSONObject(i);

                                        stringNewTeamName = object.getString("team_name");

                                        stringSpaceRemove = stringBattingTeam.replaceAll(" ", "");

                                        if (stringSpaceRemove.equals(stringNewTeamName)) {
                                            JSONArray array = object.getJSONArray("players");

                                            for (int j = 0; j < array.length(); j++) {
                                                JSONObject obj = array.getJSONObject(j);

                                                stringNewBatsmanName = obj.getString("fullname");
                                                stringNewBatsmanId = obj.getString("player_id");

                                                if (stringBatsmanName1.equals(stringNewBatsmanName)) {
                                                    stringBatsmanId1 = stringNewBatsmanId;
                                                }

                                            }


                                        }
                                        else {
                                            JSONArray array = object.getJSONArray("players");

                                            for (int j = 0; j < array.length(); j++) {
                                                JSONObject obj = array.getJSONObject(j);

                                                stringCatcherName = obj.getString("fullname");
                                                stringCatcherId = obj.getString("player_id");


                                                if (stringCatcherName1.equals(stringCatcherName)) {
                                                    stringCatcherId1 = stringCatcherId;

                                                    Log.d("catcherName",stringCatcherId1);
                                                }

                                            }
                                        }
                                    }
                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            stringPlayingTeamRun = textViewPlayingTeamRun.getText().toString();

                            playingTeamRum = Integer.parseInt(stringPlayingTeamRun);

                            textViewPlayingTeamRun.setText(String.valueOf(playingTeamRum + 1));

                            stringPlayingTeamWicket = textViewPlayingTeamWicket.getText().toString();

                            playingTeamWicket = Integer.parseInt(stringPlayingTeamWicket);

                            textViewPlayingTeamWicket.setText(String.valueOf(playingTeamWicket + 1));

                            stringPlayingTeamOvers = textViewPlayingTeamOvers.getText().toString();

                            playingTeamOversFloat = Float.parseFloat(stringPlayingTeamOvers);

                            //textViewPlayingTeamOvers.setText(new DecimalFormat("##.#").format(playingTeamOversFloat + 0.1));

                            reusableFunctionPlayer();

                            stringSecondPlayerRun = textViewSecondPlayerRun.getText().toString();

                            secondPlayerRun = Integer.parseInt(stringSecondPlayerRun);

                            //textViewSecondPlayerRun.setText(String.valueOf(secondPlayerRun + 0));


                            stringSecondPlayerBall = textViewSecondPlayerBall.getText().toString();

                            secondPlayerBall = Integer.parseInt(stringSecondPlayerBall);

                            //textViewSecondPlayerBall.setText(String.valueOf(secondPlayerBall + 1));


                            stringBowlerRun = textViewBowlerRun.getText().toString();

                            bowlerRun = Integer.parseInt(stringBowlerRun);

                            //textViewBowlerRun.setText(String.valueOf(bowlerRun + 0));

                            stringBowlerWicket = textViewBowlerWicket.getText().toString();

                            bowlerWicket = Integer.parseInt(stringBowlerWicket);

                            textViewBowlerWicket.setText(String.valueOf(bowlerWicket + 1));

                            stringBowlerBall = textViewBowlerBall.getText().toString();

                            bowlerOversFloat = Float.parseFloat(stringBowlerBall);

                            //textViewBowlerBall.setText(new DecimalFormat("##.#").format(bowlerOversFloat + 0.1));

                            textViewCurrentOver.setText(textViewCurrentOver.getText() + " " + "wd+w");

                            stringExtraRun = textViewExtraRun.getText().toString();

                            extraRun = Integer.parseInt(stringExtraRun);

                            textViewExtraRun.setText(String.valueOf(extraRun + 1));
                            reusableFunctionBowler();

                        }
                        wicketServerCall();
                        dismiss();

                    }



                    if (radioButtonNor.isChecked()) {
                        if (radioButtonFirstPlayer.isChecked()) {
                            stringBatsmanName1 = spinnerBatsman.getSelectedItem().toString();
                            stringCatcherName1 = spinnerCatcherName.getSelectedItem().toString();


                            try {

                                if (jsonObjectBatsman.getString("success").equals("1")) {
                                    JSONArray jsonArray = jsonObjectBatsman.getJSONArray("player_list");

                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject object = jsonArray.getJSONObject(i);

                                        stringNewTeamName = object.getString("team_name");

                                        stringSpaceRemove = stringBattingTeam.replaceAll(" ", "");

                                        if (stringSpaceRemove.equals(stringNewTeamName)) {
                                            JSONArray array = object.getJSONArray("players");

                                            for (int j = 0; j < array.length(); j++) {
                                                JSONObject obj = array.getJSONObject(j);

                                                stringNewBatsmanName = obj.getString("fullname");
                                                stringNewBatsmanId = obj.getString("player_id");

                                                if (stringBatsmanName1.equals(stringNewBatsmanName)) {
                                                    stringBatsmanId1 = stringNewBatsmanId;
                                                }

                                            }


                                        }
                                        else {
                                            JSONArray array = object.getJSONArray("players");

                                            for (int j = 0; j < array.length(); j++) {
                                                JSONObject obj = array.getJSONObject(j);

                                                stringCatcherName = obj.getString("fullname");
                                                stringCatcherId = obj.getString("player_id");


                                                if (stringCatcherName1.equals(stringCatcherName)) {
                                                    stringCatcherId1 = stringCatcherId;

                                                    Log.d("catcherName",stringCatcherId1);
                                                }

                                            }
                                        }
                                    }
                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            stringPlayingTeamRun = textViewPlayingTeamRun.getText().toString();

                            playingTeamRum = Integer.parseInt(stringPlayingTeamRun);

                            //textViewPlayingTeamRun.setText(String.valueOf(playingTeamRum + 0));

                            stringPlayingTeamWicket = textViewPlayingTeamWicket.getText().toString();

                            playingTeamWicket = Integer.parseInt(stringPlayingTeamWicket);

                            textViewPlayingTeamWicket.setText(String.valueOf(playingTeamWicket + 1));

                            stringPlayingTeamOvers = textViewPlayingTeamOvers.getText().toString();

                            playingTeamOversFloat = Float.parseFloat(stringPlayingTeamOvers);

                            textViewPlayingTeamOvers.setText(new DecimalFormat("##.#").format(playingTeamOversFloat + 0.1));

                            reusableFunctionPlayer();

                            stringFirstPlayerRun = textViewFirstPlayerRun.getText().toString();

                            firstPlayerRun = Integer.parseInt(stringFirstPlayerRun);

                            //textViewFirstPlayerRun.setText(String.valueOf(firstPlayerRun + 0));
                            stringFirstPlayerBall = textViewFirstPlayerBall.getText().toString();

                            firstPlayerBall = Integer.parseInt(stringFirstPlayerBall);

                            textViewFirstPlayerBall.setText(String.valueOf(firstPlayerBall + 1));


                            stringBowlerRun = textViewBowlerRun.getText().toString();

                            bowlerRun = Integer.parseInt(stringBowlerRun);

                            //textViewBowlerRun.setText(String.valueOf(bowlerRun + 0));

                            stringBowlerWicket = textViewBowlerWicket.getText().toString();

                            bowlerWicket = Integer.parseInt(stringBowlerWicket);

                            textViewBowlerWicket.setText(String.valueOf(bowlerWicket + 1));

                            stringBowlerBall = textViewBowlerBall.getText().toString();

                            bowlerOversFloat = Float.parseFloat(stringBowlerBall);

                            textViewBowlerBall.setText(new DecimalFormat("##.#").format(bowlerOversFloat + 0.1));

                            textViewCurrentOver.setText(textViewCurrentOver.getText() + " " + "w");

                            stringExtraRun = textViewExtraRun.getText().toString();

                            extraRun = Integer.parseInt(stringExtraRun);

                            //textViewExtraRun.setText(String.valueOf(extraRun + numberByes + 1));
                            reusableFunctionBowler();


                        } else {

                            stringBatsmanName1 = spinnerBatsman.getSelectedItem().toString();
                            stringCatcherName1 = spinnerCatcherName.getSelectedItem().toString();


                            try {

                                if (jsonObjectBatsman.getString("success").equals("1")) {
                                    JSONArray jsonArray = jsonObjectBatsman.getJSONArray("player_list");

                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject object = jsonArray.getJSONObject(i);

                                        stringNewTeamName = object.getString("team_name");

                                        stringSpaceRemove = stringBattingTeam.replaceAll(" ", "");

                                        if (stringSpaceRemove.equals(stringNewTeamName)) {
                                            JSONArray array = object.getJSONArray("players");

                                            for (int j = 0; j < array.length(); j++) {
                                                JSONObject obj = array.getJSONObject(j);

                                                stringNewBatsmanName = obj.getString("fullname");
                                                stringNewBatsmanId = obj.getString("player_id");

                                                if (stringBatsmanName1.equals(stringNewBatsmanName)) {
                                                    stringBatsmanId1 = stringNewBatsmanId;
                                                }

                                            }


                                        }
                                        else {
                                            JSONArray array = object.getJSONArray("players");

                                            for (int j = 0; j < array.length(); j++) {
                                                JSONObject obj = array.getJSONObject(j);

                                                stringCatcherName = obj.getString("fullname");
                                                stringCatcherId = obj.getString("player_id");


                                                if (stringCatcherName1.equals(stringCatcherName)) {
                                                    stringCatcherId1 = stringCatcherId;

                                                    Log.d("catcherName",stringCatcherId1);
                                                }

                                            }
                                        }
                                    }
                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            stringPlayingTeamRun = textViewPlayingTeamRun.getText().toString();

                            playingTeamRum = Integer.parseInt(stringPlayingTeamRun);

                            //textViewPlayingTeamRun.setText(String.valueOf(playingTeamRum + 0));

                            stringPlayingTeamWicket = textViewPlayingTeamWicket.getText().toString();

                            playingTeamWicket = Integer.parseInt(stringPlayingTeamWicket);

                            textViewPlayingTeamWicket.setText(String.valueOf(playingTeamWicket + 1));

                            stringPlayingTeamOvers = textViewPlayingTeamOvers.getText().toString();

                            playingTeamOversFloat = Float.parseFloat(stringPlayingTeamOvers);

                            textViewPlayingTeamOvers.setText(new DecimalFormat("##.#").format(playingTeamOversFloat + 0.1));

                            reusableFunctionPlayer();

                            stringSecondPlayerRun = textViewSecondPlayerRun.getText().toString();

                            secondPlayerRun = Integer.parseInt(stringSecondPlayerRun);

                            //textViewSecondPlayerRun.setText(String.valueOf(secondPlayerRun + 0));


                            stringSecondPlayerBall = textViewSecondPlayerBall.getText().toString();

                            secondPlayerBall = Integer.parseInt(stringSecondPlayerBall);

                            textViewSecondPlayerBall.setText(String.valueOf(secondPlayerBall + 1));


                            stringBowlerRun = textViewBowlerRun.getText().toString();

                            bowlerRun = Integer.parseInt(stringBowlerRun);

                            //textViewBowlerRun.setText(String.valueOf(bowlerRun + 0));

                            stringBowlerWicket = textViewBowlerWicket.getText().toString();

                            bowlerWicket = Integer.parseInt(stringBowlerWicket);

                            textViewBowlerWicket.setText(String.valueOf(bowlerWicket + 1));

                            stringBowlerBall = textViewBowlerBall.getText().toString();

                            bowlerOversFloat = Float.parseFloat(stringBowlerBall);

                            textViewBowlerBall.setText(new DecimalFormat("##.#").format(bowlerOversFloat + 0.1));

                            textViewCurrentOver.setText(textViewCurrentOver.getText() + " " + "w");
                            stringExtraRun = textViewExtraRun.getText().toString();

                            extraRun = Integer.parseInt(stringExtraRun);

                            //textViewExtraRun.setText(String.valueOf(extraRun + numberByes + 1));
                            reusableFunctionBowler();

                        }
                        wicketServerCall();
                        dismiss();
                    }

                }
            });

            buttonReset.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });


            return view;
        }

        @Override
        public void onResume() {
            super.onResume();

            getDialog().getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        }
    }

    @SuppressLint("ValidFragment")
    public class FirstDialogRunOut extends DialogFragment {
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.layout_new_batsman_runout, container, true);
            getDialog().setTitle("Run Out");

            ///view.setBackgroundColor(getResources().getColor(android.R.color.holo_purple));
            Button buttonReset = (Button) view.findViewById(R.id.buttonNewBatsmanReset);
            spinnerBatsman = (Spinner) view.findViewById(R.id.spinnerNewBatsman);
            spinnerCatcherName = (Spinner) view.findViewById(R.id.spinnerCatcherName);
            Button buttonSelect = (Button) view.findViewById(R.id.buttonNewBatsmanSelect);
            final RadioButton radioButtonWide = view.findViewById(R.id.radioButtonWide);
            final RadioButton radioButtonNo = view.findViewById(R.id.radioButtonNo);
            final RadioButton radioButtonNor = view.findViewById(R.id.radioButtonNor);
            //final LinearLayout linearLayout = view.findViewById(R.id.linearLayoutLBB);

            final RadioButton radioButtonByes = view.findViewById(R.id.radioButtonByes);
            final RadioButton radioButtonLegByes = view.findViewById(R.id.radioButtonLegByes);
            final RadioButton radioButtonNone = view.findViewById(R.id.radioButtonNone);

            radioButtonFirst = view.findViewById(R.id.radioButtonFirst);
            radioButtonSecond = view.findViewById(R.id.radioButtonSecond);

            editTextRuns = view.findViewById(R.id.editTextRunOutRuns);
            radioButtonFirst.setText(radioButtonFirstPlayer.getText());
            radioButtonSecond.setText(radioButtonSecondPlayer.getText());
            final String strRuns = editTextRuns.getText().toString();

            //spinnerBatsman.setOnItemSelectedListener(MatchPlayActivity.this);
            //spinnerCatcherName.setOnItemSelectedListener(MatchPlayActivity.this);

            buttonSelect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    editTextRuns1 = Integer.parseInt(editTextRuns.getText().toString());
                    if (radioButtonWide.isChecked()) {


                        if (radioButtonFirstPlayer.isChecked()) {

                            stringBatsmanName1 = spinnerBatsman.getSelectedItem().toString();
                            stringCatcherName1 = spinnerCatcherName.getSelectedItem().toString();


                            try {

                                if (jsonObjectBatsman.getString("success").equals("1")) {
                                    JSONArray jsonArray = jsonObjectBatsman.getJSONArray("player_list");

                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject object = jsonArray.getJSONObject(i);

                                        stringNewTeamName = object.getString("team_name");

                                        stringSpaceRemove = stringBattingTeam.replaceAll(" ", "");

                                        if (stringSpaceRemove.equals(stringNewTeamName)) {
                                            JSONArray array = object.getJSONArray("players");

                                            for (int j = 0; j < array.length(); j++) {
                                                JSONObject obj = array.getJSONObject(j);

                                                stringNewBatsmanName = obj.getString("fullname");
                                                stringNewBatsmanId = obj.getString("player_id");

                                                if (stringBatsmanName1.equals(stringNewBatsmanName)) {
                                                    stringBatsmanId1 = stringNewBatsmanId;
                                                }

                                            }


                                        }
                                        else {
                                            JSONArray array = object.getJSONArray("players");

                                            for (int j = 0; j < array.length(); j++) {
                                                JSONObject obj = array.getJSONObject(j);

                                                stringCatcherName = obj.getString("fullname");
                                                stringCatcherId = obj.getString("player_id");


                                                if (stringCatcherName1.equals(stringCatcherName)) {
                                                    stringCatcherId1 = stringCatcherId;

                                                    Log.d("catcherName",stringCatcherId1);
                                                }

                                            }
                                        }
                                    }
                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            stringPlayingTeamRun = textViewPlayingTeamRun.getText().toString();

                            playingTeamRum = Integer.parseInt(stringPlayingTeamRun);

                            textViewPlayingTeamRun.setText(String.valueOf(playingTeamRum + editTextRuns1 + 1));

                            stringPlayingTeamWicket = textViewPlayingTeamWicket.getText().toString();

                            playingTeamWicket = Integer.parseInt(stringPlayingTeamWicket);

                            textViewPlayingTeamWicket.setText(String.valueOf(playingTeamWicket + 1));

                            stringPlayingTeamOvers = textViewPlayingTeamOvers.getText().toString();

                            playingTeamOversFloat = Float.parseFloat(stringPlayingTeamOvers);

                            //textViewPlayingTeamOvers.setText(new DecimalFormat("##.#").format(playingTeamOversFloat + 0.1));

                            reusableFunctionPlayer();

                            stringFirstPlayerRun = textViewFirstPlayerRun.getText().toString();

                            firstPlayerRun = Integer.parseInt(stringFirstPlayerRun);

                            //textViewFirstPlayerRun.setText(String.valueOf(firstPlayerRun + 0));
                            stringFirstPlayerBall = textViewFirstPlayerBall.getText().toString();

                            firstPlayerBall = Integer.parseInt(stringFirstPlayerBall);

                            //textViewFirstPlayerBall.setText(String.valueOf(firstPlayerBall + 1));


                            stringBowlerRun = textViewBowlerRun.getText().toString();

                            bowlerRun = Integer.parseInt(stringBowlerRun);

                            textViewBowlerRun.setText(String.valueOf(bowlerRun + editTextRuns1 + 1));

                            stringBowlerWicket = textViewBowlerWicket.getText().toString();

                            bowlerWicket = Integer.parseInt(stringBowlerWicket);

                            //textViewBowlerWicket.setText(String.valueOf(bowlerWicket + 1));

                            stringBowlerBall = textViewBowlerBall.getText().toString();

                            bowlerOversFloat = Float.parseFloat(stringBowlerBall);

                            //textViewBowlerBall.setText(new DecimalFormat("##.#").format(bowlerOversFloat + 0.1));

                            textViewCurrentOver.setText(textViewCurrentOver.getText() + " " + "wd+w");

                            stringExtraRun = textViewExtraRun.getText().toString();

                            extraRun = Integer.parseInt(stringExtraRun);

                            textViewExtraRun.setText(String.valueOf(extraRun + editTextRuns1 + 1));
                            reusableFunctionBowler();

                        } else {

                            stringBatsmanName1 = spinnerBatsman.getSelectedItem().toString();
                            stringCatcherName1 = spinnerCatcherName.getSelectedItem().toString();


                            try {

                                if (jsonObjectBatsman.getString("success").equals("1")) {
                                    JSONArray jsonArray = jsonObjectBatsman.getJSONArray("player_list");

                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject object = jsonArray.getJSONObject(i);

                                        stringNewTeamName = object.getString("team_name");

                                        stringSpaceRemove = stringBattingTeam.replaceAll(" ", "");

                                        if (stringSpaceRemove.equals(stringNewTeamName)) {
                                            JSONArray array = object.getJSONArray("players");

                                            for (int j = 0; j < array.length(); j++) {
                                                JSONObject obj = array.getJSONObject(j);

                                                stringNewBatsmanName = obj.getString("fullname");
                                                stringNewBatsmanId = obj.getString("player_id");

                                                if (stringBatsmanName1.equals(stringNewBatsmanName)) {
                                                    stringBatsmanId1 = stringNewBatsmanId;
                                                }

                                            }


                                        }
                                        else {
                                            JSONArray array = object.getJSONArray("players");

                                            for (int j = 0; j < array.length(); j++) {
                                                JSONObject obj = array.getJSONObject(j);

                                                stringCatcherName = obj.getString("fullname");
                                                stringCatcherId = obj.getString("player_id");


                                                if (stringCatcherName1.equals(stringCatcherName)) {
                                                    stringCatcherId1 = stringCatcherId;

                                                    Log.d("catcherName",stringCatcherId1);
                                                }

                                            }
                                        }
                                    }
                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            stringPlayingTeamRun = textViewPlayingTeamRun.getText().toString();

                            playingTeamRum = Integer.parseInt(stringPlayingTeamRun);

                            textViewPlayingTeamRun.setText(String.valueOf(playingTeamRum + editTextRuns1 + 1));

                            stringPlayingTeamWicket = textViewPlayingTeamWicket.getText().toString();

                            playingTeamWicket = Integer.parseInt(stringPlayingTeamWicket);

                            textViewPlayingTeamWicket.setText(String.valueOf(playingTeamWicket + 1));

                            stringPlayingTeamOvers = textViewPlayingTeamOvers.getText().toString();

                            playingTeamOversFloat = Float.parseFloat(stringPlayingTeamOvers);

                            //textViewPlayingTeamOvers.setText(new DecimalFormat("##.#").format(playingTeamOversFloat + 0.1));

                            reusableFunctionPlayer();

                            stringSecondPlayerRun = textViewSecondPlayerRun.getText().toString();

                            secondPlayerRun = Integer.parseInt(stringSecondPlayerRun);

                            //textViewSecondPlayerRun.setText(String.valueOf(secondPlayerRun + 0));


                            stringSecondPlayerBall = textViewSecondPlayerBall.getText().toString();

                            secondPlayerBall = Integer.parseInt(stringSecondPlayerBall);

                            //textViewSecondPlayerBall.setText(String.valueOf(secondPlayerBall + 1));


                            stringBowlerRun = textViewBowlerRun.getText().toString();

                            bowlerRun = Integer.parseInt(stringBowlerRun);

                            textViewBowlerRun.setText(String.valueOf(bowlerRun + editTextRuns1 + 1));

                            stringBowlerWicket = textViewBowlerWicket.getText().toString();

                            bowlerWicket = Integer.parseInt(stringBowlerWicket);

                            //textViewBowlerWicket.setText(String.valueOf(bowlerWicket + 1));

                            stringBowlerBall = textViewBowlerBall.getText().toString();

                            bowlerOversFloat = Float.parseFloat(stringBowlerBall);

                            //textViewBowlerBall.setText(new DecimalFormat("##.#").format(bowlerOversFloat + 0.1));

                            textViewCurrentOver.setText(textViewCurrentOver.getText() + " " + "wd+w");

                            stringExtraRun = textViewExtraRun.getText().toString();

                            extraRun = Integer.parseInt(stringExtraRun);

                            textViewExtraRun.setText(String.valueOf(extraRun + editTextRuns1 + 1));

                            reusableFunctionBowler();

                        }
                        wicketServerCall();
                        dismiss();

                    }

                    if (radioButtonNo.isChecked()) {
                        if (radioButtonFirstPlayer.isChecked()) {
                            stringBatsmanName1 = spinnerBatsman.getSelectedItem().toString();
                            stringCatcherName1 = spinnerCatcherName.getSelectedItem().toString();


                            try {

                                if (jsonObjectBatsman.getString("success").equals("1")) {
                                    JSONArray jsonArray = jsonObjectBatsman.getJSONArray("player_list");

                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject object = jsonArray.getJSONObject(i);

                                        stringNewTeamName = object.getString("team_name");

                                        stringSpaceRemove = stringBattingTeam.replaceAll(" ", "");

                                        if (stringSpaceRemove.equals(stringNewTeamName)) {
                                            JSONArray array = object.getJSONArray("players");

                                            for (int j = 0; j < array.length(); j++) {
                                                JSONObject obj = array.getJSONObject(j);

                                                stringNewBatsmanName = obj.getString("fullname");
                                                stringNewBatsmanId = obj.getString("player_id");

                                                if (stringBatsmanName1.equals(stringNewBatsmanName)) {
                                                    stringBatsmanId1 = stringNewBatsmanId;
                                                }

                                            }


                                        }
                                        else {
                                            JSONArray array = object.getJSONArray("players");

                                            for (int j = 0; j < array.length(); j++) {
                                                JSONObject obj = array.getJSONObject(j);

                                                stringCatcherName = obj.getString("fullname");
                                                stringCatcherId = obj.getString("player_id");


                                                if (stringCatcherName1.equals(stringCatcherName)) {
                                                    stringCatcherId1 = stringCatcherId;

                                                    Log.d("catcherName",stringCatcherId1);
                                                }

                                            }
                                        }
                                    }
                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            stringPlayingTeamRun = textViewPlayingTeamRun.getText().toString();

                            playingTeamRum = Integer.parseInt(stringPlayingTeamRun);

                            textViewPlayingTeamRun.setText(String.valueOf(playingTeamRum + editTextRuns1 + 1));

                            stringPlayingTeamWicket = textViewPlayingTeamWicket.getText().toString();

                            playingTeamWicket = Integer.parseInt(stringPlayingTeamWicket);

                            textViewPlayingTeamWicket.setText(String.valueOf(playingTeamWicket + 1));

                            stringPlayingTeamOvers = textViewPlayingTeamOvers.getText().toString();

                            playingTeamOversFloat = Float.parseFloat(stringPlayingTeamOvers);

                            //textViewPlayingTeamOvers.setText(new DecimalFormat("##.#").format(playingTeamOversFloat + 0.1));

                            reusableFunctionPlayer();

                            stringFirstPlayerRun = textViewFirstPlayerRun.getText().toString();

                            firstPlayerRun = Integer.parseInt(stringFirstPlayerRun);

                            //textViewFirstPlayerRun.setText(String.valueOf(firstPlayerRun + 0));
                            stringFirstPlayerBall = textViewFirstPlayerBall.getText().toString();

                            firstPlayerBall = Integer.parseInt(stringFirstPlayerBall);

                            //textViewFirstPlayerBall.setText(String.valueOf(firstPlayerBall + 1));


                            stringBowlerRun = textViewBowlerRun.getText().toString();

                            bowlerRun = Integer.parseInt(stringBowlerRun);

                            textViewBowlerRun.setText(String.valueOf(bowlerRun + editTextRuns1 + 1));

                            stringBowlerWicket = textViewBowlerWicket.getText().toString();

                            bowlerWicket = Integer.parseInt(stringBowlerWicket);

                            //textViewBowlerWicket.setText(String.valueOf(bowlerWicket + 1));

                            stringBowlerBall = textViewBowlerBall.getText().toString();

                            bowlerOversFloat = Float.parseFloat(stringBowlerBall);

                            //textViewBowlerBall.setText(new DecimalFormat("##.#").format(bowlerOversFloat + 0.1));

                            textViewCurrentOver.setText(textViewCurrentOver.getText() + " " + "w+nb");

                            stringExtraRun = textViewExtraRun.getText().toString();

                            extraRun = Integer.parseInt(stringExtraRun);

                            textViewExtraRun.setText(String.valueOf(extraRun + editTextRuns1 + 1));

                            reusableFunctionBowler();

                        } else {

                            stringBatsmanName1 = spinnerBatsman.getSelectedItem().toString();
                            stringCatcherName1 = spinnerCatcherName.getSelectedItem().toString();


                            try {

                                if (jsonObjectBatsman.getString("success").equals("1")) {
                                    JSONArray jsonArray = jsonObjectBatsman.getJSONArray("player_list");

                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject object = jsonArray.getJSONObject(i);

                                        stringNewTeamName = object.getString("team_name");

                                        stringSpaceRemove = stringBattingTeam.replaceAll(" ", "");

                                        if (stringSpaceRemove.equals(stringNewTeamName)) {
                                            JSONArray array = object.getJSONArray("players");

                                            for (int j = 0; j < array.length(); j++) {
                                                JSONObject obj = array.getJSONObject(j);

                                                stringNewBatsmanName = obj.getString("fullname");
                                                stringNewBatsmanId = obj.getString("player_id");

                                                if (stringBatsmanName1.equals(stringNewBatsmanName)) {
                                                    stringBatsmanId1 = stringNewBatsmanId;
                                                }

                                            }


                                        }
                                        else {
                                            JSONArray array = object.getJSONArray("players");

                                            for (int j = 0; j < array.length(); j++) {
                                                JSONObject obj = array.getJSONObject(j);

                                                stringCatcherName = obj.getString("fullname");
                                                stringCatcherId = obj.getString("player_id");


                                                if (stringCatcherName1.equals(stringCatcherName)) {
                                                    stringCatcherId1 = stringCatcherId;

                                                    Log.d("catcherName",stringCatcherId1);
                                                }

                                            }
                                        }
                                    }
                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            stringPlayingTeamRun = textViewPlayingTeamRun.getText().toString();

                            playingTeamRum = Integer.parseInt(stringPlayingTeamRun);

                            textViewPlayingTeamRun.setText(String.valueOf(playingTeamRum + editTextRuns1 + 1));

                            stringPlayingTeamWicket = textViewPlayingTeamWicket.getText().toString();

                            playingTeamWicket = Integer.parseInt(stringPlayingTeamWicket);

                            textViewPlayingTeamWicket.setText(String.valueOf(playingTeamWicket + 1));

                            stringPlayingTeamOvers = textViewPlayingTeamOvers.getText().toString();

                            playingTeamOversFloat = Float.parseFloat(stringPlayingTeamOvers);

                            //textViewPlayingTeamOvers.setText(new DecimalFormat("##.#").format(playingTeamOversFloat + 0.1));

                            reusableFunctionPlayer();

                            stringSecondPlayerRun = textViewSecondPlayerRun.getText().toString();

                            secondPlayerRun = Integer.parseInt(stringSecondPlayerRun);

                            //textViewSecondPlayerRun.setText(String.valueOf(secondPlayerRun + 0));


                            stringSecondPlayerBall = textViewSecondPlayerBall.getText().toString();

                            secondPlayerBall = Integer.parseInt(stringSecondPlayerBall);

                            //textViewSecondPlayerBall.setText(String.valueOf(secondPlayerBall + 1));


                            stringBowlerRun = textViewBowlerRun.getText().toString();

                            bowlerRun = Integer.parseInt(stringBowlerRun);

                            textViewBowlerRun.setText(String.valueOf(bowlerRun + editTextRuns1 + 1));

                            stringBowlerWicket = textViewBowlerWicket.getText().toString();

                            bowlerWicket = Integer.parseInt(stringBowlerWicket);

                            //textViewBowlerWicket.setText(String.valueOf(bowlerWicket + 1));

                            stringBowlerBall = textViewBowlerBall.getText().toString();

                            bowlerOversFloat = Float.parseFloat(stringBowlerBall);

                            //textViewBowlerBall.setText(new DecimalFormat("##.#").format(bowlerOversFloat + 0.1));

                            textViewCurrentOver.setText(textViewCurrentOver.getText() + " " + "w+nb");
                            stringExtraRun = textViewExtraRun.getText().toString();

                            extraRun = Integer.parseInt(stringExtraRun);

                            textViewExtraRun.setText(String.valueOf(extraRun + editTextRuns1 + 1));
                            reusableFunctionBowler();

                        }

                        wicketServerCall();
                        dismiss();

                    }


                    if (radioButtonNor.isChecked()) {
                        if (radioButtonFirstPlayer.isChecked()) {
                            stringBatsmanName1 = spinnerBatsman.getSelectedItem().toString();
                            stringCatcherName1 = spinnerCatcherName.getSelectedItem().toString();


                            try {

                                if (jsonObjectBatsman.getString("success").equals("1")) {
                                    JSONArray jsonArray = jsonObjectBatsman.getJSONArray("player_list");

                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject object = jsonArray.getJSONObject(i);

                                        stringNewTeamName = object.getString("team_name");

                                        stringSpaceRemove = stringBattingTeam.replaceAll(" ", "");

                                        if (stringSpaceRemove.equals(stringNewTeamName)) {
                                            JSONArray array = object.getJSONArray("players");

                                            for (int j = 0; j < array.length(); j++) {
                                                JSONObject obj = array.getJSONObject(j);

                                                stringNewBatsmanName = obj.getString("fullname");
                                                stringNewBatsmanId = obj.getString("player_id");

                                                if (stringBatsmanName1.equals(stringNewBatsmanName)) {
                                                    stringBatsmanId1 = stringNewBatsmanId;
                                                }

                                            }


                                        }
                                        else {
                                            JSONArray array = object.getJSONArray("players");

                                            for (int j = 0; j < array.length(); j++) {
                                                JSONObject obj = array.getJSONObject(j);

                                                stringCatcherName = obj.getString("fullname");
                                                stringCatcherId = obj.getString("player_id");


                                                if (stringCatcherName1.equals(stringCatcherName)) {
                                                    stringCatcherId1 = stringCatcherId;

                                                    Log.d("catcherName",stringCatcherId1);
                                                }

                                            }
                                        }
                                    }
                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            stringPlayingTeamRun = textViewPlayingTeamRun.getText().toString();

                            playingTeamRum = Integer.parseInt(stringPlayingTeamRun);

                            textViewPlayingTeamRun.setText(String.valueOf(playingTeamRum + editTextRuns1));

                            stringPlayingTeamWicket = textViewPlayingTeamWicket.getText().toString();

                            playingTeamWicket = Integer.parseInt(stringPlayingTeamWicket);

                            textViewPlayingTeamWicket.setText(String.valueOf(playingTeamWicket + 1));

                            stringPlayingTeamOvers = textViewPlayingTeamOvers.getText().toString();

                            playingTeamOversFloat = Float.parseFloat(stringPlayingTeamOvers);

                            textViewPlayingTeamOvers.setText(new DecimalFormat("##.#").format(playingTeamOversFloat + 0.1));

                            reusableFunctionPlayer();

                            stringFirstPlayerRun = textViewFirstPlayerRun.getText().toString();

                            firstPlayerRun = Integer.parseInt(stringFirstPlayerRun);

                            //textViewFirstPlayerRun.setText(String.valueOf(firstPlayerRun + 0));
                            stringFirstPlayerBall = textViewFirstPlayerBall.getText().toString();

                            firstPlayerBall = Integer.parseInt(stringFirstPlayerBall);

                            //textViewFirstPlayerBall.setText(String.valueOf(firstPlayerBall + 1));


                            stringBowlerRun = textViewBowlerRun.getText().toString();

                            bowlerRun = Integer.parseInt(stringBowlerRun);

                            textViewBowlerRun.setText(String.valueOf(bowlerRun + editTextRuns1));

                            stringBowlerWicket = textViewBowlerWicket.getText().toString();

                            bowlerWicket = Integer.parseInt(stringBowlerWicket);

                            //textViewBowlerWicket.setText(String.valueOf(bowlerWicket + 1));

                            stringBowlerBall = textViewBowlerBall.getText().toString();

                            bowlerOversFloat = Float.parseFloat(stringBowlerBall);

                            textViewBowlerBall.setText(new DecimalFormat("##.#").format(bowlerOversFloat + 0.1));

                            textViewCurrentOver.setText(textViewCurrentOver.getText() + " " + "w");
                            reusableFunctionBowler();



                        } else {

                            stringBatsmanName1 = spinnerBatsman.getSelectedItem().toString();
                            stringCatcherName1 = spinnerCatcherName.getSelectedItem().toString();


                            try {

                                if (jsonObjectBatsman.getString("success").equals("1")) {
                                    JSONArray jsonArray = jsonObjectBatsman.getJSONArray("player_list");

                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject object = jsonArray.getJSONObject(i);

                                        stringNewTeamName = object.getString("team_name");

                                        stringSpaceRemove = stringBattingTeam.replaceAll(" ", "");

                                        if (stringSpaceRemove.equals(stringNewTeamName)) {
                                            JSONArray array = object.getJSONArray("players");

                                            for (int j = 0; j < array.length(); j++) {
                                                JSONObject obj = array.getJSONObject(j);

                                                stringNewBatsmanName = obj.getString("fullname");
                                                stringNewBatsmanId = obj.getString("player_id");

                                                if (stringBatsmanName1.equals(stringNewBatsmanName)) {
                                                    stringBatsmanId1 = stringNewBatsmanId;
                                                }

                                            }


                                        }
                                        else {
                                            JSONArray array = object.getJSONArray("players");

                                            for (int j = 0; j < array.length(); j++) {
                                                JSONObject obj = array.getJSONObject(j);

                                                stringCatcherName = obj.getString("fullname");
                                                stringCatcherId = obj.getString("player_id");


                                                if (stringCatcherName1.equals(stringCatcherName)) {
                                                    stringCatcherId1 = stringCatcherId;

                                                    Log.d("catcherName",stringCatcherId1);
                                                }

                                            }
                                        }
                                    }
                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            stringPlayingTeamRun = textViewPlayingTeamRun.getText().toString();

                            playingTeamRum = Integer.parseInt(stringPlayingTeamRun);

                            textViewPlayingTeamRun.setText(String.valueOf(playingTeamRum + editTextRuns1));

                            stringPlayingTeamWicket = textViewPlayingTeamWicket.getText().toString();

                            playingTeamWicket = Integer.parseInt(stringPlayingTeamWicket);

                            textViewPlayingTeamWicket.setText(String.valueOf(playingTeamWicket + 1));

                            stringPlayingTeamOvers = textViewPlayingTeamOvers.getText().toString();

                            playingTeamOversFloat = Float.parseFloat(stringPlayingTeamOvers);

                            textViewPlayingTeamOvers.setText(new DecimalFormat("##.#").format(playingTeamOversFloat + 0.1));

                            reusableFunctionPlayer();

                            stringSecondPlayerRun = textViewSecondPlayerRun.getText().toString();

                            secondPlayerRun = Integer.parseInt(stringSecondPlayerRun);

                            //textViewSecondPlayerRun.setText(String.valueOf(secondPlayerRun + 0));


                            stringSecondPlayerBall = textViewSecondPlayerBall.getText().toString();

                            secondPlayerBall = Integer.parseInt(stringSecondPlayerBall);

                            //textViewSecondPlayerBall.setText(String.valueOf(secondPlayerBall + 1));


                            stringBowlerRun = textViewBowlerRun.getText().toString();

                            bowlerRun = Integer.parseInt(stringBowlerRun);

                            textViewBowlerRun.setText(String.valueOf(bowlerRun + editTextRuns1));

                            stringBowlerWicket = textViewBowlerWicket.getText().toString();

                            bowlerWicket = Integer.parseInt(stringBowlerWicket);

                            //textViewBowlerWicket.setText(String.valueOf(bowlerWicket + 1));

                            stringBowlerBall = textViewBowlerBall.getText().toString();

                            bowlerOversFloat = Float.parseFloat(stringBowlerBall);

                            textViewBowlerBall.setText(new DecimalFormat("##.#").format(bowlerOversFloat + 0.1));

                            textViewCurrentOver.setText(textViewCurrentOver.getText() + " " + "w");
                            reusableFunctionBowler();

                        }
                        wicketServerCall();
                        dismiss();
                    }

                    if (radioButtonByes.isChecked()) {
                        if (radioButtonFirstPlayer.isChecked()) {
                            stringBatsmanName1 = spinnerBatsman.getSelectedItem().toString();
                            stringCatcherName1 = spinnerCatcherName.getSelectedItem().toString();


                            try {

                                if (jsonObjectBatsman.getString("success").equals("1")) {
                                    JSONArray jsonArray = jsonObjectBatsman.getJSONArray("player_list");

                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject object = jsonArray.getJSONObject(i);

                                        stringNewTeamName = object.getString("team_name");

                                        stringSpaceRemove = stringBattingTeam.replaceAll(" ", "");

                                        if (stringSpaceRemove.equals(stringNewTeamName)) {
                                            JSONArray array = object.getJSONArray("players");

                                            for (int j = 0; j < array.length(); j++) {
                                                JSONObject obj = array.getJSONObject(j);

                                                stringNewBatsmanName = obj.getString("fullname");
                                                stringNewBatsmanId = obj.getString("player_id");

                                                if (stringBatsmanName1.equals(stringNewBatsmanName)) {
                                                    stringBatsmanId1 = stringNewBatsmanId;
                                                }

                                            }


                                        }
                                        else {
                                            JSONArray array = object.getJSONArray("players");

                                            for (int j = 0; j < array.length(); j++) {
                                                JSONObject obj = array.getJSONObject(j);

                                                stringCatcherName = obj.getString("fullname");
                                                stringCatcherId = obj.getString("player_id");


                                                if (stringCatcherName1.equals(stringCatcherName)) {
                                                    stringCatcherId1 = stringCatcherId;

                                                    Log.d("catcherName",stringCatcherId1);
                                                }

                                            }
                                        }
                                    }
                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            stringPlayingTeamRun = textViewPlayingTeamRun.getText().toString();

                            playingTeamRum = Integer.parseInt(stringPlayingTeamRun);

                            textViewPlayingTeamRun.setText(String.valueOf(playingTeamRum + editTextRuns1));

                            stringPlayingTeamWicket = textViewPlayingTeamWicket.getText().toString();

                            playingTeamWicket = Integer.parseInt(stringPlayingTeamWicket);

                            textViewPlayingTeamWicket.setText(String.valueOf(playingTeamWicket + 1));

                            stringPlayingTeamOvers = textViewPlayingTeamOvers.getText().toString();

                            playingTeamOversFloat = Float.parseFloat(stringPlayingTeamOvers);

                            textViewPlayingTeamOvers.setText(new DecimalFormat("##.#").format(playingTeamOversFloat + 0.1));

                            reusableFunctionPlayer();

                            stringFirstPlayerRun = textViewFirstPlayerRun.getText().toString();

                            firstPlayerRun = Integer.parseInt(stringFirstPlayerRun);

                            //textViewFirstPlayerRun.setText(String.valueOf(firstPlayerRun + 0));
                            stringFirstPlayerBall = textViewFirstPlayerBall.getText().toString();

                            firstPlayerBall = Integer.parseInt(stringFirstPlayerBall);

                            //textViewFirstPlayerBall.setText(String.valueOf(firstPlayerBall + 1));


                            stringBowlerRun = textViewBowlerRun.getText().toString();

                            bowlerRun = Integer.parseInt(stringBowlerRun);

                            //textViewBowlerRun.setText(String.valueOf(bowlerRun + editTextRuns1));

                            stringBowlerWicket = textViewBowlerWicket.getText().toString();

                            bowlerWicket = Integer.parseInt(stringBowlerWicket);

                            //textViewBowlerWicket.setText(String.valueOf(bowlerWicket + 1));

                            stringBowlerBall = textViewBowlerBall.getText().toString();

                            bowlerOversFloat = Float.parseFloat(stringBowlerBall);

                            textViewBowlerBall.setText(new DecimalFormat("##.#").format(bowlerOversFloat + 0.1));

                            textViewCurrentOver.setText(textViewCurrentOver.getText() + " " + "w");

                            stringExtraRun = textViewExtraRun.getText().toString();

                            extraRun = Integer.parseInt(stringExtraRun);

                            textViewExtraRun.setText(String.valueOf(extraRun + editTextRuns1));
                            reusableFunctionBowler();



                        } else {

                            stringBatsmanName1 = spinnerBatsman.getSelectedItem().toString();
                            stringCatcherName1 = spinnerCatcherName.getSelectedItem().toString();


                            try {

                                if (jsonObjectBatsman.getString("success").equals("1")) {
                                    JSONArray jsonArray = jsonObjectBatsman.getJSONArray("player_list");

                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject object = jsonArray.getJSONObject(i);

                                        stringNewTeamName = object.getString("team_name");

                                        stringSpaceRemove = stringBattingTeam.replaceAll(" ", "");

                                        if (stringSpaceRemove.equals(stringNewTeamName)) {
                                            JSONArray array = object.getJSONArray("players");

                                            for (int j = 0; j < array.length(); j++) {
                                                JSONObject obj = array.getJSONObject(j);

                                                stringNewBatsmanName = obj.getString("fullname");
                                                stringNewBatsmanId = obj.getString("player_id");

                                                if (stringBatsmanName1.equals(stringNewBatsmanName)) {
                                                    stringBatsmanId1 = stringNewBatsmanId;
                                                }

                                            }


                                        }
                                        else {
                                            JSONArray array = object.getJSONArray("players");

                                            for (int j = 0; j < array.length(); j++) {
                                                JSONObject obj = array.getJSONObject(j);

                                                stringCatcherName = obj.getString("fullname");
                                                stringCatcherId = obj.getString("player_id");


                                                if (stringCatcherName1.equals(stringCatcherName)) {
                                                    stringCatcherId1 = stringCatcherId;

                                                    Log.d("catcherName",stringCatcherId1);
                                                }

                                            }
                                        }
                                    }
                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            stringPlayingTeamRun = textViewPlayingTeamRun.getText().toString();

                            playingTeamRum = Integer.parseInt(stringPlayingTeamRun);

                            textViewPlayingTeamRun.setText(String.valueOf(playingTeamRum + editTextRuns1));

                            stringPlayingTeamWicket = textViewPlayingTeamWicket.getText().toString();

                            playingTeamWicket = Integer.parseInt(stringPlayingTeamWicket);

                            textViewPlayingTeamWicket.setText(String.valueOf(playingTeamWicket + 1));

                            stringPlayingTeamOvers = textViewPlayingTeamOvers.getText().toString();

                            playingTeamOversFloat = Float.parseFloat(stringPlayingTeamOvers);

                            textViewPlayingTeamOvers.setText(new DecimalFormat("##.#").format(playingTeamOversFloat + 0.1));

                            reusableFunctionPlayer();

                            stringSecondPlayerRun = textViewSecondPlayerRun.getText().toString();

                            secondPlayerRun = Integer.parseInt(stringSecondPlayerRun);

                            //textViewSecondPlayerRun.setText(String.valueOf(secondPlayerRun + 0));


                            stringSecondPlayerBall = textViewSecondPlayerBall.getText().toString();

                            secondPlayerBall = Integer.parseInt(stringSecondPlayerBall);

                            //textViewSecondPlayerBall.setText(String.valueOf(secondPlayerBall + 1));


                            stringBowlerRun = textViewBowlerRun.getText().toString();

                            bowlerRun = Integer.parseInt(stringBowlerRun);

                            //textViewBowlerRun.setText(String.valueOf(bowlerRun + editTextRuns1));

                            stringBowlerWicket = textViewBowlerWicket.getText().toString();

                            bowlerWicket = Integer.parseInt(stringBowlerWicket);

                            //textViewBowlerWicket.setText(String.valueOf(bowlerWicket + 1));

                            stringBowlerBall = textViewBowlerBall.getText().toString();

                            bowlerOversFloat = Float.parseFloat(stringBowlerBall);

                            textViewBowlerBall.setText(new DecimalFormat("##.#").format(bowlerOversFloat + 0.1));

                            textViewCurrentOver.setText(textViewCurrentOver.getText() + " " + "w");
                            stringExtraRun = textViewExtraRun.getText().toString();

                            extraRun = Integer.parseInt(stringExtraRun);

                            textViewExtraRun.setText(String.valueOf(extraRun + editTextRuns1));
                            reusableFunctionBowler();

                        }
                        wicketServerCall();
                        dismiss();
                    }

                    if (radioButtonLegByes.isChecked()) {
                        if (radioButtonFirstPlayer.isChecked()) {
                            stringBatsmanName1 = spinnerBatsman.getSelectedItem().toString();
                            stringCatcherName1 = spinnerCatcherName.getSelectedItem().toString();


                            try {

                                if (jsonObjectBatsman.getString("success").equals("1")) {
                                    JSONArray jsonArray = jsonObjectBatsman.getJSONArray("player_list");

                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject object = jsonArray.getJSONObject(i);

                                        stringNewTeamName = object.getString("team_name");

                                        stringSpaceRemove = stringBattingTeam.replaceAll(" ", "");

                                        if (stringSpaceRemove.equals(stringNewTeamName)) {
                                            JSONArray array = object.getJSONArray("players");

                                            for (int j = 0; j < array.length(); j++) {
                                                JSONObject obj = array.getJSONObject(j);

                                                stringNewBatsmanName = obj.getString("fullname");
                                                stringNewBatsmanId = obj.getString("player_id");

                                                if (stringBatsmanName1.equals(stringNewBatsmanName)) {
                                                    stringBatsmanId1 = stringNewBatsmanId;
                                                }

                                            }


                                        }
                                        else {
                                            JSONArray array = object.getJSONArray("players");

                                            for (int j = 0; j < array.length(); j++) {
                                                JSONObject obj = array.getJSONObject(j);

                                                stringCatcherName = obj.getString("fullname");
                                                stringCatcherId = obj.getString("player_id");


                                                if (stringCatcherName1.equals(stringCatcherName)) {
                                                    stringCatcherId1 = stringCatcherId;

                                                    Log.d("catcherName",stringCatcherId1);
                                                }

                                            }
                                        }
                                    }
                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            stringPlayingTeamRun = textViewPlayingTeamRun.getText().toString();

                            playingTeamRum = Integer.parseInt(stringPlayingTeamRun);

                            textViewPlayingTeamRun.setText(String.valueOf(playingTeamRum + editTextRuns1));

                            stringPlayingTeamWicket = textViewPlayingTeamWicket.getText().toString();

                            playingTeamWicket = Integer.parseInt(stringPlayingTeamWicket);

                            textViewPlayingTeamWicket.setText(String.valueOf(playingTeamWicket + 1));

                            stringPlayingTeamOvers = textViewPlayingTeamOvers.getText().toString();

                            playingTeamOversFloat = Float.parseFloat(stringPlayingTeamOvers);

                            textViewPlayingTeamOvers.setText(new DecimalFormat("##.#").format(playingTeamOversFloat + 0.1));

                            reusableFunctionPlayer();

                            stringFirstPlayerRun = textViewFirstPlayerRun.getText().toString();

                            firstPlayerRun = Integer.parseInt(stringFirstPlayerRun);

                            //textViewFirstPlayerRun.setText(String.valueOf(firstPlayerRun + 0));
                            stringFirstPlayerBall = textViewFirstPlayerBall.getText().toString();

                            firstPlayerBall = Integer.parseInt(stringFirstPlayerBall);

                            //textViewFirstPlayerBall.setText(String.valueOf(firstPlayerBall + 1));


                            stringBowlerRun = textViewBowlerRun.getText().toString();

                            bowlerRun = Integer.parseInt(stringBowlerRun);

                            textViewBowlerRun.setText(String.valueOf(bowlerRun + editTextRuns1));

                            stringBowlerWicket = textViewBowlerWicket.getText().toString();

                            bowlerWicket = Integer.parseInt(stringBowlerWicket);

                            //textViewBowlerWicket.setText(String.valueOf(bowlerWicket + 1));

                            stringBowlerBall = textViewBowlerBall.getText().toString();

                            bowlerOversFloat = Float.parseFloat(stringBowlerBall);

                            textViewBowlerBall.setText(new DecimalFormat("##.#").format(bowlerOversFloat + 0.1));

                            textViewCurrentOver.setText(textViewCurrentOver.getText() + " " + "w");

                            stringExtraRun = textViewExtraRun.getText().toString();

                            extraRun = Integer.parseInt(stringExtraRun);

                            //textViewExtraRun.setText(String.valueOf(extraRun + editTextRuns1));
                            reusableFunctionBowler();


                        } else {

                            stringBatsmanName1 = spinnerBatsman.getSelectedItem().toString();
                            stringCatcherName1 = spinnerCatcherName.getSelectedItem().toString();


                            try {

                                if (jsonObjectBatsman.getString("success").equals("1")) {
                                    JSONArray jsonArray = jsonObjectBatsman.getJSONArray("player_list");

                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject object = jsonArray.getJSONObject(i);

                                        stringNewTeamName = object.getString("team_name");

                                        stringSpaceRemove = stringBattingTeam.replaceAll(" ", "");

                                        if (stringSpaceRemove.equals(stringNewTeamName)) {
                                            JSONArray array = object.getJSONArray("players");

                                            for (int j = 0; j < array.length(); j++) {
                                                JSONObject obj = array.getJSONObject(j);

                                                stringNewBatsmanName = obj.getString("fullname");
                                                stringNewBatsmanId = obj.getString("player_id");

                                                if (stringBatsmanName1.equals(stringNewBatsmanName)) {
                                                    stringBatsmanId1 = stringNewBatsmanId;
                                                }

                                            }


                                        }
                                        else {
                                            JSONArray array = object.getJSONArray("players");

                                            for (int j = 0; j < array.length(); j++) {
                                                JSONObject obj = array.getJSONObject(j);

                                                stringCatcherName = obj.getString("fullname");
                                                stringCatcherId = obj.getString("player_id");


                                                if (stringCatcherName1.equals(stringCatcherName)) {
                                                    stringCatcherId1 = stringCatcherId;

                                                    Log.d("catcherName",stringCatcherId1);
                                                }

                                            }
                                        }
                                    }
                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            stringPlayingTeamRun = textViewPlayingTeamRun.getText().toString();

                            playingTeamRum = Integer.parseInt(stringPlayingTeamRun);

                            textViewPlayingTeamRun.setText(String.valueOf(playingTeamRum + editTextRuns1));

                            stringPlayingTeamWicket = textViewPlayingTeamWicket.getText().toString();

                            playingTeamWicket = Integer.parseInt(stringPlayingTeamWicket);

                            textViewPlayingTeamWicket.setText(String.valueOf(playingTeamWicket + 1));

                            stringPlayingTeamOvers = textViewPlayingTeamOvers.getText().toString();

                            playingTeamOversFloat = Float.parseFloat(stringPlayingTeamOvers);

                            textViewPlayingTeamOvers.setText(new DecimalFormat("##.#").format(playingTeamOversFloat + 0.1));

                            reusableFunctionPlayer();

                            stringSecondPlayerRun = textViewSecondPlayerRun.getText().toString();

                            secondPlayerRun = Integer.parseInt(stringSecondPlayerRun);

                            //textViewSecondPlayerRun.setText(String.valueOf(secondPlayerRun + 0));


                            stringSecondPlayerBall = textViewSecondPlayerBall.getText().toString();

                            secondPlayerBall = Integer.parseInt(stringSecondPlayerBall);

                            //textViewSecondPlayerBall.setText(String.valueOf(secondPlayerBall + 1));


                            stringBowlerRun = textViewBowlerRun.getText().toString();

                            bowlerRun = Integer.parseInt(stringBowlerRun);

                            textViewBowlerRun.setText(String.valueOf(bowlerRun + editTextRuns1));

                            stringBowlerWicket = textViewBowlerWicket.getText().toString();

                            bowlerWicket = Integer.parseInt(stringBowlerWicket);

                            //textViewBowlerWicket.setText(String.valueOf(bowlerWicket + 1));

                            stringBowlerBall = textViewBowlerBall.getText().toString();

                            bowlerOversFloat = Float.parseFloat(stringBowlerBall);

                            textViewBowlerBall.setText(new DecimalFormat("##.#").format(bowlerOversFloat + 0.1));

                            textViewCurrentOver.setText(textViewCurrentOver.getText() + " " + "w");
                            stringExtraRun = textViewExtraRun.getText().toString();

                            extraRun = Integer.parseInt(stringExtraRun);

                            //textViewExtraRun.setText(String.valueOf(extraRun + editTextRuns1));
                            reusableFunctionBowler();

                        }
                        wicketServerCall();
                        dismiss();
                    }

                    if (radioButtonNone.isChecked()) {
                        if (radioButtonFirstPlayer.isChecked()) {
                            stringBatsmanName1 = spinnerBatsman.getSelectedItem().toString();
                            stringCatcherName1 = spinnerCatcherName.getSelectedItem().toString();


                            try {

                                if (jsonObjectBatsman.getString("success").equals("1")) {
                                    JSONArray jsonArray = jsonObjectBatsman.getJSONArray("player_list");

                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject object = jsonArray.getJSONObject(i);

                                        stringNewTeamName = object.getString("team_name");

                                        stringSpaceRemove = stringBattingTeam.replaceAll(" ", "");

                                        if (stringSpaceRemove.equals(stringNewTeamName)) {
                                            JSONArray array = object.getJSONArray("players");

                                            for (int j = 0; j < array.length(); j++) {
                                                JSONObject obj = array.getJSONObject(j);

                                                stringNewBatsmanName = obj.getString("fullname");
                                                stringNewBatsmanId = obj.getString("player_id");

                                                if (stringBatsmanName1.equals(stringNewBatsmanName)) {
                                                    stringBatsmanId1 = stringNewBatsmanId;
                                                }

                                            }


                                        }
                                        else {
                                            JSONArray array = object.getJSONArray("players");

                                            for (int j = 0; j < array.length(); j++) {
                                                JSONObject obj = array.getJSONObject(j);

                                                stringCatcherName = obj.getString("fullname");
                                                stringCatcherId = obj.getString("player_id");


                                                if (stringCatcherName1.equals(stringCatcherName)) {
                                                    stringCatcherId1 = stringCatcherId;

                                                    Log.d("catcherName",stringCatcherId1);
                                                }

                                            }
                                        }
                                    }
                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            stringPlayingTeamRun = textViewPlayingTeamRun.getText().toString();

                            playingTeamRum = Integer.parseInt(stringPlayingTeamRun);

                            textViewPlayingTeamRun.setText(String.valueOf(playingTeamRum + editTextRuns1));

                            stringPlayingTeamWicket = textViewPlayingTeamWicket.getText().toString();

                            playingTeamWicket = Integer.parseInt(stringPlayingTeamWicket);

                            textViewPlayingTeamWicket.setText(String.valueOf(playingTeamWicket + 1));

                            stringPlayingTeamOvers = textViewPlayingTeamOvers.getText().toString();

                            playingTeamOversFloat = Float.parseFloat(stringPlayingTeamOvers);

                            textViewPlayingTeamOvers.setText(new DecimalFormat("##.#").format(playingTeamOversFloat + 0.1));

                            reusableFunctionPlayer();

                            stringFirstPlayerRun = textViewFirstPlayerRun.getText().toString();

                            firstPlayerRun = Integer.parseInt(stringFirstPlayerRun);

                            //textViewFirstPlayerRun.setText(String.valueOf(firstPlayerRun + 0));
                            stringFirstPlayerBall = textViewFirstPlayerBall.getText().toString();

                            firstPlayerBall = Integer.parseInt(stringFirstPlayerBall);

                            //textViewFirstPlayerBall.setText(String.valueOf(firstPlayerBall + 1));


                            stringBowlerRun = textViewBowlerRun.getText().toString();

                            bowlerRun = Integer.parseInt(stringBowlerRun);

                            textViewBowlerRun.setText(String.valueOf(bowlerRun + editTextRuns1));

                            stringBowlerWicket = textViewBowlerWicket.getText().toString();

                            bowlerWicket = Integer.parseInt(stringBowlerWicket);

                            //textViewBowlerWicket.setText(String.valueOf(bowlerWicket + 1));

                            stringBowlerBall = textViewBowlerBall.getText().toString();

                            bowlerOversFloat = Float.parseFloat(stringBowlerBall);

                            textViewBowlerBall.setText(new DecimalFormat("##.#").format(bowlerOversFloat + 0.1));

                            textViewCurrentOver.setText(textViewCurrentOver.getText() + " " + "w");

                            stringExtraRun = textViewExtraRun.getText().toString();

                            extraRun = Integer.parseInt(stringExtraRun);

                            //textViewExtraRun.setText(String.valueOf(extraRun + editTextRuns1));
                            reusableFunctionBowler();


                        } else {

                            stringBatsmanName1 = spinnerBatsman.getSelectedItem().toString();
                            stringCatcherName1 = spinnerCatcherName.getSelectedItem().toString();


                            try {

                                if (jsonObjectBatsman.getString("success").equals("1")) {
                                    JSONArray jsonArray = jsonObjectBatsman.getJSONArray("player_list");

                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject object = jsonArray.getJSONObject(i);

                                        stringNewTeamName = object.getString("team_name");

                                        stringSpaceRemove = stringBattingTeam.replaceAll(" ", "");

                                        if (stringSpaceRemove.equals(stringNewTeamName)) {
                                            JSONArray array = object.getJSONArray("players");

                                            for (int j = 0; j < array.length(); j++) {
                                                JSONObject obj = array.getJSONObject(j);

                                                stringNewBatsmanName = obj.getString("fullname");
                                                stringNewBatsmanId = obj.getString("player_id");

                                                if (stringBatsmanName1.equals(stringNewBatsmanName)) {
                                                    stringBatsmanId1 = stringNewBatsmanId;
                                                }

                                            }


                                        }
                                        else {
                                            JSONArray array = object.getJSONArray("players");

                                            for (int j = 0; j < array.length(); j++) {
                                                JSONObject obj = array.getJSONObject(j);

                                                stringCatcherName = obj.getString("fullname");
                                                stringCatcherId = obj.getString("player_id");


                                                if (stringCatcherName1.equals(stringCatcherName)) {
                                                    stringCatcherId1 = stringCatcherId;

                                                    Log.d("catcherName",stringCatcherId1);
                                                }

                                            }
                                        }
                                    }
                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            stringPlayingTeamRun = textViewPlayingTeamRun.getText().toString();

                            playingTeamRum = Integer.parseInt(stringPlayingTeamRun);

                            textViewPlayingTeamRun.setText(String.valueOf(playingTeamRum + editTextRuns1));

                            stringPlayingTeamWicket = textViewPlayingTeamWicket.getText().toString();

                            playingTeamWicket = Integer.parseInt(stringPlayingTeamWicket);

                            textViewPlayingTeamWicket.setText(String.valueOf(playingTeamWicket + 1));

                            stringPlayingTeamOvers = textViewPlayingTeamOvers.getText().toString();

                            playingTeamOversFloat = Float.parseFloat(stringPlayingTeamOvers);

                            textViewPlayingTeamOvers.setText(new DecimalFormat("##.#").format(playingTeamOversFloat + 0.1));

                            reusableFunctionPlayer();

                            stringSecondPlayerRun = textViewSecondPlayerRun.getText().toString();

                            secondPlayerRun = Integer.parseInt(stringSecondPlayerRun);

                            //textViewSecondPlayerRun.setText(String.valueOf(secondPlayerRun + 0));


                            stringSecondPlayerBall = textViewSecondPlayerBall.getText().toString();

                            secondPlayerBall = Integer.parseInt(stringSecondPlayerBall);

                            //textViewSecondPlayerBall.setText(String.valueOf(secondPlayerBall + 1));


                            stringBowlerRun = textViewBowlerRun.getText().toString();

                            bowlerRun = Integer.parseInt(stringBowlerRun);

                            textViewBowlerRun.setText(String.valueOf(bowlerRun + editTextRuns1));

                            stringBowlerWicket = textViewBowlerWicket.getText().toString();

                            bowlerWicket = Integer.parseInt(stringBowlerWicket);

                            //textViewBowlerWicket.setText(String.valueOf(bowlerWicket + 1));

                            stringBowlerBall = textViewBowlerBall.getText().toString();

                            bowlerOversFloat = Float.parseFloat(stringBowlerBall);

                            textViewBowlerBall.setText(new DecimalFormat("##.#").format(bowlerOversFloat + 0.1));

                            textViewCurrentOver.setText(textViewCurrentOver.getText() + " " + "w");
                            stringExtraRun = textViewExtraRun.getText().toString();

                            extraRun = Integer.parseInt(stringExtraRun);

                            //textViewExtraRun.setText(String.valueOf(extraRun + editTextRuns1));
                            reusableFunctionBowler();


                        }
                        wicketServerCall();
                        dismiss();
                    }

                    if (radioButtonNo.isChecked() && radioButtonByes.isChecked()) {

                        if (radioButtonFirstPlayer.isChecked()) {
                            stringBatsmanName1 = spinnerBatsman.getSelectedItem().toString();
                            stringCatcherName1 = spinnerCatcherName.getSelectedItem().toString();


                            try {

                                if (jsonObjectBatsman.getString("success").equals("1")) {
                                    JSONArray jsonArray = jsonObjectBatsman.getJSONArray("player_list");

                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject object = jsonArray.getJSONObject(i);

                                        stringNewTeamName = object.getString("team_name");

                                        stringSpaceRemove = stringBattingTeam.replaceAll(" ", "");

                                        if (stringSpaceRemove.equals(stringNewTeamName)) {
                                            JSONArray array = object.getJSONArray("players");

                                            for (int j = 0; j < array.length(); j++) {
                                                JSONObject obj = array.getJSONObject(j);

                                                stringNewBatsmanName = obj.getString("fullname");
                                                stringNewBatsmanId = obj.getString("player_id");

                                                if (stringBatsmanName1.equals(stringNewBatsmanName)) {
                                                    stringBatsmanId1 = stringNewBatsmanId;
                                                }

                                            }


                                        }
                                        else {
                                            JSONArray array = object.getJSONArray("players");

                                            for (int j = 0; j < array.length(); j++) {
                                                JSONObject obj = array.getJSONObject(j);

                                                stringCatcherName = obj.getString("fullname");
                                                stringCatcherId = obj.getString("player_id");


                                                if (stringCatcherName1.equals(stringCatcherName)) {
                                                    stringCatcherId1 = stringCatcherId;

                                                    Log.d("catcherName",stringCatcherId1);
                                                }

                                            }
                                        }
                                    }
                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            stringPlayingTeamRun = textViewPlayingTeamRun.getText().toString();

                            playingTeamRum = Integer.parseInt(stringPlayingTeamRun);

                            textViewPlayingTeamRun.setText(String.valueOf(playingTeamRum + editTextRuns1 + 1));

                            stringPlayingTeamWicket = textViewPlayingTeamWicket.getText().toString();

                            playingTeamWicket = Integer.parseInt(stringPlayingTeamWicket);

                            textViewPlayingTeamWicket.setText(String.valueOf(playingTeamWicket + 1));

                            stringPlayingTeamOvers = textViewPlayingTeamOvers.getText().toString();

                            playingTeamOversFloat = Float.parseFloat(stringPlayingTeamOvers);

                            //textViewPlayingTeamOvers.setText(new DecimalFormat("##.#").format(playingTeamOversFloat + 0.1));

                            reusableFunctionPlayer();

                            stringFirstPlayerRun = textViewFirstPlayerRun.getText().toString();

                            firstPlayerRun = Integer.parseInt(stringFirstPlayerRun);

                            //textViewFirstPlayerRun.setText(String.valueOf(firstPlayerRun + 0));
                            stringFirstPlayerBall = textViewFirstPlayerBall.getText().toString();

                            firstPlayerBall = Integer.parseInt(stringFirstPlayerBall);

                            //textViewFirstPlayerBall.setText(String.valueOf(firstPlayerBall + 1));


                            stringBowlerRun = textViewBowlerRun.getText().toString();

                            bowlerRun = Integer.parseInt(stringBowlerRun);

                            textViewBowlerRun.setText(String.valueOf(bowlerRun + editTextRuns1));

                            stringBowlerWicket = textViewBowlerWicket.getText().toString();

                            bowlerWicket = Integer.parseInt(stringBowlerWicket);

                            //textViewBowlerWicket.setText(String.valueOf(bowlerWicket + 1));

                            stringBowlerBall = textViewBowlerBall.getText().toString();

                            bowlerOversFloat = Float.parseFloat(stringBowlerBall);

                            //textViewBowlerBall.setText(new DecimalFormat("##.#").format(bowlerOversFloat + 0.1));

                            textViewCurrentOver.setText(textViewCurrentOver.getText() + " " + "w+nb");
                            stringExtraRun = textViewExtraRun.getText().toString();

                            extraRun = Integer.parseInt(stringExtraRun);

                            textViewExtraRun.setText(String.valueOf(extraRun + editTextRuns1 + 1));
                            reusableFunctionBowler();


                        } else {

                            stringBatsmanName1 = spinnerBatsman.getSelectedItem().toString();
                            stringCatcherName1 = spinnerCatcherName.getSelectedItem().toString();


                            try {

                                if (jsonObjectBatsman.getString("success").equals("1")) {
                                    JSONArray jsonArray = jsonObjectBatsman.getJSONArray("player_list");

                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject object = jsonArray.getJSONObject(i);

                                        stringNewTeamName = object.getString("team_name");

                                        stringSpaceRemove = stringBattingTeam.replaceAll(" ", "");

                                        if (stringSpaceRemove.equals(stringNewTeamName)) {
                                            JSONArray array = object.getJSONArray("players");

                                            for (int j = 0; j < array.length(); j++) {
                                                JSONObject obj = array.getJSONObject(j);

                                                stringNewBatsmanName = obj.getString("fullname");
                                                stringNewBatsmanId = obj.getString("player_id");

                                                if (stringBatsmanName1.equals(stringNewBatsmanName)) {
                                                    stringBatsmanId1 = stringNewBatsmanId;
                                                }

                                            }


                                        }
                                        else {
                                            JSONArray array = object.getJSONArray("players");

                                            for (int j = 0; j < array.length(); j++) {
                                                JSONObject obj = array.getJSONObject(j);

                                                stringCatcherName = obj.getString("fullname");
                                                stringCatcherId = obj.getString("player_id");


                                                if (stringCatcherName1.equals(stringCatcherName)) {
                                                    stringCatcherId1 = stringCatcherId;

                                                    Log.d("catcherName",stringCatcherId1);
                                                }

                                            }
                                        }
                                    }
                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            stringPlayingTeamRun = textViewPlayingTeamRun.getText().toString();

                            playingTeamRum = Integer.parseInt(stringPlayingTeamRun);

                            textViewPlayingTeamRun.setText(String.valueOf(playingTeamRum + editTextRuns1 + 1));

                            stringPlayingTeamWicket = textViewPlayingTeamWicket.getText().toString();

                            playingTeamWicket = Integer.parseInt(stringPlayingTeamWicket);

                            textViewPlayingTeamWicket.setText(String.valueOf(playingTeamWicket + 1));

                            stringPlayingTeamOvers = textViewPlayingTeamOvers.getText().toString();

                            playingTeamOversFloat = Float.parseFloat(stringPlayingTeamOvers);

                            //textViewPlayingTeamOvers.setText(new DecimalFormat("##.#").format(playingTeamOversFloat + 0.1));

                            reusableFunctionPlayer();

                            stringSecondPlayerRun = textViewSecondPlayerRun.getText().toString();

                            secondPlayerRun = Integer.parseInt(stringSecondPlayerRun);

                            //textViewSecondPlayerRun.setText(String.valueOf(secondPlayerRun + 0));


                            stringSecondPlayerBall = textViewSecondPlayerBall.getText().toString();

                            secondPlayerBall = Integer.parseInt(stringSecondPlayerBall);

                            //textViewSecondPlayerBall.setText(String.valueOf(secondPlayerBall + 1));


                            stringBowlerRun = textViewBowlerRun.getText().toString();

                            bowlerRun = Integer.parseInt(stringBowlerRun);

                            textViewBowlerRun.setText(String.valueOf(bowlerRun + editTextRuns1));

                            stringBowlerWicket = textViewBowlerWicket.getText().toString();

                            bowlerWicket = Integer.parseInt(stringBowlerWicket);

                            //textViewBowlerWicket.setText(String.valueOf(bowlerWicket + 1));

                            stringBowlerBall = textViewBowlerBall.getText().toString();

                            bowlerOversFloat = Float.parseFloat(stringBowlerBall);

                            //textViewBowlerBall.setText(new DecimalFormat("##.#").format(bowlerOversFloat + 0.1));

                            textViewCurrentOver.setText(textViewCurrentOver.getText() + " " + "w+nb");
                            stringExtraRun = textViewExtraRun.getText().toString();

                            extraRun = Integer.parseInt(stringExtraRun);

                            textViewExtraRun.setText(String.valueOf(extraRun + editTextRuns1 + 1));
                            reusableFunctionBowler();

                        }
                        wicketServerCall();
                        dismiss();
                    }

                    if (radioButtonNo.isChecked() && radioButtonLegByes.isChecked()) {

                        if (radioButtonFirstPlayer.isChecked()) {
                            stringBatsmanName1 = spinnerBatsman.getSelectedItem().toString();
                            stringCatcherName1 = spinnerCatcherName.getSelectedItem().toString();


                            try {

                                if (jsonObjectBatsman.getString("success").equals("1")) {
                                    JSONArray jsonArray = jsonObjectBatsman.getJSONArray("player_list");

                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject object = jsonArray.getJSONObject(i);

                                        stringNewTeamName = object.getString("team_name");

                                        stringSpaceRemove = stringBattingTeam.replaceAll(" ", "");

                                        if (stringSpaceRemove.equals(stringNewTeamName)) {
                                            JSONArray array = object.getJSONArray("players");

                                            for (int j = 0; j < array.length(); j++) {
                                                JSONObject obj = array.getJSONObject(j);

                                                stringNewBatsmanName = obj.getString("fullname");
                                                stringNewBatsmanId = obj.getString("player_id");

                                                if (stringBatsmanName1.equals(stringNewBatsmanName)) {
                                                    stringBatsmanId1 = stringNewBatsmanId;
                                                }

                                            }


                                        }
                                        else {
                                            JSONArray array = object.getJSONArray("players");

                                            for (int j = 0; j < array.length(); j++) {
                                                JSONObject obj = array.getJSONObject(j);

                                                stringCatcherName = obj.getString("fullname");
                                                stringCatcherId = obj.getString("player_id");


                                                if (stringCatcherName1.equals(stringCatcherName)) {
                                                    stringCatcherId1 = stringCatcherId;

                                                    Log.d("catcherName",stringCatcherId1);
                                                }

                                            }
                                        }
                                    }
                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            stringPlayingTeamRun = textViewPlayingTeamRun.getText().toString();

                            playingTeamRum = Integer.parseInt(stringPlayingTeamRun);

                            textViewPlayingTeamRun.setText(String.valueOf(playingTeamRum + editTextRuns1 + 1));

                            stringPlayingTeamWicket = textViewPlayingTeamWicket.getText().toString();

                            playingTeamWicket = Integer.parseInt(stringPlayingTeamWicket);

                            textViewPlayingTeamWicket.setText(String.valueOf(playingTeamWicket + 1));

                            stringPlayingTeamOvers = textViewPlayingTeamOvers.getText().toString();

                            playingTeamOversFloat = Float.parseFloat(stringPlayingTeamOvers);

                            //textViewPlayingTeamOvers.setText(new DecimalFormat("##.#").format(playingTeamOversFloat + 0.1));

                            reusableFunctionPlayer();

                            stringFirstPlayerRun = textViewFirstPlayerRun.getText().toString();

                            firstPlayerRun = Integer.parseInt(stringFirstPlayerRun);

                            //textViewFirstPlayerRun.setText(String.valueOf(firstPlayerRun + 0));
                            stringFirstPlayerBall = textViewFirstPlayerBall.getText().toString();

                            firstPlayerBall = Integer.parseInt(stringFirstPlayerBall);

                            //textViewFirstPlayerBall.setText(String.valueOf(firstPlayerBall + 1));


                            stringBowlerRun = textViewBowlerRun.getText().toString();

                            bowlerRun = Integer.parseInt(stringBowlerRun);

                            textViewBowlerRun.setText(String.valueOf(bowlerRun + editTextRuns1));

                            stringBowlerWicket = textViewBowlerWicket.getText().toString();

                            bowlerWicket = Integer.parseInt(stringBowlerWicket);

                            //textViewBowlerWicket.setText(String.valueOf(bowlerWicket + 1));

                            stringBowlerBall = textViewBowlerBall.getText().toString();

                            bowlerOversFloat = Float.parseFloat(stringBowlerBall);

                            //textViewBowlerBall.setText(new DecimalFormat("##.#").format(bowlerOversFloat + 0.1));

                            textViewCurrentOver.setText(textViewCurrentOver.getText() + " " + "w+nb");
                            stringExtraRun = textViewExtraRun.getText().toString();

                            extraRun = Integer.parseInt(stringExtraRun);

                            textViewExtraRun.setText(String.valueOf(extraRun + editTextRuns1 + 1));
                            reusableFunctionBowler();

                        } else {

                            stringBatsmanName1 = spinnerBatsman.getSelectedItem().toString();
                            stringCatcherName1 = spinnerCatcherName.getSelectedItem().toString();


                            try {

                                if (jsonObjectBatsman.getString("success").equals("1")) {
                                    JSONArray jsonArray = jsonObjectBatsman.getJSONArray("player_list");

                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject object = jsonArray.getJSONObject(i);

                                        stringNewTeamName = object.getString("team_name");

                                        stringSpaceRemove = stringBattingTeam.replaceAll(" ", "");

                                        if (stringSpaceRemove.equals(stringNewTeamName)) {
                                            JSONArray array = object.getJSONArray("players");

                                            for (int j = 0; j < array.length(); j++) {
                                                JSONObject obj = array.getJSONObject(j);

                                                stringNewBatsmanName = obj.getString("fullname");
                                                stringNewBatsmanId = obj.getString("player_id");

                                                if (stringBatsmanName1.equals(stringNewBatsmanName)) {
                                                    stringBatsmanId1 = stringNewBatsmanId;
                                                }

                                            }


                                        }
                                        else {
                                            JSONArray array = object.getJSONArray("players");

                                            for (int j = 0; j < array.length(); j++) {
                                                JSONObject obj = array.getJSONObject(j);

                                                stringCatcherName = obj.getString("fullname");
                                                stringCatcherId = obj.getString("player_id");


                                                if (stringCatcherName1.equals(stringCatcherName)) {
                                                    stringCatcherId1 = stringCatcherId;

                                                    Log.d("catcherName",stringCatcherId1);
                                                }

                                            }
                                        }
                                    }
                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            stringPlayingTeamRun = textViewPlayingTeamRun.getText().toString();

                            playingTeamRum = Integer.parseInt(stringPlayingTeamRun);

                            textViewPlayingTeamRun.setText(String.valueOf(playingTeamRum + editTextRuns1 + 1));

                            stringPlayingTeamWicket = textViewPlayingTeamWicket.getText().toString();

                            playingTeamWicket = Integer.parseInt(stringPlayingTeamWicket);

                            textViewPlayingTeamWicket.setText(String.valueOf(playingTeamWicket + 1));

                            stringPlayingTeamOvers = textViewPlayingTeamOvers.getText().toString();

                            playingTeamOversFloat = Float.parseFloat(stringPlayingTeamOvers);

                            //textViewPlayingTeamOvers.setText(new DecimalFormat("##.#").format(playingTeamOversFloat + 0.1));

                            reusableFunctionPlayer();

                            stringSecondPlayerRun = textViewSecondPlayerRun.getText().toString();

                            secondPlayerRun = Integer.parseInt(stringSecondPlayerRun);

                            //textViewSecondPlayerRun.setText(String.valueOf(secondPlayerRun + 0));


                            stringSecondPlayerBall = textViewSecondPlayerBall.getText().toString();

                            secondPlayerBall = Integer.parseInt(stringSecondPlayerBall);

                            //textViewSecondPlayerBall.setText(String.valueOf(secondPlayerBall + 1));


                            stringBowlerRun = textViewBowlerRun.getText().toString();

                            bowlerRun = Integer.parseInt(stringBowlerRun);

                            textViewBowlerRun.setText(String.valueOf(bowlerRun + editTextRuns1));

                            stringBowlerWicket = textViewBowlerWicket.getText().toString();

                            bowlerWicket = Integer.parseInt(stringBowlerWicket);

                            //textViewBowlerWicket.setText(String.valueOf(bowlerWicket + 1));

                            stringBowlerBall = textViewBowlerBall.getText().toString();

                            bowlerOversFloat = Float.parseFloat(stringBowlerBall);

                            //textViewBowlerBall.setText(new DecimalFormat("##.#").format(bowlerOversFloat + 0.1));

                            textViewCurrentOver.setText(textViewCurrentOver.getText() + " " + "w+nb");
                            stringExtraRun = textViewExtraRun.getText().toString();

                            extraRun = Integer.parseInt(stringExtraRun);

                            textViewExtraRun.setText(String.valueOf(extraRun + editTextRuns1 + 1));
                            reusableFunctionBowler();


                        }
                        wicketServerCall();
                        dismiss();
                    }

                    if (radioButtonNo.isChecked() && radioButtonNone.isChecked()) {

                        if (radioButtonFirstPlayer.isChecked()) {
                            stringBatsmanName1 = spinnerBatsman.getSelectedItem().toString();
                            stringCatcherName1 = spinnerCatcherName.getSelectedItem().toString();


                            try {

                                if (jsonObjectBatsman.getString("success").equals("1")) {
                                    JSONArray jsonArray = jsonObjectBatsman.getJSONArray("player_list");

                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject object = jsonArray.getJSONObject(i);

                                        stringNewTeamName = object.getString("team_name");

                                        stringSpaceRemove = stringBattingTeam.replaceAll(" ", "");

                                        if (stringSpaceRemove.equals(stringNewTeamName)) {
                                            JSONArray array = object.getJSONArray("players");

                                            for (int j = 0; j < array.length(); j++) {
                                                JSONObject obj = array.getJSONObject(j);

                                                stringNewBatsmanName = obj.getString("fullname");
                                                stringNewBatsmanId = obj.getString("player_id");

                                                if (stringBatsmanName1.equals(stringNewBatsmanName)) {
                                                    stringBatsmanId1 = stringNewBatsmanId;
                                                }

                                            }


                                        }
                                        else {
                                            JSONArray array = object.getJSONArray("players");

                                            for (int j = 0; j < array.length(); j++) {
                                                JSONObject obj = array.getJSONObject(j);

                                                stringCatcherName = obj.getString("fullname");
                                                stringCatcherId = obj.getString("player_id");


                                                if (stringCatcherName1.equals(stringCatcherName)) {
                                                    stringCatcherId1 = stringCatcherId;

                                                    Log.d("catcherName",stringCatcherId1);
                                                }

                                            }
                                        }
                                    }
                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            stringPlayingTeamRun = textViewPlayingTeamRun.getText().toString();

                            playingTeamRum = Integer.parseInt(stringPlayingTeamRun);

                            textViewPlayingTeamRun.setText(String.valueOf(playingTeamRum + editTextRuns1 + 1));

                            stringPlayingTeamWicket = textViewPlayingTeamWicket.getText().toString();

                            playingTeamWicket = Integer.parseInt(stringPlayingTeamWicket);

                            textViewPlayingTeamWicket.setText(String.valueOf(playingTeamWicket + 1));

                            stringPlayingTeamOvers = textViewPlayingTeamOvers.getText().toString();

                            playingTeamOversFloat = Float.parseFloat(stringPlayingTeamOvers);

                            //textViewPlayingTeamOvers.setText(new DecimalFormat("##.#").format(playingTeamOversFloat + 0.1));

                            reusableFunctionPlayer();

                            stringFirstPlayerRun = textViewFirstPlayerRun.getText().toString();

                            firstPlayerRun = Integer.parseInt(stringFirstPlayerRun);

                            //textViewFirstPlayerRun.setText(String.valueOf(firstPlayerRun + 0));
                            stringFirstPlayerBall = textViewFirstPlayerBall.getText().toString();

                            firstPlayerBall = Integer.parseInt(stringFirstPlayerBall);

                            //textViewFirstPlayerBall.setText(String.valueOf(firstPlayerBall + 1));


                            stringBowlerRun = textViewBowlerRun.getText().toString();

                            bowlerRun = Integer.parseInt(stringBowlerRun);

                            textViewBowlerRun.setText(String.valueOf(bowlerRun + editTextRuns1 + 1));

                            stringBowlerWicket = textViewBowlerWicket.getText().toString();

                            bowlerWicket = Integer.parseInt(stringBowlerWicket);

                            //textViewBowlerWicket.setText(String.valueOf(bowlerWicket + 1));

                            stringBowlerBall = textViewBowlerBall.getText().toString();

                            bowlerOversFloat = Float.parseFloat(stringBowlerBall);

                            //textViewBowlerBall.setText(new DecimalFormat("##.#").format(bowlerOversFloat + 0.1));

                            textViewCurrentOver.setText(textViewCurrentOver.getText() + " " + "w+nb");
                            stringExtraRun = textViewExtraRun.getText().toString();

                            extraRun = Integer.parseInt(stringExtraRun);

                            textViewExtraRun.setText(String.valueOf(extraRun + editTextRuns1 + 1));
                            reusableFunctionBowler();


                        } else {

                            stringBatsmanName1 = spinnerBatsman.getSelectedItem().toString();
                            stringCatcherName1 = spinnerCatcherName.getSelectedItem().toString();


                            try {

                                if (jsonObjectBatsman.getString("success").equals("1")) {
                                    JSONArray jsonArray = jsonObjectBatsman.getJSONArray("player_list");

                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject object = jsonArray.getJSONObject(i);

                                        stringNewTeamName = object.getString("team_name");

                                        stringSpaceRemove = stringBattingTeam.replaceAll(" ", "");

                                        if (stringSpaceRemove.equals(stringNewTeamName)) {
                                            JSONArray array = object.getJSONArray("players");

                                            for (int j = 0; j < array.length(); j++) {
                                                JSONObject obj = array.getJSONObject(j);

                                                stringNewBatsmanName = obj.getString("fullname");
                                                stringNewBatsmanId = obj.getString("player_id");

                                                if (stringBatsmanName1.equals(stringNewBatsmanName)) {
                                                    stringBatsmanId1 = stringNewBatsmanId;
                                                }

                                            }


                                        }
                                        else {
                                            JSONArray array = object.getJSONArray("players");

                                            for (int j = 0; j < array.length(); j++) {
                                                JSONObject obj = array.getJSONObject(j);

                                                stringCatcherName = obj.getString("fullname");
                                                stringCatcherId = obj.getString("player_id");


                                                if (stringCatcherName1.equals(stringCatcherName)) {
                                                    stringCatcherId1 = stringCatcherId;

                                                    Log.d("catcherName",stringCatcherId1);
                                                }

                                            }
                                        }
                                    }
                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            stringPlayingTeamRun = textViewPlayingTeamRun.getText().toString();

                            playingTeamRum = Integer.parseInt(stringPlayingTeamRun);

                            textViewPlayingTeamRun.setText(String.valueOf(playingTeamRum + editTextRuns1 + 1));

                            stringPlayingTeamWicket = textViewPlayingTeamWicket.getText().toString();

                            playingTeamWicket = Integer.parseInt(stringPlayingTeamWicket);

                            textViewPlayingTeamWicket.setText(String.valueOf(playingTeamWicket + 1));

                            stringPlayingTeamOvers = textViewPlayingTeamOvers.getText().toString();

                            playingTeamOversFloat = Float.parseFloat(stringPlayingTeamOvers);

                            //textViewPlayingTeamOvers.setText(new DecimalFormat("##.#").format(playingTeamOversFloat + 0.1));

                            reusableFunctionPlayer();

                            stringSecondPlayerRun = textViewSecondPlayerRun.getText().toString();

                            secondPlayerRun = Integer.parseInt(stringSecondPlayerRun);

                            //textViewSecondPlayerRun.setText(String.valueOf(secondPlayerRun + 0));


                            stringSecondPlayerBall = textViewSecondPlayerBall.getText().toString();

                            secondPlayerBall = Integer.parseInt(stringSecondPlayerBall);

                            //textViewSecondPlayerBall.setText(String.valueOf(secondPlayerBall + 1));


                            stringBowlerRun = textViewBowlerRun.getText().toString();

                            bowlerRun = Integer.parseInt(stringBowlerRun);

                            textViewBowlerRun.setText(String.valueOf(bowlerRun + editTextRuns1 + 1));

                            stringBowlerWicket = textViewBowlerWicket.getText().toString();

                            bowlerWicket = Integer.parseInt(stringBowlerWicket);

                            //textViewBowlerWicket.setText(String.valueOf(bowlerWicket + 1));

                            stringBowlerBall = textViewBowlerBall.getText().toString();

                            bowlerOversFloat = Float.parseFloat(stringBowlerBall);

                            //textViewBowlerBall.setText(new DecimalFormat("##.#").format(bowlerOversFloat + 0.1));

                            textViewCurrentOver.setText(textViewCurrentOver.getText() + " " + "w+nb");
                            stringExtraRun = textViewExtraRun.getText().toString();

                            extraRun = Integer.parseInt(stringExtraRun);

                            textViewExtraRun.setText(String.valueOf(extraRun + editTextRuns1 + 1));
                            reusableFunctionBowler();

                        }
                        wicketServerCall();
                        dismiss();
                    }

                    if (radioButtonNor.isChecked() && radioButtonByes.isChecked()) {

                        if (radioButtonFirstPlayer.isChecked()) {
                            stringBatsmanName1 = spinnerBatsman.getSelectedItem().toString();
                            stringCatcherName1 = spinnerCatcherName.getSelectedItem().toString();


                            try {

                                if (jsonObjectBatsman.getString("success").equals("1")) {
                                    JSONArray jsonArray = jsonObjectBatsman.getJSONArray("player_list");

                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject object = jsonArray.getJSONObject(i);

                                        stringNewTeamName = object.getString("team_name");

                                        stringSpaceRemove = stringBattingTeam.replaceAll(" ", "");

                                        if (stringSpaceRemove.equals(stringNewTeamName)) {
                                            JSONArray array = object.getJSONArray("players");

                                            for (int j = 0; j < array.length(); j++) {
                                                JSONObject obj = array.getJSONObject(j);

                                                stringNewBatsmanName = obj.getString("fullname");
                                                stringNewBatsmanId = obj.getString("player_id");

                                                if (stringBatsmanName1.equals(stringNewBatsmanName)) {
                                                    stringBatsmanId1 = stringNewBatsmanId;
                                                }

                                            }


                                        }
                                        else {
                                            JSONArray array = object.getJSONArray("players");

                                            for (int j = 0; j < array.length(); j++) {
                                                JSONObject obj = array.getJSONObject(j);

                                                stringCatcherName = obj.getString("fullname");
                                                stringCatcherId = obj.getString("player_id");


                                                if (stringCatcherName1.equals(stringCatcherName)) {
                                                    stringCatcherId1 = stringCatcherId;

                                                    Log.d("catcherName",stringCatcherId1);
                                                }

                                            }
                                        }
                                    }
                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            stringPlayingTeamRun = textViewPlayingTeamRun.getText().toString();

                            playingTeamRum = Integer.parseInt(stringPlayingTeamRun);

                            textViewPlayingTeamRun.setText(String.valueOf(playingTeamRum + editTextRuns1));

                            stringPlayingTeamWicket = textViewPlayingTeamWicket.getText().toString();

                            playingTeamWicket = Integer.parseInt(stringPlayingTeamWicket);

                            textViewPlayingTeamWicket.setText(String.valueOf(playingTeamWicket + 1));

                            stringPlayingTeamOvers = textViewPlayingTeamOvers.getText().toString();

                            playingTeamOversFloat = Float.parseFloat(stringPlayingTeamOvers);

                            textViewPlayingTeamOvers.setText(new DecimalFormat("##.#").format(playingTeamOversFloat + 0.1));

                            reusableFunctionPlayer();

                            stringFirstPlayerRun = textViewFirstPlayerRun.getText().toString();

                            firstPlayerRun = Integer.parseInt(stringFirstPlayerRun);

                            //textViewFirstPlayerRun.setText(String.valueOf(firstPlayerRun + 0));
                            stringFirstPlayerBall = textViewFirstPlayerBall.getText().toString();

                            firstPlayerBall = Integer.parseInt(stringFirstPlayerBall);

                            //textViewFirstPlayerBall.setText(String.valueOf(firstPlayerBall + 1));


                            stringBowlerRun = textViewBowlerRun.getText().toString();

                            bowlerRun = Integer.parseInt(stringBowlerRun);

                            //textViewBowlerRun.setText(String.valueOf(bowlerRun + editTextRuns1 + 1));

                            stringBowlerWicket = textViewBowlerWicket.getText().toString();

                            bowlerWicket = Integer.parseInt(stringBowlerWicket);

                            //textViewBowlerWicket.setText(String.valueOf(bowlerWicket + 1));

                            stringBowlerBall = textViewBowlerBall.getText().toString();

                            bowlerOversFloat = Float.parseFloat(stringBowlerBall);

                            textViewBowlerBall.setText(new DecimalFormat("##.#").format(bowlerOversFloat + 0.1));

                            textViewCurrentOver.setText(textViewCurrentOver.getText() + " " + "w");
                            stringExtraRun = textViewExtraRun.getText().toString();

                            extraRun = Integer.parseInt(stringExtraRun);

                            textViewExtraRun.setText(String.valueOf(extraRun + editTextRuns1));
                            reusableFunctionBowler();

                        } else {

                            stringBatsmanName1 = spinnerBatsman.getSelectedItem().toString();
                            stringCatcherName1 = spinnerCatcherName.getSelectedItem().toString();


                            try {

                                if (jsonObjectBatsman.getString("success").equals("1")) {
                                    JSONArray jsonArray = jsonObjectBatsman.getJSONArray("player_list");

                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject object = jsonArray.getJSONObject(i);

                                        stringNewTeamName = object.getString("team_name");

                                        stringSpaceRemove = stringBattingTeam.replaceAll(" ", "");

                                        if (stringSpaceRemove.equals(stringNewTeamName)) {
                                            JSONArray array = object.getJSONArray("players");

                                            for (int j = 0; j < array.length(); j++) {
                                                JSONObject obj = array.getJSONObject(j);

                                                stringNewBatsmanName = obj.getString("fullname");
                                                stringNewBatsmanId = obj.getString("player_id");

                                                if (stringBatsmanName1.equals(stringNewBatsmanName)) {
                                                    stringBatsmanId1 = stringNewBatsmanId;
                                                }

                                            }


                                        }
                                        else {
                                            JSONArray array = object.getJSONArray("players");

                                            for (int j = 0; j < array.length(); j++) {
                                                JSONObject obj = array.getJSONObject(j);

                                                stringCatcherName = obj.getString("fullname");
                                                stringCatcherId = obj.getString("player_id");


                                                if (stringCatcherName1.equals(stringCatcherName)) {
                                                    stringCatcherId1 = stringCatcherId;

                                                    Log.d("catcherName",stringCatcherId1);
                                                }

                                            }
                                        }
                                    }
                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            stringPlayingTeamRun = textViewPlayingTeamRun.getText().toString();

                            playingTeamRum = Integer.parseInt(stringPlayingTeamRun);

                            textViewPlayingTeamRun.setText(String.valueOf(playingTeamRum + editTextRuns1));

                            stringPlayingTeamWicket = textViewPlayingTeamWicket.getText().toString();

                            playingTeamWicket = Integer.parseInt(stringPlayingTeamWicket);

                            textViewPlayingTeamWicket.setText(String.valueOf(playingTeamWicket + 1));

                            stringPlayingTeamOvers = textViewPlayingTeamOvers.getText().toString();

                            playingTeamOversFloat = Float.parseFloat(stringPlayingTeamOvers);

                            textViewPlayingTeamOvers.setText(new DecimalFormat("##.#").format(playingTeamOversFloat + 0.1));

                            reusableFunctionPlayer();

                            stringSecondPlayerRun = textViewSecondPlayerRun.getText().toString();

                            secondPlayerRun = Integer.parseInt(stringSecondPlayerRun);

                            //textViewSecondPlayerRun.setText(String.valueOf(secondPlayerRun + 0));


                            stringSecondPlayerBall = textViewSecondPlayerBall.getText().toString();

                            secondPlayerBall = Integer.parseInt(stringSecondPlayerBall);

                            //textViewSecondPlayerBall.setText(String.valueOf(secondPlayerBall + 1));


                            stringBowlerRun = textViewBowlerRun.getText().toString();

                            bowlerRun = Integer.parseInt(stringBowlerRun);

                            //textViewBowlerRun.setText(String.valueOf(bowlerRun + editTextRuns1 + 1));

                            stringBowlerWicket = textViewBowlerWicket.getText().toString();

                            bowlerWicket = Integer.parseInt(stringBowlerWicket);

                            //textViewBowlerWicket.setText(String.valueOf(bowlerWicket + 1));

                            stringBowlerBall = textViewBowlerBall.getText().toString();

                            bowlerOversFloat = Float.parseFloat(stringBowlerBall);

                            textViewBowlerBall.setText(new DecimalFormat("##.#").format(bowlerOversFloat + 0.1));

                            textViewCurrentOver.setText(textViewCurrentOver.getText() + " " + "w");
                            stringExtraRun = textViewExtraRun.getText().toString();

                            extraRun = Integer.parseInt(stringExtraRun);

                            textViewExtraRun.setText(String.valueOf(extraRun + editTextRuns1));
                            reusableFunctionBowler();

                        }
                        wicketServerCall();
                        dismiss();
                    }

                    if (radioButtonNor.isChecked() && radioButtonLegByes.isChecked()) {

                        if (radioButtonFirstPlayer.isChecked()) {
                            stringBatsmanName1 = spinnerBatsman.getSelectedItem().toString();
                            stringCatcherName1 = spinnerCatcherName.getSelectedItem().toString();


                            try {

                                if (jsonObjectBatsman.getString("success").equals("1")) {
                                    JSONArray jsonArray = jsonObjectBatsman.getJSONArray("player_list");

                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject object = jsonArray.getJSONObject(i);

                                        stringNewTeamName = object.getString("team_name");

                                        stringSpaceRemove = stringBattingTeam.replaceAll(" ", "");

                                        if (stringSpaceRemove.equals(stringNewTeamName)) {
                                            JSONArray array = object.getJSONArray("players");

                                            for (int j = 0; j < array.length(); j++) {
                                                JSONObject obj = array.getJSONObject(j);

                                                stringNewBatsmanName = obj.getString("fullname");
                                                stringNewBatsmanId = obj.getString("player_id");

                                                if (stringBatsmanName1.equals(stringNewBatsmanName)) {
                                                    stringBatsmanId1 = stringNewBatsmanId;
                                                }

                                            }


                                        }
                                        else {
                                            JSONArray array = object.getJSONArray("players");

                                            for (int j = 0; j < array.length(); j++) {
                                                JSONObject obj = array.getJSONObject(j);

                                                stringCatcherName = obj.getString("fullname");
                                                stringCatcherId = obj.getString("player_id");


                                                if (stringCatcherName1.equals(stringCatcherName)) {
                                                    stringCatcherId1 = stringCatcherId;

                                                    Log.d("catcherName",stringCatcherId1);
                                                }

                                            }
                                        }
                                    }
                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            stringPlayingTeamRun = textViewPlayingTeamRun.getText().toString();

                            playingTeamRum = Integer.parseInt(stringPlayingTeamRun);

                            textViewPlayingTeamRun.setText(String.valueOf(playingTeamRum + editTextRuns1));

                            stringPlayingTeamWicket = textViewPlayingTeamWicket.getText().toString();

                            playingTeamWicket = Integer.parseInt(stringPlayingTeamWicket);

                            textViewPlayingTeamWicket.setText(String.valueOf(playingTeamWicket + 1));

                            stringPlayingTeamOvers = textViewPlayingTeamOvers.getText().toString();

                            playingTeamOversFloat = Float.parseFloat(stringPlayingTeamOvers);

                            textViewPlayingTeamOvers.setText(new DecimalFormat("##.#").format(playingTeamOversFloat + 0.1));

                            reusableFunctionPlayer();

                            stringFirstPlayerRun = textViewFirstPlayerRun.getText().toString();

                            firstPlayerRun = Integer.parseInt(stringFirstPlayerRun);

                            //textViewFirstPlayerRun.setText(String.valueOf(firstPlayerRun + 0));
                            stringFirstPlayerBall = textViewFirstPlayerBall.getText().toString();

                            firstPlayerBall = Integer.parseInt(stringFirstPlayerBall);

                            //textViewFirstPlayerBall.setText(String.valueOf(firstPlayerBall + 1));


                            stringBowlerRun = textViewBowlerRun.getText().toString();

                            bowlerRun = Integer.parseInt(stringBowlerRun);

                            textViewBowlerRun.setText(String.valueOf(bowlerRun + editTextRuns1));

                            stringBowlerWicket = textViewBowlerWicket.getText().toString();

                            bowlerWicket = Integer.parseInt(stringBowlerWicket);

                            //textViewBowlerWicket.setText(String.valueOf(bowlerWicket + 1));

                            stringBowlerBall = textViewBowlerBall.getText().toString();

                            bowlerOversFloat = Float.parseFloat(stringBowlerBall);

                            textViewBowlerBall.setText(new DecimalFormat("##.#").format(bowlerOversFloat + 0.1));

                            textViewCurrentOver.setText(textViewCurrentOver.getText() + " " + "w");
                            stringExtraRun = textViewExtraRun.getText().toString();

                            extraRun = Integer.parseInt(stringExtraRun);

                            //textViewExtraRun.setText(String.valueOf(extraRun + editTextRuns1 ));
                            reusableFunctionBowler();

                            radioButtonFirstPlayer.setChecked(true);
                            radioButtonSecondPlayer.setChecked(false);

                        } else {

                            stringBatsmanName1 = spinnerBatsman.getSelectedItem().toString();
                            stringCatcherName1 = spinnerCatcherName.getSelectedItem().toString();


                            try {

                                if (jsonObjectBatsman.getString("success").equals("1")) {
                                    JSONArray jsonArray = jsonObjectBatsman.getJSONArray("player_list");

                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject object = jsonArray.getJSONObject(i);

                                        stringNewTeamName = object.getString("team_name");

                                        stringSpaceRemove = stringBattingTeam.replaceAll(" ", "");

                                        if (stringSpaceRemove.equals(stringNewTeamName)) {
                                            JSONArray array = object.getJSONArray("players");

                                            for (int j = 0; j < array.length(); j++) {
                                                JSONObject obj = array.getJSONObject(j);

                                                stringNewBatsmanName = obj.getString("fullname");
                                                stringNewBatsmanId = obj.getString("player_id");

                                                if (stringBatsmanName1.equals(stringNewBatsmanName)) {
                                                    stringBatsmanId1 = stringNewBatsmanId;
                                                }

                                            }


                                        }
                                        else {
                                            JSONArray array = object.getJSONArray("players");

                                            for (int j = 0; j < array.length(); j++) {
                                                JSONObject obj = array.getJSONObject(j);

                                                stringCatcherName = obj.getString("fullname");
                                                stringCatcherId = obj.getString("player_id");


                                                if (stringCatcherName1.equals(stringCatcherName)) {
                                                    stringCatcherId1 = stringCatcherId;

                                                    Log.d("catcherName",stringCatcherId1);
                                                }

                                            }
                                        }
                                    }
                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            stringPlayingTeamRun = textViewPlayingTeamRun.getText().toString();

                            playingTeamRum = Integer.parseInt(stringPlayingTeamRun);

                            textViewPlayingTeamRun.setText(String.valueOf(playingTeamRum + editTextRuns1));

                            stringPlayingTeamWicket = textViewPlayingTeamWicket.getText().toString();

                            playingTeamWicket = Integer.parseInt(stringPlayingTeamWicket);

                            textViewPlayingTeamWicket.setText(String.valueOf(playingTeamWicket + 1));

                            stringPlayingTeamOvers = textViewPlayingTeamOvers.getText().toString();

                            playingTeamOversFloat = Float.parseFloat(stringPlayingTeamOvers);

                            textViewPlayingTeamOvers.setText(new DecimalFormat("##.#").format(playingTeamOversFloat + 0.1));

                            reusableFunctionPlayer();

                            stringSecondPlayerRun = textViewSecondPlayerRun.getText().toString();

                            secondPlayerRun = Integer.parseInt(stringSecondPlayerRun);

                            //textViewSecondPlayerRun.setText(String.valueOf(secondPlayerRun + 0));


                            stringSecondPlayerBall = textViewSecondPlayerBall.getText().toString();

                            secondPlayerBall = Integer.parseInt(stringSecondPlayerBall);

                            //textViewSecondPlayerBall.setText(String.valueOf(secondPlayerBall + 1));


                            stringBowlerRun = textViewBowlerRun.getText().toString();

                            bowlerRun = Integer.parseInt(stringBowlerRun);

                            textViewBowlerRun.setText(String.valueOf(bowlerRun + editTextRuns1));

                            stringBowlerWicket = textViewBowlerWicket.getText().toString();

                            bowlerWicket = Integer.parseInt(stringBowlerWicket);

                            //textViewBowlerWicket.setText(String.valueOf(bowlerWicket + 1));

                            stringBowlerBall = textViewBowlerBall.getText().toString();

                            bowlerOversFloat = Float.parseFloat(stringBowlerBall);

                            textViewBowlerBall.setText(new DecimalFormat("##.#").format(bowlerOversFloat + 0.1));

                            textViewCurrentOver.setText(textViewCurrentOver.getText() + " " + "w");
                            stringExtraRun = textViewExtraRun.getText().toString();

                            extraRun = Integer.parseInt(stringExtraRun);

                            //textViewExtraRun.setText(String.valueOf(extraRun + editTextRuns1));
                            reusableFunctionBowler();

                        }
                        wicketServerCall();
                        dismiss();
                    }

                    if (radioButtonNor.isChecked() && radioButtonNone.isChecked()) {

                        if (radioButtonFirstPlayer.isChecked()) {
                            stringBatsmanName1 = spinnerBatsman.getSelectedItem().toString();
                            stringCatcherName1 = spinnerCatcherName.getSelectedItem().toString();


                            try {

                                if (jsonObjectBatsman.getString("success").equals("1")) {
                                    JSONArray jsonArray = jsonObjectBatsman.getJSONArray("player_list");

                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject object = jsonArray.getJSONObject(i);

                                        stringNewTeamName = object.getString("team_name");

                                        stringSpaceRemove = stringBattingTeam.replaceAll(" ", "");

                                        if (stringSpaceRemove.equals(stringNewTeamName)) {
                                            JSONArray array = object.getJSONArray("players");

                                            for (int j = 0; j < array.length(); j++) {
                                                JSONObject obj = array.getJSONObject(j);

                                                stringNewBatsmanName = obj.getString("fullname");
                                                stringNewBatsmanId = obj.getString("player_id");

                                                if (stringBatsmanName1.equals(stringNewBatsmanName)) {
                                                    stringBatsmanId1 = stringNewBatsmanId;
                                                }

                                            }


                                        }
                                        else {
                                            JSONArray array = object.getJSONArray("players");

                                            for (int j = 0; j < array.length(); j++) {
                                                JSONObject obj = array.getJSONObject(j);

                                                stringCatcherName = obj.getString("fullname");
                                                stringCatcherId = obj.getString("player_id");


                                                if (stringCatcherName1.equals(stringCatcherName)) {
                                                    stringCatcherId1 = stringCatcherId;

                                                    Log.d("catcherName",stringCatcherId1);
                                                }

                                            }
                                        }
                                    }
                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            stringPlayingTeamRun = textViewPlayingTeamRun.getText().toString();

                            playingTeamRum = Integer.parseInt(stringPlayingTeamRun);

                            textViewPlayingTeamRun.setText(String.valueOf(playingTeamRum + editTextRuns1));

                            stringPlayingTeamWicket = textViewPlayingTeamWicket.getText().toString();

                            playingTeamWicket = Integer.parseInt(stringPlayingTeamWicket);

                            textViewPlayingTeamWicket.setText(String.valueOf(playingTeamWicket + 1));

                            stringPlayingTeamOvers = textViewPlayingTeamOvers.getText().toString();

                            playingTeamOversFloat = Float.parseFloat(stringPlayingTeamOvers);

                            textViewPlayingTeamOvers.setText(new DecimalFormat("##.#").format(playingTeamOversFloat + 0.1));

                            reusableFunctionPlayer();

                            stringFirstPlayerRun = textViewFirstPlayerRun.getText().toString();

                            firstPlayerRun = Integer.parseInt(stringFirstPlayerRun);

                            //textViewFirstPlayerRun.setText(String.valueOf(firstPlayerRun + 0));
                            stringFirstPlayerBall = textViewFirstPlayerBall.getText().toString();

                            firstPlayerBall = Integer.parseInt(stringFirstPlayerBall);

                            //textViewFirstPlayerBall.setText(String.valueOf(firstPlayerBall + 1));


                            stringBowlerRun = textViewBowlerRun.getText().toString();

                            bowlerRun = Integer.parseInt(stringBowlerRun);

                            textViewBowlerRun.setText(String.valueOf(bowlerRun + editTextRuns1));

                            stringBowlerWicket = textViewBowlerWicket.getText().toString();

                            bowlerWicket = Integer.parseInt(stringBowlerWicket);

                            //textViewBowlerWicket.setText(String.valueOf(bowlerWicket + 1));

                            stringBowlerBall = textViewBowlerBall.getText().toString();

                            bowlerOversFloat = Float.parseFloat(stringBowlerBall);

                            textViewBowlerBall.setText(new DecimalFormat("##.#").format(bowlerOversFloat + 0.1));

                            textViewCurrentOver.setText(textViewCurrentOver.getText() + " " + "w");
                            stringExtraRun = textViewExtraRun.getText().toString();

                            extraRun = Integer.parseInt(stringExtraRun);

                            //textViewExtraRun.setText(String.valueOf(extraRun + editTextRuns1 ));
                            reusableFunctionBowler();

                        } else {

                            stringBatsmanName1 = spinnerBatsman.getSelectedItem().toString();
                            stringCatcherName1 = spinnerCatcherName.getSelectedItem().toString();


                            try {

                                if (jsonObjectBatsman.getString("success").equals("1")) {
                                    JSONArray jsonArray = jsonObjectBatsman.getJSONArray("player_list");

                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject object = jsonArray.getJSONObject(i);

                                        stringNewTeamName = object.getString("team_name");

                                        stringSpaceRemove = stringBattingTeam.replaceAll(" ", "");

                                        if (stringSpaceRemove.equals(stringNewTeamName)) {
                                            JSONArray array = object.getJSONArray("players");

                                            for (int j = 0; j < array.length(); j++) {
                                                JSONObject obj = array.getJSONObject(j);

                                                stringNewBatsmanName = obj.getString("fullname");
                                                stringNewBatsmanId = obj.getString("player_id");

                                                if (stringBatsmanName1.equals(stringNewBatsmanName)) {
                                                    stringBatsmanId1 = stringNewBatsmanId;
                                                }

                                            }


                                        }
                                        else {
                                            JSONArray array = object.getJSONArray("players");

                                            for (int j = 0; j < array.length(); j++) {
                                                JSONObject obj = array.getJSONObject(j);

                                                stringCatcherName = obj.getString("fullname");
                                                stringCatcherId = obj.getString("player_id");


                                                if (stringCatcherName1.equals(stringCatcherName)) {
                                                    stringCatcherId1 = stringCatcherId;

                                                    Log.d("catcherName",stringCatcherId1);
                                                }

                                            }
                                        }
                                    }
                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            stringPlayingTeamRun = textViewPlayingTeamRun.getText().toString();

                            playingTeamRum = Integer.parseInt(stringPlayingTeamRun);

                            textViewPlayingTeamRun.setText(String.valueOf(playingTeamRum + editTextRuns1));

                            stringPlayingTeamWicket = textViewPlayingTeamWicket.getText().toString();

                            playingTeamWicket = Integer.parseInt(stringPlayingTeamWicket);

                            textViewPlayingTeamWicket.setText(String.valueOf(playingTeamWicket + 1));

                            stringPlayingTeamOvers = textViewPlayingTeamOvers.getText().toString();

                            playingTeamOversFloat = Float.parseFloat(stringPlayingTeamOvers);

                            textViewPlayingTeamOvers.setText(new DecimalFormat("##.#").format(playingTeamOversFloat + 0.1));

                            reusableFunctionPlayer();

                            stringSecondPlayerRun = textViewSecondPlayerRun.getText().toString();

                            secondPlayerRun = Integer.parseInt(stringSecondPlayerRun);

                            //textViewSecondPlayerRun.setText(String.valueOf(secondPlayerRun + 0));


                            stringSecondPlayerBall = textViewSecondPlayerBall.getText().toString();

                            secondPlayerBall = Integer.parseInt(stringSecondPlayerBall);

                            //textViewSecondPlayerBall.setText(String.valueOf(secondPlayerBall + 1));


                            stringBowlerRun = textViewBowlerRun.getText().toString();

                            bowlerRun = Integer.parseInt(stringBowlerRun);

                            textViewBowlerRun.setText(String.valueOf(bowlerRun + editTextRuns1));

                            stringBowlerWicket = textViewBowlerWicket.getText().toString();

                            bowlerWicket = Integer.parseInt(stringBowlerWicket);

                            //textViewBowlerWicket.setText(String.valueOf(bowlerWicket + 1));

                            stringBowlerBall = textViewBowlerBall.getText().toString();

                            bowlerOversFloat = Float.parseFloat(stringBowlerBall);

                            textViewBowlerBall.setText(new DecimalFormat("##.#").format(bowlerOversFloat + 0.1));

                            textViewCurrentOver.setText(textViewCurrentOver.getText() + " " + "w");
                            stringExtraRun = textViewExtraRun.getText().toString();

                            extraRun = Integer.parseInt(stringExtraRun);

                            //textViewExtraRun.setText(String.valueOf(extraRun + editTextRuns1));
                            reusableFunctionBowler();

                        }
                        wicketServerCall();
                        dismiss();
                    }

                }
            });

            buttonReset.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });


            return view;
        }

        @Override
        public void onResume() {
            super.onResume();

            getDialog().getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        }
    }

    void zeroToSevenRunsServerCall(final int i) {
        if (radioButtonFirstPlayer.isChecked()) {
            stringPlayingTeamRun = textViewPlayingTeamRun.getText().toString();

            playingTeamRum = Integer.parseInt(stringPlayingTeamRun);

            textViewPlayingTeamRun.setText(String.valueOf(playingTeamRum + i));

            stringPlayingTeamOvers = textViewPlayingTeamOvers.getText().toString();

            playingTeamOversFloat = Float.parseFloat(stringPlayingTeamOvers);

            textViewPlayingTeamOvers.setText(new DecimalFormat("##.#").format(playingTeamOversFloat + 0.1));

            reusableFunctionPlayer();

            stringFirstPlayerRun = textViewFirstPlayerRun.getText().toString();

            firstPlayerRun = Integer.parseInt(stringFirstPlayerRun);

            textViewFirstPlayerRun.setText(String.valueOf(firstPlayerRun + i));
            stringFirstPlayerBall = textViewFirstPlayerBall.getText().toString();

            firstPlayerBall = Integer.parseInt(stringFirstPlayerBall);

            textViewFirstPlayerBall.setText(String.valueOf(firstPlayerBall + 1));


            stringBowlerRun = textViewBowlerRun.getText().toString();

            bowlerRun = Integer.parseInt(stringBowlerRun);

            textViewBowlerRun.setText(String.valueOf(bowlerRun + i));


            stringBowlerBall = textViewBowlerBall.getText().toString();

            bowlerOversFloat = Float.parseFloat(stringBowlerBall);

            textViewBowlerBall.setText(new DecimalFormat("##.#").format(bowlerOversFloat + 0.1));

            textViewCurrentOver.setText(textViewCurrentOver.getText() + " " + i);

            reusableFunctionBowler();

        } else {

            stringPlayingTeamRun = textViewPlayingTeamRun.getText().toString();

            playingTeamRum = Integer.parseInt(stringPlayingTeamRun);

            textViewPlayingTeamRun.setText(String.valueOf(playingTeamRum + i));

            stringPlayingTeamOvers = textViewPlayingTeamOvers.getText().toString();

            playingTeamOversFloat = Float.parseFloat(stringPlayingTeamOvers);

            textViewPlayingTeamOvers.setText(new DecimalFormat("##.#").format(playingTeamOversFloat + 0.1));

            reusableFunctionPlayer();

            stringSecondPlayerRun = textViewSecondPlayerRun.getText().toString();

            secondPlayerRun = Integer.parseInt(stringSecondPlayerRun);

            textViewSecondPlayerRun.setText(String.valueOf(secondPlayerRun + i));


            stringSecondPlayerBall = textViewSecondPlayerBall.getText().toString();

            secondPlayerBall = Integer.parseInt(stringSecondPlayerBall);

            textViewSecondPlayerBall.setText(String.valueOf(secondPlayerBall + 1));


            stringBowlerRun = textViewBowlerRun.getText().toString();

            bowlerRun = Integer.parseInt(stringBowlerRun);

            textViewBowlerRun.setText(String.valueOf(bowlerRun + i));


            stringBowlerBall = textViewBowlerBall.getText().toString();

            bowlerOversFloat = Float.parseFloat(stringBowlerBall);

            textViewBowlerBall.setText(new DecimalFormat("##.#").format(bowlerOversFloat + 0.1));

            textViewCurrentOver.setText(textViewCurrentOver.getText() + " " + i);

            reusableFunctionBowler();

        }


        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.RUN_DETAILS_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            if (jsonObject.getString("success").equals("1")) {
                                if (i % 2 == 0) {
                                    if (bBowler.equals("6")) {
                                        if (radioButtonFirstPlayer.isChecked()) {
                                            radioButtonFirstPlayer.setChecked(false);
                                            radioButtonSecondPlayer.setChecked(true);

                                        } else {
                                            radioButtonFirstPlayer.setChecked(true);
                                            radioButtonSecondPlayer.setChecked(false);
                                        }
                                    } else {

                                        if (radioButtonFirstPlayer.isChecked()) {
                                            radioButtonFirstPlayer.setChecked(true);
                                            radioButtonSecondPlayer.setChecked(false);

                                        } else {
                                            radioButtonFirstPlayer.setChecked(false);
                                            radioButtonSecondPlayer.setChecked(true);
                                        }
                                    }

                                } else {

                                    if (bBowler.equals("6")) {
                                        if (radioButtonFirstPlayer.isChecked()) {
                                            radioButtonFirstPlayer.setChecked(true);
                                            radioButtonSecondPlayer.setChecked(false);

                                        } else {
                                            radioButtonFirstPlayer.setChecked(false);
                                            radioButtonSecondPlayer.setChecked(true);
                                        }
                                    } else {

                                        if (radioButtonFirstPlayer.isChecked()) {
                                            radioButtonFirstPlayer.setChecked(false);
                                            radioButtonSecondPlayer.setChecked(true);

                                        } else {
                                            radioButtonFirstPlayer.setChecked(true);
                                            radioButtonSecondPlayer.setChecked(false);
                                        }
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
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();

                if (radioButtonFirstPlayer.isChecked()) {
                    params.put("total_runs", String.valueOf(playingTeamRum + i));
                    params.put("total_overs", new DecimalFormat("##.#").format(playingTeamOversFloat + 0.1));
                    params.put("player_run", String.valueOf(firstPlayerRun + i));
                    params.put("player_ball", String.valueOf(firstPlayerBall + 1));
                    params.put("bowler_ball", new DecimalFormat("##.#").format(bowlerOversFloat + 0.1));
                    params.put("bowler_runs", String.valueOf(bowlerRun + i));
                    params.put("match_key", stringMatchKey);
                    params.put("player_id", stringFristPlayerId);
                    if (TextUtils.isEmpty(stringBowlerName1)) {
                        params.put("bowler_id", stringBowlerId);
                    } else {
                        params.put("bowler_id", stringBowlerId1);
                    }

                    params.put("ball_type", String.valueOf(i));
                    params.put("extra_run", "0");
                    params.put("wicket_type", "0");
                    params.put("total_wickets", "0");
                    params.put("wicket_id", "0");
                }

                if (radioButtonSecondPlayer.isChecked()) {
                    params.put("total_runs", String.valueOf(playingTeamRum + i));
                    params.put("total_overs", new DecimalFormat("##.#").format(playingTeamOversFloat + 0.1));
                    params.put("player_run", String.valueOf(secondPlayerRun + i));
                    params.put("player_ball", String.valueOf(secondPlayerBall + 1));
                    params.put("bowler_ball", new DecimalFormat("##.#").format(bowlerOversFloat + 0.1));
                    params.put("bowler_runs", String.valueOf(bowlerRun + i));
                    params.put("match_key", stringMatchKey);
                    params.put("player_id", stringSecondPlayerId);
                    if (TextUtils.isEmpty(stringBowlerName1)) {
                        params.put("bowler_id", stringBowlerId);
                    } else {
                        params.put("bowler_id", stringBowlerId1);
                    }
                    params.put("ball_type", String.valueOf(i));
                    params.put("extra_run", "0");
                    params.put("wicket_type", "0");
                    params.put("total_wickets", "0");
                    params.put("wicket_id", "0");
                }


                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }


    void zeroToSevenMethod(int i) {

        zeroToSevenRunsServerCall(i);
    }

    void dataGetter() {

        stringTeamRuns = textViewPlayingTeamRun.getText().toString();
        stringTeamOvers = textViewPlayingTeamOvers.getText().toString();
        stringFirstPlayerRuns = textViewFirstPlayerRun.getText().toString();
        stringFirstPlayerBalls = textViewFirstPlayerBall.getText().toString();
        stringSecondPlayerRuns = textViewSecondPlayerRun.getText().toString();
        stringSecondPlayerBalls = textViewSecondPlayerBall.getText().toString();
        stringBowlerBalls = textViewBowlerBall.getText().toString();
        stringBowlerRuns = textViewBowlerRun.getText().toString();
        stringExtraRuns = textViewExtraRun.getText().toString();
        stringTeamWickets = textViewPlayingTeamWicket.getText().toString();
        stringBowlerWickets = textViewBowlerWicket.getText().toString();
    }

    void runsServerCall(final int number) {

        dataGetter();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.RUN_DETAILS_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            if (jsonObject.getString("success").equals("1")) {

                                if (number % 2 == 0) {

                                    if (radioButtonFirstPlayer.isChecked()) {
                                        radioButtonFirstPlayer.setChecked(true);
                                        radioButtonSecondPlayer.setChecked(false);

                                    } else {
                                        radioButtonFirstPlayer.setChecked(false);
                                        radioButtonSecondPlayer.setChecked(true);
                                    }

                                } else {


                                    if (radioButtonFirstPlayer.isChecked()) {
                                        radioButtonFirstPlayer.setChecked(false);
                                        radioButtonSecondPlayer.setChecked(true);

                                    } else {
                                        radioButtonFirstPlayer.setChecked(true);
                                        radioButtonSecondPlayer.setChecked(false);
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
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();

                if (radioButtonFirstPlayer.isChecked()) {
                    params.put("total_runs", stringTeamRuns);
                    params.put("total_overs", stringTeamOvers);
                    params.put("player_run", stringFirstPlayerRuns);
                    params.put("player_ball", stringFirstPlayerBalls);
                    params.put("bowler_ball", stringBowlerBalls);
                    params.put("bowler_runs", stringBowlerRuns);
                    params.put("match_key", stringMatchKey);
                    params.put("player_id", stringFristPlayerId);
                    params.put("ball_type", stringBallType1);
                    params.put("extra_run", stringExtraRuns);
                    params.put("wicket_type", "0");
                    params.put("wicket_id", "0");
                    params.put("total_wickets", "0");
                    if (TextUtils.isEmpty(stringBowlerName1)) {
                        params.put("bowler_id", stringBowlerId);
                    } else {
                        params.put("bowler_id", stringBowlerId1);
                    }


                }

                if (radioButtonSecondPlayer.isChecked()) {
                    params.put("total_runs", stringTeamRuns);
                    params.put("total_overs", stringTeamOvers);
                    params.put("player_run", stringSecondPlayerRuns);
                    params.put("player_ball", stringSecondPlayerBalls);
                    params.put("bowler_ball", stringBowlerBalls);
                    params.put("bowler_runs", stringBowlerRuns);
                    params.put("match_key", stringMatchKey);
                    params.put("player_id", stringSecondPlayerId);
                    params.put("ball_type", stringBallType1);
                    params.put("extra_run", stringExtraRuns);
                    params.put("wicket_type", "0");
                    params.put("total_wickets", "0");
                    params.put("wicket_id", "0");
                    if (TextUtils.isEmpty(stringBowlerName1)) {
                        params.put("bowler_id", stringBowlerId);
                    } else {
                        params.put("bowler_id", stringBowlerId1);
                    }

                }


                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }

    void wicketServerCall() {

        dataGetter();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.RUN_DETAILS_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            if (jsonObject.getString("success").equals("1")) {


                                if (radioButtonFirstPlayer.isChecked()) {
                                    radioButtonFirstPlayer.setText(stringBatsmanName1);
                                    textViewFirstPlayerBall.setText("0");
                                    textViewFirstPlayerRun.setText("0");
                                } else {
                                    radioButtonSecondPlayer.setText(stringBatsmanName1);
                                    textViewSecondPlayerBall.setText("0");
                                    textViewSecondPlayerRun.setText("0");
                                }

                                if (radioButtonFirst.isChecked())
                                {
                                    radioButtonFirstPlayer.setText(stringBatsmanName1);
                                    textViewFirstPlayerBall.setText("0");
                                    textViewFirstPlayerRun.setText("0");
                                }else {
                                    radioButtonSecondPlayer.setText(stringBatsmanName1);
                                    textViewSecondPlayerBall.setText("0");
                                    textViewSecondPlayerRun.setText("0");
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
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();

                if (radioButtonFirstPlayer.isChecked()) {
                    params.put("total_runs", stringTeamRuns);
                    params.put("total_overs", stringTeamOvers);
                    params.put("player_run", stringFirstPlayerRuns);
                    params.put("player_ball", stringFirstPlayerBalls);
                    params.put("bowler_ball", stringBowlerBalls);
                    params.put("bowler_runs", stringBowlerRuns);
                    params.put("match_key", stringMatchKey);
                    params.put("ball_type", "w");
                    params.put("extra_run", stringExtraRuns);
                    params.put("wicket_type", stringWicketType);
                    params.put("total_wickets", stringTeamWickets);
                    if (TextUtils.isEmpty(stringCatcherName1)) {
                        params.put("wicket_id", "0");
                    } else {
                        params.put("wicket_id", stringCatcherId1);
                    }
                    if (!TextUtils.isEmpty(stringBatsmanName1)) {
                        params.put("player_id", stringFristPlayerId);
                    } else {
                        params.put("player_id", stringBatsmanId1);
                    }

                    if (TextUtils.isEmpty(stringBowlerName1)) {
                        params.put("bowler_id", stringBowlerId);
                    } else {
                        params.put("bowler_id", stringBowlerId1);
                    }

                }

                if (radioButtonSecondPlayer.isChecked()) {
                    params.put("total_runs", stringTeamRuns);
                    params.put("total_overs", stringTeamOvers);
                    params.put("player_run", stringSecondPlayerRuns);
                    params.put("player_ball", stringSecondPlayerBalls);
                    params.put("bowler_ball", stringBowlerBalls);
                    params.put("bowler_runs", stringBowlerRuns);
                    params.put("match_key", stringMatchKey);
                    params.put("ball_type", "w");
                    params.put("extra_run", stringExtraRuns);
                    params.put("wicket_type", stringWicketType);
                    params.put("total_wickets", stringTeamWickets);
                    if (TextUtils.isEmpty(stringCatcherName1)) {
                        params.put("wicket_id", "0");
                    } else {
                        params.put("wicket_id", stringCatcherId1);
                    }
                    if (!TextUtils.isEmpty(stringBatsmanName1)) {
                        params.put("player_id", stringSecondPlayerId);
                    } else {
                        params.put("player_id", stringBatsmanId1);
                    }

                    if (TextUtils.isEmpty(stringBowlerName1)) {
                        params.put("bowler_id", stringBowlerId);
                    } else {
                        params.put("bowler_id", stringBowlerId1);
                    }

                }


                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }

    void extraRuns(String name) {
        if (name.equals("Wide")) {
            //Toast.makeText(this, "Wide", Toast.LENGTH_SHORT).show();
            wideBallPopUp();
        } else if (name.equals("NoBall")) {
            //Toast.makeText(this, "NoBall", Toast.LENGTH_SHORT).show();
            noBallPopUp();
        } else if (name.equals("Byes")) {
            //Toast.makeText(this, "Byes", Toast.LENGTH_SHORT).show();
            byesPopUp();
        } else if (name.equals("LegByes")) {
            //Toast.makeText(this, "LegByes", Toast.LENGTH_SHORT).show();
            legByPopUp();
        } else if (name.equals("Wicket")) {
            wicketPopUp();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_logout:
                customDialogClass.show();
                break;

        }
        return super.onOptionsItemSelected(item);
    }


    private class CustomDialogClass extends Dialog implements View.OnClickListener {

        public CustomDialogClass(Context context) {
            super(context);
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.custom_dialog);
            yes = findViewById(R.id.btn_yes);
            no = findViewById(R.id.btn_no);
            yes.setOnClickListener(this);
            no.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_yes:
                    SessionManager.getInstance(MatchPlayActivity.this).logout();

                    finish();
                    break;
                case R.id.btn_no:
                    dismiss();
                    break;
                default:
                    break;
            }
            dismiss();
        }
    }

    // onBack pressed
    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        MatchPlayActivity.this.finish();
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


