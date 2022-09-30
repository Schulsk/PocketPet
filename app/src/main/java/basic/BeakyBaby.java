package basic;

import java.util.HashMap;

public class BeakyBaby extends Baby{

    public BeakyBaby(long currentTime, String parentName){
        super(currentTime, parentName);
        setType("BeakyBaby");
    }

    public BeakyBaby(HashMap<String, String> stats) {
        super(stats);
        setType("BeakyBaby");
    }

    @Override
    public void onCreate(){
        super.onCreate();
        /*
        babyTime += TimeConverter.secondsToMillis(0);
        childTime += TimeConverter.secondsToMillis(15);
        adultTime += TimeConverter.secondsToMillis(30);

         */
    }
}
