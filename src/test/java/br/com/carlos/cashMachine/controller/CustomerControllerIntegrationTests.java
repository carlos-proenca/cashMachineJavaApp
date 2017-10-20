package br.com.carlos.cashMachine.controller;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.carlos.cashMachine.model.Customer;
import br.com.carlos.cashMachine.repository.CustomerRepository;
import br.com.carlos.cashMachine.util.BanknotesType;
import br.com.carlos.cashMachine.vo.BanknotesResponseVo;
import br.com.carlos.cashMachine.vo.MoneyRequest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@RunWith(SpringRunner.class)
@Sql(value = "/db/util/init_database.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = "/db/util/clean_database.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@TestPropertySource("classpath:application_test.properties")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CustomerControllerIntegrationTests {

	private static final String CUSTOMER_BASE_URL = "/api/v1/customers";
	
	private static final Long CUSTOMER_ID = 1l;
	private static final String CUSTOMER_EMAIL = "carlos_proenca@live.com";
	private static final String CUSTOMER_NAME = "Carlos Eduardo Proença";
	private static final String CUSTOMER_DOCUMENT = "356.832.118-16";
	private static final Double CUSTOMER_BALANCE = 500.0;

	private static final int FIRST_CUSTOMER = 0;

	private static final Integer FIFTY_BANKNOTE = 1;
	private static final Integer TEN_BANKNOTE = 3;

	@Value("${local.server.port}")
	private int serverPort;
	
	@Autowired
	private CustomerRepository customerRepository;
	
	private Customer customer;
	
	@Before
	public void setup() {
		RestAssured.port = serverPort;
		customer = getExpectedCustomer();
	}
	
	@Test
	public void testListAllCustomersEndpoint() {
		Customer[] customers = given().get(CUSTOMER_BASE_URL).then()
		.statusCode(HttpStatus.OK.value())
		.extract().as(Customer[].class);

		Customer firstCustomer = customers[FIRST_CUSTOMER];
		assertThat(customers).isNotEmpty();
		assertThat(firstCustomer).isEqualTo(customer);
	}
	
	@Test
	public void testCreateCustomersEndpoint() {
		customer.setId(null);
		
		Customer customerCreated = 
					given()
			          .request()
			          .header("Accept", ContentType.ANY)
			          .header("Content-type", ContentType.JSON)
			          .body(customer)
					  .when()
					  .post(CUSTOMER_BASE_URL)
					  .then()
					          .log().headers()
					      .and()
					          .log().body()
					      .and()
					          .statusCode(HttpStatus.CREATED.value())
					      .extract().as(Customer.class);

		customer.setId(2l);
		assertThat(customerCreated).isEqualTo(customer);
	}
	
	@Test
	public void testUpdateCustomersEndpoint() {
		customer.setName("Carlos silva");
		
		Customer customerUpdated = 
					given()
			          .request()
			          .header("Accept", ContentType.ANY)
			          .header("Content-type", ContentType.JSON)
			          .body(customer)
					  .when()
					  .put(CUSTOMER_BASE_URL.concat("/1"))
					  .then()
					          .log().headers()
					      .and()
					          .log().body()
					      .and()
					          .statusCode(HttpStatus.OK.value())
					      .extract().as(Customer.class);

		assertThat(customerUpdated).isEqualTo(customer);
	}
	
	@Test
	public void testDeleteCustomersEndpoint() {
		
		given()
          .request()
          .header("Accept", ContentType.ANY)
          .header("Content-type", ContentType.JSON)
          .body(customer)
		  .when()
		  .delete(CUSTOMER_BASE_URL.concat("/1"))
		  .then()
		          .log().headers()
		      .and()
		          .log().body()
		      .and()
		          .statusCode(HttpStatus.NO_CONTENT.value());
		
		Customer customerFromDatabase = customerRepository.findOne(1l);
		assertThat(customerFromDatabase).isNull();
	}
	
	
	@Test
	public void testCashOutCustomersEndpoint() {
		 MoneyRequest money = new MoneyRequest();
		 money.setMoneyRequested(60.0);
		 
		 BanknotesResponseVo response =
				 given()
		          .request()
		          .header("Accept", ContentType.JSON)
		          .header("Content-type", ContentType.JSON)
		          .body(money)
				  .when()
				  .post(CUSTOMER_BASE_URL.concat("/1/cash/out"))
				  .then()
			          .log().headers()
			      .and()
			          .log().body()
			      .and()
			          .statusCode(HttpStatus.OK.value())
			       .extract().as(BanknotesResponseVo.class);
		 
		assertThat(response.getTotalRequested()).isEqualTo(60);
		assertThat(response.getBanknotes().get(FIFTY_BANKNOTE).getQuantity()).isEqualTo(1);
		assertThat(response.getBanknotes().get(FIFTY_BANKNOTE).getType()).isEqualTo(BanknotesType.FIFTY.getDescription());
		assertThat(response.getBanknotes().get(TEN_BANKNOTE).getQuantity()).isEqualTo(1);
		assertThat(response.getBanknotes().get(TEN_BANKNOTE).getType()).isEqualTo(BanknotesType.TEN.getDescription());
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
