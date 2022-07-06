


class TimeConverter{



    public static int millisToSeconds(long millis){
        return (int)(millis / 1000);
    }

    public static int millisToMinutes(long millis){
        return (int)(millis / 60000);
    }

    public static int millisToHours(long millis){
        return (int)(millis / 3600000);
    }

    public static int millisToDays(long millis){
        return (int)(millis / 86400000);
    }


    public static long secondsToMillis(int seconds){
        return (long)(seconds * 1000);
    }

    public static long minutesToMillis(int minutes){
        return (long)(minutes * 60000);
    }

    public static long hoursToMillis(int hours){
        return (long)(hours * 3600000);
    }

    public static long daysToMillis(int days){
        return (long)(days * 86400000);
    }


    public static String toString(long millis){
        long temp = millis;
        int days = millisToDays(temp);
        temp -= daysToMillis(days);
        int hours = millisToHours(temp);
        temp -= hoursToMillis(hours);
        int minutes = millisToMinutes(temp);
        temp -= minutesToMillis(minutes);
        int seconds = millisToSeconds(temp);
        temp -= secondsToMillis(seconds);
        int milliseconds = (int)temp;
        String string = days + "d " + hours + "h " + minutes + "m " + seconds + "s " + milliseconds + "ms";
        return string;


    }

}
