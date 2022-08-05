package com.example.pocketpet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import basic.Controller;
import basic.General;

public class PlayActivity extends AppCompatActivity {

    AnimationDrawable petAnimation;
    Controller controller;


    // Handle all the activity lifecycle things

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        setAllSavePaths();
        controller = new Controller();

        ImageView petImageView = findViewById(R.id.imageView2);
        petImageView.setBackgroundResource(R.drawable.beaky_idle_animation);
        petAnimation = (AnimationDrawable) petImageView.getBackground();

        // Buttons
        Button menuButton = findViewById(R.id.menu_button);
        menuButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                clickMenu();
            }
        });

        Button feedButton = findViewById(R.id.feed_button);
        feedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.feedPet();
            }
        });
    }

    @Override
    public void onStart(){
        super.onStart();
    }

    @Override
    public void onRestart() {
        super.onRestart();
    }

    @Override
    public void onResume() {
        super.onResume();
        petAnimation.start();
    }

    @Override
    public void onPause() {
        super.onPause();
        petAnimation.stop();
    }

    @Override
    public void onStop() {
        super.onStop();
        // Todo: Gotta stop the update thread here maybe? possibly in onPause()
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }



    // Buttons
    private void clickMenu(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void clickFeed(){
        controller.feedPet();
    }

    private void setAllSavePaths(){
        String contextPath = getApplicationContext().getFilesDir().getPath();
        General.setPetSavefileDirectory(contextPath + "/savefiles/pets/");
        General.setInventorySavefileDirectory(contextPath + "/savefiles/");
        General.setStateSavefileDirectory(contextPath + "/savefiles/");
    }

    // Hello testing testing
}