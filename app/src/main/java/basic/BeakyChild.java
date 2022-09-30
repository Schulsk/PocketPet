package basic;

import java.util.HashMap;

public class BeakyChild extends Child{
    public BeakyChild(long currentTime, String parentName){
        super(currentTime, parentName);
        setType("BeakyChild");
    }

    public BeakyChild(HashMap<String, String> stats) {
        super(stats);
        setType("BeakyChild");
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
