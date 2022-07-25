package basic;

import java.io.File;
import java.util.Scanner;
import java.util.HashMap;

public class Loader extends General{

    public static Pet loadPet(String savefileName){
        Pet loaded = null;
        File file = new File(petSavefileDirectory + savefileName);
        Scanner scanner = null;
        try{
            scanner = new Scanner(file);
        }
        catch(Exception e){
            System.out.println(e);
            return null;
        }

        HashMap<String, String> stats = new HashMap<>();
        try{
            // Read file and put into stats
            while (scanner.hasNext()){
                String[] line = scanner.nextLine().split(" ");
                stats.put(line[0], line[1]);
            }
        }
        catch(Exception e){
            System.out.println(e);
            System.out.println("Error when reading file and splitting lines");
            scanner.close();
            return null;
        }
        scanner.close();

        // Make the pet
        if (stats.get("type").equals("TestPet01")){
            loaded = new TestPet01(stats);
        }
        else if (stats.get("type").equals("TestEgg01")){
            loaded = new TestEgg01(stats);
        }

        return loaded;
    }
}
