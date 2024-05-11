package restaurant.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import restaurant.controller.model.RestaurantData;
import restaurant.controller.model.RestaurantData.RestaurantCustomer;
import restaurant.controller.model.RestaurantData.RestaurantEmployee;
import restaurant.service.RestaurantService;

@RestController
@RequestMapping("/restaurant")
@Slf4j
public class RestaurantController {
	
	@Autowired
	private RestaurantService restaurantService;

	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
    public RestaurantData createRestaurant(@RequestBody RestaurantData restaurantData) {
        log.info("Creating restaurant: {}", restaurantData);
        return restaurantService.saveRestaurant(restaurantData);
        
}
	@PutMapping("/{restaurantId}")
	public RestaurantData updateRestaurant(@RequestBody RestaurantData restaurantData, @PathVariable Long restaurantId) {
		restaurantData.setRestaurantId(restaurantId);
		log.info("Updating restaurant: {}", restaurantData);
        return restaurantService.saveRestaurant(restaurantData);
        
}
	
	@PostMapping("{restaurantId}/employee")
    @ResponseStatus(HttpStatus.CREATED)
    public RestaurantEmployee addEmployeeToRestaurant(@PathVariable Long restaurantId, 
                                                  @RequestBody RestaurantEmployee employee) {
        log.info("Received request to add employee to Restaurant with ID: {}", restaurantId);
        return restaurantService.saveEmployee(restaurantId, employee);
	

}
	
	@PostMapping("{restaurantId}/customer")
    @ResponseStatus(HttpStatus.CREATED)
    public RestaurantCustomer addCustomerToRestaurant(@PathVariable Long restaurantId, 
                                                  @RequestBody RestaurantCustomer customer) {
        log.info("Received request to add customer to Restaurant with ID: {}", restaurantId);
        return restaurantService.saveCustomer(restaurantId, customer);
	
	}
	
	 @GetMapping
	    public List<RestaurantData> retrieveAllRestaurants() {  
		 return restaurantService.retrieveAllRestaurants();	 
	 }
	 
	 @GetMapping("{restaurantId}")
	    public RestaurantData retrieveRestaurantById(@PathVariable Long restaurantId) {
	     log.info("Retrieving restaurant with ID={}", restaurantId);
		 return restaurantService.retrieveRestaurantById(restaurantId);
}
	 
	 @DeleteMapping("/restaurant")
	 public void deleteRestaurantById() {
		 log.info("Attempting to delete all restaurants");
		 throw new UnsupportedOperationException(
				 "Deleting all restaurants is not allowed.");
		 
	 }

	 @DeleteMapping("{restaurantId}")
	 
	 public Map<String, String> deleteRestaurantById(@PathVariable Long restaurantId){
		 log.info("Deleting restaurant with ID={}", restaurantId);
		 
		 restaurantService.deleteRestaurantById(restaurantId);
		 
		 return Map.of("message", "Deletion of restaurant with ID=" + restaurantId + " was successful.");
	 }
	 



}
