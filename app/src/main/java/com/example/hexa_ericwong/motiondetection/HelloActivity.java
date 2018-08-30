package com.example.hexa_ericwong.motiondetection;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class HelloActivity extends AppCompatActivity {

    private CountDownTimer timer;
    private Boolean interacting=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello);

        startTimer();
    }

    public void startTimer(){
        timer = new CountDownTimer(5000, 20) {

            @Override
            public void onTick(long millisUntilFinished) {
                Log.e("YO", String.valueOf(millisUntilFinished));

            }

            @Override
            public void onFinish() {
                try{
                    yo();
                }catch(Exception e){
                    Log.e("Error", "Error: " + e.toString());
                }
            }
        }.start();
    }

    public void yo(){
        if(!interacting){

            timer.cancel();
            finish();
            Intent i = new Intent(HelloActivity.this, MainActivity.class);
            startActivity(i);
        }else{
            interacting=false;
            startTimer();
        }
    }

    public void go(View v){

    }

    @Override
    public void onUserInteraction() {
        super.onUserInteraction();
        interacting=true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        timer.cancel();
        Intent i = new Intent(HelloActivity.this, MainActivity.class);
        startActivity(i);
        finish();
    }
}
