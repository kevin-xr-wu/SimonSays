package com.example.kevinwu.simonsays;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.jar.Attributes;

public class Scoreboard extends AppCompatActivity {

    private ArrayList<PlayerInfo> leaderList = new ArrayList<>();

    private final String STATE_KEY = "leaderListKey";
    private final String MY_PREFS_NAME = "SimonSays";
    private final String PREF_INITIALIZED = "init";

    private int score;
    private String name;
    private TextView score_display;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scoreboard);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        score = -1;
        score_display = (TextView) findViewById(R.id.player_score);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            score = extras.getInt("Score");
            name = extras.getString("Name");
        }

        if(score == -1)
        {
            TextView textView = (TextView) findViewById(R.id.score_message);
            textView.setVisibility(View.INVISIBLE);
            TextView textView1 = (TextView) findViewById(R.id.player_score);
            textView1.setVisibility(View.INVISIBLE);
        }
        else {
            score_display.setText(Integer.toString(score));

            PlayerInfo player = new PlayerInfo();
            player.setPlayerName(name);
            player.setPlayerScore(score);

            leaderList.add(player);
        }

        ListView listView;
        ListAdapter listAdapter;

        SharedPreferences sharedPref = getSharedPreferences(MY_PREFS_NAME, Context.MODE_PRIVATE);
        boolean isInitialized = sharedPref.getBoolean(PREF_INITIALIZED, false);
        if (isInitialized) {
            JSONArray jsonArray = new JSONArray();
            String data = sharedPref.getString(MY_PREFS_NAME, null);
            try {
                jsonArray = new JSONArray(data);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            for (int i = 0; i < jsonArray.length(); i++) {
                try {
                    PlayerInfo playerInfo = new PlayerInfo(jsonArray.getJSONObject(i));

                    leaderList.add(playerInfo);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        Collections.sort(leaderList, new Comparator<PlayerInfo>() {
            @Override
            public int compare(PlayerInfo o1, PlayerInfo o2) {
                return o1.getPlayerScore() > o2.getPlayerScore() ? -1
                        : o1.getPlayerScore() < o2.getPlayerScore() ? 1
                        : 0;
            }
        });

        PlayerInfo[] mLeaders = new PlayerInfo[leaderList.size()];

        mLeaders = leaderList.toArray(mLeaders);

        listAdapter = new CustomAdapter(this, mLeaders);
        listView = (ListView) findViewById(R.id.leaderBoard);
        listView.setAdapter(listAdapter);

        if (savedInstanceState != null) {
            leaderList = savedInstanceState.getParcelableArrayList(STATE_KEY);
            mLeaders = new PlayerInfo[leaderList.size()];
            mLeaders = leaderList.toArray(mLeaders);
            listAdapter = new CustomAdapter(this, mLeaders);
            listView = (ListView) findViewById(R.id.leaderBoard);
            listView.setAdapter(listAdapter);
        }

    }

    @Override
    public void onBackPressed() {
    }

    @Override
    protected void onPause() {
        super.onPause();
        //shared preferences, saves after the listview adapter is set
        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        editor.putBoolean(PREF_INITIALIZED, true);

        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < leaderList.size(); i++) {
            jsonArray.put(leaderList.get(i).getJSONObject());
        }

        String jsonString = jsonArray.toString();
        editor.putString(MY_PREFS_NAME, jsonString);

        editor.apply();

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(STATE_KEY, leaderList);
    }

    public void restart(View view) {
        Intent intent = new Intent(this, WelcomeScreen.class);
        startActivity(intent);
    }
}
