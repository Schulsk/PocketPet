
import java.util.Random;
import java.util.HashMap;


// abstract
abstract class Pet{
    private Model model = null;
    private Random random;

    private String name;
    private long birthtime, age, maxAge;
    private long babyTime, childTime, adultTime;
    private String type = "";
    private int[] eggLayingTimes;

    // Hunger
    private float hunger, maxHunger;
    private long hungerCounter, millisSinceFed, lastFed;

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
        // Hunger
        this.hunger = (float)stats.get("hunger");
        lastFed = (long)stats.get("lastFed");
        millisSinceFed = System.currentTimeMillis() - lastFed;
        hungerCounter = System.currentTimeMillis() - lastTimeCheck;


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

    public void setModel(Model model){
        this.model = model;

    }

    @Override
    public String toString(){
        String string = "";
        string += "Type: " + type;
        string += "\nName: " + name;
        string += "\nBirthtime: " + birthtime;
        string += "\nLast Timecheck: " + lastTimeCheck;
        string += "\nLastFed: " + lastFed;
        // string += "\nTime since last fed: " + TimeConverter.toString(hungerCounter) + " : " + hungerCounter;
        string += "\nHunger: " + hunger;
        string += "\nAge: " + TimeConverter.toString(age);
        string += "\nDays: " + getAgeInDays() + " Hours: " + getAgeInHours() + " Minutes: " + getAgeInMinutes() + " Seconds: " + getAgeInSeconds() + " Millis: " + age;

        return string;
    }

    public String getSaveFormat(){
        String string = "";
        string += type;
        string += "\n" + name;
        string += "\n" + birthtime;
        string += "\n" + lastTimeCheck;
        string += "\n" + hunger;
        string += "\n" + lastFed;

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
        /*
        Todo:
            I wanna do the math and display how I increase the hunger more
            clearly later
        */
        long timesADay = 10000;
        long step = 86400000 / timesADay;
        // long interval = System.currentTimeMillis() - lastTimeCheck;


        hungerCounter += model.getTime() - lastTimeCheck;  // Here is the issue for tomorrow *****************************************
        // millisSinceFed += model.getTime() - lastTimeCheck;
        // System.out.println(millisSinceFed + " += " + model.getTime() + " - " + lastTimeCheck);
        // System.out.println(millisSinceFed);

        while (hungerCounter >= step){    // 24 hours in milliseconds divided by 1000
            hunger -= 100.0 / (float)timesADay;
            hungerCounter -= step;
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
