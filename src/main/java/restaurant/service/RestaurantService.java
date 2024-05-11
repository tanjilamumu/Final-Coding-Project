package restaurant.service;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import restaurant.controller.model.RestaurantData;
import restaurant.controller.model.RestaurantData.RestaurantCustomer;
import restaurant.controller.model.RestaurantData.RestaurantEmployee;
import restaurant.dao.CustomerDao;
import restaurant.dao.EmployeeDao;
import restaurant.dao.RestaurantDao;
import restaurant.entity.Customer;
import restaurant.entity.Employee;
import restaurant.entity.Restaurant;

@Service
public class RestaurantService {
	
	@Autowired
	private CustomerDao customerDao;
	
	@Autowired
	private EmployeeDao employeeDao;
	
	@Autowired
	private RestaurantDao restaurantDao;

	public RestaurantData saveRestaurant(RestaurantData restaurantData) {
		 Long restaurantId = restaurantData.getRestaurantId();
		 Restaurant restaurant = findOrCreateRestaurant(restaurantId);
	      
	      copyRestaurantFields(restaurant, restaurantData);
	      return new RestaurantData(restaurantDao.save(restaurant));
	      
		}
		
	      private void copyRestaurantFields(Restaurant restaurant, RestaurantData restaurantData) {
	    	  restaurant.setRestaurantName(restaurantData.getRestaurantName());
	    	  restaurant.setRestaurantAddress(restaurantData.getRestaurantAddress());
	    	  restaurant.setRestaurantCity(restaurantData.getRestaurantCity());
	    	  restaurant.setRestaurantState(restaurantData.getRestaurantState());
	    	  restaurant.setRestaurantZip(restaurantData.getRestaurantZip());
	    	  restaurant.setRestaurantPhone(restaurantData.getRestaurantPhone());
	      
	      }

		private Restaurant findOrCreateRestaurant(Long restaurantId) {
			Restaurant restaurant;
	      
	      if(Objects.isNull(restaurantId)) {
	    	  restaurant = new Restaurant();
	      }
	      else {
	    	  restaurant = findRestaurantById(restaurantId);
	    	  
	      }

	      return restaurant;
		}


		private Restaurant findRestaurantById(Long restaurantId) {
			return restaurantDao.findById(restaurantId).orElseThrow(() -> new NoSuchElementException("Restaurant with ID=" + restaurantId + " was not found"));
		}


		@Transactional(readOnly = false)
		public RestaurantEmployee saveEmployee(Long restaurantId, RestaurantEmployee restaurantEmployee) {
			
			Restaurant restaurant = findRestaurantById(restaurantId);
			Long employeeId = restaurantEmployee.getEmployeeId();
			Employee employee = findOrCreateEmployee(restaurantId, employeeId);
			
	        copyEmployeeFields(employee, restaurantEmployee);
			
	        employee.setRestaurant(restaurant);
	        restaurant.getEmployees().add(employee);
			
	        return new RestaurantEmployee(employeeDao.save(employee));
		}
		
	    private void copyEmployeeFields(Employee employee, RestaurantEmployee restaurantEmployee) {
	    	
	    	employee.setEmployeeId(restaurantEmployee.getEmployeeId());
	        employee.setEmployeeFirstName(restaurantEmployee.getEmployeeFirstName());
	        employee.setEmployeeLastName(restaurantEmployee.getEmployeeLastName());
	        employee.setEmployeePhone(restaurantEmployee.getEmployeePhone());
	        employee.setEmployeeJobTitle(restaurantEmployee.getEmployeeJobTitle());

	    	
		}

		@Transactional(readOnly = false)
		private Employee findOrCreateEmployee(Long restaurantId, Long employeeId) {
			if (employeeId == null) {
	            return new Employee();
	        } else {
	            return findEmployeeById(restaurantId, employeeId);
	        }
	}

	    
	    @Transactional(readOnly = false)
		private Employee findEmployeeById(Long restaurantId, Long employeeId) {
			  Employee employee = employeeDao.findById(employeeId)
		                .orElseThrow(() -> new NoSuchElementException("Employee not found"));
			  if (employee.getRestaurant().getRestaurantId() != (restaurantId)) {
		            throw new IllegalArgumentException("Employee does not belong to the given restaurant");
		        }
			  
			  
		        return employee;
		    }

		@Transactional(readOnly = false)
		public RestaurantCustomer saveCustomer(Long restaurantId, RestaurantCustomer restaurantCustomer) {
				

			Restaurant restaurant = findRestaurantById(restaurantId);
			
			Long customerId = restaurantCustomer.getCustomerId();
			Customer customer = findOrCreateCustomer(restaurantId, customerId);
			
	        copyCustomerFields(customer, restaurantCustomer);
				
				
				customer.getRestaurants().add(restaurant);
				restaurant.getCustomers().add(customer);
				
		        return new RestaurantCustomer(customerDao.save(customer));	
			}
			
		    private void copyCustomerFields(Customer customer, RestaurantCustomer restaurantCustomer) {
		    	
		    	customer.setCustomerId(restaurantCustomer.getCustomerId());
		    	customer.setCustomerFirstName(restaurantCustomer.getCustomerFirstName());
		    	customer.setCustomerLastName(restaurantCustomer.getCustomerLastName());
		    	customer.setCustomerEmail(restaurantCustomer.getCustomerEmail());
	 	
			}

			@Transactional(readOnly = false)
			private Customer findOrCreateCustomer(Long restaurantId, Long customerId) {
				if (customerId == null) {
		            return new Customer();
		        } else {
		            return findCustomerById(restaurantId, customerId);
		        }
		}

		    
		    @Transactional(readOnly = false)
			private Customer findCustomerById(Long restaurantId, Long customerId) {
				  Customer customer = customerDao.findById(customerId)
			                .orElseThrow(() -> new NoSuchElementException("Customer not found"));
			       
			        
			      boolean found = false;
				  for (Restaurant restaurant: customer.getRestaurants()){
				  if (restaurant.getRestaurantId()== restaurantId){
				  found = true;
				  break;
				  }
		    }
				  
		    if (!found){
				  throw new IllegalArgumentException("Customer does not belong to the given restaurant");
				  }
				  return customer;
				  }
		    
		    
		    
		    @Transactional
			public List<RestaurantData> retrieveAllRestaurants() {
		        List<Restaurant> restaurants = restaurantDao.findAll();
		        List<RestaurantData> result = new LinkedList<>();
		    	
		    	for(Restaurant restaurant : restaurants) {
		    		RestaurantData rd = new RestaurantData(restaurant);
		    				
		    				rd.getCustomers().clear();
		    				rd.getEmployees().clear();
		    				
		    		result.add(rd);
		    	}
		    	return result;
			}
		    
		    @Transactional
			public RestaurantData retrieveRestaurantById(Long restaurantId) {
		    	Restaurant restaurant = findRestaurantById(restaurantId);
				
				return new RestaurantData(restaurant);
			}
		    
		    @Transactional
			public void deleteRestaurantById(Long restaurantId) {
		    	Restaurant restaurant = findRestaurantById(restaurantId);
		    	restaurantDao.delete(restaurant);
			}

		}
		
		
		
