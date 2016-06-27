package com.example.kevinwu.simonsays;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;

public class WelcomeScreen extends AppCompatActivity {

    private RelativeLayout screen;
    private int nextColor;
    private boolean abort;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        nextColor = 0;
        abort = false;
        screen = (RelativeLayout) findViewById(R.id.background);
        changeBackgroundColor(nextColor);
    }

    @Override
    protected void onPause() {
        abort = true;
        super.onPause();
    }

    @Override
    protected void onResume() {
        abort = false;
        this.changeBackgroundColor(nextColor);
        super.onResume();
    }

    public void startGame(View view) {

        Intent intent = new Intent(this, NameScreen.class);
        startActivity(intent);

    }

    public void toLeaderboard(View view) {

        Intent intent = new Intent(this, Scoreboard.class);
        startActivity(intent);

    }

    public void changeBackgroundColor(int nextColor) {

        if (abort)
            return;


        final int color = nextColor % 4;
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after half a second = 500ms
                switch (color) {
                    case 0:
                        screen.setBackgroundColor(0xFF00FF00);
                        break;
                    case 1:
                        screen.setBackgroundColor(0xFF0000FF);
                        break;
                    case 2:
                        screen.setBackgroundColor(0xFFFF0000);
                        break;
                    case 3:
                        screen.setBackgroundColor(0xFFFFFF00);
                        break;
                }
                changeBackgroundColor(color + 1);
            }
        }, 2000);

    }
}
