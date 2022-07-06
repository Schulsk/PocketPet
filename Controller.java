

import java.lang.Thread;
import java.util.concurrent.locks.ReentrantLock;
import java.lang.InterruptedException;


class Controller{

    Model model;
    Thread update;
    UpdateMonitor monitor;
    ReentrantLock lock;
    boolean updating = false;



    public Controller(){
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
            if (! model.savePet()){
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
            if (! model.loadPet()){
                return false;
            }

            return true;
        }
        finally{
            lock.unlock();
        }
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
