package basic;

import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;

// This is a static saver object that has the function of saving all the objects
// that need saving in the project
public class Saver extends General{

    public static boolean savePet(Pet pet){
        if (pet == null){
            System.out.println("No pet to save");
            return true;
        }

        File file = new File(petSavefileDirectory + pet.getSavefileName());
        PrintWriter writer = null;
        try{
            writer = new PrintWriter(file);
        }
        catch(Exception e){
            System.out.println(e);
            return false;
        }

        writer.print(pet.getSaveFormat());
        writer.close();
        return true;
    }

    public static boolean makeNewSavefile(){
        File file = new File(stateSavefileDirectory + "/savefile.txt");
        PrintWriter writer = null;
        try{
            writer = new PrintWriter(file);
        }
        catch(Exception e){
            System.out.println("Saver - Couldn't make new savefile");
            return false;
        }
        writer.println("0");
        writer.println("null");
        writer.close();
        return true;
    }

    public static boolean makeNewInventorySavefile(){
        File file = new File(inventorySavefileDirectory + "inventorySavefile.txt");
        System.out.println("Making new inventorySavefile at: " + inventorySavefileDirectory + "inventorySavefile.txt");
        PrintWriter writer = null;
        try{
            writer = new PrintWriter(file);
        }
        catch(Exception e){
            System.out.println("Saver - Couldn't make new inventorySavefile");
            return false;
        }
        int numberOfInventorySlots = 3;
        for (int i = 0; i < numberOfInventorySlots; i++){
            writer.println("null");
        }
        writer.close();
        return true;
    }

    // Gotta make Slot a public class for this to work
    /*
    static public boolean saveInventory(Inventory inventory){
        File file = new File(inventorySavefileDirectory + "inventorySavefile");
        PrintWriter writer = null;

        try{
            writer = new PrintWriter(file);
        }
        catch(Exception e){
            return false;
        }

        for (Slot slot : inventory.getSlots()){
            writer.println(slot);
        }

        writer.close();

        return true;
    }
    */

}
