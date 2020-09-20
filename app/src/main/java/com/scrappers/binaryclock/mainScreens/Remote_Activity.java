package com.scrappers.binaryclock.mainScreens;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.scrappers.binaryclock.R;
import com.scrappers.binaryclock.blueToothSerialInterface.BluetoothConnection;

import java.io.File;
import java.util.Date;
import java.util.Objects;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import app.akexorcist.bluetotohspp.library.BluetoothSPP;
import app.akexorcist.bluetotohspp.library.BluetoothState;
import app.akexorcist.bluetotohspp.library.DeviceList;

import static java.lang.System.out;

public class Remote_Activity extends AppCompatActivity {

    @SuppressLint("StaticFieldLeak")
    public static BluetoothSPP bluetoothSPP;
    public static boolean POWER_STATE=false;
    public static boolean ENABLE_EDITING_TIME=false;
    private int NOW_HOURS=0;
    private int NOW_MINUTES=0;
    private int NOW_SECONDS=0;
    private int time=0;
    //random attrs

    AlertDialog d;

    TextView clockFormat;
    ImageView buttonLock0;
    ImageView buttonLock1;
    ImageView statusImage;
    TextView statusText;
    ImageButton autoConfigBtn;
    //attributes
    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        //set default value for the time format if the file doesn't exist
        // ==> the same as if the app opens for the first time
        if(!new File(getFilesDir()+"/TimeFormat/"+"format.time").exists()){
            //make the expansion directory before creating the default file
            make_expansion(getFilesDir()+"/TimeFormat/");
            new Settings().write_format(getFilesDir() + "/TimeFormat/" + "format.time", "military");
        }
        if(!new File(getFilesDir()+"/Theme/"+"theme.theme").exists()){
            make_expansion(getFilesDir()+"/Theme/");
            new Settings().write_format(getFilesDir()+"/Theme/"+"theme.theme",String.valueOf(R.style.AppTheme));
        }

        setTheme(Integer.parseInt(new Settings().read_format(getFilesDir()+"/Theme/"+"theme.theme")));

        setContentView(R.layout.activity_main);


        //lock screen orientation to portrait
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //id defining
        statusImage=findViewById(R.id.statusimage);
        statusText=findViewById(R.id.statustext);

        autoConfigBtn=findViewById(R.id.autoConfigBtn);
        buttonLock0=findViewById(R.id.buttonLock0);
        buttonLock1=findViewById(R.id.buttonLock1);
        clockFormat=findViewById(R.id.clockFormatText);
        //setting auto config to disabled state as a default state before establishing connections
        autoConfigBtn.setEnabled(false);
        clockFormat.setText(new Settings().read_format(getFilesDir()+"/TimeFormat/"+"format.time"));
    }

    @Override
    protected void onResume() {
        super.onResume();
        clockFormat.setText(new Settings().read_format(getFilesDir() + "/TimeFormat/" + "format.time"));

    }

    //make expansions method
    public void make_expansion(String file){
        File directory=new File(file);
        out.println(directory.mkdir());
    }

    public void power_control(Intent data){
        //connect to the device through here
        try {
            //device turned on
            POWER_STATE = true;
            bluetoothSPP = new BluetoothSPP(this);
            bluetoothSPP.setupService();
            bluetoothSPP.startService(BluetoothState.DEVICE_OTHER);
            bluetoothSPP.connect(data);
            //  new BluetoothConnection(bluetoothSPP, getFilesDir() + "/TimeFormat/" + "format.time",clockFormat,buttonLock0,buttonLock1,statusImage,statusText) is the constructor call
            bluetoothSPP.setBluetoothConnectionListener(
                    new BluetoothConnection(bluetoothSPP, getFilesDir() + "/TimeFormat/" + "format.time",buttonLock0,buttonLock1,statusImage,statusText,autoConfigBtn));
            autoConfigBtn.setEnabled(true);
        }catch (Exception e){
            e.printStackTrace();
            bluetoothSPP.disconnect();
            POWER_STATE=false;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode!= Activity.RESULT_OK ){
            Toast.makeText(getApplicationContext(),"Try Connecting Again",Toast.LENGTH_SHORT).show();
        }else{
            if(requestCode==BluetoothState.REQUEST_CONNECT_DEVICE){
                //now use the power btn
                power_control(data);
            }
        }
    }






    public void Power_Btn(View view) {
        try {
            BluetoothAdapter bluetoothAdapter=BluetoothAdapter.getDefaultAdapter();
            if(!bluetoothAdapter.isEnabled()){
                startActivity(new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE));
            }else {
                if ( !POWER_STATE ){
                    startActivityForResult(new Intent(this, DeviceList.class), BluetoothState.REQUEST_CONNECT_DEVICE);
                } else {
                    //disconnect from the device
                    try {
                        Snackbar.make(view, (bluetoothSPP.getConnectedDeviceAddress() == null ? "No Services check Bluetooth module" : "Disconnected From " + bluetoothSPP.getConnectedDeviceAddress()), Snackbar.LENGTH_LONG).show();
                        bluetoothSPP.disconnect();
                        //device turned on
                        POWER_STATE = false;
                        bluetoothSPP = null;
                    } catch (Exception e) {
                        e.printStackTrace();
                        Snackbar.make(view, "No Devices are connected yet !", Snackbar.LENGTH_LONG).show();

                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void Auto_Config_Btn(View view) {

        try{

            NOW_HOURS=
            new Settings().read_format(getFilesDir() + "/TimeFormat/" + "format.time").equals("AM/PM")
            ?
            new Date().getHours() % 12  //separate the clock into 2 halves till 12 O,clock morning am & 12 O,clock midnight pm
                                        //limiting the values & looping them starting from 1 till 24 % 12 =00 :-))
            :
            new Date().getHours();

            NOW_MINUTES=new Date().getMinutes();
            NOW_SECONDS=new Date().getSeconds();
            time=NOW_HOURS*10000+NOW_MINUTES*100+NOW_SECONDS;

            adjustTime(time+"");
        }catch (Exception e){
            e.printStackTrace();
            Snackbar.make(view, "No Devices are connected yet !", Snackbar.LENGTH_LONG).show();
        }
    }

    public void Manual_Config_Btn(View view) {
        try{
            if(!POWER_STATE){
                buttonLock0.setImageResource(R.drawable.ic_lock_outline_black_24dp);
                buttonLock1.setImageResource(R.drawable.ic_lock_outline_black_24dp);
                Snackbar.make(view, "No Devices are connected yet !", Snackbar.LENGTH_LONG).show();
            }else{
                if(!ENABLE_EDITING_TIME){
                    ENABLE_EDITING_TIME = true;
                    buttonLock0.setImageResource(R.drawable.open_lock);
                    buttonLock1.setImageResource(R.drawable.open_lock);
                    Snackbar.make(view, "Editing is Enabled", Snackbar.LENGTH_LONG).show();
                }else{
                    ENABLE_EDITING_TIME=false;
                    buttonLock0.setImageResource(R.drawable.ic_lock_outline_black_24dp);
                    buttonLock1.setImageResource(R.drawable.ic_lock_outline_black_24dp);
                    Snackbar.make(view, "Editing is Disabled", Snackbar.LENGTH_LONG).show();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            Snackbar.make(view, "No Devices are connected yet !", Snackbar.LENGTH_LONG).show();
        }
    }


    public void adjustTime(String message){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        try {

            progressDialog.setTitle("Please Wait");
            progressDialog.setMessage("Adjusting Time");
            progressDialog.setCancelable(false);
            progressDialog.show();
            bluetoothSPP.send(message + "", false);
            new CountDownTimer(500, 150) {
                @Override
                public void onTick(long millisUntilFinished) {
                }

                @Override
                public void onFinish() {
                    Toast.makeText(getApplicationContext(), "AutoConfigured to " + NOW_HOURS + ":" + NOW_MINUTES + ":" + NOW_SECONDS, Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                }
            }.start();
        }catch (Exception e){
            e.printStackTrace();
            progressDialog.dismiss();
        }
    }
    public void Edit_Time(View view) {

        try {
                if ( !ENABLE_EDITING_TIME ){
                    Snackbar.make(view, "Please Enable the Editing First", Snackbar.LENGTH_LONG).show();
                } else {
                    //do something
                    switch (String.valueOf(view.getTag())) {
                        case "UP_HOURS":

                            if(NOW_HOURS< (new Settings().read_format(getFilesDir() + "/TimeFormat/" + "format.time").equals("military") ? 24 :12)){
                                //convert into integers
                                ++NOW_HOURS;
                                time=NOW_HOURS*10000+NOW_MINUTES*100+NOW_SECONDS;
                                adjustTime(time+"");
                                Snackbar.make(view, "Hours now is " + NOW_HOURS, Snackbar.LENGTH_SHORT).show();
                            }else{
                                Snackbar.make(view, "Max HRS Reached", Snackbar.LENGTH_SHORT).show();
                            }
                            break;
                        case "DOWN_HOURS":
                            if(NOW_HOURS>0){
                                //convert into integers
                                --NOW_HOURS;
                                time=NOW_HOURS*10000+NOW_MINUTES*100+NOW_SECONDS;
                                adjustTime(time+"");
                                Snackbar.make(view, "Hours now is " + NOW_HOURS, Snackbar.LENGTH_SHORT).show();
                            }else{
                                Snackbar.make(view, "Min HRS Reached", Snackbar.LENGTH_SHORT).show();
                            }
                            break;
                        case "UP_MINUTES":

                            if(NOW_MINUTES<60){
                                //convert into integers
                                ++NOW_MINUTES;
                                time=NOW_HOURS*10000+NOW_MINUTES*100+NOW_SECONDS;
                                adjustTime(time+"");
                                Snackbar.make(view, "Minutes now is " + NOW_MINUTES, Snackbar.LENGTH_SHORT).show();
                            }else{
                                Snackbar.make(view, "Max MINS Reached", Snackbar.LENGTH_SHORT).show();
                            }


                            break;
                        case "DOWN_MINUTES":

                            if(NOW_MINUTES>0){
                                //convert into integers
                                --NOW_MINUTES;
                                time=NOW_HOURS*10000+NOW_MINUTES*100+NOW_SECONDS;
                                adjustTime(time+"");
                                Snackbar.make(view, "Minutes now is " + NOW_MINUTES, Snackbar.LENGTH_SHORT).show();
                            }else{
                                Snackbar.make(view, "Min MINS Reached", Snackbar.LENGTH_SHORT).show();

                            }
                            break;
                    }
                }

        } catch (Exception e) {
            e.printStackTrace();
            Snackbar.make(view, "No Devices are connected yet !", Snackbar.LENGTH_LONG).show();
                bluetoothSPP=null;
        }
    }



    public void open_dashboard(View view) {
        //Dialog builder & its layout Inflater & its Listener
        AlertDialog.Builder builder =
                new AlertDialog.Builder(this);
        final LayoutInflater inflater = this.getLayoutInflater();
        //side_bar_menu XML layout code inflated
        @SuppressLint("InflateParams")
        final View layout = inflater.inflate(R.layout.dashboard, null);
        //setting the layout to the view of the AlertDialog
        builder.setView(layout);

        Button btnOpenBinary=layout.findViewById(R.id.binaryGameOpener);
        Button btnOpenGuide=layout.findViewById(R.id.openGuide);
        Button btnOpenAlarm=layout.findViewById(R.id.openAlarm);

        Button openSettings=layout.findViewById(R.id.settingsbtn);
        Button about=layout.findViewById(R.id.about);

        btnOpenBinary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    bluetoothSPP.send("300000",false);
                    startActivity(new Intent(getApplicationContext(),Binary_Game.class));
                }catch (Exception e){
                    e.printStackTrace();
                    Snackbar.make(layout, "No Devices are connected yet !", Snackbar.LENGTH_LONG).show();
                }
            }
        });

        btnOpenGuide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Guide_Activity.class));
            }
        });
        btnOpenAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    bluetoothSPP.send(500000 + "", false);
                    alarm_dialog();
                }catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),"Please ensure your device is connected",Toast.LENGTH_LONG).show();

                }
            }
        });

        openSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Settings.class));
            }
        });

        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),aboutActivity.class));
            }
        });
            //by doing this , you can cancel that dialog by pressing on the blacklight space outside its Design borders
            builder.setCancelable(true);
            //create it
            d = builder.create();
            //Dialog background ,by doing this you are making the background as a blacklight space(The Versa is the dimmed background)
//            d.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            //Dialog gravity
            Objects.requireNonNull(d.getWindow()).setGravity(Gravity.BOTTOM);
            //dialog animtion
            //show that button w/ an animation
            d.show();
    }
     AlertDialog alarm_dialog = null;

    public void alarm_dialog(){

        //Dialog builder & its layout Inflater & its Listener
        AlertDialog.Builder builder =
                new AlertDialog.Builder(this);
        final LayoutInflater inflater = this.getLayoutInflater();
        //side_bar_menu XML layout code inflated
        @SuppressLint("InflateParams")
        final View layout = inflater.inflate(R.layout.activity_alarm, null);
        //setting the layout to the view of the AlertDialog
        builder.setView(layout);

        final TimePicker timePicker=layout.findViewById(R.id.timePicker);
        Button set_alarm=layout.findViewById(R.id.setAlarm);
        Button cancel_alarm=layout.findViewById(R.id.cancelAlarm);

        timePicker.setIs24HourView(new Settings().read_format(getFilesDir() + "/TimeFormat/" + "format.time").equals("military"));


        set_alarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    send_alarm_data(timePicker);
            }
        });

        cancel_alarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alarm_dialog.dismiss();
                new CountDownTimer(2000, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) { }
                    @Override
                    public void onFinish() {
                        try {
                            bluetoothSPP.send(500500 + "", false);
                        }catch (Exception e){
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(),"Please ensure your device is connected",Toast.LENGTH_LONG).show();
                        }
                       this.cancel();
                    }
                }.start();
            }
        });




        //by doing this , you can cancel that dialog by pressing on the blacklight space outside its Design borders
        builder.setCancelable(false);
        //create it
         alarm_dialog = builder.create();
        //Dialog background ,by doing this you are making the background as a blacklight space(The Versa is the dimmed background)
//            d.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        //Dialog gravity
        Objects.requireNonNull(alarm_dialog.getWindow()).setGravity(Gravity.CENTER);
        //dialog animtion
        //show that button w/ an animation
        alarm_dialog.show();
    }

    private void send_alarm_data(final TimePicker timePicker) {

        final ProgressDialog pg = new ProgressDialog(this);
        pg.setMessage("Please wait");
        pg.setCancelable(false);
        pg.setTitle("Sending data to the device");
        pg.show();
        new CountDownTimer(2000,1000) {
            @Override
            public void onTick(long millisUntilFinished) { }
            @Override
            public void onFinish() {
                try {
                    if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.M ){
                        bluetoothSPP.send((timePicker.getHour() * 10000 + timePicker.getMinute() * 100) +"", false);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),"Please ensure your device is connected",Toast.LENGTH_LONG).show();
                }
                    this.cancel();
            }
        }.start();

        new CountDownTimer(4000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                try {
                    bluetoothSPP.send(500500 + "", false);
                }catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),"Please ensure your device is connected",Toast.LENGTH_LONG).show();
                }
                alarm_dialog.dismiss();
                this.cancel();
                pg.dismiss();

            }
        }.start();
    }
}