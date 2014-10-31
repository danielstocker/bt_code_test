package OrganisationSearch;
import java.util.ArrayList;


public class Employee {
	
	private ArrayList<Employee> reports;
	private Employee manager;
	
	private int employeeNumber;
	private int managerNumber;
	private String employeeName;
	
	public Employee(int nEmployeeNumber, String nEmployeeName, int nManagerNumber) {
		if(nEmployeeNumber < 0) {
			throw new NumberFormatException("Employee Number cannot be smaller than 0.");
		}
		
		if(nManagerNumber < 0) {
			throw new NumberFormatException("Manager Number cannot be samller than 0.");
		}
		
		if(nEmployeeName == null) {
			throw new NullPointerException();			
		}
		
		if(nEmployeeName.trim().isEmpty()) {
			throw new IllegalArgumentException();
		}
		
		this.employeeName = nEmployeeName;
		this.managerNumber = nManagerNumber;
		this.employeeNumber = nEmployeeNumber;
	}
	
	void addToTree(Employee directManager, ArrayList<Employee> directReports) {
		
		// no checks here as they can be null
		this.manager = directManager;
		this.reports = directReports;
	}

	public int getManagerNumber() {
		return this.managerNumber;
	}

	public int getEmployeeNumber() {
		return this.employeeNumber;
	}

	public String getEmployeeName() {
		return this.employeeName;
	}

	public ArrayList<Employee> getReports() {
		return this.reports;
	}

	public Employee getManager() {
		return this.manager;
	}

}
