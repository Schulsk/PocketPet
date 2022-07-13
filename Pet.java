
import java.util.Random;
import java.util.HashMap;
import java.io.PrintWriter;
import java.io.File;
import java.util.Scanner;


// abstract
abstract class Pet{
    final static String savefileDirectory = "savefiles/pets/";
    private Model model = null;     // Old
    private Random random;

    protected String name;
    private long babyTime, childTime, adultTime;
    private String type = "";
    private String stage = "";

    // Health stuff
    private boolean alive, starving, full;
    private long birthtime, deathtime, age;
    protected long maxAge;
    private long totalTimeStarving;
    private long totalTimeFull;
    private double starveThreshold, fullThreshold;       // Percent %

    // Hunger
    private long hunger, maxHunger;
    private long lastFed;

    // Positions
    private PlayPen playPen;
    private int posX;
    private int posY;

    // Egg stuff
    private String egg;
    private double[] eggLayingTimes;    // Percentages of the adult stage's max

    // Other
    private long timesADay;
    private long step;
    private long lastTimeCheck;
    private String parent;
    private String[] children = new String[3];
    //private HashMap<String, Pet> children;
    private int id;

    private static int petCount = 0;        // I think maybe the model should handle this

    // Creating a new pet
    public Pet(long currentTime, String parentName){
        System.out.println("Made new pet");
        random = new Random();

        setValuesNotInFile();

        // Setting all the values that you'd normally store in a savefile
        name = "unnamed";
        birthtime = currentTime;
        deathtime = 0;
        lastTimeCheck = currentTime;
        alive = true;
        totalTimeStarving = 0;
        totalTimeFull = 0;
        hunger = maxHunger;     // This must be dealt with, maxHunger can be set in subclasses after this
        lastFed = currentTime;
        parent = parentName;
        egg = "null";
        eggLayingTimes[0] = random.nextDouble();
        id = petCount++;

    }
    // Loading a pet from file
    public Pet(HashMap<String, String> stats){
        System.out.println("Made pet with stats");
        random = new Random();

        setValuesNotInFile();

        name = stats.get("name");
        id = Integer.parseInt(stats.get("id"));
        // Time
        birthtime = Long.parseLong(stats.get("birthtime"));
        deathtime = Long.parseLong(stats.get("deathtime"));
        lastTimeCheck = Long.parseLong(stats.get("lastTimeCheck"));
        // Health
        alive = Boolean.parseBoolean(stats.get("alive"));
        totalTimeStarving = Long.parseLong(stats.get("totalTimeStarving"));
        totalTimeFull = Long.parseLong(stats.get("totalTimeFull"));
        // Hunger
        hunger = Long.parseLong(stats.get("hunger"));
        lastFed = Long.parseLong(stats.get("lastFed"));

        // Egg stuff
        egg = stats.get("egg");
        eggLayingTimes[0] = Double.parseDouble(stats.get("eggLayingTimes"));

        // Other
        parent = stats.get("parent");
        children[0] = stats.get("children");


        //setEggLayingTimes(3);

        // Do catchup in case player has been gone for a while
        catchUp();
    }

    // Update
    public void update(Long currentTime){
        //long currentTime = model.getTime();
        if (alive){
            calculateAge(currentTime);
            // Todo: Check if too old

            // Hunger
            checkHunger(currentTime);
        }
        if (alive){
            checkForEggLaying();
        }

        // Last thing
        lastTimeCheck = currentTime;
    }

    public void catchUp(){
        long currentTime = System.currentTimeMillis();
        long simulatedTime = lastTimeCheck;
        age = lastTimeCheck - birthtime;

        while (simulatedTime <= currentTime){
            update(simulatedTime);
            simulatedTime += step;
        }
    }

    public void setModel(Model model){
        this.model = model;
    }

    @Override
    public String toString(){
        String string = "";
        string += "Type: " + type;
        string += "\nName: " + name + " Id: " + id;
        string += "\nAlive: " + alive;
        if (!alive){
            string += "\nDeathtime: " + deathtime;
            string += "\nTotal time dead: " + TimeConverter.toString(lastTimeCheck - deathtime);
        }
        string += "\nBirthtime: " + birthtime;
        string += "\nLast Timecheck: " + lastTimeCheck;
        string += "\nLastFed: " + lastFed;
        // string += "\nTime since last fed: " + TimeConverter.toString(hungerCounter) + " : " + hungerCounter;
        string += "\nHunger: " + hunger + " Time left: " + TimeConverter.toString(hunger);
        string += "\nTotal time starving: " + TimeConverter.toString(totalTimeStarving) + " : " + totalTimeStarving;
        string += "\nTotal time full: " + TimeConverter.toString(totalTimeFull) + " : " + totalTimeFull;
        string += "\nAge: " + TimeConverter.toString(age);
        string += "\nDays: " + getAgeInDays() + " Hours: " + getAgeInHours() + " Minutes: " + getAgeInMinutes() + " Seconds: " + getAgeInSeconds() + " Millis: " + age;

        return string;
    }

    // Saving and loading

    public String getSavefileName(){
        String string = id + "_" + name + ".txt";
        return string;
    }

    public String getSaveFormat(){
        String string = "";
        string += "type " + type;
        string += "\n" + "name " +  name;
        string += "\n" + "id " +  id;
        string += "\n" + "alive " + alive;
        string += "\n" + "birthtime " + birthtime;
        string += "\n" + "deathtime " + deathtime;
        string += "\n" + "lastTimeCheck " + lastTimeCheck;
        string += "\n" + "hunger " + hunger;
        string += "\n" + "lastFed " + lastFed;
        string += "\n" + "totalTimeStarving " + totalTimeStarving;
        string += "\n" + "totalTimeFull " + totalTimeFull;
        string += "\n" + "parent " + parent;
        string += "\n" + "children " + children[0];
        string += "\n" + "egg " + egg;
        string += "\n" + "eggLayingTimes " + eggLayingTimes[0];

        /*
        string += "\n" + "children ";
        int i = 0;
        for (String child : children){
            string += children[i++] + ",";
        }
        */

        return string;
    }

    /*
    Maybe I'll make static saver and loader objects that can save and load all
    the different items that I need to handle?

    */
    public boolean save(){
        File file = new File(savefileDirectory + getSavefileName());
        PrintWriter writer = null;
        try{
            writer = new PrintWriter(file);
        }
        catch(Exception e){
            return false;
        }

        writer.print(getSaveFormat());
        writer.close();

        return true;
    }

    public static Pet load(String savefileName){
        /*
        Todo
        */

        Pet loaded = null;
        File file = new File(savefileDirectory + savefileName);
        Scanner scanner = null;
        try{
            scanner = new Scanner(file);
        }
        catch(Exception e){
            System.out.println(e);
            return null;
        }

        HashMap<String, String> stats = new HashMap<>();
        try{
            // Read file and put into stats
            while (scanner.hasNext()){
                String[] line = scanner.nextLine().split(" ");
                stats.put(line[0], line[1]);
            }
        }
        catch(Exception e){
            System.out.println(e);
            System.out.println("Error when reading file and splitting lines");
            scanner.close();
            return null;
        }
        scanner.close();

        // Make the pet
        if (stats.get("type").equals("TestPet01")){
            loaded = new TestPet01(stats);
        }
        else if (stats.get("type").equals("TestEgg01")){
            loaded = new TestEgg01(stats);
        }

        return loaded;
    }


    protected void setType(String type){
        this.type = type;
    }


    // Age stuff

    private void calculateAge(long currentTime){
        age = currentTime - birthtime;
    }

    public int getAgeInSeconds(){
        return (int)(age / 1000);
    }

    public int getAgeInMinutes(){
        return (int)(age / 60000);
    }

    public int getAgeInHours(){
        return (int)(age / 3600000);
    }

    public int getAgeInDays(){
        return (int)(age / 86400000);
    }


    // Hunger stuff
    private void checkHunger(long currentTime){
        long digested = currentTime - lastTimeCheck;

        hunger -= digested;

        checkFoodStatus();

        // Check for death
        if (hunger <= 0){
            die();
            hunger = 0;
        }
    }

    private void checkFoodStatus(){
        /*
        Todo:
            I should probably get rid of the step here and just pass down
            currentTime like I did in checkHunger
        */
        if (hunger < maxHunger * starveThreshold){
            totalTimeStarving += step;
        }
        else if (hunger > maxHunger * fullThreshold){
            totalTimeFull += step;
        }
    }

    public void eat(Consumable food){
        if (!alive){
            return;
        }
        hunger += food.consume();
        if (hunger > maxHunger){
            hunger = maxHunger;
        }
        lastFed = lastTimeCheck;      // maybe change this to use current time
    }


    // Health stuff
    private void die(){
        if (!alive){
            return;
        }

        alive = false;
        deathtime = birthtime + age;
    }


    // Egg stuff
    public boolean checkForEggLaying(){
        for (int i = 0; i < eggLayingTimes.length; i++){
            if (eggLayingTimes[i] != 0){
                if (age >= maxAge * eggLayingTimes[i]){
                    layEgg();
                    eggLayingTimes[i] = 0;
                    return true;
                }
            }
        }
        return false;
    }

    // Public for testing purposes
    public void layEgg(){
        // gotta figure out a way to choose types here
        Egg temp = new TestEgg01(lastTimeCheck, getSavefileName());
        temp.save();
        egg = temp.getSavefileName();
        children[0] = egg;
    }

    public boolean hasEgg(){
        if (egg.equals("null")){
            return false;
        }
        return true;
    }

    public String removeEgg(){
        String temp = egg;
        egg = "null";
        System.out.println("in removeEgg()");
        System.out.println("egg: " + egg + " temp: " + temp);
        return temp;
    }


    // Get and set
    private void setEggLayingTimes(int eggNumber){
        eggLayingTimes = new double[eggNumber];
        for (int i = 0; i < eggNumber; i++){
            eggLayingTimes[i] = random.nextDouble();
            if (i > 0){
                while (eggLayingTimes[i] <= eggLayingTimes[i-1]){
                    eggLayingTimes[i] = random.nextDouble();
                }
            }
        }
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    private void setValuesNotInFile(){
        maxAge = 90;
        maxHunger = 80000000;
        age = 0;
        starveThreshold = 0.2;
        fullThreshold = 0.8;
        /*
        Todo:
        I wanna do the math and display how I increase the hunger more
        clearly later
        */
        timesADay = 10000;
        //step = 86400000 / timesADay;
        step = 1000;
        eggLayingTimes = new double[3];
    }



    // Other

    public static void setPetCount(int newPetCount){
        petCount = newPetCount;
    }

    public static int getPetCount(){
        return petCount;
    }




    /*
    Known problems:
        * When you load a pet thet is dead, it recalculates the age as if it is
        alive. When you load a pet that dies during catchUp, age is calculated
        as it should.
        * Due to the way I now do petSave-files I should check that the file has
        valid values for all the variables.
    */

}
