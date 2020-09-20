package com.scrappers.binaryclock.mainScreens;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import com.scrappers.binaryclock.R;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class Guide_Activity extends AppCompatActivity {
    private ArrayList<String> text=new ArrayList<>();

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        //lock screen orientation to portrait
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        apply();
    }

    public void apply(){
        ArrayList<Drawable> imageDrawable = new ArrayList<>();
        ArrayList<Uri> vidUri = new ArrayList<>();
        text.add("<h2>HI,</h2>" +
                "<h4> Welcome to our OPEN-SOURCE project </h4>" +
                "<br>This is a quick tour to take you through the Binary Clock.</br>" +
                "<br>Here you will learn how to read the binary clock & use our App to change the values of the clock.ie minutes & hours.</br>" +
                "<br><b><u>After you end up this tutorial: </u></b></br>" +
                "<br><ul>" +
                "<li>Check the app dashboard.</li>" +
                "<li>Check the Binary Game.</li>" +
                "<li>Press start game.</li>" +
                "<li>Try to rephrase binaries displayed on the clock in digital.</li>" +
                "<li>Train yourself on reading the binary clock through the binary game.</li>" +
                "</ul>" +
                "</br>");
        imageDrawable.add(getDrawable(R.drawable.launcher_ico));



        text.add("<h2>Let me first of all show you how the binary clock operates generally</h2>" +
                "<h4>First of all</h4>"
                +"<br>"+"Scrappers Binary clock consists of 4 rows and 6 columns ,Here all LEDs are black(turned off),If they are white,then they are up"+"</br>" +
                "<br><u><b>Rows</b></u></br>"+
                "<br>They represent numbers from 1 , 2 , 4 , 8 </br>" +
                "<br>If an LED lights up throughout those four rows , then the corresponding number (from the parallel scheme) " +
                "to that lightened LED is assigned to the TIME whether HH or MM or SS. </br> " +
                "<br>-</br>" +
                "<br><u><b>Columns</b></u></br>" +
                "<br>They represent the digital places in the digital clock HH:MM:SS for example 12:50:00 " +
                "which are 6 columns in total.</br>" +
                "<br>-</br>" +
                "<br><b>NB</b></br>" +
                "<br>-If two or three LEDs light up at the same moment,then their corresponding numbers are summed up & inserted into the right column whether the first column or the second one in HH or MM or SS.</br>"+
                "<br>-In HH columns the LED in 1st column , 3rd row represents the 12-hour system AM/PM.</br>"
        );
        imageDrawable.add(getDrawable(R.drawable.general_tutorial));



        text.add("<h2> Example 1 </h2>" +
                "<h4> Binary to digital converting in-mind </h4>"
                +"<br>"+"In front of you, an example for a time displayed in binaries on your binary clock."+"</br>" +
                "<br></br>"+
                "<br>Take a look at , try to identify the binary number displayed in this scheme " +
                "& try to link it to the digital section displayed underneath the scheme.</br> " +
                "<br></br>" +
                "<h4>HH Section</h4>"+
                "<br>*The first H bar in HH section has a white circle(LED ON) on the 1st row which corresponds to <b> 1 </b> in the scheme 1 , 2 , 4 , 8 described before.</br>" +
                "<br>*The second H bar in HH section has a white circle(LED ON) on the last row from down to up which corresponds to <b> 8 </b> .</br>" +
                "<h4>MM Section</h4>" +
                "<br>*The first M bar in MM section has no white circles(All LEDs are OFF) so it takes <b> 0 </b> .</br>" +
                "<br>*The second M bar in MM section has 2 white circles corresponding to 2 & 4 so we get the sum = 2 + 4 = <b>6</b> .</br> "
                +"<h4>SS Section</h4>" +
                "<br>The 1st S bar in SS section has 2 white circles corresponding to 1 & 4 so we get the sum= 1 + 4 = <b> 5 </b> .</br>" +
                "<br>The 2nd S bar in SS section shows 2 white circles corresponding to 1 & 8 so we get the sum= 1 + 8 = <b> 9 </b> .</br>" +
                "<h1>So,Time is 18:06:59</h1>"
        );
        imageDrawable.add(getDrawable(R.drawable.tutorial_1));



        text.add("<h2> Example 2 </h2>" +
                "<h4> Binary to digital converting in-mind </h4>"+
                "<h4>HH Section</h4>"+
                "<br>*The 1st H bar in HH section has a white circle(LED ON) on the 1st row which corresponds to <b> 1 </b> in the scheme 1 , 2 , 4 , 8 described before.</br>" +
                "<br>*The 2nd H bar in HH section has a white circle(LED ON) on the 1st row which corresponds to <b> 1 </b>.</br>" +
                "<h4>MM Section</h4>" +
                "<br>*The 1st M bar in MM section has a white circle corresponds to <b> 2 </b> .</br>" +
                "<br>*The 2nd M bar in MM section has 2 white circles corresponding to 1 & 2 so we get the sum = 1 + 2 = <b>3</b> .</br> "
                +"<h4>SS Section</h4>" +
                "<br>The 1st S bar in SS section has no white circles in place <b> 0 </b> .</br>" +
                "<br>The 2nd S bar in SS section shows 3 white circles corresponding to 1 , 2 & 4  so we get the sum= 1 + 2 + 4 = <b> 7 </b> .</br>" +
                "<h1>So,Time is 11:23:07</h1>"
        );
        imageDrawable.add(getDrawable(R.drawable.tutorial_2));


        text.add("<h2> Example 3 </h2>" +
                "<h4> Binary to digital converting in-mind </h4>"+
                "<h4>HH Section</h4>"+
                "<br>*The 1st H bar in HH section has a white circle corresponds to <b> 2 </b> in the scheme 1 , 2 , 4 , 8 described before.</br>" +
                "<br>*The 2nd H bar in HH section has a white circle(LED ON) on the 1st row which corresponds to <b> 1 </b>.</br>" +
                "<h4>MM Section</h4>" +
                "<br>*The 1st M bar in MM section has white circles correspond to 1 & 2 so sum = <b> 3 </b> .</br>" +
                "<br>*The 2nd M bar in MM section has a white circle corresponding to  <b>4</b> .</br> "
                +"<h4>SS Section</h4>" +
                "<br>The 1st S bar in SS section has no white circles in place <b> 0 </b> .</br>" +
                "<br>The 2nd S bar in SS section shows 2 white circles corresponding to 1 & 4  so we get the sum= 1 + 4 = <b> 5 </b> .</br>" +
                "<h1>So,Time is 21:34:05</h1>"
        );
        imageDrawable.add(getDrawable(R.drawable.tutorial_3));


        text.add("<h2> Example 4 </h2>" +
                "<h4> Binary to digital converting in-mind </h4>"+
                "<h4>HH Section</h4>"+
                "<br>*The first H bar in HH section has a white circle corresponds to <b> 2 </b> in the scheme 1 , 2 , 4 , 8 described before.</br>" +
                "<br>*The second H bar in HH section has  white circles corresponds to 1 & 2 sum=<b> 3 </b>.</br>" +
                "<h4>MM Section</h4>" +
                "<br>*The first M bar in MM section has white circles correspond to 1 & 4 so sum = <b> 5 </b> .</br>" +
                "<br>*The second M bar in MM section has white circles corresponding to 1 & 8 sum= <b>9</b> .</br> "
                +"<h4>SS Section</h4>" +
                "<br>The 1st S bar in SS section has a white circle corresponding to <b> 4 </b> .</br>" +
                "<br>The 2nd S bar in SS section shows 2 white circles corresponding to 1 & 2  so we get the sum= 1 + 2 = <b> 3 </b> .</br>" +
                "<h1>So,Time is 23:59:43</h1>" +
                "<h2>If you want to get the clock,check the about in the app dashboard.</h2>"+
                "<h4>Enjoy the Binaries :-))</h4>" +
                "<br></br>"+
                "<br></br>" +
                "<br></br>"

        );
        imageDrawable.add(getDrawable(R.drawable.tutorial_4));
//                vidUri.add(Uri.parse("android.resource://" + getPackageName() + "/"
//                + R.raw.lung));
        Guide_RVAdapter guide_rvAdapter=new Guide_RVAdapter(this,text, imageDrawable, vidUri);
        RecyclerView rv=findViewById(R.id.rv);
        rv.setAdapter(guide_rvAdapter);
        rv.setLayoutManager(new LinearLayoutManager(this));
    }
}
