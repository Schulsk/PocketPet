package basic;

import java.util.concurrent.locks.ReentrantLock;


class UpdateMonitor{
    Controller controller;
    ReentrantLock lock;

    public UpdateMonitor(Controller controller){
        this.controller = controller;
        lock = new ReentrantLock();
    }


}
