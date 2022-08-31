package basic;

import java.util.Random;
import java.util.HashMap;
import java.io.PrintWriter;
import java.io.File;
import java.util.Scanner;



public abstract class Pet{
    final static String savefileDirectory = General.getPetSavefileDirectory();
    private Model model = null;     // Old
    private Random random;

    protected String name;
    protected long eggTime, babyTime, childTime, adultTime;
    private String type = "";
    private String stage = "";

    // Health stuff
    private boolean alive, starving, full;
    protected long birthtime, eggBirthtime, deathtime, age, maxAge;
    private long totalTimeStarving, totalTimeFull;
    private double starveThreshold, fullThreshold;       // Percent %

    // Hunger
    private long hunger, maxHunger, lastFed;

    // Positions
    private PlayPen playPen;
    private int posX;
    private int posY;

    // Egg stuff
    protected String egg;
    private double[] eggLayingTimes;    // Percentages of the adult stage's max

    // Other
    private long timesADay, step;
    protected long lastTimeCheck;
    private String parent;
    protected String[] children = new String[3];
    //private HashMap<String, Pet> children;
    private int id;
    protected boolean evolving;

    // State stuff
    private String state = "idle";
    private long stateCounter;

    // Animation stuff
    private HashMap<String, Integer> animations;

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
        eggTime = TimeConverter.hoursToMillis(6);
        //babyTime = TimeConverter.daysToMillis(1);
        //childTime = TimeConverter.daysToMillis(1);
        //adultTime = TimeConverter.daysToMillis(1);
        onCreate();
    }
    // Loading a pet from file
    public Pet(HashMap<String, String> stats){
        System.out.println("Made pet with stats");
        random = new Random();

        setValuesNotInFile();

        name = stats.get("name");
        id = Integer.parseInt(stats.get("id"));
        // Time
        eggBirthtime = Long.parseLong(stats.get("eggBirthtime"));
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
        eggTime = Long.parseLong(stats.get("eggTime"));
        babyTime = Long.parseLong(stats.get("babyTime"));
        childTime = Long.parseLong(stats.get("childTime"));
        adultTime = Long.parseLong(stats.get("adultTime"));

        // Do catchup in case player has been gone for a while
        catchUp();
    }

    public void onCreate(){
        /** Do stuff like adjust stageTimes and stuff like that, that is only supposed to happen once
         * upon creation (or evolution). Not done in the non-stats constructor because when they evolve,
         * the stats-constructor is used */
        // Supposed to be overridden in each specific type
    }

    // Update
    public void update(Long currentTime){
        //long currentTime = model.getTime();
        if (alive){
            calculateAge(currentTime);
            // Check if too old
            checkAge();

            // Hunger
            checkHunger(currentTime);
        }
        // A second check in case the pet died during the checks
        if (alive){
            checkForEggLaying();
        }


        // State stuff
        updateState();

        if (state.equals("idle")){
            // todo: check if the criteria for happy, sad or angry are met, and change if they are
        }
        else if (state.equals("eating")){
            stateCounter -= currentTime - lastTimeCheck;
            if (stateCounter < 0){
                state = "idle";
            }
        }
        else if (state.equals("happy")){
            // todo
        }
        else if (state.equals("sad")){
            //todo
        }
        else if (state.equals("angry")){
            //todo
        }

        // Last thing
        lastTimeCheck = currentTime;
    }
    /** Todo: God damn idiot. This catchup thing clearly has to go in the model section, so that the
     *  entire system simulates the time that has passed.*/
    /** Actually, there's probably a reason why I made it here. I think it is so that when I
     * load a pet, it automatically catches up. But I think I will move it still, so that if I
     * decide to make plants and stuff, it'll be easier. */
    public void catchUp(){
        long currentTime = System.currentTimeMillis();
        long simulatedTime = lastTimeCheck;
        age = lastTimeCheck - birthtime;

        while (simulatedTime <= currentTime){
            update(simulatedTime);
            simulatedTime += step;
        }
    }


    // Representation
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
        string += "\nTime: " + System.currentTimeMillis();
        string += "\nLastFed: " + lastFed;
        // string += "\nTime since last fed: " + TimeConverter.toString(hungerCounter) + " : " + hungerCounter;
        string += "\nHunger: " + hunger + " Time left: " + TimeConverter.toString(hunger);
        string += "\nTotal time starving: " + TimeConverter.toString(totalTimeStarving) + " : " + totalTimeStarving;
        string += "\nTotal time full: " + TimeConverter.toString(totalTimeFull) + " : " + totalTimeFull;
        string += "\nAge: " + TimeConverter.toString(age);
        string += "\nDays: " + getAgeInDays() + " Hours: " + getAgeInHours() + " Minutes: " + getAgeInMinutes() + " Seconds: " + getAgeInSeconds() + " Millis: " + age;

        return string;
    }

    public HashMap<String, String> getStats(){
        HashMap<String, String> stats = new HashMap<>();
        String[] info = getSaveFormat().split("\n");
        for (String line : info){
            String[] parts = line.split(" ");
            stats.put(parts[0], parts[1]);
        }
        System.out.println(stats);
        return stats;
    }


    // State stuff
    private void changeState(String newState, long newStateCounter){
        state = newState;
        stateCounter = newStateCounter;
    }

    private void changeState(String newState){
        state = newState;
    }

    private void updateState(){
        // stateCounter is used for overriding other states, like if the pet is angry and gets fed,
        // it'll still play the eating animation before going back to being angry
        if (stateCounter > 0){
            return;
        }
        if (starving){
            setState("sad");
        }

        if (!alive){
            setState("dead");
        }
    }


    // Saving and loading

    public String getSavefileName(){
        String string = id + "_" + name + ".txt";
        return string;
    }

    public String getFullSavePath(){
        String string = savefileDirectory + getSavefileName();
        return string;
    }

    public String getSaveFormat(){
        // The order here doesn't matter that much because the reading of the file considers the
        // keyword when the value is stored.
        String string = "";
        string += "type " + type;
        string += "\n" + "name " +  name;
        string += "\n" + "id " +  id;
        string += "\n" + "alive " + alive;
        string += "\n" + "eggBirthtime " + eggBirthtime;
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
        string += "\n" + "eggTime " + eggTime;
        string += "\n" + "babyTime " + babyTime;
        string += "\n" + "childTime " + childTime;
        string += "\n" + "adultTime " + adultTime;

        /*
        string += "\n" + "children ";
        int i = 0;
        for (String child : children){
            string += children[i++] + ",";
        }
        */

        return string;
    }


    // Age stuff

    protected void calculateAge(long currentTime){
        age = currentTime - birthtime;
    }

    protected void checkAge(){
        if (age > maxAge){
            evolving = true;
        }
    }
    // The following age related methods are not necessary now that we have the TimeConverter
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
    protected void checkHunger(long currentTime){
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
            starving = true;
        }
        else{
            starving = false;
        }

        if (hunger > maxHunger * fullThreshold){
            totalTimeFull += step;
            full = true;
        }
        else{
            full = false;
        }
    }

    protected void eat(Consumable food){
        if (!alive){
            return;
        }
        hunger += food.consume();
        if (hunger > maxHunger){
            hunger = maxHunger;
        }
        lastFed = lastTimeCheck;      // maybe change this to use current time
        changeState("eating", TimeConverter.secondsToMillis(2));

    }


    // Health stuff
    protected void die(){
        if (!alive){
            return;
        }

        alive = false;
        //setState("dead");
        deathtime = birthtime + age;
    }


    // Egg stuff
    // It returns true when an egg has been laid
    public boolean checkForEggLaying(){
        for (int i = 0; i < eggLayingTimes.length; i++){
            if (eggLayingTimes[i] != 0){
                // I'm making the egglaying time be a percentage of the pet's adult life
                if (age >= babyTime + childTime + (adultTime * eggLayingTimes[i])){
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
        /*
        I also might want to change how I do the saving and stuff here. I would
        prefer to make the Pet class not dependent on the Saver class, so I
        think I might make the egg be an Egg pointer that stores the egg in
        memory until the model picks it up from there on the next update.
        To make this work smoothly, in case the updates are called rarely, could
        I make the pet save itself upon creation or something? I don't think so,
        but it might not matter that much, because if the process is terminated
        before saving, the pet will just lay the egg again the next time they
        are loaded, and then they wil be picked up and saved.
        */
        Egg temp = new TestEgg01(lastTimeCheck, getSavefileName());
        Saver.savePet(temp);
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

    protected void setType(String type){
        this.type = type;
    }

    protected void setValuesNotInFile(){
        maxAge = TimeConverter.daysToMillis(3);
        maxHunger = TimeConverter.hoursToMillis(22);
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
        evolving = false;

        setAnimationSet(Animation.getAnimationSet(this));
    }

    // might delete this
    public void setModel(Model model){
        this.model = model;
    }


    // Evolving
    public Pet evolve(){
        return Evolver.evolve(this);
    }

    public boolean isReadyToEvolve(){
        return evolving;
    }


    // Animation
    public void setAnimationSet(HashMap<String, Integer> animations){
        this.animations = animations;
    }

    public int getAnimation(String animation){
        return animations.get(animation);
    }


    // Other

    public static void setPetCount(int newPetCount){
        petCount = newPetCount;
    }

    public static int getPetCount(){
        return petCount;
    }

    public String getState(){
        return state;
    }

    public String getType(){
        return type;
    }

    // Temp
    public void setState(String newState){
        state = newState;
    }



    // Here are the old save and load methods in case I want to go back to the
    // way I did it before where each class handled all of that themselves
    // I felt it was clean in a way, but maybe not in line with all the advice I
    // read online
    /*
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

    */




    /*
    Known problems:
        * When you load a pet thet is dead, it recalculates the age as if it is
        alive. When you load a pet that dies during catchUp, age is calculated
        as it should.
        * Due to the way I now do petSave-files I should check that the file has
        valid values for all the variables.
        * Can't have pets with spaces in their names. You just load the first
        part of the name then
        * If state is not saved when an egg is laid, the id numbering is fucked
        * If state is not saved when a pet is changed, things might get fucked
    */

}
