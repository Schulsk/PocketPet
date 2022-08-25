package basic;

import java.util.HashMap;

public class NormalEgg extends Egg{
    public NormalEgg(long currentTime, String parentName){
        super(currentTime, parentName);
        setType("NormalEgg");
        eggTime = TimeConverter.secondsToMillis(30);
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
        babyTime = TimeConverter.secondsToMillis(30);
        childTime = TimeConverter.secondsToMillis(30);
        adultTime = TimeConverter.secondsToMillis(30);
    }
}
