package basic;

import java.util.HashMap;

class TestEgg01 extends Egg{

    public TestEgg01(long currentTime, String parent){
        super(currentTime, parent);
        setType("TestEgg01");
    }

    public TestEgg01(HashMap<String, String> stats){
        super(stats);
        setType("TestEgg01");
    }

}
