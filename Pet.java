
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
    private double[] eggLayingTimes;    // Percentages of the adult stage's max

    // Health stuff
    private boolean alive, starving, full;
    private long birthtime, deathtime, age;
    protected long maxAge;
    private long totalTimeStarving;
    private long totalTimeFull;
    private double starveThreshold, fullThreshold;       // Percent %

    // Hunger
    private float hunger, maxHunger;
    private long hungerCounter, millisSinceFed, lastFed;

    // Other
    private long timesADay;
    private long step;
    private long lastTimeCheck;
    private String parent;
    private String child;
    //private HashMap<String, Pet> children;

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

        maxAge = 90;   // In milliseconds
        maxHunger = 100;

        name = (String)stats.get("name");
        // Time
        birthtime = (long)stats.get("birthtime");
        deathtime = (long)stats.get("deathtime");
        age = 0;
        lastTimeCheck = (long)stats.get("lastTimeCheck");
        // Health
        alive = (boolean)stats.get("alive");
        starveThreshold = 0.2;
        fullThreshold = 0.8;
        totalTimeStarving = (long)stats.get("totalTimeStarving");
        totalTimeFull = (long)stats.get("totalTimeFull");
        // Hunger
        hunger = (float)stats.get("hunger");
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
        parent = (String)stats.get("parent");
        child = (String)stats.get("child");


        //setEggLayingTimes(3);

        // Do catchup in case player has been gone for a while
        catchUp();
    }

    // Update
    public void update(){
        long currentTime = model.getTime();
        if (alive){
            calculateAge();
            // Todo: Check if too old

            // Hunger
            checkHunger();
        }

        // Last thing
        lastTimeCheck = currentTime;
    }

    public void catchUp(){
        // I am reconsidering the way I have done this whole catchUp method now.
        // I think I should have rather made the update and everything in it
        // not depend on the current time from the system call, but rather use
        // either a time interval that is passed as an argument, or that is
        // predetermined in a constant. That way, I could just choose how many
        // times I shoud call the update method in  the catchUp  method and it
        // should all work just fine.
        /*
        Todo:
        Here I could do everything that needs to be done to catch up with the
        time that has passed while you've been away
        stuff like counting up hunger and all that shit.
        */
        long currentTime = System.currentTimeMillis();
        long simulatedTime = lastTimeCheck;
        age = lastTimeCheck - birthtime;

        while (simulatedTime <= currentTime){
            // I think I'm done here now...
            // Hunger
            hunger -= 100.0 / (float)timesADay;


            // Check for death
            if (hunger <= 0){
                die();
                hunger = 0;
            }

            // Aging
            if (alive){
                age += step;
            }

            // Check for death by old age
            /*
            Todo
            */

            // Finally
            if (alive){
                checkFoodStatus();
            }
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
        string += "\nName: " + name;
        string += "\nAlive: " + alive;
        if (!alive){
            string += "\nDeathtime: " + deathtime;
            string += "\nTotal time dead: " + TimeConverter.toString(model.getTime() - deathtime);
        }
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
        // Any changes made here must also be made in Model.java in the loadPet method
        String string = "";
        string += type;
        string += "\n" + name;
        string += "\n" + alive;
        string += "\n" + birthtime;
        string += "\n" + deathtime;
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

        // This is like this because the update call might be of different
        // intervals, and I'm too drunk too think straight right now.
        while (hungerCounter >= step){
            hunger -= 100.0 / (float)timesADay;
            hungerCounter -= step;

            checkFoodStatus();

            // Check for death
            if (hunger <= 0){
                die();
                hunger = 0;
            }
        }

    }

    private void checkFoodStatus(){
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
        lastFed = model.getTime();
    }


    // Health stuff
    private void die(){
        if (!alive){
            return;
        }

        alive = false;
        deathtime = birthtime + age;
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




    /*
    Known problems:
        * When you load a pet thet is dead, it recalculates the age as if it is
        alive. When you load a pet that dies during catchUp, age is calculated
        as it should.
    */

}
