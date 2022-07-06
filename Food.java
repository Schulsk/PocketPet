

class Food implements Consumable{

    private double nutrition;
    private boolean eaten;

    public Food(double nutrition){
        this.nutrition = nutrition;
        eaten = false;
    }

    @Override
    public double consume(){
        if (!eaten){
            double temp = nutrition;
            nutrition = 0;
            eaten = true;
            return temp;
        }
        return 0.0;
    }

}
