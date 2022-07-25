package basic;

import java.lang.Thread;
import java.util.concurrent.locks.ReentrantLock;
import java.lang.InterruptedException;


public class Controller{

    Model model;
    Thread update;
    UpdateMonitor monitor;
    ReentrantLock lock;
    boolean updating = false;



    public Controller(){
        System.out.println("Made the controller object");
        model = new Model();
        //monitor = new UpdateMonitor(this);
        lock = new ReentrantLock();
        updating = true;

        // Start the update thread
        update = new Thread(new Update(this));
        update.start();

    }

    public void update(){
        try{
            lock.lock();
            model.update();
        }
        finally{
            lock.unlock();
        }
    }

    public boolean saveState(){
        try{
            lock.lock();
            if (! model.saveData()){
                return false;
            }

            return true;
        }
        finally{
            lock.unlock();
        }
    }

    public boolean loadState(){
        try{
            lock.lock();
            if (! model.loadData()){
                return false;
            }

            return true;
        }
        finally{
            lock.unlock();
        }
    }

    public boolean changePet(String newPetSavefileName){
        try{
            lock.lock();
            if (!model.changePet(newPetSavefileName)){
                return false;
            }
            return true;
        }
        finally{
            lock.unlock();
        }
    }

    public void unloadPet(){
        model.unloadPet();
    }

    public boolean loadPetSlot(String filename){
        if (!model.loadPetSlot(filename)){
            return false;
        }
        return true;
    }

    public boolean renamePet(String newName){
        model.renamePet(newName);
        return true;
    }

    public Pet getPet(){
        return model.getPet();
    }

    public boolean getUpdating(){
        return updating;
    }

    public void setUpdating(boolean newValue){
        updating = newValue;
    }

    public UpdateMonitor getMonitor(){
        return monitor;
    }

    public long getTime(){
        return model.getTime();
    }


    //Inventory stuff
    public Inventory getInventory(){
        return model.getInventory();
    }

    public String withdraw(int index){
        return model.getInventory().withdraw(index);
    }

    public boolean quit(){
        setUpdating(false);
        try{
            update.join();
        }
        catch(InterruptedException e){
            System.out.println(e);
            return false;
        }
        return true;
    }
    
}
