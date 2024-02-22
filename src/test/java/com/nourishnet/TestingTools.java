package com.nourishnet;

public class TestingTools {
	
    // This method checks if the expected and actual values are equal; throws AssertionError if not
	public static void AssertEquals(Object expected, Object actual) {
        if (!expected.equals(actual)) {
            throw new AssertionError("Expected: " + expected + ", Actual: " + actual);
        }
    }
}
