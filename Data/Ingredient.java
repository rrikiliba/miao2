package Data;

import java.util.Locale;

public class Ingredient {
    private String name;
    private double quantity;
    private String unit;
    private String entry;

    public Ingredient(String name,double quantity, String unit){
        this.setName(name.toLowerCase());
        this.setQuantity(quantity);
        this.setUnit(unit.toLowerCase());
        this.setEntry();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public void setEntry(){
        this.entry = this.name + ":\t" + this.quantity + " " + this.unit + "\n";
    }

    public String getEntry(){
        return this.entry;
    }
}
