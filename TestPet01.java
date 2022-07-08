
import java.util.HashMap;


class TestPet01 extends Pet{


    public TestPet01(String name, long birthtime, float hunger){
        super(name, birthtime, hunger);
        setType("TestPet01");
    }
    public TestPet01(HashMap<String, Object> stats){
        super(stats);
        setType("TestPet01");
        maxAge = 500;
    }
}
