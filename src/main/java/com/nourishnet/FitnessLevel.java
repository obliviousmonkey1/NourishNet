package com.nourishnet;

public enum FitnessLevel {
	
	//Defines different food groups
	FV("Not Active"), CF("Semi-Active"), D("Active"), C("Very Active");
	//Not Active - Does not do any exercise through the week apart from normal walking. So no deliberate calories burnt through exerise 
	//Semi-Active - Up to 120 minutes of exercise per week
	//Active - Up to 240 minutes of exercise per week 
	//Very Active - More than 240 minutes per week
	private String groupStr;
	
	private FitnessLevel(String gen) {
		groupStr = gen;
	}
	
	public String toString()
	{
		return groupStr;   //Returns the string representation of a food group
	}

}
