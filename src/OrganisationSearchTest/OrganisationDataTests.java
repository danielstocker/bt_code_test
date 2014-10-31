package OrganisationSearchTest;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import OrganisationSearch.OrganisationData;

public class OrganisationDataTests {

	public void RunTests() {
		System.out.println("Running null pointer and argument tests (2)");
		int exceptionsThrown = 0;
		try {
			new OrganisationData(null);
			
		} catch(NullPointerException e) {
			System.out.println("Passed null pointer");
			exceptionsThrown++;
		} catch (NumberFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			new OrganisationData("This will not work.txt");
			
		} catch(FileNotFoundException e) {
			System.out.println("Passed not found");
			exceptionsThrown++;
		} catch (NumberFormatException | NullPointerException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(exceptionsThrown + " out of 2 tests passed.");
		System.out.println();
		
		System.out.println("Running data tests (2)");
		int passed = 0;
		
		if(!new File("testFiles/datatest_empty.txt").exists())  {
			System.out.println("testFiles/datatest_empty.txt missing. Cannot run this test. Please run this test from the directory that contains the 'testFiles' directory.");
		} else {
			// run empty test
			try {
				new OrganisationData("testFiles/datatest_empty.txt");
				
			} catch(IOException e) {
				System.out.println("Passed");
				passed++;
			} catch (NumberFormatException | NullPointerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if(!new File("testFiles/datatest_twobosses.txt").exists())  {
			System.out.println("testFiles/datatest_twobosses.txt missing. Cannot run this test. Please run this test from the directory that contains the 'testFiles' directory.");
		} else {
			// run two bosses test
			try {
				new OrganisationData("testFiles/datatest_twobosses.txt");
				
			} catch(IOException e) {
				System.out.println("Passed");
				passed++;
			} catch (NumberFormatException | NullPointerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println(passed + " out of 2 tests passed.");
		System.out.println();
		
	}

}
