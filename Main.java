
import java.util.Scanner;


class Main{
    public static void main(String[] args){
        Controller controller = new Controller();
        controller.loadState();
        System.out.println("Game loaded");

        UpdateMonitor monitor = controller.getMonitor();



        // Main loop
        Scanner scanner = new Scanner(System.in);
        String inp = "";
        while (!inp.equals("q")){
            System.out.println("F - Feed");
            System.out.println("S - Save");
            System.out.println("P - Print");
            System.out.println("T - Time");
            System.out.println("E - Lay egg");
            System.out.println("I - Inspect inventory");
            System.out.println("Q - Quit");
            System.out.println();

            inp = scanner.nextLine().toLowerCase();

            if (inp.equals("f")){
                controller.getPet().eat(new Food(3600000));
                System.out.println("Pet has been fed");
            }
            else if (inp.equals("s")){
                controller.saveState();
                System.out.println("State saved");
            }
            else if (inp.equals("p")){
                System.out.println(controller.getPet());
            }
            else if (inp.equals("q")){
                System.out.println("Do you want to save before quiting?");
                inp = scanner.nextLine().toLowerCase();
                if (inp.equals("n")){
                    System.out.println("Not saving");
                }
                else{
                    controller.saveState();
                    System.out.println("State saved");
                }
                inp = "q";
                controller.quit();
                System.out.println("Game quit");
            }
            else if (inp.equals("e")){
                controller.getPet().layEgg();
                System.out.println(controller.getPet().getName() + " laid an egg!");
            }
            else if (inp.equals("i")){
                System.out.println(controller.getInventory());
            }
            else if (inp.equals("t")){
                System.out.println("Time: " + controller.getTime());
            }
            else{
                System.out.println("Unknown input");
            }

        }
        /*
        // Left over shit
        controller.update();
        System.out.println(controller.getPet());
        for (int i = 0; i < 10000; i++){
            // wait
        }
        controller.update();
        System.out.println();
        System.out.println(controller.getPet());
        controller.saveState();
        */
    }


/*
    public static void save(){
        try{
            lock.lock();
            controller.saveState();
            System.out.println("State saved");
        }
        finally{
            lock.unlock();
        }
    }

    public void load(){
        try{
            lock.lock();
            controller.loadState();
            System.out.println("Game loaded");
        }
        finally{
            lock.unlock();
        }
    }

    */
}
