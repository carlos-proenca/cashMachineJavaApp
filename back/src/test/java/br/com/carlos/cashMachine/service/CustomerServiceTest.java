package br.com.carlos.cashMachine.service;

import static br.com.carlos.cashMachine.util.BanknotesType.FIFTY;
import static br.com.carlos.cashMachine.util.BanknotesType.ONE_HUNDRED;
import static br.com.carlos.cashMachine.util.BanknotesType.TEN;
import static br.com.carlos.cashMachine.util.BanknotesType.TWENTY;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.carlos.cashMachine.model.Customer;
import br.com.carlos.cashMachine.repository.CustomerRepository;
import br.com.carlos.cashMachine.service.provider.CustomerServiceProvider;
import br.com.carlos.cashMachine.util.exception.CustomerNotFoundException;
import br.com.carlos.cashMachine.util.exception.InvalidRequestedMoneyValueException;
import br.com.carlos.cashMachine.util.exception.UnauthorizedWithdrawalException;
import br.com.carlos.cashMachine.vo.BanknotesResponseVo;
import br.com.carlos.cashMachine.vo.MoneyRequest;

@RunWith(SpringRunner.class)
public class CustomerServiceTest {
	
	private static final Long CUSTOMER_ID = 1l;

	private static final Integer ONE_HUNDRED_BANKNOTE = 0;
	private static final Integer FIFTY_BANKNOTE = 1;
	private static final Integer TWENTY_BANKNOTE = 2;
	private static final Integer TEN_BANKNOTE = 3;
	
	@Mock
	private CustomerRepository customerRepository;
	
	@InjectMocks
	private CustomerService customerService = new CustomerServiceProvider();
	
	private MoneyRequest money;
	private Customer customer;
	
	@Before
	public void setup() {
		customer = getExpectedCustomer();
		money = new MoneyRequest();
	}
	
	@Test
	public void testCalculateBanknotesWithOneHundred() {
		when(customerRepository.findOne(CUSTOMER_ID)).thenReturn(customer);
		money.setMoneyRequested(100.0);
		
		BanknotesResponseVo response = customerService.cashOut(CUSTOMER_ID, money);
		
		assertThat(response.getTotalRequested()).isEqualTo(100.0);
		assertThat(response.getBanknotes().get(ONE_HUNDRED_BANKNOTE).getType()).isEqualTo(ONE_HUNDRED.getDescription());
		assertThat(response.getBanknotes().get(ONE_HUNDRED_BANKNOTE).getQuantity()).isEqualTo(1);
		assertThat(response.getBanknotes().get(FIFTY_BANKNOTE).getType()).isEqualTo(FIFTY.getDescription());
		assertThat(response.getBanknotes().get(FIFTY_BANKNOTE).getQuantity()).isEqualTo(0);
		assertThat(response.getBanknotes().get(TWENTY_BANKNOTE).getType()).isEqualTo(TWENTY.getDescription());
		assertThat(response.getBanknotes().get(TWENTY_BANKNOTE).getQuantity()).isEqualTo(0);
		assertThat(response.getBanknotes().get(TEN_BANKNOTE).getType()).isEqualTo(TEN.getDescription());
		assertThat(response.getBanknotes().get(TEN_BANKNOTE).getQuantity()).isEqualTo(0);
		
		verify(customerRepository, times(1)).findOne(CUSTOMER_ID);
	}
	
	@Test
	public void testCalculateBanknotesWithOneHundredAndTwenty() {
		when(customerRepository.findOne(CUSTOMER_ID)).thenReturn(customer);
		money.setMoneyRequested(120.0);
		
		BanknotesResponseVo response = customerService.cashOut(CUSTOMER_ID, money);
		
		assertThat(response.getTotalRequested()).isEqualTo(120.0);
		assertThat(response.getBanknotes().get(ONE_HUNDRED_BANKNOTE).getType()).isEqualTo(ONE_HUNDRED.getDescription());
		assertThat(response.getBanknotes().get(ONE_HUNDRED_BANKNOTE).getQuantity()).isEqualTo(1);
		assertThat(response.getBanknotes().get(FIFTY_BANKNOTE).getType()).isEqualTo(FIFTY.getDescription());
		assertThat(response.getBanknotes().get(FIFTY_BANKNOTE).getQuantity()).isEqualTo(0);
		assertThat(response.getBanknotes().get(TWENTY_BANKNOTE).getType()).isEqualTo(TWENTY.getDescription());
		assertThat(response.getBanknotes().get(TWENTY_BANKNOTE).getQuantity()).isEqualTo(1);
		assertThat(response.getBanknotes().get(TEN_BANKNOTE).getType()).isEqualTo(TEN.getDescription());
		assertThat(response.getBanknotes().get(TEN_BANKNOTE).getQuantity()).isEqualTo(0);
		
		verify(customerRepository, times(1)).findOne(CUSTOMER_ID);
	}
	
	@Test
	public void testCalculateBanknotesWithOneHundredAndThirty() {
		when(customerRepository.findOne(CUSTOMER_ID)).thenReturn(customer);
		money.setMoneyRequested(130.0);
		
		BanknotesResponseVo response = customerService.cashOut(CUSTOMER_ID, money);
		
		assertThat(response.getTotalRequested()).isEqualTo(130.0);
		assertThat(response.getBanknotes().get(ONE_HUNDRED_BANKNOTE).getType()).isEqualTo(ONE_HUNDRED.getDescription());
		assertThat(response.getBanknotes().get(ONE_HUNDRED_BANKNOTE).getQuantity()).isEqualTo(1);
		assertThat(response.getBanknotes().get(FIFTY_BANKNOTE).getType()).isEqualTo(FIFTY.getDescription());
		assertThat(response.getBanknotes().get(FIFTY_BANKNOTE).getQuantity()).isEqualTo(0);
		assertThat(response.getBanknotes().get(TWENTY_BANKNOTE).getType()).isEqualTo(TWENTY.getDescription());
		assertThat(response.getBanknotes().get(TWENTY_BANKNOTE).getQuantity()).isEqualTo(1);
		assertThat(response.getBanknotes().get(TEN_BANKNOTE).getType()).isEqualTo(TEN.getDescription());
		assertThat(response.getBanknotes().get(TEN_BANKNOTE).getQuantity()).isEqualTo(1);
		
		verify(customerRepository, times(1)).findOne(CUSTOMER_ID);
	}
	
	@Test
	public void testCalculateBanknotesWithTwoHundredAndFifty() {
		when(customerRepository.findOne(CUSTOMER_ID)).thenReturn(customer);
		money.setMoneyRequested(250.0);
		
		BanknotesResponseVo response = customerService.cashOut(CUSTOMER_ID, money);
		
		assertThat(response.getTotalRequested()).isEqualTo(250.0);
		assertThat(response.getBanknotes().get(ONE_HUNDRED_BANKNOTE).getType()).isEqualTo(ONE_HUNDRED.getDescription());
		assertThat(response.getBanknotes().get(ONE_HUNDRED_BANKNOTE).getQuantity()).isEqualTo(2);
		assertThat(response.getBanknotes().get(FIFTY_BANKNOTE).getType()).isEqualTo(FIFTY.getDescription());
		assertThat(response.getBanknotes().get(FIFTY_BANKNOTE).getQuantity()).isEqualTo(1);
		assertThat(response.getBanknotes().get(TWENTY_BANKNOTE).getType()).isEqualTo(TWENTY.getDescription());
		assertThat(response.getBanknotes().get(TWENTY_BANKNOTE).getQuantity()).isEqualTo(0);
		assertThat(response.getBanknotes().get(TEN_BANKNOTE).getType()).isEqualTo(TEN.getDescription());
		assertThat(response.getBanknotes().get(TEN_BANKNOTE).getQuantity()).isEqualTo(0);
		
		verify(customerRepository, times(1)).findOne(CUSTOMER_ID);
	}
	
	@Test(expected = UnauthorizedWithdrawalException.class)
	public void testCalculateBanknotesWithoutBalance() {
		when(customerRepository.findOne(CUSTOMER_ID)).thenReturn(customer);
		money.setMoneyRequested(600.0);
		
		customerService.cashOut(CUSTOMER_ID, money);
		verify(customerRepository, times(1)).findOne(CUSTOMER_ID);
	}
	
	@Test(expected = CustomerNotFoundException.class)
	public void testCalculateBanknotesWithCustomerNotFound() {
		when(customerRepository.findOne(CUSTOMER_ID)).thenReturn(null);
		money.setMoneyRequested(600.0);
		
		customerService.cashOut(CUSTOMER_ID, money);
		verify(customerRepository, times(1)).findOne(CUSTOMER_ID);
	}
	
	@Test(expected = InvalidRequestedMoneyValueException.class)
	public void testCalculateBanknotesWithRequestedMoneyNotFound() {
		when(customerRepository.findOne(CUSTOMER_ID)).thenReturn(customer);
		
		customerService.cashOut(CUSTOMER_ID, null);
		verify(customerRepository, times(1)).findOne(CUSTOMER_ID);
	}
	
	@Test(expected = InvalidRequestedMoneyValueException.class)
	public void testCalculateBanknotesWithInvalidMoneyValue() {
		when(customerRepository.findOne(CUSTOMER_ID)).thenReturn(customer);
		money.setMoneyRequested(-20.0);
		customerService.cashOut(CUSTOMER_ID, money);
		verify(customerRepository, times(1)).findOne(CUSTOMER_ID);
	}
	
	
	private Customer getExpectedCustomer() {
		Customer expectedCustomer = new Customer();
		
		expectedCustomer.setId(CUSTOMER_ID);
		expectedCustomer.setName("Carlos Proença");
		expectedCustomer.setEmail("carlos_proenca@live.com");
		expectedCustomer.setDocument("356.832.118-16");
		expectedCustomer.setBalance(500.0);

		return expectedCustomer;
	}

}
