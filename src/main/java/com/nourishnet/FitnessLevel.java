
public enum FitnessLevel {
	
	//Defines different food groups
	FV("Fruit & Veg"), CF("Composite Food"), D("Dairy"), C("Cereal"), P("Protein"), S("Sugar"), F("Fat"), SH("Spice & Herb"), EN("Essential Nutrient"), N("Null");
	
	private String groupStr;
	
	private FitnessLevel(String gen) {
		groupStr = gen;
	}
	
	public String toString()
	{
		return groupStr;   //Returns the string representation of a food group
	}

}
