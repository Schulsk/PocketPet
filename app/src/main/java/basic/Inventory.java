package basic;

import java.io.PrintWriter;
import java.io.File;
import java.util.Scanner;

public class Inventory{
    private Slot<String>[] eggSlots;
    static final String savefilePath = General.getInventorySavefileDirectory() + "inventorySavefile.txt";


    class Slot<T>{
        T item;

        public Slot(){
            item = null;
        }

        public String toString(){
            if (item == null){
                return "null";
            }
            return item.toString();
        }

        public boolean setItem(T newItem){
            if (item != null){
                return false;
            }
            item = newItem;

            return true;
        }

        public T getItem(){
            return item;
        }

        public T removeItem(){
            T temp = item;
            item = null;
            return temp;
        }

        public boolean isEmpty(){
            if (item == null){
                return true;
            }
            return false;
        }

    }


    public Inventory(int slotNumber){
        eggSlots = new Slot[slotNumber];

        for (int i = 0; i < slotNumber; i++){
            eggSlots[i] = new Slot<>();
        }

    }

    @Override
    public String toString(){
        String string = "Inventory:";
        for (Slot slot : eggSlots){
            string += "\n" + slot;
        }
        return string;
    }

    public boolean put(String newEgg){
        for (Slot slot : eggSlots){
            if (slot.isEmpty()){
                slot.setItem(newEgg);
                return true;
            }
        }

        return false;
    }

    public boolean hasRoom(){
        for (Slot slot : eggSlots){
            if (slot.isEmpty()){
                return true;
            }
        }
        return false;
    }

    public Slot<String>[] getSlots(){
        return eggSlots;
    }

    // Fetching
    public String withdraw(int index){
        try{
            String temp = eggSlots[index].removeItem();
            eggSlots[index] = null;
            return temp;
        }
        catch(Exception e){
            System.out.println("Index out of bounds");
            return null;
        }
    }

    public String readSlotContent(int index){
        try{
            return eggSlots[index].toString();
        }
        catch(Exception e){
            System.out.println(e);
            System.out.println("Couldn't access the index at in the Inventory");
            return "null";
        }
    }


    // Saving and loading
    public boolean save(){
        File file = new File(savefilePath);
        PrintWriter writer = null;

        try{
            writer = new PrintWriter(file);
        }
        catch(Exception e){
            return false;
        }

        for (Slot slot : eggSlots){
            writer.println(slot);
        }

        writer.close();

        return true;
    }

    public static Inventory load(){
        File file = new File(savefilePath);
        Scanner scanner = null;
        try{
            scanner = new Scanner(file);
        }
        catch(Exception e){
            System.out.println(e);
            return null;
        }

        Inventory loaded = new Inventory(3);        // This should change based on the inventory that was saved
        for (Slot slot : loaded.getSlots()){
            if (scanner.hasNext()){
                String nextLine = scanner.nextLine();
                if (!nextLine.equals("null")){
                    slot.setItem(nextLine);
                }
            }
        }

        return loaded;
    }
}
