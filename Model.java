
import java.io.File;
import java.util.Scanner;
import java.io.PrintWriter;
import java.util.HashMap;

class Model{
    // petCounter used in the save file system
    private int petCounter;

    private File savefile = new File("savefiles/savefile.txt");
    private Pet pet;
    private String petSaveFilename = "petSave.txt";
    //private String inventorySaveFilename = "inventorySavefile.txt";
    private long time;

    // Inventory
    private Inventory inventory;

    public Model(){
        Scanner scanner = null;
        try{
            scanner = new Scanner(savefile);
        }
        catch(Exception e){
            System.out.println("The savefile could not be found");
            System.exit(1);
        }

        Pet.setPetCount(Integer.parseInt(scanner.nextLine()));
        petSaveFilename = scanner.nextLine();

        scanner.close();

        pet = null;
        time = System.currentTimeMillis();
    }

    public void update(){
        time = System.currentTimeMillis();
        if (pet != null){
            pet.update(time);
            if (pet.hasEgg()){
                inventory.put(pet.removeEgg());
            }
        }
    }

    // Saving and loading
    public boolean saveData(){
        if (!inventory.save()){
            return false;
        }
        // PrintWriter writer = null;
        // try{
        //     writer = new PrintWriter(savefile);
        // }
        // catch (Exception e){
        //     return false;
        // }
        if (!savePet(pet)){
            return false;
        }

        PrintWriter writer = null;
        try{
            writer = new PrintWriter(savefile);
        }
        catch(Exception e){
            System.out.println(e);
            return false;
        }
        writer.println(Pet.getPetCount());
        if (pet != null){
            writer.println(pet.getSavefileName());
        }
        else{
            writer.println("null");
        }

        writer.close();

        return true;
    }

    public boolean loadData(){
        inventory = Inventory.load();
        if (inventory == null){
            return false;
        }

        if (!loadPet()){
            return false;
        }

        return true;
    }


    // Pet saving and loading
    public boolean savePet(){
        if (pet == null){
            System.out.println("No pet to save");
            return false;
        }
        petSaveFilename = petCounter + "_" + pet.getName() + ".txt";
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
        writer.close();

        return true;
    }
    // Overload to save any pet
    // working here ------------------------------------------------------------------------------
    //trying to get inventory and eggs to work properly
    public boolean savePet(Pet pet){
        if (pet == null){
            System.out.println("No pet to save");
            return false;
        }
        return pet.save();
    }

    public boolean loadPet(){
        File file = new File("savefiles/pets/" + petSaveFilename);
        Scanner scanner = null;

        try{
            scanner = new Scanner(file);
        }
        catch(Exception e){
            // FileNotFoundException
            return false;
        }

        HashMap<String, String> stats = new HashMap<>();

        try{
            // An attempt to make a loopable readable file with variable names and data
            String line = "";
            String[] parts;
            while (scanner.hasNext()){
                line = scanner.nextLine();
                parts = line.split(" ");
                stats.put(parts[0], parts[1]);

                // This part is in case I want to have several children
                //Todo

            }
        }
        catch(Exception e){
            System.out.println("Couldn't get the next line in reading the " + file + " file.");
            scanner.close();
            return false;
        }

        scanner.close();

        if (! makePet(stats)){
            System.out.println("Couldn't make pet of type " + stats.get("type"));
            return false;
        }

        return true;
    }


    private boolean makePet(HashMap<String, String> stats){
        if (stats.get("type").equals("TestPet01")){
            pet = new TestPet01(stats);
            pet.setModel(this);
            return true;
        }
        return false;
    }


    // Inventory
    // Handling
    public boolean putIntoInventory(Egg egg){
        if (inventory.hasRoom()){

            return true;
        }
        return false;
    }

    public Egg takeFromInventory(String name){

        return null;
    }

    public void inspect(){
        // Loads the eggs (and pets?) in memory from files and lets you see their data

    }

    // saving and loading
    private boolean saveInventory(){
        // Trying to figure out how to do this.

        return false;
    }

    private boolean loadInventory(){

        return false;
    }

    public Inventory getInventory(){
        return inventory;
    }


    // Pet handling
    public void renamePet(String newName){
        // Gotta delete the old pet-savefile and make a new with the new name after this is done
        File oldPetSavefile = new File(pet.getSavefileName());

        pet.setName(newName);

        File newPetSavefile = new File(pet.getSavefileName());
        savePet(pet);
        oldPetSavefile.delete();
    }




    // Get and set methods

    public Pet getPet(){
        return pet;
    }

    public long getTime(){
        return time;
    }
}
