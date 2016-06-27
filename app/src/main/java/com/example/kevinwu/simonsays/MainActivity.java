package com.example.kevinwu.simonsays;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private Button greenButt;
    private Button redButt;
    private Button yellowButt;
    private Button blueButt;
    private TextView userScore;

    private String sequence;
    private String userSequence;
    private int userIndex;
    private int numButtonsClicked;
    private int simonButtonsClicked;
    private int score;
    private String playerName;
    private TextView nameDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        greenButt = (Button) findViewById(R.id.greenButton);
        redButt = (Button) findViewById(R.id.redButton);
        yellowButt = (Button) findViewById(R.id.yellowButton);
        blueButt = (Button) findViewById(R.id.blueButton);
        userScore = (TextView) findViewById(R.id.score);
        nameDisplay = (TextView) findViewById(R.id.player_name);

        greenButt.setOnClickListener(colorHandler);
        redButt.setOnClickListener(colorHandler);
        yellowButt.setOnClickListener(colorHandler);
        blueButt.setOnClickListener(colorHandler);

        sequence = "";
        userSequence = "";
        userIndex = -1;
        numButtonsClicked = 0;
        simonButtonsClicked = 0;
        score = 0;


        Bundle extras = getIntent().getExtras();
        if(extras != null)
        {
            playerName = extras.getString("Name");
        }

        nameDisplay.setText("Name: " + playerName);
        nameDisplay.setTextColor(0xFF000000);

        startSimon();
    }

    View.OnClickListener colorHandler = new View.OnClickListener() {
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.greenButton:
                    userSequence += "1";
                    view.setBackgroundColor(0xFF006400);
                    break;
                case R.id.blueButton:
                    userSequence += "3";
                    view.setBackgroundColor(0xFF000080);
                    break;
                case R.id.redButton:
                    userSequence += "0";
                    view.setBackgroundColor(0xFF8b0000);
                    break;
                case R.id.yellowButton:
                    userSequence += "2";
                    view.setBackgroundColor(0xFFCC9900);
                    break;
            }

            pressed(view);
            userIndex++;

            if(numButtonsClicked == -1)
            {
                userSequence = "";
                userIndex = -1;
            }
            numButtonsClicked++;

//            Log.i("Kevin", "User in OnClickListener " + Integer.toString(numButtonsClicked));
//            Log.i("Kevin", "Simon in OnClickListener " + Integer.toString(simonButtonsClicked));

            if(userIndex != -1) {
                if (!checkSequence(userIndex)) {
                    //go to a lose intent
                    Intent intent = new Intent(MainActivity.this, Scoreboard.class);
                    intent.putExtra("Score", score);
                    intent.putExtra("Name", playerName);
                    startActivity(intent);

                   // Toast.makeText(getApplicationContext(), "You Lose!", Toast.LENGTH_SHORT).show();
                }
            }

            if(numButtonsClicked >= simonButtonsClicked && checkSequence(userIndex)) { //this means that they got the sequence right
                numButtonsClicked = 0; //reset the user input
                userIndex = -1;
                score++;
                userScore.setText(getString(R.string.scoreboard, score));
                playSequence(); //start for next iteration
            }
        }
    };

    public void pressed(View view) {
        final View colorView = view;
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after half a second = 500ms
                switch (colorView.getId()) {
                    case R.id.greenButton:
                        colorView.setBackgroundColor(0xFF00FF00);
                        break;
                    case R.id.blueButton:
                        colorView.setBackgroundColor(0xFF0000FF);
                        break;
                    case R.id.redButton:
                        colorView.setBackgroundColor(0xFFFF0000);
                        break;
                    case R.id.yellowButton:
                        colorView.setBackgroundColor(0xFFFFFF00);
                        break;
                }
            }
        }, 350);


    }

    //AI part
    public void startSimon() {

        int nextSequence = nextKey();

        //playSequence();
        numButtonsClicked--; //to cancel out Simon's click, does not count toward user click

        switch (nextSequence) {

            case 0: //red
                redButt.performClick();
                break;
            case 1: //green
                greenButt.performClick();
                break;
            case 2: //yellow
                yellowButt.performClick();
                break;
            case 3: //blue
                blueButt.performClick();
                break;
        }

    }

    public void playSequence() {

        nextKey();

        for(int i = 0; i < sequence.length(); i++)
        {
            int code = Integer.parseInt(Character.toString(sequence.charAt(i)));
            numButtonsClicked--; //to cancel out Simon's click, does not count toward user click

            //TODO: get the delay right for simon //DONE
            final int filler = code;
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    // Do something after half a second = 500ms
                    switch(filler)
                    {
                        case 0: //red
                            redButt.performClick();
                            break;
                        case 1: //green
                            greenButt.performClick();
                            break;
                        case 2: //yellow
                            yellowButt.performClick();
                            break;
                        case 3: //blue
                            blueButt.performClick();
                            break;
                        default:
                            break;
                    }
                }
            }, 500 * (i + 1)); //adds a delay for each iteration of the loop, every button click

        }


    }

    public int nextKey(){

        Random generator = new Random();
        int nextSequence = generator.nextInt(4);
        simonButtonsClicked++;

        sequence += Integer.toString(nextSequence);

        //Log.i("Kevin", "Sequence " + sequence);

        return nextSequence;
    }

    public boolean checkSequence(int index){
        if(sequence.charAt(index) == userSequence.charAt(index)){
//            Log.i("Kevin", "sequence char: " + sequence.charAt(index));
//            Log.i("Kevin", "user char: "  + userSequence.charAt(index));
            return true;
        }
        else{
            return false;
        }
    }
}


