package restaurant.controller.model;

import java.util.HashSet;
import java.util.Set;

import lombok.Data;
import lombok.NoArgsConstructor;
import restaurant.entity.Customer;
import restaurant.entity.Employee;
import restaurant.entity.Restaurant;

@Data
@NoArgsConstructor
public class RestaurantData {
	
	private Long restaurantId;
	 private String restaurantName;
	 private String restaurantAddress;
	 private String restaurantCity;
	 private String restaurantState;
	 private String restaurantZip;
	 private String restaurantPhone;
	 
	
	 private Set<RestaurantCustomer> customers = new HashSet<>();
	 private Set<RestaurantEmployee> employees = new HashSet<>();

	public RestaurantData (Restaurant restaurant) {
		
		restaurantId = restaurant.getRestaurantId();
		restaurantName = restaurant.getRestaurantName();
		restaurantAddress = restaurant.getRestaurantAddress();
		restaurantCity = restaurant.getRestaurantCity();
		restaurantState = restaurant.getRestaurantState();
		restaurantZip = restaurant.getRestaurantZip();
		restaurantPhone = restaurant.getRestaurantPhone();
	
	
	for(Customer customer : restaurant.getCustomers()) {
		customers.add(new RestaurantCustomer(customer));
	}
	
	
	for(Employee employee : restaurant.getEmployees()) {
		employees.add(new RestaurantEmployee(employee));
	
	}
}
	 
	 
	 
@Data
@NoArgsConstructor
public static class RestaurantCustomer {

	private Long customerId;
	private String customerFirstName;
	private String customerLastName;
	private String customerEmail;
	
	
	public RestaurantCustomer(Customer customer) {
		
		customerId = customer.getCustomerId();
		customerFirstName = customer.getCustomerFirstName();
		customerLastName = customer.getCustomerLastName();
		customerEmail = customer.getCustomerEmail();
	
	}
}

@Data
@NoArgsConstructor
public static class RestaurantEmployee {
		
		private Long employeeId;
		private String employeeFirstName;
		private String employeeLastName;
		private String employeePhone;
		private String employeeJobTitle;
		
		
		public RestaurantEmployee(Employee employee) {

		    	employeeId = employee.getEmployeeId();
		    	employeeFirstName = employee.getEmployeeFirstName();
		    	employeeLastName = employee.getEmployeeLastName();
		    	employeePhone = employee.getEmployeePhone();
		    	employeeJobTitle = employee.getEmployeeJobTitle();
		    		
		}
}
}