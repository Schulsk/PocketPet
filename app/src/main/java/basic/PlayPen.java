package basic;

import java.util.ArrayList;

class PlayPen{

    private int width, height;
    private Pet pet;
    private ArrayList<Egg> eggs;

    public PlayPen(int width, int height){
        this.width = width;
        this.height = width;
    }

    // Update
    public void update(long currentTime){
        if (pet != null){
            pet.update(currentTime);
        }
    }

    public boolean outOfBounds(int x, int y){
        if (x < 0 || x > width){
            return true;
        }
        if (y < 0 || y > height){
            return true;
        }

        return false;
    }


    // Get and set
    public Pet getPet(){
        return pet;
    }

    public ArrayList<Egg> getEggs(){
        return eggs;
    }

    public Egg findEgg(String eggId){

        return null;
    }
}
