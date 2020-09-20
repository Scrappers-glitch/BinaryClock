package com.scrappers.binaryclock.mainScreens;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.scrappers.binaryclock.R;

import java.util.Date;
import java.util.Random;

import androidx.appcompat.app.AppCompatActivity;
import app.akexorcist.bluetotohspp.library.BluetoothSPP;

import static com.scrappers.binaryclock.mainScreens.Remote_Activity.bluetoothSPP;

public class Binary_Game extends AppCompatActivity {
     private int hrsRandom=0;
     private int minsRandom=0;
     private int secsRandom=0;
     private int clockTimeNow=0;
     private int oldTime=0;
    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_binary__game);
        //lock screen orientation to portrait
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


        oldTime=new Date().getHours()*10000+new Date().getMinutes()*100+new Date().getSeconds();

        bluetoothSPP.setOnDataReceivedListener(new BluetoothSPP.OnDataReceivedListener() {
            @Override
            public void onDataReceived(byte[] data, String message) {
                clockTimeNow=Integer.parseInt(message);
                bluetoothSPP.setOnDataReceivedListener(null);
                Toast.makeText(getApplicationContext(),String.valueOf(clockTimeNow),Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        // Not calling **super**, disables back button in current screen
        final ProgressDialog pd=new ProgressDialog(this);
        pd.setMessage("Refreshing Time");
        pd.setCancelable(false);
        pd.show();

        bluetoothSPP.send("300500",false);
        new CountDownTimer(2000, 100) {

            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                pd.dismiss();
                refreshLocalTime();
                finish();
            }
        }.start();
    }

    public  int randomNum(int max){
        Random random=new Random();
        return random.nextInt(max);
    }

    public void startGame(View view){
        try {
            hrsRandom = randomNum(24);
            minsRandom = randomNum(60);
            secsRandom = randomNum(60);
            int Randomtime = hrsRandom * 10000 + minsRandom * 100 + secsRandom;
            bluetoothSPP.send(Randomtime + "", false);
        }catch (Exception e){
            e.printStackTrace();
            Snackbar.make(view, "Check Connectivity", Snackbar.LENGTH_LONG).show();
        }
    }

    public void refreshLocalTime(){
                    //new Android App time
                long newTime = new Date().getHours()*10000+new Date().getMinutes()*100+new Date().getSeconds();
                long difference = newTime - oldTime;
                long time_send = clockTimeNow + difference;
                bluetoothSPP.send(time_send+"",false);
    }

    public void Send_binary(View view){
        try {
            EditText editText_hrs = findViewById(R.id.hours);
            EditText editText_mins = findViewById(R.id.minutes);
            int hrsCheck = Integer.parseInt(editText_hrs.getText().toString());
            int minsCheck = Integer.parseInt(editText_mins.getText().toString());

            if ( hrsCheck == hrsRandom && minsCheck == minsRandom ){
                Toast.makeText(getApplicationContext(), "You Gained 1 points", Toast.LENGTH_LONG).show();
            }
        }catch (Exception e){
            e.printStackTrace();
            Snackbar.make(view, "Check Connectivity & Start the game", Snackbar.LENGTH_LONG).show();
        }

    }

}
