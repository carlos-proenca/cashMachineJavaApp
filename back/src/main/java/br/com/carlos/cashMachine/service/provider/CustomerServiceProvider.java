package br.com.carlos.cashMachine.service.provider;

import static br.com.carlos.cashMachine.util.MapperUtils.changeValueIfExists;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.carlos.cashMachine.model.Customer;
import br.com.carlos.cashMachine.repository.CustomerRepository;
import br.com.carlos.cashMachine.service.CustomerService;
import br.com.carlos.cashMachine.util.BanknotesType;
import br.com.carlos.cashMachine.util.exception.CustomerNotFoundException;
import br.com.carlos.cashMachine.util.exception.InvalidRequestedMoneyValueException;
import br.com.carlos.cashMachine.util.exception.UnauthorizedWithdrawalException;
import br.com.carlos.cashMachine.vo.BanknoteVo;
import br.com.carlos.cashMachine.vo.BanknotesResponseVo;
import br.com.carlos.cashMachine.vo.MoneyRequest;

/**
 * This class implements all service methods of Customer
 * 
 * @author carlos proença(carlos_proenca@live.com)
 *
 */
@Service
public class CustomerServiceProvider implements CustomerService{
	
	@Autowired
	private CustomerRepository customerRepository;

	@Override
	public List<Customer> listAll() {
		return customerRepository.findAll();
	}

	@Override
	public Customer create(Customer customer) {
		return customerRepository.save(customer);
	}

	@Override
	public Customer update(Long customerId, Customer customer) {
		Customer customerFound = findCustomer(customerId);
		mapCustomerToUpdate(customerFound, customer);
		return customerRepository.save(customerFound);
	}

	@Override
	public void delete(Long customerId) {
		Customer customerFound = findCustomer(customerId);
		customerRepository.delete(customerFound);
	}
	
	@Override
	public BanknotesResponseVo cashOut(Long customerId, MoneyRequest money) {
		
		Customer customer = findCustomer(customerId);
		validateMoney(money);
		Double totalAmount = money.getMoneyRequested();
		
		validateCustomerBalance(totalAmount, customer);
		
		List<BanknoteVo> banknoteList = new ArrayList<BanknoteVo>();
		
		for (BanknotesType banknoteType : BanknotesType.values()) {
			BanknoteVo banknote = new BanknoteVo();
			banknote.setType(banknoteType.getDescription());
			Integer quantityNotes = calculateQuantityNotes(banknoteType, totalAmount);
			banknote.setQuantity(quantityNotes);
			totalAmount -= (quantityNotes * banknoteType.getValue());
			banknoteList.add(banknote);
		}

		Double remaingBalance = customer.getBalance() - money.getMoneyRequested();
		customer.setBalance(remaingBalance);
		customerRepository.save(customer);

		BanknotesResponseVo banknotes = new BanknotesResponseVo();
		banknotes.setTotalRequested(money.getMoneyRequested());
		banknotes.setBanknotes(banknoteList);
		banknotes.setCustomerCurrentBalance(remaingBalance);
		return banknotes;
	}

	/**
	 * validate the customer money information
	 * 
	 * @param money the money to be validated
	 */
	private void validateMoney(MoneyRequest money) {
		if(money == null || money.getMoneyRequested() == null) {
			throw new InvalidRequestedMoneyValueException("The money value is invalid or not informed! try again please.");
		} else if(money.getMoneyRequested() < 0) {
			throw new InvalidRequestedMoneyValueException("The money value can not be negative!");
		}
	}

	/**
	 * Calculate how many banknotes the will needed for a banknotetype
	 * 
	 * @param banknoteType the banknote type to be calculated
	 * @param totalAmount the total amount of money will be taken
	 * 
	 * @return the quantity of notes is needed for this amount
	 */
	private Integer calculateQuantityNotes(BanknotesType banknoteType, Double totalAmount) {
		Integer quantityCounter = 0;
		while(totalAmount >= banknoteType.getValue()){
			quantityCounter++;
			totalAmount -= banknoteType.getValue();
		}
		return quantityCounter;
	}
	
	/**
	 * Validate the customer stored balance
	 * 
	 * @param money the money to be take
	 * @param customer the customer that has a balance
	 */
	private void validateCustomerBalance(Double money, Customer customer) {
		if(customer.getBalance() < money) {
			throw new UnauthorizedWithdrawalException(
					"The customer ".concat(customer.getName())
					.concat(" does not have enough balance to complete the operation"));
		}
	}
	
	/**
	 * Maps the customer informations from the requested customer changes to database customer
	 *  
	 * @param customerFound the customer found into database to be changed
	 * @param customer the requested changes to be made into customer
	 */
	private void mapCustomerToUpdate(Customer customerFound, Customer customer) {
		String name = changeValueIfExists(customerFound.getName(), customer.getName());
		String document = changeValueIfExists(customerFound.getDocument(), customer.getDocument());
		String email = changeValueIfExists(customerFound.getEmail(), customer.getEmail());
		Double customerBalance = changeValueIfExists(customerFound.getBalance(), customer.getBalance());
		
		customerFound.setName(name);
		customerFound.setDocument(document);
		customerFound.setEmail(email);
		customerFound.setBalance(customerBalance);
	}
	
	/**
	 * Find a customer from database and throw a execption if hes does not exist
	 * 
	 * @param customerId the customer identification to be retrieved
	 * @return the customer found into database
	 */
	private Customer findCustomer(Long customerId) {
		Customer customerFound = customerRepository.findOne(customerId);
		if(customerFound == null) {
			throw new CustomerNotFoundException("The customer with id "+customerId+" was not found");
		}
		return customerFound;
	}
}
