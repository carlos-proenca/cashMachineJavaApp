package br.com.carlos.cashMachine.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.carlos.cashMachine.model.Customer;

@DataJpaTest
@RunWith(SpringRunner.class)
@TestPropertySource("classpath:application_test.properties")
@Sql(value = "/db/util/init_database.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = "/db/util/clean_database.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@AutoConfigureTestDatabase(replace= Replace.NONE)
public class CustomerRepositoryIntegrationTest {
	
	private static final long CUSTOMER_ID = 1l;
	private static final String CUSTOMER_EMAIL = "carlos_proenca@live.com";
	private static final String CUSTOMER_NAME = "Carlos Eduardo Proença";
	private static final String CUSTOMER_DOCUMENT = "356.832.118-16";
	private static final Double CUSTOMER_BALANCE = 500.0;
	
	private static final Integer FIST_CUSTOMER = 0;
	
	@Autowired
	private CustomerRepository customerRepository;
	
	private Customer customer;
	
	@Before
	public void setup() {
		customer = getExpectedCustomer();
	}

	@Test
	public void testFindAllCustomers() {
		List<Customer> customersFound = customerRepository.findAll();
		assertThat(customersFound.get(FIST_CUSTOMER)).isEqualTo(customer);
	}
	
	@Test
	public void testSaveCustomer() {
		customer.setId(null);
		Customer customerCreated = customerRepository.save(customer);
		customer.setId(2l);
		assertThat(customerCreated).isEqualTo(customer);
	}
	
	@Test
	public void testDeleteCustomer() {
		customerRepository.delete(1l);
		Customer customerFound = customerRepository.findOne(1l);
		assertThat(customerFound).isNull();
	}
	
	private Customer getExpectedCustomer() {
		Customer expectedCustomer = new Customer();
		expectedCustomer.setId(CUSTOMER_ID);
		expectedCustomer.setName(CUSTOMER_NAME);
		expectedCustomer.setEmail(CUSTOMER_EMAIL);
		expectedCustomer.setDocument(CUSTOMER_DOCUMENT);
		expectedCustomer.setBalance(CUSTOMER_BALANCE);
		return expectedCustomer;
	}
}
