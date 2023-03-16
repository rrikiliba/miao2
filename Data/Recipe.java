package Data;

import java.util.ArrayList;

public class Recipe {
    private double rate;
    private ArrayList<Ingredient> list;

    public Recipe(){
        this.list = new ArrayList<>();
    }

    public void add(Ingredient ingredient){
        this.getList().add(ingredient);
    }

    public boolean remove(Ingredient i){
        return this.getList().remove(i);
    }
    public boolean remove(int index){
        try {
            this.getList().remove(index);
        }
        catch (IndexOutOfBoundsException e){
            e.printStackTrace();
            return false;
        }
            return true;
    }

    public int search(Ingredient ingredient){
        return this.getList().indexOf(ingredient);
    }

    public Ingredient search(int index){
        if(index<=this.getList().size())
            return this.getList().get(index);
        else
            return null;
    }
    public ArrayList<Ingredient> getList() {
        return list;
    }

    public void setList(ArrayList<Ingredient> list) {
        this.list = list;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public void convert(){
        for (Ingredient i: this.getList()) {
            i.setQuantity(i.getQuantity() * this.getRate());
            i.setEntry();
        }
    }
}
