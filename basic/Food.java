

class Food implements Consumable{

    private long nutrition;
    private boolean eaten;

    public Food(long nutrition){
        this.nutrition = nutrition;
        eaten = false;
    }

    @Override
    public long consume(){
        if (!eaten){
            long temp = nutrition;
            nutrition = 0;
            eaten = true;
            return temp;
        }
        return 0;
    }

}
