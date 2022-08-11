package basic;

import java.util.HashMap;


public class TestPet01 extends Pet{

    public TestPet01(long currentTime, String parent){
        super(currentTime, parent);
        setType("TestPet01");
    }

    public TestPet01(HashMap<String, String> stats){
        super(stats);
        setType("TestPet01");
    }

    @Override
    public Pet evolve() {
        die();
        return this;
    }
}
