package ViewUpdate;

import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;

import com.example.pocketpet.PlayActivity;
import com.example.pocketpet.R;

import java.lang.Runnable;

import basic.Beaky;
import basic.BeakyBaby;
import basic.BeakyChild;
import basic.Egg;
import basic.Pet;
import basic.Model;

public class ViewUpdate implements Runnable{

    ImageView view;
    boolean active;
    Pet pet;
    Model model;
    String lastPetState;
    String lastPetType;
    PlayActivity activity;


    public ViewUpdate(ImageView view, Model model, PlayActivity activity){
        this.activity = activity;   // This might go if I decide I definitely don't need it
        this.view = view;
        this.model = model;
        this.pet = model.getPet();
        active = true;
        // Bad implementation, but I can't figure anything else out right now
        lastPetState = "";
        lastPetType = "";
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
                String petType = pet.getType();
                AnimationDrawable currentAnimation = (AnimationDrawable) view.getBackground();

                if (shouldUpdate()){
                    resource = pet.getAnimation(petState);
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
                lastPetType = petType;
            }

        }
        // Thread done
    }

    private boolean shouldUpdate(){
        if (lastPetState != pet.getState()){
            return true;
        }
        if (lastPetType != pet.getType()){
            return true;
        }

        return false;
    }
}
