
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

        pet = Pet.load(petSaveFilename);
        if (pet == null){
            System.out.println("Couldn't load pet");
            return false;
        }

        return true;
    }


    // Pet saving and loading
    public boolean savePet(Pet pet){
        if (pet == null){
            System.out.println("No pet to save");
            return false;
        }
        return pet.save();
    }

    public Pet loadPet(String SavefileName){
        pet = Pet.load(SavefileName);
        return pet;
    }

    public void unloadPet(){
        savePet(pet);
        pet = null;
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

    public Inventory getInventory(){
        return inventory;
    }


    // Pet handling
    public void renamePet(String newName){
        // Gotta delete the old pet-savefile and make a new with the new name after this is done
        File oldPetSavefile = new File(pet.getFullSavePath());

        pet.setName(newName);

        //File newPetSavefile = new File(pet.getSavefileName());
        savePet(pet);
        oldPetSavefile.delete();
    }

    // When this works I need to do seperate methods so the system can unload
    // pet, and the nload pet from memory without craching when no pet is loaded
    public boolean changePet(String newPetSavefileName){
        Pet newPet = Pet.load(newPetSavefileName);
        if (newPet == null){
            return false;
        }
        savePet(pet);
        pet = newPet;
        return true;
    }




    // Get and set methods

    public Pet getPet(){
        return pet;
    }

    public long getTime(){
        return time;
    }
}
