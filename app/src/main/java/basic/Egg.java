package basic;

import java.util.HashMap;

public abstract class Egg extends Pet{

    long timeIncubated;
    private boolean incubating = true;

    public Egg(long currentTime, String parent){
        super(currentTime, parent);
        eggBirthtime = currentTime;
        timeIncubated = 0;
    }

    public Egg(HashMap<String, String> stats){
        super(stats);
        timeIncubated = Long.parseLong(stats.get("timeIncubated"));
    }

    // By overriding this method I get to add some functionality like incubation and other permanent stats
    // without carrying it over into the other subclasses of Pet where they will not be used
    @Override
    public String getSaveFormat(){
        String string = super.getSaveFormat();

        string += "\n" + "timeIncubated " + timeIncubated;
        return string;
    }

    @Override
    protected void checkHunger(long currentTime) {
        // Do nothing. Eggs don't eat.
    }

    @Override
    public boolean checkForEggLaying(){
        // Do nothing. Eggs don't lay eggs.
        return false;
    }

    @Override
    public Pet evolve(){
        birthtime = lastTimeCheck;
        return super.evolve();
    }

    @Override
    protected void calculateAge(long currentTime){
        age = currentTime - eggBirthtime;
        incubate(currentTime);
    }

    @Override
    protected void checkAge(){
        // use incubation time to evolve
        if (timeIncubated >= eggTime){
            evolving = true;
        }
    }

    private void incubate(long currentTime){
        if (incubating){
            timeIncubated += currentTime - lastTimeCheck;
        }
    }

    /*
    public void setStartedIncubating(long time){
        startedIncubating = time;
    }
     */
}
