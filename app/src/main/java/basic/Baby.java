package basic;

import java.util.HashMap;

public class Baby extends Pet{
    public Baby(long currentTime, String parentName){
        super(currentTime, parentName);
    }

    public Baby(HashMap<String, String> stats){
        super(stats);
    }

    @Override
    public boolean checkForEggLaying(){
        return false;
    }

    @Override
    protected void checkAge(){
        if (age > babyTime){
            evolving = true;
        }
    }
}
