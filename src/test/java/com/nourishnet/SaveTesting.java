package com.nourishnet;

import java.util.ArrayList;
import java.util.Scanner;


public class SaveTesting {
    static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        userSaving();
        newUserSaving();
        recipeSaving();
    }


    public static void userSaving(){
        User user = ResourceLoader.loadUser(LogIn.getUserJsonPath("TestUser"));
        System.out.println(user.getUsername());

        System.out.println("Users age : " + user.getAge());

        user.setAge(user.getAge()+2);

        System.out.println("Users new age : " + user.getAge());

        SerializeJsonData.serializeUser(user, LogIn.getUserJsonPath("TestUser"));
    }

    public static void newUserSaving() {
        Scanner input = new Scanner(System.in);

        User user = new User();

        System.out.print("Enter name: ");
        String name = input.nextLine();
        user.setUsername(name);

        System.out.print("Enter age: ");
        int age = input.nextInt();
        user.setAge(age);

        System.out.print("Enter height (in meters): ");
        double height = input.nextDouble();
        user.setHeight(height);

        System.out.print("Enter weight (in kilograms): ");
        int weight = input.nextInt();
        user.setWeight(weight);

        System.out.print("Enter diet (0 for none, 1 for vegetarian, 2 for vegan, etc.): ");
        String diet = input.nextLine();
        user.setDiet(diet);

        // saved recipes don't need to be made at the start, also password is easy to do but cba
        input.close();

        SerializeJsonData.serializeNewUser(user);


    }

    public static void recipeSaving(){

        ArrayList<Ingredient> ing = ResourceLoader.loadIngredients();
        
        
        ArrayList<Recipe> recipes = ResourceLoader.loadRecipes();
    
        recipes.get(0).setIngredients(ResourceLoader.loadIngredientsIntoRecipes(recipes.get(0).getQuantitiesNames(), ing));

        recipes.get(0).setDescription("hello world");

        SerializeJsonData.serializeRecipes(recipes, Pointers.content + "/Recipes.json");
    }

}
