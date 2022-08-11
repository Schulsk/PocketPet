package basic;

import java.util.Random;

public class Evolver {

    public static Pet evolve(Pet oldPet){
        // Uses the method getSaveFormat(), converts it to a stats Hashmap, and then makes use of
        // the Pet constructor Pet(HashMap<String, String> stats) to make the new evolved version
        Pet newPet = null;

        switch (oldPet.getType()){
            case "TestEgg01":
                double random = new Random().nextDouble();
                if (random < 0.5){
                    newPet = new TestPet01(oldPet.getStats());
                }
                else{
                    newPet = new TestPet02(oldPet.getStats());
                }
                break;
        }
        return newPet;
    }
}
