package com.nourishnet;

import java.util.ArrayList;

public class tomsTesting {
    private static ArrayList<Recipe> recipe = ResourceLoader.loadRecipes();

    public static void main(String[] args) {
        System.out.println(recipe.get(0).generateImageName());
   
    }
}
