package basic;// THis is a bad idea, but here I have constants that are defined once and used
// other places, like Saver and Loader classes

public class General{
    // these used to be constants, but are now not, because android has weird restrictions on file
    // manipulation
    public static String petSavefileDirectory = "savefiles/pets/";
    public static String inventorySavefileDirectory = "savefiles/";
    public static String stateSavefileDirectory = "savefiles/";

    public static void setPetSavefileDirectory(String newPath) {petSavefileDirectory = newPath;}

    public static String getPetSavefileDirectory() {return petSavefileDirectory;}

    public static void setInventorySavefileDirectory(String newPath) {inventorySavefileDirectory = newPath;}

    public static String getInventorySavefileDirectory() {return inventorySavefileDirectory;}

    public static void setStateSavefileDirectory(String newPath) {stateSavefileDirectory = newPath;}

    public static String getStateSavefileDirectory() {return stateSavefileDirectory;}
}
