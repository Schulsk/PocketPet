
import java.util.Random;
import java.util.HashMap;


// abstract
abstract class Pet{
    private Model model = null;
    private Random random;

    private String name;
    private long babyTime, childTime, adultTime;
    private String type = "";
    private String stage = "";
    private int[] eggLayingTimes;

    // Health stuff
    private boolean alive, starving, full;
    private long birthtime, age, maxAge;
    private long totalTimeStarving;
    private long totalTimeFull;
    private float starveThreshold, fullThreshold;

    // Hunger
    private float hunger, maxHunger;
    private long hungerCounter, millisSinceFed, lastFed;

    // Other
    private long timesADay;
    private long step;
    private long lastTimeCheck;

    /*
    Maybe make seperate overloads of the constructor that are made for loading
    and creation?
    */
    public Pet(String name, long birthtime, float hunger){
        random = new Random();

        this.maxAge = 90;   // In hours
        this.maxHunger = 100;

        this.name = name;
        this.birthtime = birthtime;
        this.age = 0;

        // Hunger
        this.hunger = hunger;

        this.lastTimeCheck = 0;

        //setEggLayingTimes();
    }
    public Pet(HashMap<String, Object> stats){
        System.out.println("Made pet with stats");
        random = new Random();

        this.maxAge = 90;   // In hours
        this.maxHunger = 100;

        this.name = (String)stats.get("name");
        // Time
        this.birthtime = (long)stats.get("birthtime");
        this.age = 0;
        this.lastTimeCheck = (long)stats.get("lastTimeCheck");
        // Health
        alive = (boolean)stats.get("alive");
        starveThreshold = 20;
        fullThreshold = 80;
        totalTimeStarving = (long)stats.get("totalTimeStarving");
        totalTimeFull = (long)stats.get("totalTimeFull");
        // Hunger
        this.hunger = (float)stats.get("hunger");
        lastFed = (long)stats.get("lastFed");
        millisSinceFed = System.currentTimeMillis() - lastFed;
        hungerCounter = System.currentTimeMillis() - lastTimeCheck;

        // Other
        /*
        Todo:
        I wanna do the math and display how I increase the hunger more
        clearly later
        */
        timesADay = 10000;
        step = 86400000 / timesADay;


        //setEggLayingTimes(3);
    }

    // Update
    public void update(){
        long currentTime = model.getTime();
        calculateAge();
        // Todo: Check if too old

        // Hunger
        checkHunger();

        // Last thing
        lastTimeCheck = currentTime;
    }

    public void catchUp(){
        /*
        Todo:
        Here I could do everything that needs to be done to catch up with the
        time that has passed while you've been away
        stuff like counting up hunger and all that shit.
        */
    }

    public void setModel(Model model){
        this.model = model;

    }

    @Override
    public String toString(){
        String string = "";
        string += "Type: " + type;
        string += "\nName: " + name;
        string += "\nAlive: " + alive;
        string += "\nBirthtime: " + birthtime;
        string += "\nLast Timecheck: " + lastTimeCheck;
        string += "\nLastFed: " + lastFed;
        // string += "\nTime since last fed: " + TimeConverter.toString(hungerCounter) + " : " + hungerCounter;
        string += "\nHunger: " + hunger;
        string += "\nTotal time starving: " + TimeConverter.toString(totalTimeStarving) + " : " + totalTimeStarving;
        string += "\nTotal time full: " + TimeConverter.toString(totalTimeFull) + " : " + totalTimeFull;
        string += "\nAge: " + TimeConverter.toString(age);
        string += "\nDays: " + getAgeInDays() + " Hours: " + getAgeInHours() + " Minutes: " + getAgeInMinutes() + " Seconds: " + getAgeInSeconds() + " Millis: " + age;

        return string;
    }

    public String getSaveFormat(){
        String string = "";
        string += type;
        string += "\n" + name;
        string += "\n" + alive;
        string += "\n" + birthtime;
        string += "\n" + lastTimeCheck;
        string += "\n" + hunger;
        string += "\n" + lastFed;
        string += "\n" + totalTimeStarving;
        string += "\n" + totalTimeFull;

        return string;
    }


    protected void setType(String type){
        this.type = type;
    }


    // Age stuff
    private void increaseAge(long amount){
        age += amount;
        if (age > maxAge){
            System.out.println(name + " is old and dead");
        }
    }

    private void calculateAge(){
        if (model == null){
            return;
        }
        age = model.getTime() - birthtime;
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
    private void checkHunger(){
        hungerCounter += model.getTime() - lastTimeCheck;

        /*
            Here I can do a calculation in stead of a while loop to save some
            time if you've been away for a long time
            Might have to keep it like this to figure out how long they have
            been starving or full
        */
        while (hungerCounter >= step){
            hunger -= 100.0 / (float)timesADay;
            hungerCounter -= step;

            checkFoodStatus();
        }

    }

    private void checkFoodStatus(){
        if (hunger < starveThreshold){
            totalTimeStarving += step;
        }
        else if (hunger > fullThreshold){
            totalTimeFull += step;
        }
    }

    public void eat(Consumable food){
        hunger += food.consume();
        if (hunger > maxHunger){
            hunger = maxHunger;
        }
        lastFed = model.getTime();
    }


    // Get and set
    private void setEggLayingTimes(int eggNumber){
        eggLayingTimes = new int[eggNumber];
        for (int i = 0; i < eggNumber; i++){
            eggLayingTimes[i] = random.nextInt();
        }
    }

}
