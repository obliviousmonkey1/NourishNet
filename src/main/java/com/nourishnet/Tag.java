package com.nourishnet;
import java.util.ArrayList;

public class Tag {
    private ArrayList<String> tagNames = new ArrayList<String>(); //an array that holds the names of the different types of tags
    //it should hold "Low calories", "Medium Calories", "High Calories", "Vegan", etc descriptive tags
    // but also all the names of the diets instantiated - this is done through the constructor

    public Tag(ArrayList<String> dietNames, ArrayList<String> generalNames) {
        populateWithDietNames(dietNames);
        populateWithGeneralNames(generalNames);
    }

    private void populateWithDietNames(ArrayList<String> dietNames) {

        for (int i = 0; i < dietNames.size(); i++) {
            tagNames.add(dietNames.get(i));
        }
    }

    private void populateWithGeneralNames(ArrayList<String> generalNames)
    {
        for (int i = 0; i < generalNames.size(); i++) {
            tagNames.add(generalNames.get(i));
        }
    }

}
