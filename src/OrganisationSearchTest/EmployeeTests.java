package OrganisationSearchTest;

import OrganisationSearch.Employee;

public class EmployeeTests {

	public void RunTests() {
		System.out.println("Running null pointer and argument tests (4)");
		
		int exceptionsThrown = 0;
		try {
			new Employee(0, null, 0);
		} catch(NullPointerException e) {
			System.out.println("Passed null pointer");
			exceptionsThrown++;
		}
		
		try {
			new Employee(0, " ", 0);
		} catch(IllegalArgumentException e) {
			System.out.println("Passed empty");
			exceptionsThrown++;
		}
		
		try {
			new Employee(-1, "Jessica", 0);
		} catch (NumberFormatException e) {
			System.out.println("Passed number 1");
			exceptionsThrown++;
		}
		
		try {
			new Employee(0, "Melvin", -1);
		} catch (NumberFormatException e) {
			System.out.println("Passed Number 2");
			exceptionsThrown++;
		}
		
		System.out.println(exceptionsThrown + " out of 4 tests passed.");
	}
	
}
