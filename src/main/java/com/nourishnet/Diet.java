package com.nourishnet;

import java.util.ArrayList;

public class Diet {

    private static int nextID = 0;
    private int ID;

    private String name;

    private int ageRange[] = new int[2]; 
    //ageRange will be a variable that contains 2 different integers, the lower bracket and upper bracket of the range of ages that the diet applies to. 
    //  for example, a keto diet might only apply to people aged 21-56, so the values held would be [21, 56]

    private ArrayList<Recipe> recipes = new ArrayList<Recipe>();


    public Diet(String name, int[] ageRange, ArrayList<Recipe> recipes)
    {
        this.name = name; 
        this.ageRange = ageRange;
        this.recipes = recipes;

        this.ID = nextID;
        nextID++;
    }


}

    
