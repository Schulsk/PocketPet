
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
            System.out.println("U - Unload pet");
            System.out.println("L - Load pet from Inventory");
            System.out.println("C - Change pet");
            System.out.println("R - Rename pet");
            System.out.println("Q - Quit");
            System.out.println();

            inp = scanner.nextLine().toLowerCase();

            if (inp.equals("f")){
                controller.getPet().eat(new Food(TimeConverter.hoursToMillis(6)));
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
            else if (inp.equals("u")){
                controller.unloadPet();
            }
            else if (inp.equals("l")){
                System.out.println("You can choose between the pets you have in inventory (1, 2, 3):");
                System.out.println(controller.getInventory());
                inp = scanner.nextLine();
                int index = Integer.parseInt(inp) - 1;
                String petFile = controller.withdraw(index);
                controller.loadPet(petFile);

                if (controller.getPet() != null){
                    System.out.println("Pet changed");
                }
                else{
                    System.out.println("Couldn't change to that pet");
                }
            }
            else if (inp.equals("c")){
                System.out.println("You can choose between the pets you have in inventory:");
                System.out.println(controller.getInventory());
                inp = scanner.nextLine();
                if (controller.changePet(inp)){
                    System.out.println("Pet changed");
                }
                else{
                    System.out.println("Couldn't change to that pet");
                }
                inp = "c";
            }
            else if (inp.equals("r")){
                System.out.println("What do you want to name your pet?");
                inp = scanner.nextLine();
                controller.renamePet(inp);
                System.out.println("Pet renamed");
                inp = "r";
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
