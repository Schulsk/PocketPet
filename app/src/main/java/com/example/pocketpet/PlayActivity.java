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

import ViewUpdate.ViewUpdate;
import basic.Pet;
import basic.Saver;
import basic.TestPet01;

public class PlayActivity extends AppCompatActivity {

    AnimationDrawable petAnimation;
    Controller controller;

    Thread viewUpdate;

    /**     Handle all the activity lifecycle things        */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        setAllSavePaths();
        controller = new Controller();

        ImageView petImageView = findViewById(R.id.imageView2);
        petImageView.setBackgroundResource(R.drawable.empty_animation);
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
                clickFeed();
            }
        });

        Button changeMoodButton = findViewById(R.id.change_mood_button);
        changeMoodButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Pet pet = controller.getPet();
                System.out.println("Pet: " + pet);
                if (pet != null){
                    if (!pet.getState().equals("happy")){
                        pet.setState("happy");
                        System.out.println("happy");
                    }
                    else if (!pet.getState().equals("idle")){
                        pet.setState("idle");
                        System.out.println("idle");
                    }
                }
            }
        });

        Button newPetButton = findViewById(R.id.new_pet_button);
        newPetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Pet newPet = new TestPet01(controller.getTime(), "null");
                System.out.println(newPet.getSavefileName());
                Saver.savePet(newPet);
                controller.loadPetSlot(newPet.getSavefileName());
            }
        });

        // Make and start the ViewUpdate thread
        viewUpdate = new Thread(new ViewUpdate(petImageView, controller.getModel(), this));
        viewUpdate.start();
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
        controller.loadState();
        if (petAnimation != null){
            petAnimation.start();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        // todo save the state
        controller.saveState();

        // Stop the animation
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


    /**     Buttons     */
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

    /**     Updating views      */
    // I'm doing it this way because of an error I got that said only the thread that made a view
    // hierarchy could touch its views
    // Update: Unecesary it seems, but it's here in case I change my mind
    public void updatePetView(int resource){
        ImageView petView = findViewById(R.id.imageView2);
        petView.setBackgroundResource(resource);
        petAnimation = (AnimationDrawable) petView.getBackground();
        petAnimation.start();
    }
}