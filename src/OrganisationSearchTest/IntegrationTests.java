package OrganisationSearchTest;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.file.Files;

import OrganisationSearch.OrganisationSearch;

public class IntegrationTests {

	private static final String TestOutputFileName = "testOutput.txt";
	private static PrintStream old;

	public void RunTests() {
		
		int passed = 0;
		System.out.println("Running integration tests (2)");
		
		if(!new File("testFiles/datatest_multiple.txt").exists() || !new File("testFiles/mock_selection_multiple.txt").exists())  {
			System.out.println("testFiles/datatest_multiple.txt or testFiles/mock_selection_multiple.txt missing. Please run this test from the directory that contains the 'testFiles' directory.");
		} else {
			SetUpForTestRun();
			InputStream oldIn = System.in;
			
			try {
				System.setIn((new FileInputStream("testFiles/mock_selection_multiple.txt")));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
						
			new OrganisationSearch("testFiles/datatest_multiple.txt", "   Catwoman", "Hit    Girl");
			System.setIn(oldIn);
			
			System.out.flush();
			try {
				String testOutput = this.readTestOutput();
				if(testOutput.contains("Catwoman (17) -> Black Widow (6) -> Gonzo the Great (2) -> Dangermouse (1) <- Hit Girl (19)")) {
					passed++;
					System.setOut(old);
					System.out.println("String test passed");
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			CleanUpForTestRun();
		}
		
		if(!new File("testFiles/datatest_normal.txt").exists())  {
			System.out.println("testFiles/datatest_normal.txt missing. Cannot run this test. Please run this test from the directory that contains the 'testFiles' directory.");
		} else {
			SetUpForTestRun();
			new OrganisationSearch("testFiles/datatest_normal.txt", " gonzo THE gREAT", "Dangermouse    ");
			System.out.flush();
			try {
				String testOutput = this.readTestOutput();
				if(testOutput.contains("Gonzo the Great (2) -> Dangermouse (1)")) {
					passed++;
					System.setOut(old);
					System.out.println("String test passed");
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			CleanUpForTestRun();
		}
		
		System.out.println(passed + " out of 2 tests passed.");
		System.out.println();
	}
	
    private String readTestOutput() throws IOException {
		String output = "";

		BufferedReader bufferedReader = new BufferedReader(new FileReader(TestOutputFileName));
		
		String nextLine;
		while((nextLine = bufferedReader.readLine())!= null) {
			output = output + nextLine;	
		}
		
		bufferedReader.close();
		
		return output;
	}

	private static void SetUpForTestRun() {
		
		old = System.out;
		File output = new File(TestOutputFileName);
		if(output.exists()) {
			output.delete();
		}
		
		try {
			output.createNewFile();
		} catch (IOException e) {
			System.out.println("Could not set up test environment!");
			e.printStackTrace();
		}
		
		try {
			System.setOut(new PrintStream(new FileOutputStream(TestOutputFileName)));
		} catch (FileNotFoundException e) {
			System.setOut(System.out);
			System.out.println("Could not set up test environment!");
			e.printStackTrace();
		}
	}
	
	private static void CleanUpForTestRun() {
		System.setOut(old);
		File output = new File(TestOutputFileName);
		if(output.exists()) {
			output.delete();
		}
	}
}
