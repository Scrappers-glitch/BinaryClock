package com.scrappers.binaryclock.blueToothSerialInterface;

import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.scrappers.binaryclock.App;
import com.scrappers.binaryclock.mainScreens.Remote_Activity;
import com.scrappers.binaryclock.mainScreens.Settings;

import app.akexorcist.bluetotohspp.library.BluetoothSPP;

import static com.scrappers.binaryclock.R.drawable.ic_lock_outline_black_24dp;
import static com.scrappers.binaryclock.R.drawable.signalconnected;
import static com.scrappers.binaryclock.R.drawable.signaloff;
import static com.scrappers.binaryclock.R.drawable.signalretry;

public class BluetoothConnection implements BluetoothSPP.BluetoothConnectionListener {

    //private : means it cannot be used outside this context
    private BluetoothSPP bluetoothSPP;
    private String timeFormat;
    private ImageView buttonLock0;
    private ImageView buttonLock1;
    private ImageView statusImage;
    private TextView statusText;
    private ImageButton autoConfigBtn;


    public BluetoothConnection(BluetoothSPP bluetoothSPP, String timeFormat, ImageView buttonLock0, ImageView buttonLock1, ImageView statusImage, TextView statusText, ImageButton autoConfigBtn){
       //default values in the formula --> General(Global) Object = Local Object
        this.bluetoothSPP=bluetoothSPP;
        this.timeFormat=timeFormat;
        this.buttonLock0=buttonLock0;
        this.buttonLock1=buttonLock1;
        this.statusImage=statusImage;
        this.statusText=statusText;
        this.autoConfigBtn=autoConfigBtn;
    }
    @Override
    public void onDeviceConnected(String name, String address) {
        statusImage.setImageResource(signalconnected);
        statusText.setText(bluetoothSPP.getConnectedDeviceAddress());
        //send the mode if its military or pm/am mode
        bluetoothSPP.send(
                (new Settings().read_format(timeFormat).equals("military")
                        ?
                        400500+""
                        :
                        400000+"")
                ,false);


        Toast.makeText(App.getContext(), "Connected to " + bluetoothSPP.getConnectedDeviceAddress(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onDeviceDisconnected() {
        statusImage.setImageResource(signaloff);
        statusText.setText("");
        Remote_Activity.POWER_STATE=false;
        Remote_Activity.ENABLE_EDITING_TIME=false;
        autoConfigBtn.setEnabled(false);
        buttonLock0.setImageResource(ic_lock_outline_black_24dp);
        buttonLock1.setImageResource(ic_lock_outline_black_24dp);
        Toast.makeText(App.getContext(),  "Service Disconnected", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onDeviceConnectionFailed() {
        statusImage.setImageResource(signalretry);
        statusText.setText("");
        Remote_Activity.POWER_STATE=false;
        Remote_Activity.ENABLE_EDITING_TIME=false;
        autoConfigBtn.setEnabled(false);
        buttonLock0.setImageResource(ic_lock_outline_black_24dp);
        buttonLock1.setImageResource(ic_lock_outline_black_24dp);
        Toast.makeText(App.getContext(), " Connection Failed" , Toast.LENGTH_LONG).show();
    }
}
