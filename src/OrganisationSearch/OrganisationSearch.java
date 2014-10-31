package OrganisationSearch;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;


public class OrganisationSearch {

	private Employee firstEmployee;
	private Employee secondEmployee;
	private OrganisationData organisationData;
	
	public OrganisationSearch(String inputFileName, String firstEmployeeName, String secondEmployeeName) {
		// none of the inputs should be null or empty
		if(inputFileName == null || firstEmployeeName == null || secondEmployeeName == null) {
			System.out.println("One or more of the supplied arguments are invalid.");
			System.out.println("This program is expecting three input arguments. Please call it on the command line like so: java OrganisationSearch.OrganisationSearch YourInputFileName Employee1 Employee2");
		    return;
		}
		
		// trim leading and trailing whitespace
		inputFileName = inputFileName.trim();
		firstEmployeeName = firstEmployeeName.trim();
		secondEmployeeName = secondEmployeeName.trim();
		
		// does the input file exist and is it accessible?
		if (!new File(inputFileName).exists()) {
			System.out.println("The program cannot access or find the input file specified as the first argument: " + inputFileName);
			System.out.println("Please make sure that this file exists. Try specifying an absolute path if you have previously used a local path.");
			System.out.println("This program is expecting three input arguments. Please call it on the command line like so: java OrganisationSearch.OrganisationSearch YourInputFileName Employee1 Employee2");
		    return;
		}
		
		// reduce whitespace to max 1 for names so they can be more easily matched with the input
		// we will do the same with the input to make it easier to generate matches
		firstEmployeeName = OrganisationSearch.reduceWhitespace(firstEmployeeName);
		secondEmployeeName = OrganisationSearch.reduceWhitespace(secondEmployeeName);
		
		if(inputFileName.isEmpty() || firstEmployeeName.isEmpty() || secondEmployeeName.isEmpty()) {
			System.out.println("One or more of the supplied arguments are empty.");
			System.out.println("This program is expecting three input arguments. Please call it on the command line like so: java OrganisationSearch.OrganisationSearch YourInputFileName Employee1 Employee2");
		    return;
		}
		
		try {
			this.organisationData = new OrganisationData(inputFileName);
		} catch (Exception e) {
			System.out.println("A problem has occured while trying to load the input data from file: " + inputFileName);
			e.printStackTrace();
			return;
		}
		
		ArrayList<Employee> resultsForFirst = this.organisationData.findEmployeesWithName(firstEmployeeName);
		
		if(resultsForFirst.size() == 0) {
			System.out.println("Could not match the name " + firstEmployeeName + " to any employees in the organisation.");			
			return;
		} else if (resultsForFirst.size() == 1) {
			firstEmployee = resultsForFirst.get(0);
		} else {
			firstEmployee = this.offerEmployeeChoice(firstEmployeeName, resultsForFirst);
		}
		
		ArrayList<Employee> resultsForSecond = this.organisationData.findEmployeesWithName(secondEmployeeName);
	
		if(resultsForSecond.size() == 0) {
			System.out.println("Could not match the name " + secondEmployeeName + " to any employees in the organisation.");			
			return;
		} else if (resultsForSecond.size() == 1) {
			secondEmployee = resultsForSecond.get(0);
		} else {
			secondEmployee = this.offerEmployeeChoice(secondEmployeeName, resultsForSecond);
		}
		
		if(firstEmployee.getEmployeeNumber() == secondEmployee.getEmployeeNumber()) {
			System.out.println("Both records point to the same person: " + firstEmployee.getEmployeeName() + " (" + firstEmployee.getEmployeeNumber() + ")");
			return;
		} 
		
		String path = this.findStringPath(firstEmployee, secondEmployee, "", new ArrayList<Integer>());
		
		if(path != null) {
			System.out.println(path);
		} else {
			System.out.println("There is no path between the employees " + firstEmployeeName + " and " + secondEmployeeName + ". Maybe they are not part of the same org?");
		}
	}

	private String findStringPath(Employee currentNode, Employee goal, String currentPath, ArrayList<Integer> visited) {
		visited.add(currentNode.getEmployeeNumber());
		currentPath = currentPath + currentNode.getEmployeeName() + " (" + currentNode.getEmployeeNumber() + ") ";

		// is the goal one of the direct reports?
		ArrayList<Employee> currentReports = currentNode.getReports();
		if(currentReports != null) {
			for (Employee currentReport : currentReports) {
				if(currentReport.getEmployeeNumber() == goal.getEmployeeNumber()) {
					// if match return path
					currentPath = currentPath + "<- " + goal.getEmployeeName() + " (" + goal.getEmployeeNumber() + ") ";
					return currentPath;
				} else {
					// if no match continue to explore direct reports
					if(!visited.contains(currentReport.getEmployeeNumber())) {
						String tryFind = findStringPath(currentReport, goal, currentPath + "<- ", visited);
						if(tryFind != null) {
							return tryFind; 
						} else {
							visited.add(new Integer(currentReport.getEmployeeNumber()));
						}
					}
				}
			}
		}
		
		// is the goal near this node?
		// is it the manager? have we visited them yet?
		// or are we currently at the big boss?
		if(currentNode.getManagerNumber() != 0) {
			if(currentNode.getManager().getEmployeeNumber() == goal.getEmployeeNumber()) {
				currentPath = currentPath + "-> " + goal.getEmployeeName() + " (" + goal.getEmployeeNumber() + ") ";
				return currentPath;
			} else {
				if(visited.contains(currentNode.getManager().getEmployeeNumber())) {
					return null;
				} else {
					return findStringPath(currentNode.getManager(), goal, currentPath + "-> ", visited);
				}
			}
		}
		
		return null;
	}

	private Employee offerEmployeeChoice(String name, ArrayList<Employee> results) {
		System.out.println(name + " has more than one match in the organisation. These are the matches that we found:");
		
		for(int iterator = 0; iterator < results.size(); iterator++) {
			Employee thisEmployee = results.get(iterator);
			System.out.println("[" + iterator + "] " + thisEmployee.getEmployeeName() + " (" + thisEmployee.getEmployeeNumber() + ") ");
		}
		
		System.out.println("Please enter one of the numbers shown in square brackets ([0]-[" + (results.size()-1) + "]) in the list above to select an employee. ");
		
		Scanner s = new Scanner(System.in);
		while(!s.hasNextInt()) {}
		
		int selected = s.nextInt();
		if(selected < 0 || selected >= results.size()) {
			System.out.println("This did not work... Please try again:");
			return offerEmployeeChoice(name, results);
		}
		
		System.out.println("Employee selected successfully!");
		return results.get(selected);
	}

	static String reduceWhitespace(String input) {
		
		if(input == null) {
			return input;
		}
		
		if(input.isEmpty()) {
			return input;
		}
		
		boolean lastCharacterWasWhitespace = false;
		String output = "";
		
		for (int i = 0; i < input.length(); i++) {
			char currentCharacter = input.charAt(i);
		    if (Character.isWhitespace(currentCharacter)) {
		        if(!lastCharacterWasWhitespace) {
		        	// keep only one whitespace
		        	output = output + currentCharacter;
		        }
		        lastCharacterWasWhitespace = true;
		    } else {
		    	lastCharacterWasWhitespace = false;
		    	output = output + currentCharacter;
		    }
		}
		
		return output;
	}

	public static void main(String[] args) {
		// check if the arguments where supplied in the expected way
		if(args.length != 3) {
			// we are expecting 3 arguments
			System.out.println("This program is expecting three input arguments. Please call it on the command line as follows: java OrganisationSearch.OrganisationSearch YourInputFileName Employee1 Employee2");
		    return;
		}
		
		// find the inputs
		// we will verify this in the start up process of the program
		String inputFileName = args[0]; 
		String firstEmployeeName = args[1];
		String secondEmployeeName = args[2];
		
		// start the program
		new OrganisationSearch(inputFileName, firstEmployeeName, secondEmployeeName);
	}

}
