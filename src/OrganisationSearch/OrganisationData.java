package OrganisationSearch;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class OrganisationData {
	
	private ArrayList<Employee> employees;
	
	// the root of the tree that we are building
	private Employee bigBoss;
	
	public OrganisationData(String inputFileName) throws FileNotFoundException, NullPointerException, NumberFormatException, IOException {
		this.bigBoss = null;
		
		if(inputFileName == null) {
			throw new NullPointerException();
		}
		
		File inputFile = new File(inputFileName);
		
		if(!inputFile.exists()) {
			throw new FileNotFoundException();
		}
		
		employees = new ArrayList<Employee>();
		
		BufferedReader bufferedReader = null;
		int lineNumber = 0;
		 
		try {
 
			String currentLine;
 
			bufferedReader = new BufferedReader(new FileReader(inputFileName));
 
			while ((currentLine = bufferedReader.readLine()) != null) {
				if(lineNumber != 0) {
					// do not do anything with the first line as the input is required to have a header line
					
					// is the line in the required format
					if(!currentLine.contains("|")){
						System.out.println("Warning! Had to skip a line while parsing the input file, because it did not include the separation character '|'");
						System.out.println("Line: " + currentLine + " this is line number: " + (lineNumber + 1));
						lineNumber++;
						continue;
					}
					
					// a correctly formated line should look like this
					//   | Employee ID | Name            | Manager ID |
					// leading to an array of
					// { nothing, int as string, string as string, int or nothing for big boss as string, nothing}
					// checking if this structure is correct
					String[] currentLineParts = currentLine.split("\\|");
					
					// required size at least 4
					if(currentLineParts.length < 4) {
						System.out.println("Warning! Had to skip a line while parsing the input file, because it did not seem to be in the correct format.");
						System.out.println("The expected format is: '| Employee ID | Name | Manager ID |'");
						System.out.println("Line: " + currentLine + " this is line number: " + (lineNumber + 1));
						lineNumber++;
						continue;
					}
					
					int currentEmployeeNumber = (-1);
					try {
						currentEmployeeNumber = Integer.parseInt(currentLineParts[1].trim());
						if(currentEmployeeNumber < 0) {
							throw new NumberFormatException("Number has to be > 0.");
						}
					}
					catch(NumberFormatException e) {
						System.out.println("Warning! Had to skip a line while parsing the input file, because it did not seem to be in the correct format. The employee ID in this line could not be converted to a number or is below 0.");
						System.out.println("The expected format is: '|Employee ID|Name|Manager ID|'");
						System.out.println("Line: " + currentLine + " this is line number: " + (lineNumber + 1));
						lineNumber++;
						continue;
					}
					
					int currentManagerNumber = (-1);
					
					if(currentLineParts[3].trim().isEmpty()) {
						// this is the big boss
						currentManagerNumber = 0;
					} else {
						try {
							currentManagerNumber = Integer.parseInt(currentLineParts[3].trim());
							if(currentManagerNumber < 1) {
								throw new NumberFormatException("Number has to be > 1.");
							}
						}
						catch(NumberFormatException e) {
							System.out.println("Warning! Had to skip a line while parsing the input file, because it did not seem to be in the correct format. The manager ID in this line could not be converted to a number or is below 1, and the field was not empty.");
							System.out.println("The expected format is: '|Employee ID|Name|Manager ID|'");
							System.out.println("Line: " + currentLine + " this is line number: " + (lineNumber + 1));
							lineNumber++;
							continue;
						}
					}
					
					String currentEmployeeName = currentLineParts[2];
					currentEmployeeName = OrganisationSearch.reduceWhitespace(currentEmployeeName.trim());
					
					if (currentEmployeeName.isEmpty()) {
						System.out.println("Warning! Had to skip a line while parsing the input file, because it did not seem to be in the correct format. The employee name is empty.");
						System.out.println("The expected format is: '|Employee ID|Name|Manager ID|'");
						System.out.println("Line: " + currentLine + " this is line number: " + (lineNumber + 1));
						lineNumber++;
						continue;
					}
					
					employees.add(new Employee(currentEmployeeNumber, currentEmployeeName, currentManagerNumber));
					if(currentManagerNumber == 0) {
						if(bigBoss != null) {
							System.out.println("More than one big boss specified. Input file invalid. Only one employee can be the boss.");
							
							// abort
							employees = new ArrayList<Employee>();
							break;
						}
						
						bigBoss = employees.get(employees.size()-1);
					}
				}
				lineNumber++;
			}
			
			if(employees.size() < 1) {
				// we could not find any employees 
				// either the format isn't right
				// or the input is empty
				throw new IOException("The input file " + inputFileName + " does not seem to be in the correct format. This program expects the input file to have a header line. The input has either not got a header line or is empty. ");	
			}
			
			this.BuildTree(bigBoss);
 
		} catch (IOException e) {
			throw e;
		} finally {
			try {
				if (bufferedReader != null)bufferedReader.close();
			} catch (IOException ex) {
				throw ex;
			}
		}
	}

	private void BuildTree(Employee currentNode) {
		
		Employee manager = this.getEmployeeWithNumber(currentNode.getManagerNumber());
		ArrayList<Employee> reports = this.getReportsOfEmployeeWithNumber(currentNode.getEmployeeNumber());
		
		currentNode.addToTree(manager, reports);
		
		// stop with this branch when a leaf is reached
		if(reports == null) {
			return;
		}
		
		// continue until a leaf is reached
		for(Employee report : reports){
			BuildTree(report, currentNode);
		}
	}
	
	private void BuildTree(Employee currentNode, Employee knownManager) {
		ArrayList<Employee> reports = this.getReportsOfEmployeeWithNumber(currentNode.getEmployeeNumber());
		currentNode.addToTree(knownManager, reports);
		
		// stop with this branch when a leaf is reached
		if(reports == null) {
			return;
		}
		
		// continue until a leaf is reached
		for(Employee report : reports){
			BuildTree(report, currentNode);
		}
	}
	
	public ArrayList<Employee> findEmployeesWithName(String name) {
		name = OrganisationSearch.reduceWhitespace(name.trim());
		
		ArrayList<Employee> returnList = new ArrayList<Employee>();
		for(Employee thisEmployee : this.employees) {
			if(thisEmployee.getEmployeeName().toLowerCase().equals(name.toLowerCase())) {
				returnList.add(thisEmployee);
			}
		}
		
		return returnList;
	}

	private ArrayList<Employee> getReportsOfEmployeeWithNumber(int eNumber) {
		ArrayList<Employee> reports = new ArrayList<Employee>();
		
		for(Employee thisEmployee : employees) {
			if(thisEmployee.getManagerNumber() == eNumber) {
				reports.add(thisEmployee);
			}
		}
		
		if(reports.size() == 0) {
			return null;
		} else {
			return reports;
		}
	}

	private Employee getEmployeeWithNumber(int eNumber) {
		for(Employee thisEmployee : employees) {
			if(thisEmployee.getEmployeeNumber() == eNumber) {
				return thisEmployee;
			}
		}
		return null;
	}
}
