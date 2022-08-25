package basic;

import java.util.HashMap;

public class Child extends Pet{

    public Child(long currentTime, String parentName){
        super(currentTime, parentName);
    }

    public Child(HashMap<String, String> stats){
        super(stats);
    }

    @Override
    public boolean checkForEggLaying(){
        return false;
    }

    @Override
    protected void checkAge(){
        if (age > babyTime + childTime){
            evolving = true;
        }
    }
}
