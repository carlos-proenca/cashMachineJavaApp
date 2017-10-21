package br.com.carlos.cashMachine.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.carlos.cashMachine.model.Customer;
import br.com.carlos.cashMachine.service.CustomerService;
import br.com.carlos.cashMachine.vo.BanknotesResponseVo;
import br.com.carlos.cashMachine.vo.MoneyRequest;

/**
 * This class contains all endpoints for manage the customer information
 * 
 * @author carlos proença(carlos_proenca@live.com)
 *
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("api/v1/customers")
public class CustomerController {

	@Autowired
	private CustomerService customerService;

	/**
	 * This API retrieve all customers from database
	 */
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<Customer>> listAll(){
		return new ResponseEntity<List<Customer>>(customerService.listAll(), HttpStatus.OK);
	}
	
	/**
	 * Create a new customer
	 * 
	 * @param customer the customer information to be created
	 * 
	 * @return the customer created
	 */
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Customer> create(@RequestBody Customer customer){
		return new ResponseEntity<Customer>(customerService.create(customer), HttpStatus.CREATED);
	}
	
	/**
	 * Update the customer informations
	 * 
	 * @param customer the new customer informations to be updated
	 * @param customerId the customer identification
	 * @return the customer with the new informations
	 */
	@RequestMapping(value="{customerId}", method=RequestMethod.PUT)
	public ResponseEntity<Customer> update(@PathVariable("customerId") Long customerId, @RequestBody Customer customer){
		return new ResponseEntity<Customer>(customerService.update(customerId, customer), HttpStatus.OK);
	}
	
	/**
	 * Remove the customer from the system
	 * 
	 * @param customerId the customer identification to be removed
	 * 
	 */
	@ResponseStatus(value=HttpStatus.NO_CONTENT)
	@RequestMapping(value="{customerId}", method=RequestMethod.DELETE)
	public void  delete(@PathVariable("customerId") Long customerId){
		customerService.delete(customerId);
	}
	
	/**
	 * cash out customer savings
	 * 
	 * @param customerId the customer identification to be take the money
	 * @return
	 */
	@RequestMapping(value="{customerId}/cash/out", method=RequestMethod.POST)
	public ResponseEntity<BanknotesResponseVo> cashOut(@PathVariable("customerId") Long customerId, 
			@RequestBody MoneyRequest money){
		return new ResponseEntity<BanknotesResponseVo>(customerService.cashOut(customerId, money), HttpStatus.OK);
	}
}
