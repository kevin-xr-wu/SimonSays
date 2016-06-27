package com.example.kevinwu.simonsays;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

public class NameScreen extends AppCompatActivity {

    private EditText name;
    private Button ready;
    private String playerName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        name = (EditText) findViewById(R.id.enter_name);
        ready = (Button) findViewById(R.id.name_ready_button);
    }

    public void readyToPlay(View view){
        playerName = name.getText().toString();

        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("Name", playerName);
        startActivity(intent);
    }

}
