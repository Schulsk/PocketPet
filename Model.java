
import java.io.File;
import java.util.Scanner;
import java.io.PrintWriter;
import java.util.HashMap;

class Model{
    private Pet pet;
    private String petSaveFilename = "petSave.txt";
    private long time;

    public Model(){
        pet = null;
        time = System.currentTimeMillis();
    }

    public void update(){
        time = System.currentTimeMillis();
        if (pet != null){
            pet.update();
        }
    }


    // Pet saving and loading
    /*
        The petSave.txt file should follow the following structure:
        type
        name
        age
        hunger
    */
    public boolean savePet(){
        File file = new File(petSaveFilename);
        PrintWriter writer = null;

        try{
            writer = new PrintWriter(file);
        }
        catch(Exception e){
            // FileNotFoundException
            return false;
        }

        writer.print(pet.getSaveFormat());
        writer.flush();

        return true;
    }

    public boolean loadPet(){
        File file = new File(petSaveFilename);
        Scanner scanner = null;

        try{
            scanner = new Scanner(file);
        }
        catch(Exception e){
            // FileNotFoundException
            return false;
        }

        HashMap<String, Object> stats = new HashMap<>();

        try{
            /*
            TODO:
            I think I wanna make this saving system a little different
            I might make it so that each line starts with the name of the
            variable, then the value. Then I can just make the loading happen
            with a simple loop.
            Pros: changes will no longer have to be made here when I add more
            variables
            */
            stats.put("type", scanner.nextLine());
            stats.put("name", scanner.nextLine());
            stats.put("alive", Boolean.parseBoolean(scanner.nextLine()));
            stats.put("birthtime", Long.parseLong(scanner.nextLine()));
            stats.put("deathtime", Long.parseLong(scanner.nextLine()));
            stats.put("lastTimeCheck", Long.parseLong(scanner.nextLine()));
            stats.put("hunger", Float.parseFloat(scanner.nextLine()));
            stats.put("lastFed", Long.parseLong(scanner.nextLine()));
            stats.put("totalTimeStarving", Long.parseLong(scanner.nextLine()));
            stats.put("totalTimeFull", Long.parseLong(scanner.nextLine()));
        }
        catch(Exception e){
            System.out.println("Couldn't get the next line in reading the " + file + " file.");
            return false;
        }

        if (! makePet(stats)){
            System.out.println("Couldn't make pet of type " + stats.get("type"));
            return false;
        }

        return true;
    }

    private boolean makePet(String type, String name, long birthtime, float hunger){
        if (type.equals("TestPet01")){
            pet = new TestPet01(name, birthtime, hunger);
            pet.setModel(this);
            return true;
        }
        return false;
    }

    private boolean makePet(HashMap<String, Object> stats){
        if (stats.get("type").equals("TestPet01")){
            pet = new TestPet01(stats);
            pet.setModel(this);
            return true;
        }
        return false;
    }


    // Get and set methods

    public Pet getPet(){
        return pet;
    }

    public long getTime(){
        return time;
    }
}
