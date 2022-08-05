package com.example.pocketpet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.nio.file.Files;

import basic.Controller;
import basic.General;
import basic.Saver;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("Got started");
        setContentView(R.layout.activity_main);

        setAllSavePaths();

        Button playButton = findViewById(R.id.play_button);
        Button familyTreeButton = findViewById(R.id.family_tree);
        Button quitButton = findViewById(R.id.quit_button);     // I'll probably get rid of this button

        playButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                goToPlay();
            }
        });

        quitButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                finish();
                quit();
            }
        });


    }

    private void goToPlay(){
        Intent intent = new Intent(this, PlayActivity.class);
        startActivity(intent);
    }

    private void quit(){
        System.exit(0);
    }

    private void setAllSavePaths(){
        String contextPath = getApplicationContext().getFilesDir().getPath();
        General.setPetSavefileDirectory(contextPath + "/savefiles/pets/");
        General.setInventorySavefileDirectory(contextPath + "/savefiles/");
        General.setStateSavefileDirectory(contextPath + "/savefiles/");
    }
}