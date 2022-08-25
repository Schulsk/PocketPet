package basic;

import java.util.HashMap;

public class Adult extends Pet{

    public Adult(long currentTime, String parentName){
        super(currentTime, parentName);
    }

    public Adult(HashMap<String, String> stats){
        super(stats);
    }

    @Override
    public Pet evolve() {
        die();
        return this;
    }

    // I'm putting all the choices of eggs in here so it's all in one place, in case it needs to be
    // changed at some point
    @Override
    public void layEgg(){
        Egg temp = null;
        if (getType().equals("Beaky")){
            temp = new NormalEgg(lastTimeCheck, getName());
        }
        else{
            temp = new NormalEgg(lastTimeCheck, getName());
        }
        temp.onCreate();
        Saver.savePet(temp);
        egg = temp.getSavefileName();
        children[0] = egg;
    }

    @Override
    protected void checkAge(){
        if (age > babyTime + childTime + adultTime){
            evolving = true;
        }
    }
}
