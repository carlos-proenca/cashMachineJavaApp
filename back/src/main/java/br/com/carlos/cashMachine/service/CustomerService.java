package br.com.carlos.cashMachine.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.carlos.cashMachine.model.Customer;
import br.com.carlos.cashMachine.vo.BanknotesResponseVo;
import br.com.carlos.cashMachine.vo.MoneyRequest;

/**
 * This class contains all service methods for customer
 * 
 * @author carlos proença(carlos_proenca@live.com)
 *
 */
@Service
public interface CustomerService {
	
	/**
	 * List all customers into database
	 * 
	 * @return a list of customer stored into database
	 */
	List<Customer> listAll();
	
	/**
	 * Create a new customer into database
	 * 
	 * @param customer the customer information to be stored into database
	 * 
	 * @return the created customer
	 */
	Customer create(Customer customer);
	
	/**
	 * Update the customer information
	 * 
	 * @param customerId the customer identification to be updated
	 * @param customer the new customer information to be stored
	 * @return the customer with the new informations
	 */
	Customer update(Long customerId, Customer customer);
	
	/**
	 * Remove a customer from database
	 * 
	 * @param customerId the customer identification to be removed
	 */
	void delete(Long customerId);
	
	/**
	 * Make a cash withdrawal for a customer
	 * this method calculate how many notes the customer can take from stored balance
	 * 
	 * @param money the money to be take
	 * @return the banknotes informations that the customer will take
	 */
	BanknotesResponseVo cashOut(Long customerId, MoneyRequest money);
}