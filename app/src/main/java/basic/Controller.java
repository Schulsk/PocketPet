package basic;

import java.lang.Thread;
import java.util.concurrent.locks.ReentrantLock;
import java.lang.InterruptedException;


public class Controller{

    Model model;
    Thread update;
    UpdateMonitor monitor;
    ReentrantLock lock;
    boolean updating = false;       // Used to know when to shut down the updating thread



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
        try{
            lock.lock();
            model.unloadPet();
        }
        finally{
            lock.unlock();
        }
    }

    public boolean loadPetSlot(String filename){
        if (!model.loadPetSlot(filename)){
            return false;
        }
        return true;
    }

    public boolean renamePet(String newName){
        try{
            lock.lock();
            model.renamePet(newName);
            return true;
        }
        finally{
            lock.unlock();
        }
    }

    public void feedPet(){
        try{
            lock.lock();
            model.feedPet();
        }
        finally{
            lock.unlock();
        }
    }

    public Pet getPet(){
        return model.getPet();
    }

    public Model getModel(){return model;}

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
        try{
            lock.lock();
            return model.getInventory().withdraw(index);
        }
        finally{
            lock.unlock();
        }
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
