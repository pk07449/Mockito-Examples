package github.vikram.mockito.powermock;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import github.vikram.mockito.model.Customer;
import github.vikram.mockito.model.CustomerDao;
import github.vikram.mockito.model.CustomerManager;

import java.util.logging.Logger;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;


/*
 * Specify the @RunWith annotation with PowerMockRunner.class as argument
 * @PrepareForTest takes as argument, the class that contains the static method
 * that you are trying to mock 
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({CustomerDao.class, CustomerManager.class})
public class ReservationManagerTest {
	
	private static Logger logger = null;
	private String TEST_CUSTOMER_NAME = "Alice";
	
	/*
	 * The @BeforeClass annotation is run ONCE before
	 * beginning to execute any of the test cases. You
	 * can use this section to define objects such as loggers
	 */
	@BeforeClass
	public static void init() {
		logger = Logger.getLogger("Test logger");
	}
	
	
	/*
	 * The @AfterClass annotation is run ONCE after
	 * all the tests have been executed. You can
	 * use this section to destroy or clear any resources
	 * you have allocated in the @BeforeClass section
	 */
	@AfterClass
	public static void destroy() {
		logger = null;
	}
	
	
	/*
	 * @Before section is run before EVERY test. Use this
	 * section to initialize objects that need to be inited
	 * for every run
	 */
	@Before
	public void initManager() {
		
	}
	
	
	/*
	 * @After us run after EVERY test case is executed.
	 * Use this section to destory or clear our objects
	 * that you inited in the @Before section
	 */
	@After
	public void destroyManager() {
		
	}
	
	
	/*
	 * Methods annotated with @Test signal JUnit to treat
	 * this as a test case. 
	 * 
	 * All test cases must follow this convention of
	 * 			Setup
	 * 			Execution
	 * 			Verification
	 *
	 */
	@Test
	@Ignore
	public void testStaticGetCustomer() {
		
		/*
		 * 			SETUP
		 * Creating a dummyAlice object that will be used to return 
		 * when we make a call to a static function in CustomerDao.class
		
		 * Before you define the mockBehavior, run mockStatic(Static.class)
		 * and pass the Static class being tested
		 * 
		 * Proceed to specify the mock behavior using when/then pattern
		 */
		Customer dummyAlice = new Customer();
		dummyAlice.setFirstName("Mock-Alice");
		dummyAlice.setLicenseNumber("Mock-License");
		
		PowerMockito.mockStatic(CustomerDao.class);
		PowerMockito.when(CustomerDao.getCustomerByFirstName(TEST_CUSTOMER_NAME)).thenReturn(dummyAlice);
		
		
		
		/*
		 * 			EXECUTION
		 * Execute the static method under test
		 * 
		 */	
		Customer c = CustomerManager.getCustomerStaticByFirstName(TEST_CUSTOMER_NAME);
		
		
		/*			VERIFICATION
		 * Verify the result returned by the above invocation
		 * 
		 */	
		PowerMockito.verifyStatic();
		assertNotNull(c);
		assertEquals(c.getFirstName(), dummyAlice.getFirstName());
		logger.info("Customer: " + c);
		
		
	}
	
	
	/*
	 * Methods annotated with @Test signal JUnit to treat
	 * this as a test case. 
	 * 
	 * All test cases must follow this convention of
	 * 			Setup
	 * 			Execution
	 * 			Verification
	 *
	 */
	@Test
	@Ignore
	public void testMockConstructionOfObjects() throws Exception {
		
		/*
		 * 			SETUP
		 * 
		 * Specify the class and the constructor of the class for which you 
		 * want to mock. Here we are mocking the Customer() no-arg constructor
		 * and then we return "Mock-Alice" anytime, somebody does a new Customer()
		 * and then does getFirstName()
		 * 
		 */
		Customer custMock = PowerMockito.mock(Customer.class);
		PowerMockito.when(custMock.getFirstName()).thenReturn("Mock-Alice");
        PowerMockito.whenNew(Customer.class).withNoArguments().thenReturn(custMock);
        
        
        /*
		 * 			EXECUTION
		 * 
		 * Get instance of CustomerManager and invoke Customer()
		 * no-arg constructor from inside the createNewCustomer() method
		 * 
		 * For this, we need add CustomerManager.class to @PrepareForTest
		 * as well. Because the constructor is being invoked from that class
		 * 
		 */	
        CustomerManager cManager = CustomerManager.createInstance();
        Customer c = cManager.createNewCustomer();
        
        
        /*			VERIFICATION
		 * Verify the result returned by the above invocation
		 * 
		 */	
        logger.info("FirstName=" + c.getFirstName());
        logger.info("LastName=" + c.getLastName());
        assertEquals(c.getFirstName(), "Mock-Alice");
		
		
		
	}

	
	

}
