


class Test_Inventory{

    public static void main(String[] args){
        Inventory inv = new Inventory(3);
        long time = System.currentTimeMillis();

        Pet.setPetCount(100);

        Egg egg00 = new TestEgg01(time, "null");
        Egg egg01 = new TestEgg01(time, "null");
        Egg egg02 = new TestEgg01(time, "null");
        Egg egg03 = new TestEgg01(time, "null");

        Saver.savePet(egg00);
        Saver.savePet(egg01);
        Saver.savePet(egg02);
        Saver.savePet(egg03);

        String egg00SaveFile = egg00.getSavefileName();
        String egg01SaveFile = egg01.getSavefileName();
        String egg02SaveFile = egg02.getSavefileName();
        String egg03SaveFile = egg03.getSavefileName();

        inv.put(egg00SaveFile);

        System.out.println(inv);

        inv.put(egg01SaveFile);
        inv.put(egg02SaveFile);
        inv.put(egg03SaveFile);
        egg00.setName("00");
        egg01.setName("01");
        egg02.setName("02");
        egg03.setName("03");

        System.out.println(inv);
        System.out.println("Pet count: " + Pet.getPetCount());
    }
}
