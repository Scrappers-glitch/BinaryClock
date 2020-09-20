package com.scrappers.binaryclock.mainScreens;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.scrappers.binaryclock.R;

import androidx.appcompat.app.AppCompatActivity;

public class aboutActivity extends AppCompatActivity {
    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_about);
        //lock screen orientation to portrait
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

    }

    public void runSocials(String socialLink){
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(socialLink)));
    }

    public void gMail(View view){
        runSocials("https://mail.google.com/mail/u/0/#inbox");
    }


    public void facebook_bishoy(View view){
        runSocials("https://www.facebook.com/profile.php?id=100008917261532");
    }
    public void facebook_pavly(View view){
        runSocials("https://www.facebook.com/JavaScrappers");
    }
    public void facebook_Thomas(View view){
        runSocials("https://www.facebook.com/2Math.toto");
    }



}
