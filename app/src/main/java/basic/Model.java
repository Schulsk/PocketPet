package basic;

import java.io.File;
import java.util.Scanner;
import java.io.PrintWriter;
import java.util.HashMap;

public class Model{
    // petCounter used in the save file system
    private int petCounter;

    private File savefile = new File(General.getStateSavefileDirectory() + "savefile.txt");
    private File savefileDirectory = new File(General.getStateSavefileDirectory());
    private Pet pet;
    private String petSavefileName = "petSave.txt";
    //private String inventorySaveFilename = "inventorySavefile.txt";
    private long time;

    // Inventory
    private Inventory inventory;

    public Model(){
        // Checking for savefile
        Scanner scanner = null;
        try{
            scanner = new Scanner(savefile);
        }
        catch(Exception e){
            System.out.println("The savefile could not be found");
            // make new savefile
            if(!savefileDirectory.exists()){
                System.out.println("making new savefileDirectory");
                System.out.println(savefileDirectory.getPath());
                System.out.println(savefileDirectory.mkdir());
                System.out.println(new File(savefileDirectory + "/pets").mkdir());
            }
            if (Saver.makeNewSavefile()){
                // This is a terrible way to do this, try/catch within a catch
                try{
                    scanner = new Scanner(savefile);
                }
                catch(Exception ex){
                    System.out.println("Couldn't find newly made savefile");
                    System.exit(4);
                }
            }
            else{
                System.exit(1);
            }

        }

        // Reading the savefile
        Pet.setPetCount(Integer.parseInt(scanner.nextLine()));
        petSavefileName = scanner.nextLine();

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

        if (!Saver.savePet(pet)){
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
            System.out.println("No inventory savefile could be found. Making a new one");
            Saver.makeNewInventorySavefile();
            inventory = Inventory.load();
            System.out.println("Inventory:\n" + inventory);
            //return false;
        }

        pet = Loader.loadPet(petSavefileName);
        if (pet == null){
            System.out.println("Couldn't load pet");
            return false;
        }

        return true;
    }


    public boolean loadPetSlot(String SavefileName){
        // Use this to load the pet slot of the Model
        pet = Loader.loadPet(SavefileName);
        return pet != null;
    }

    public void unloadPet(){
        Saver.savePet(pet);
        pet = null;
    }

    public void firstTimeFileSetup(){
        // Working here at the moment -------------------------------------------------------------------------
        new File(General.getStateSavefileDirectory()).mkdir();
        //todo: make the default savefile

        new File(General.getPetSavefileDirectory()).mkdir();
        //todo: make the default Pet file
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
        Saver.savePet(pet);
        oldPetSavefile.delete();
    }

    // When this works I need to do seperate methods so the system can unload
    // pet, and the nload pet from memory without craching when no pet is loaded
    public boolean changePet(String newPetSavefileName){
        Pet newPet = Loader.loadPet(newPetSavefileName);
        if (newPet == null){
            return false;
        }
        Saver.savePet(pet);
        pet = newPet;
        return true;
    }

    public void feedPet(){
        if (pet != null){
            pet.eat(new Food(TimeConverter.hoursToMillis(6)));
        }
    }




    // Get and set methods

    public Pet getPet(){
        return pet;
    }

    public long getTime(){
        return time;
    }
}





/*
    * What happens if I save when pet = null? And then what if I load that savefile?

*/
