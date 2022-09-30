package basic;

import java.util.HashMap;

public class Beaky extends Adult{
    public Beaky(long currentTime, String parentName){
        super(currentTime, parentName);
        setType("Beaky");
    }

    public Beaky(HashMap<String, String> stats) {
        super(stats);
        setType("Beaky");
    }

    @Override
    public void onCreate(){
        super.onCreate();
        /*
        adultTime += TimeConverter.secondsToMillis(30);

         */
    }
}
