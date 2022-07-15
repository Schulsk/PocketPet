
import java.util.HashMap;

abstract class Egg extends Pet{

    public Egg(long currentTime, String parent){
        super(currentTime, parent);
    }

    public Egg(HashMap<String, String> stats){
        super(stats);
    }


}
