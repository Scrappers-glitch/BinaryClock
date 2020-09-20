package com.scrappers.binaryclock.mainScreens;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

import com.google.android.material.snackbar.Snackbar;
import com.scrappers.binaryclock.R;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import androidx.appcompat.app.AppCompatActivity;

import static com.scrappers.binaryclock.mainScreens.Remote_Activity.bluetoothSPP;

public class Settings extends AppCompatActivity {

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(Integer.parseInt(read_format(getFilesDir()+"/Theme/"+"theme.theme")));
        setContentView(R.layout.settings);
        //lock screen orientation to portrait
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        RadioButton military=findViewById(R.id.militarybtn);
        RadioButton hr12btn=findViewById(R.id.hr12btn);

        try {
            switch (read_format(getFilesDir() + "/TimeFormat/" + "format.time")) {
                case "military":
                    military.setChecked(true);
                    break;

                case "AM/PM":
                    hr12btn.setChecked(true);
                    break;
            }
        }catch (Exception e){
            write_format(getFilesDir()+"/TimeFormat/"+"format.time","military");
            e.printStackTrace();
        }

    }

    public  String read_format(String s) {

        try{
            BufferedReader reader=new BufferedReader(new FileReader(s));
            return reader.readLine();
        }catch (Exception e){
            e.printStackTrace();
            return "";
        }

    }
     public void write_format(String s, String s1) {
            try{
                BufferedWriter writer=new BufferedWriter(new FileWriter(new File(s)));
                writer.write(s1);
                writer.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    public void change_clockMode(View view) {
        try{
                switch (String.valueOf(view.getTag())){
                    case "military":
                        write_format(getFilesDir()+"/TimeFormat/"+"format.time","military");
                        bluetoothSPP.send(400500+"",false);
                    break;

                    case "AM/PM":
                        write_format(getFilesDir()+"/TimeFormat/"+"format.time","AM/PM");
                        bluetoothSPP.send(400000+"",false);
                    break;
                }
        }catch (Exception e){
            e.printStackTrace();
            Snackbar.make(view,"Please Connect your device to the binary clock !",Snackbar.LENGTH_LONG).show();
        }
    }

    public void change_theme(View view){
        try{

                switch (String.valueOf(view.getTag())){
                    case "Light":
                        write_format(getFilesDir()+"/Theme/"+"theme.theme",String.valueOf(R.style.AppTheme));
                           System.exit(0);
                           startActivity(new Intent(this,Remote_Activity.class));
                        break;
                    case "Dark":
                        write_format(getFilesDir()+"/Theme/"+"theme.theme",String.valueOf(R.style.DarkTheme));
                        System.exit(0);
                        startActivity(new Intent(this,Remote_Activity.class));
                        break;
                }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}