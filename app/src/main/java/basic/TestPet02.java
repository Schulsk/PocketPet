package basic;

import java.util.HashMap;

public class TestPet02 extends Pet{
    public TestPet02(long currentTime, String parent){
        super(currentTime, parent);
        setType("TestPet02");
    }

    public TestPet02(HashMap<String, String> stats){
        super(stats);
        setType("TestPet02");
    }
}
