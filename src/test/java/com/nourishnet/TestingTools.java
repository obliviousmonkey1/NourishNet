package com.nourishnet;

import java.io.File;

public class TestingTools {
	
    // This method checks if the expected and actual values are equal; throws AssertionError if not
	public static void AssertEquals(Object expected, Object actual) {
        if (!expected.equals(actual)) {
            throw new AssertionError("Expected: " + expected + ", Actual: " + actual);
        }
    }

    public static void assertFileExistence(String filePath, Boolean expectedToExist) {
        File fileToCheck = new File(filePath);

        if (expectedToExist) {
            if (!fileToCheck.exists()) {
                throw new AssertionError("File does not exist: " + filePath);
            }
        } else {
            if (fileToCheck.exists()) {
                throw new AssertionError("File exists: " + filePath);
            }
        }
    }

}
