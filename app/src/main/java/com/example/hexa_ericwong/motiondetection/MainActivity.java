package com.example.hexa_ericwong.motiondetection;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.SurfaceView;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.hexa_ericwong.motiondetection.motiondetection.MotionDetector;
import com.example.hexa_ericwong.motiondetection.motiondetection.MotionDetectorCallback;

public class MainActivity extends AppCompatActivity {

    //private TextView txtStatus;
    private MotionDetector motionDetector;
    private VideoView vv;
    private SurfaceView sv;

    static int count=0;
    boolean firstLoad=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.CAMERA}, 1);
        }

        count=0;
        //txtStatus = (TextView) findViewById(R.id.txtStatus);

        sv = (SurfaceView) findViewById(R.id.surfaceView);

        motionDetector = new MotionDetector(this, sv);
        motionListener();

        ////// Config Options
        //motionDetector.setCheckInterval(500);
        //motionDetector.setLeniency(70);
        //motionDetector.setMinLuma(1000);


        ///Video
        vv = (VideoView)findViewById(R.id.videoView);

        vv.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);
            }
        });


        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.go);

        vv.setVideoURI(uri);
        vv.requestFocus();
        vv.start();
    }

    private void motionListener() {

        motionDetector.setMotionDetectorCallback(new MotionDetectorCallback() {

            @Override
            public void onMotionDetected() {

                // for testing purpose
                //Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                //v.vibrate(80);

                //txtStatus.setText("Motion detected");

                if(!firstLoad){
                    if(count<1){
                        count+=1;
                        Intent i = new Intent(MainActivity.this,HelloActivity.class);
                        startActivity(i);
                        finish();
                    }
                }else{
                    firstLoad=false;
                }

            }

            @Override
            public void onTooDark() {
                //txtStatus.setText("Too dark here");
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        motionDetector.onResume();

        vv.start();

        if (motionDetector.checkCameraHardware()) {
            //txtStatus.setText("Camera found");
        } else {
            //txtStatus.setText("No camera available");
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        motionDetector.onPause();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1) {

            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
                finish();
                Intent i = new Intent(MainActivity.this, MainActivity.class);
                startActivity(i);


            } else {

                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
                finish();
            }

        }}//end onRequestPermissionsResult
}
