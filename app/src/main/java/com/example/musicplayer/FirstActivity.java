package com.example.musicplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class FirstActivity extends AppCompatActivity {

    Handler handler= new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent= new Intent(FirstActivity.this,MainActivity.class);
                startActivity(intent);
                finish();

            }
        },5000);
    }
}