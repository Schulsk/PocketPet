
import java.util.HashMap;


class TestPet01 extends Pet{

    public TestPet01(long currentTime, String parent){
        super(currentTime, parent);
        setType("TestPet01");
        maxAge = 500;
    }

    public TestPet01(HashMap<String, String> stats){
        super(stats);
        setType("TestPet01");
        maxAge = 500;
    }
}
