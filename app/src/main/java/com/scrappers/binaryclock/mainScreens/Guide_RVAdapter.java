package com.scrappers.binaryclock.mainScreens;

import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.VideoView;

import com.scrappers.binaryclock.R;

import org.sufficientlysecure.htmltextview.HtmlTextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

public class Guide_RVAdapter extends RecyclerView.Adapter<Guide_RVAdapter.DesignHolder> {


    private final AppCompatActivity context;
    private ArrayList<String> text;
    private ArrayList<Drawable> images;
    private ArrayList<Uri> vidUri;

    Guide_RVAdapter(AppCompatActivity context, ArrayList<String> text, ArrayList<Drawable> images, ArrayList<Uri> vidUri){
        this.context=context;
        this.text=text;
        this.images=images;
        this.vidUri=vidUri;
    }

    @NonNull
    @Override
    public DesignHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DesignHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.guide_rvadapter, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull DesignHolder holder, int position) {

     try {
         holder.dataText.setHtml(text.get(position));
     }catch (Exception e){
         e.printStackTrace();
         LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0, 0);
         holder.dataText.setLayoutParams(layoutParams);
         holder.dataText.setVisibility(View.INVISIBLE);
     }

     try {
         holder.illustrator.setImageDrawable(images.get(position));
     }catch (Exception e){
         e.printStackTrace();
         LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0, 0);
         holder.illustrator.setLayoutParams(layoutParams);
         holder.illustrator.setVisibility(View.INVISIBLE);
     }

                //video attributes

        try {
                //Creating MediaController
            MediaController mediaController= new MediaController(context);
            mediaController.setAnchorView(holder.videoView);
            holder.videoView.setMediaController(mediaController);
            holder.videoView.setVideoURI(vidUri.get(position));
        }catch (Exception e){
            e.printStackTrace();
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0, 0);
            holder.videoView.setLayoutParams(layoutParams);
            holder.videoView.setVisibility(View.INVISIBLE);
        }

    }

    @Override
    public int getItemCount() {
        return text.size();
    }

    static class DesignHolder extends RecyclerView.ViewHolder {
        HtmlTextView dataText;
        ImageView illustrator;
        VideoView videoView;
        DesignHolder(@NonNull View itemView) {
            super(itemView);
            dataText=itemView.findViewById(R.id.data);
            illustrator=itemView.findViewById(R.id.illustrator);
            videoView=itemView.findViewById(R.id.videoView);
        }
    }
}

