package basic;

import java.lang.Runnable;

class Update implements Runnable{
    private Controller controller;
    private long counter;

    public Update(Controller controller){
        this.controller = controller;
        counter = 0;
    }

    public void run(){
        long currentTime = System.currentTimeMillis();
        long lastTimeCheck = currentTime;
        boolean step = true;
        while (controller.getUpdating() == true){
            currentTime = System.currentTimeMillis();

            counter += currentTime - lastTimeCheck;
            //System.out.println(counter);
            lastTimeCheck = currentTime;
            //System.out.println("Hello");

            if (counter >= 1000){
                //System.out.println("Updating");
                update();
                counter -= 1000;
            }

            /*try{
                System.out.println("Before wait");
                wait(64);
                System.out.println("After wait");
            }
            catch(InterruptedException e){
                System.out.println(e);
            }*/
        }
    }

    public void update(){
        // try{
        //     lock.lock();
        //     controller.update();
        // }
        // finally{
        //     lock.unlock();
        // }
        controller.update();

    }
}
