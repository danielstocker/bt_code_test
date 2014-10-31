package OrganisationSearchTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

public class TestRunner {

	public static void main(String[] args) {
		new TestRunner().RunTests();
	}

	private void RunTests() {
		
		System.out.println("Test Routine started. This program should be run from the folder that contains 'testFiles/'");
		System.out.println();
		
		System.out.println("Now testing the employee class on its own");
		new EmployeeTests().RunTests();
		System.out.println("DONE");
		System.out.println();
		
		System.out.println("Now testing the data class");
		new OrganisationDataTests().RunTests();
		System.out.println("DONE");
		System.out.println();
		
		System.out.println("Now testing the main program and the underlying data class");
		new IntegrationTests().RunTests();
		System.out.println("DONE");
		System.out.println();
		
		
	}

}
