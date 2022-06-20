package com.metro.app;

import java.io.File;

import org.junit.Test;

public class MetroBillingSystemTest {

	@Test
	public void successFileTest() {
		MetroBillingSystem.main(new String[] { "tapsSuccessTest.csv" });
		File file = new File("trips.csv");
		if(file.exists()) {
			file.delete();
			assert(true);
		}
		else
			assert(false);
	}

	@Test
	public void failureFileWithZeroRecordsTest() {
		MetroBillingSystem.main(new String[] { "tapsFailureTest.csv" });
		File file = new File("trips.csv");
		if(file.exists()) 
			assert(false);
		else
			assert(true);
	}
	
	@Test
	public void failureFileFalseRecordsTest() {
		MetroBillingSystem.main(new String[] { "tapsFailureWithFalseRecordsTest.csv" });
		File file = new File("trips.csv");
		if(file.exists()) 
			assert(false);
		else
			assert(true);
	}
	
	@Test
	public void failureBlankFileTest() {
		MetroBillingSystem.main(new String[] { "tapsBlankFileTest.csv" });
		File file = new File("trips.csv");
		if(file.exists()) 
			assert(false);
		else
			assert(true);
	}
}
