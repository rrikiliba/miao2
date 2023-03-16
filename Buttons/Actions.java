package Buttons;

import Data.Ingredient;
import Data.Recipe;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Actions {

    static private Recipe recipe = new Recipe();

    public static void add(Ingredient ingredient){
        recipe.getList().add(ingredient);
    }

    public static boolean remove(Ingredient ingredient){
        return recipe.getList().remove(ingredient);
    }
    public static boolean remove(int index){
        return recipe.remove(index);
    }

    public static void convert(int ogNum, int newNum){
        recipe.setRate(((double) newNum)/((double) ogNum));
        recipe.convert();
    }

    public static String recipeToString(){
        String retval = "";
        for (Ingredient i: recipe.getList())
            retval+=i.getEntry();
        return retval;
    }

    public static String[] recipeToStringArray(){
        String[] retval = new String[recipe.getList().size()];
        int j = 0;
        for (Ingredient i: recipe.getList()){
            retval[j] = i.getEntry();
            j++;
        }
        return retval;
    }

    public static int importFromFile(File file){
        String buffer = "";
        try {
                Scanner input = new Scanner(file);
                while (input.hasNextLine()){
                    buffer = input.nextLine();
                    String[] formatted = new String[3];
                    formatted[0] = buffer.split(":")[0];
                    formatted[1] = buffer.split(": ")[1].split(",")[0];
                    if(buffer.split(", ")[1].split(".").length == 2)
                        formatted[2] = buffer.split(", ")[1].split(".")[0];
                    else
                        formatted[2] = "";
                    recipe.add(new Ingredient(formatted[0], Double.parseDouble(formatted[1]), formatted[2]));
                }
            }
            catch (FileNotFoundException e) {
                return 1;
            }
            catch (IndexOutOfBoundsException e){
                return 2;
            }
            catch (NumberFormatException e){
                return 2;
            }
        return 0;
    }
    public static String getTitle(){
        String[] titles = new String[11];
        String suffix = "Miao 2";
        titles[0]=suffix;
        titles[1]=suffix+": Electric Bogaloo";
        titles[2]=suffix+": comes with more bugs!";
        titles[3]=suffix+": our Average User Count is less than 1";
        titles[4]=suffix+": Revenge of The Mathematically Illitarate";
        titles[5]=suffix+": What Are You Cooking Today?";
        titles[6]=suffix+": I Spent More Time on These Titles than Coding.";
        titles[7]=suffix+": I Have Been Informed This is not Funny";
        titles[8]=suffix+": How you Feeling Today?";
        titles[9]=suffix+". Fun Fact: I AM Actually a Cat.";
        titles[10]=suffix+": Say hi to the Cats for me, will ya?";
        Random picker = new Random();
        return titles[picker.nextInt(10)];
    }
}
