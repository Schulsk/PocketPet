package basic;

import java.util.HashMap;
import java.util.Random;

public class Evolver {

    public static Pet evolve(Pet oldPet){
        // Uses the method getSaveFormat(), converts it to a stats Hashmap, and then makes use of
        // the Pet constructor Pet(HashMap<String, String> stats) to make the new evolved version
        Pet newPet = null;
        HashMap<String, String> stats = oldPet.getStats();

        switch (oldPet.getType()){
            case "TestEgg01":
                double random = new Random().nextDouble();
                if (random < 0.5){
                    newPet = new TestPet01(stats);
                }
                else{
                    newPet = new TestPet02(stats);
                }
                break;
            case "NormalEgg":
                newPet = new BeakyBaby(stats);
                break;
            case "BeakyBaby":
                newPet = new BeakyChild(stats);
                break;
            case "BeakyChild":
                newPet = new Beaky(stats);
                break;
        }
        newPet.onCreate();
        return newPet;
    }
}
