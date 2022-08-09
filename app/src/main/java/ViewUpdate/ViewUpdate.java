package ViewUpdate;

import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;

import com.example.pocketpet.PlayActivity;
import com.example.pocketpet.R;

import java.lang.Runnable;

import basic.Pet;
import basic.Model;

public class ViewUpdate implements Runnable{

    ImageView view;
    boolean active;
    Pet pet;
    Model model;
    String lastPetState;
    PlayActivity activity;


    public ViewUpdate(ImageView view, Model model, PlayActivity activity){
        this.activity = activity;   // This might go if I decide I definitely don't need it
        this.view = view;
        this.model = model;
        this.pet = model.getPet();
        active = true;
        // Bad implementation, but I can't figure anything else out right now
        lastPetState = "";
    }

    public void run(){

        while (active){
            pet = model.getPet();
            try{
                // Todo: here I could perhaps do a wait in stead of a sleep, and trigger the thread
                //  when the Update thread calls the update() method
                Thread.sleep(500);
            }
            catch(InterruptedException e){
                System.out.println(e);
                System.out.println("ViewUpdate thread interrupted while sleeping");
                active = false;
            }
            // Check pet status and set new animation
            // Todo: The following code needs refactoring
            if (pet != null){
                int resource = 0;
                String petState = pet.getState();
                AnimationDrawable currentAnimation = (AnimationDrawable) view.getBackground();

                if (lastPetState != petState){
                    if (petState.equals("idle")){
                        resource = R.drawable.beaky_idle_animation;
                        System.out.println("Pet state idle");
                    }
                    else if (petState.equals("angry")){
                        resource = R.drawable.beaky_angry_animation;
                    }
                    else if (petState.equals("sad")){
                        resource = R.drawable.beaky_sad_animation;
                    }
                    else if (petState.equals("happy")){
                        resource = R.drawable.beaky_happy_animation;
                        System.out.println("Pet state happy");
                    }
                    else if (petState.equals("walk")){
                        resource = R.drawable.beaky_walk_animation;
                    }
                    else if (petState.equals("dead")){
                        resource = R.drawable.beaky_dead_animation;
                    }
                    else if (petState.equals("eating")){
                        resource = R.drawable.beaky_walk_animation;
                    }
                }


                // Set the background and start the animation
                // If  resource is 0, the mood is not changed since last check
                if (resource != 0){
                    System.out.println("changing background");

                    // Trying this new thing
                    //activity.updatePetView(resource);

                    view.setBackgroundResource(resource);
                    currentAnimation = (AnimationDrawable) view.getBackground();
                    currentAnimation.start();
                }

                // Set lastState
                // lastState is used for checking if there is a need to change the animation
                lastPetState = petState;
            }

        }
        // Thread done
    }
}
