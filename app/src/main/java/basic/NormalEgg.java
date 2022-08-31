package basic;

import java.util.HashMap;

public class NormalEgg extends Egg{
    public NormalEgg(long currentTime, String parentName){
        super(currentTime, parentName);
        setType("NormalEgg");
    }

    public NormalEgg(HashMap<String, String> stats) {
        super(stats);
        setType("NormalEgg");
    }

    @Override
    protected void setValuesNotInFile(){
        super.setValuesNotInFile();
        maxAge = TimeConverter.hoursToMillis(1);
    }

    @Override
    public void onCreate(){
        eggTime = TimeConverter.secondsToMillis(10);
        babyTime = TimeConverter.secondsToMillis(10);
        childTime = TimeConverter.secondsToMillis(10);
        adultTime = TimeConverter.secondsToMillis(10);
    }
}
