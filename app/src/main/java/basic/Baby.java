package basic;

import java.util.HashMap;

public class Baby extends Pet{
    public Baby(long currentTime, String parentName){
        super(currentTime, parentName);
    }

    public Baby(HashMap<String, String> stats){
        super(stats);

    }
}
