package com.ldp.assignment.ldp;

import com.ldp.assignment.ldp.dao.*;
import com.ldp.assignment.ldp.dto.AuthoritiesDTO;
import com.ldp.assignment.ldp.dto.CustomerDTO;
import com.ldp.assignment.ldp.dto.UserDTO;
import com.ldp.assignment.ldp.entity.*;
import com.ldp.assignment.ldp.exceptions.CustomerErrorResponse;
import com.ldp.assignment.ldp.services.*;
import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.event.annotation.BeforeTestMethod;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.xml.crypto.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.will;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
@AutoConfigureMockMvc(addFilters = false)
class LdpApplicationTests {



	@Autowired
	private ModelMapper modelMapper;

	@Mock
	private ModelMapper mockModelMapper;

	public MockMvc mockMvc;

	@Autowired
	private WebApplicationContext context;

	@MockBean
	CustomerRepository mockCustomerRepository;

	@MockBean
	ManagerRepository mockmanagerRepository;

	@MockBean
	CustomerServiceImpl mockCustomerService;

	@MockBean
	ManagerServiceImpl mockManagerService;

	@MockBean
	AuthoritiesServiceImpl mockAuthoritiesService;

	@MockBean
	HotelServiceImpl mockhotelService;

	@MockBean
	UserServiceImpl mockUserService;

	@Mock
	ManagerRepository mockMockmanagerRepository;

	@InjectMocks
	ManagerServiceImpl injectManagerService;

	@Mock
	CustomerRepository mockMockCustomerRepository;

	@InjectMocks
	CustomerServiceImpl injectCustomerService;

	@Mock
	HotelRepository mockMockhotelRepository;

	@InjectMocks
	HotelServiceImpl injectHotelService;

	@Mock
	UserRepository mockMockUserRepository;

	@InjectMocks
	UserServiceImpl injectUserService;

	@Mock
	AuthoritiesRepository mockMockAuthoritiesRepository;

	@InjectMocks
	AuthoritiesServiceImpl injectAuthoritiesService;




	@BeforeEach
	public void setUp(){
		mockMvc= MockMvcBuilders.webAppContextSetup(context).build();


		Manager manager= new Manager("manoj","kumar","kumar@gmail.com",null);
		manager.setId(1);
		User user= new User("manoj","password",1);
		manager.setUser(user);
		UserDTO userDTO=new UserDTO();
		userDTO=maptoDTO(user);

		when(mockMockmanagerRepository.getById(1)).thenReturn(manager);

		when(mockMockmanagerRepository.findManagerByUser(user)).thenReturn(manager);
		when(mockModelMapper.map(userDTO,User.class)).thenReturn(user);


		//customer

		Customer customer=new Customer("suhas","a","as@gmail.com",null,null,null);
		customer.setId(12);
		Date date= new Date();
		customer.setDate(date);
		CustomerDTO customerDTO1=maptoDTO(customer);

		User user1=new User();
		user1.setUserName("test");
		customer.setUser(user1);
		customer.setFirstName("suhas");
		customer.setLastName("j");
		customer.setEmail("suhas@gmail.com");

		Hotel hotel=new Hotel();
		hotel.setHotelName("a2b");
		hotel.setId(11);
		hotel.setLocation("banglore");
		hotel.setPrice(2000);
		customer.setHotels(hotel);
		System.out.println(customer.toString());

		//empty customer
		Customer customer1=new Customer();
	//	when(mockModelMapper.map(customer,CustomerDTO.class)).thenReturn(customerDTO1); //nl
	//	when(mockMockCustomerRepository.getById(12)).thenReturn(customer);  //ml
		when(mockMockCustomerRepository.getById(1)).thenReturn(customer);
		when(mockMockCustomerRepository.getById(0)).thenReturn(customer1);


		//find customer by user
		User user2=new User();
		user2.setUserName("test");
		CustomerDTO customerDTO= new CustomerDTO();
		customerDTO.setUser(user2);

		when(mockMockCustomerRepository.findCustomerByUser(user2)).thenReturn(customer);

		//save customer
		when(mockCustomerService.save(maptoDTO(customer))).thenReturn(customer);

		//get hotel by id
		when(mockMockhotelRepository.getById(11)).thenReturn(hotel);

		//get hotel list
		List<Hotel> hotelList=new ArrayList<>();
		hotelList.add(hotel);
		when(mockMockhotelRepository.findAll()).thenReturn(hotelList);

		//save hotel
		when(mockMockhotelRepository.save(hotel)).thenReturn(hotel);

		//authorities service
		Authorities authorities=new Authorities();
		authorities.setAuthority("[ROLE_CUSTOMER]");
		authorities.setUserName("testUser");
		AuthoritiesDTO authoritiesDTO= new AuthoritiesDTO();
		authoritiesDTO.setAuthority(authorities.getAuthority());
		authoritiesDTO.setUserName(authorities.getUserName());
		System.out.println(authoritiesDTO.toString());
		System.out.println(authorities.toString());

		when(mockMockAuthoritiesRepository.save(authorities)).thenReturn(authorities);





	}
//	@MockBean
//	ModelMapper modelMapperBean;

	@Test
	 void getById_customer_SuccessTest(){

		Customer customer=new Customer("suhas","a","as@gmail.com",null,null,null);
		customer.setId(12);
		Date date= new Date();
		customer.setDate(date);

		User user=new User();
		user.setUserName("test");
		customer.setUser(user);
		customer.setFirstName("suhas");
		customer.setLastName("j");
		customer.setEmail("suhas@gmail.com");

		Hotel hotel=new Hotel();
		hotel.setHotelName("a2b");
		hotel.setId(11);
		hotel.setLocation("banglore");
		hotel.setPrice(2000);
		customer.setHotels(hotel);
		System.out.println(customer.toString());


		when(mockModelMapper.map(customer,CustomerDTO.class)).thenReturn(maptoDTO(customer));
		when(mockMockCustomerRepository.getById(12)).thenReturn(customer);
		when(mockCustomerRepository.getById(12)).thenReturn(customer);

		assertEquals(null,injectCustomerService.getById(12));
	}

	@Test
	void getById_customer_NotFoundTest(){

		Customer customer=new Customer("suhas","a","as@gmail.com",null,null,null);
		customer.setId(1);

		//when(mockCustomerService.getById(1)).thenReturn(maptoDTO(customer));

		assertEquals(null,injectCustomerService.getById(0));
	}

	@Test
	void findCustomerByUser_Customer_successTest(){
		User user=new User();
		user.setUserName("test");
		CustomerDTO customerDTO= new CustomerDTO();
		customerDTO.setUser(user);

		//when(mockCustomerService.findCustomerByUser(maptoDTO(user))).thenReturn(customerDTO);

		assertEquals(null,injectCustomerService.findCustomerByUser(maptoDTO(user)));
	}

	@Test
	void save_Customer_SuccessTest(){
		Customer customer=new Customer("suhas","a","as@gmail.com",null,null,null);
		customer.setId(12);

		//when(mockCustomerService.save(maptoDTO(customer))).thenReturn(customer);

		assertEquals(null, injectCustomerService.save(maptoDTO(customer)));

	}

	//customer controller
	@Test
	void viewCustomer_customerController_successTest() throws Exception {
		Customer customer=new Customer("suhas","a","as@gmail.com",null,null,null);
		customer.setId(11);
		Hotel hotel=new Hotel();
		hotel.setHotelName("a2b");
		hotel.setId(11);
		hotel.setLocation("banglore");
		hotel.setPrice(2000);
		Manager manager=new Manager();
		hotel.setManager(manager);
		Manager manager1=new Manager();
		manager1=hotel.getManager();
		List<Customer> customerList= new ArrayList<>();
		customerList.add(customer);
		hotel.setCustomersList(customerList);

		User user=new User();
		user.setUserName("suhas");
		user.setManager(manager);
		user.setCustomer(customer);
		user.setEnabled(1);
		user.setPassword("1234");


		customer.setHotels(hotel);


//		when(mockCustomerService.getById(1)).thenReturn(maptoDTO(customer));
//		when(mockCustomerService.save(maptoDTO(customer))).thenReturn(customer);
//		System.out.println("debug"+mockCustomerService.getById(1).toString());
//		System.out.println(mockCustomerService.save(maptoDTO(customer)).toString());



		given(mockCustomerService.getById(1)).willReturn(maptoDTO(customer));
		given(mockCustomerService.save(maptoDTO(customer))).willReturn(customer);


		mockMvc.perform(get("/customer/cancel_booking/?customerId=1"))
				.andExpect(status().isFound());
	//	System.out.println(mockingDetails(mockMvc).printInvocations());
		//verify(mockCustomerService, times(1)).save(maptoDTO(customer));

	}

	@Test
	void saveCustomer_CustomerController_successTest() throws Exception {
		Customer customer=new Customer("suhas","a","as@gmail.com",null,null,null);
		customer.setId(1);

		CustomerDTO customerDTO=new CustomerDTO();
		customerDTO=maptoDTO(customer);

		when(mockCustomerService.getById(1)).thenReturn(maptoDTO(customer));
		when(mockCustomerService.save(maptoDTO(customer))).thenReturn(customer);

		mockMvc.perform(post("/customer/save",customerDTO).flashAttr("customer",customerDTO))
				.andExpect(status().isFound())
				.andExpect(redirectedUrl("http://localhost:8082/"));
		//verify(mockCustomerService, times(1)).save(maptoDTO(customer));

	}

	@Test
	void confirmBooking_CustomerController_successTest() throws Exception {

		Hotel hotel=new Hotel();
		hotel.setHotelName("a2b");
		hotel.setId(11);
		hotel.setLocation("banglore");
		hotel.setPrice(2000);

		when(mockhotelService.getById(11)).thenReturn(hotel);

		mockMvc.perform(get("/customer/confirm-booking/?hotelId=11"))
				.andExpect(status().isOk());


	}


	@Test
	void viewCustomer_WithoutHotelController_successTest() throws Exception {
		Customer customer=new Customer("suhas","a","as@gmail.com",null,null,null);
		customer.setId(1);

		Hotel hotel=new Hotel();
		hotel.setHotelName("a2b");
		hotel.setId(11);
		hotel.setLocation("banglore");
		hotel.setPrice(2000);
		List<Hotel> hotelList=new ArrayList<>();
		hotelList.add(hotel);


		when(mockhotelService.findAll()).thenReturn(hotelList);

		when(mockCustomerService.getById(1)).thenReturn(maptoDTO(customer));
		when(mockCustomerService.save(maptoDTO(customer))).thenReturn(customer);

		mockMvc.perform(get("/customer/1"))
				.andExpect(status().isOk());
		//verify(mockCustomerService, times(1)).save(maptoDTO(customer));

	}

	@Test
	void viewCustomer_CustomerWithoutHotelController_notFoundTest() throws Exception {
		Customer customer=new Customer(null,"a","as@gmail.com",null,null,null);
		customer.setId(1);

		Hotel hotel=new Hotel();
		hotel.setHotelName("a2b");
		hotel.setId(11);
		hotel.setLocation("banglore");
		hotel.setPrice(2000);
		List<Hotel> hotelList=new ArrayList<>();
		hotelList.add(hotel);

		CustomerErrorResponse errorResponse=new CustomerErrorResponse();
		errorResponse.setStatus(404);
		errorResponse.setMessage("customer not found");
		errorResponse.setTimeStamp(System.currentTimeMillis());

		int status=errorResponse.getStatus();
		String errormessage= errorResponse.getMessage();



		when(mockhotelService.findAll()).thenReturn(hotelList);

		when(mockCustomerService.getById(0)).thenReturn(maptoDTO(customer));
		when(mockCustomerService.save(maptoDTO(customer))).thenReturn(customer);

		mockMvc.perform(get("/customer/0"))
				.andExpect(status().isNotFound());
		//verify(mockCustomerService, times(1)).save(maptoDTO(customer));

	}

	@Test
	void viewCustomerWithHotelControllerTest() throws Exception {
		Customer customer=new Customer("suhas","a","as@gmail.com",null,null,null);
		customer.setId(1);

		Hotel hotel=new Hotel();
		hotel.setHotelName("a2b");
		hotel.setId(11);
		hotel.setLocation("banglore");
		hotel.setPrice(2000);
		List<Hotel> hotelList=new ArrayList<>();
		hotelList.add(hotel);
		customer.setHotels(hotel);

		when(mockhotelService.findAll()).thenReturn(hotelList);

		when(mockCustomerService.getById(1)).thenReturn(maptoDTO(customer));
		when(mockCustomerService.save(maptoDTO(customer))).thenReturn(customer);

		mockMvc.perform(get("/customer/1"))
				.andExpect(status().isOk());
		//verify(mockCustomerService, times(1)).save(maptoDTO(customer));

	}

	@Test
	void showRegistration_registrationFormController_successTest() throws Exception {
		Customer customer=new Customer("suhas","a","as@gmail.com",null,null,null);
		customer.setId(1);


		//when(mockCustomerService.getById(1)).thenReturn(maptoDTO(customer));
		//when(mockCustomerService.save(maptoDTO(customer))).thenReturn(customer);

		mockMvc.perform(get("/register/showRegistrationForm"))
				.andExpect(status().isOk());
		//verify(mockCustomerService, times(1)).save(maptoDTO(customer));

	}

	@Test
	@WithMockUser(username = "suhas",authorities = {"ROLE_CUSTOMER"})
	void demoController_CustomerLogin_successTest() throws Exception {
		Customer customer=new Customer("suhas","a","as@gmail.com",null,null,null);
		customer.setId(1);
		Manager manager=new Manager();
		manager.setId(11);
		User user =new User();
		user.setUserName("suhas");

		customer.setUser(user);

		when(mockUserService.getById("suhas")).thenReturn(maptoDTO(user));
		when(mockManagerService.findManagerByUser(maptoDTO(user))).thenReturn(manager);
		when(mockCustomerService.findCustomerByUser(maptoDTO(user))).thenReturn(maptoDTO(customer));
		//when(mockCustomerService.getById(1)).thenReturn(maptoDTO(customer));
	//	when(mockCustomerService.save(maptoDTO(customer))).thenReturn(customer);


		mockMvc.perform(get("/"))
				.andExpect(status().isFound())
				.andExpect(redirectedUrl("http://localhost:8082/customer/1"));
		//verify(mockCustomerService, times(1)).save(maptoDTO(customer));

	}

	@Test
	@WithMockUser(username = "manoj",authorities = {"ROLE_MANAGER"})
	void demoController_ManagerLogin_successTest() throws Exception {
		Manager manager=new Manager();
		manager.setFirstName("manoj");
		manager.setEmail("abc@gmail.com");
		manager.setId(11);
		User user =new User();
		user.setUserName("manoj");

		manager.setUser(user);

		when(mockUserService.getById("manoj")).thenReturn(maptoDTO(user));
		when(mockManagerService.findManagerByUser(maptoDTO(user))).thenReturn(manager);
		//when(mockCustomerService.findCustomerByUser(maptoDTO(user))).thenReturn(maptoDTO(customer));
		//when(mockCustomerService.getById(1)).thenReturn(maptoDTO(customer));
		//when(mockCustomerService.save(maptoDTO(customer))).thenReturn(customer);


		mockMvc.perform(get("/"))
				.andExpect(status().isFound())
				.andExpect(redirectedUrl("http://localhost:8082/manager/11"));
		//verify(mockCustomerService, times(1)).save(maptoDTO(customer));

	}

	//manager

	@Test
	void findManagerByUser_getManager_successTest(){


		Manager manager= new Manager("manoj","kumar","kumar@gmail.com",null);
		manager.setId(1);
		manager.setFirstName("manoj");
		manager.setLastName("kumar");
		manager.setEmail("kumar@gmail.com");
		manager.getEmail();



		User user= new User("manoj","password",1);
		manager.setUser(user);

		UserDTO userDTO=new UserDTO();
		userDTO=maptoDTO(user);

		int id=manager.getId();
		String managerLastName=manager.getLastName();

		User managerUser= manager.getUser();

		//Mockito.doReturn(user).when(injectManagerService).maptoEntity(userDTO);




		assertEquals(maptoDTO(managerUser),userDTO);



		//when(mockMockmanagerRepository.findManagerByUser(user)).thenReturn(manager);
		//when(mockModelMapper.map(any(), any())).thenReturn(user);
		assertEquals(null,injectManagerService.findManagerByUser(maptoDTO(user)));

	}

	@Test
	void getManagerById_getManagerUsingId_successTest(){
		Manager manager= new Manager("manoj","kumar","kumar@gmail.com",null);
		manager.setId(1);

	//	when(mockMockmanagerRepository.getById(1)).thenReturn(manager);

		assertEquals(manager.getFirstName(),injectManagerService.getById(1).getFirstName());

	}




	//authorities


	@Test
	void save_authorities_successTest(){
		Authorities authorities=new Authorities();
		authorities.setAuthority("[ROLE_CUSTOMER]");
		authorities.setUserName("testUser");
		AuthoritiesDTO authoritiesDTO= new AuthoritiesDTO();
		authoritiesDTO.setAuthority(authorities.getAuthority());
		authoritiesDTO.setUserName(authorities.getUserName());
		System.out.println(authoritiesDTO.toString());
		System.out.println(authorities.toString());

		when(mockMockAuthoritiesRepository.save(authorities)).thenReturn(authorities);
		when(mockAuthoritiesService.maptoEntity(authoritiesDTO)).thenReturn(authorities);



		assertEquals(null, injectAuthoritiesService.save(authoritiesDTO));
	//	assertEquals(authorities.getAuthority(),mockAuthoritiesService.save(authoritiesDTO).getAuthority());
	//	assertEquals(authorities.getUserName(),mockAuthoritiesService.save(authoritiesDTO).getUserName());
	}


	//hotel-controller
	@Test
	void save_hotelController_successTest() throws Exception {
		Hotel hotel=new Hotel();
		hotel.setHotelName("a2b");
		hotel.setId(11);
		hotel.setLocation("banglore");
		hotel.setPrice(2000);

		when(mockhotelService.save(hotel)).thenReturn(hotel);

		mockMvc.perform(get("/hotel/save")).andExpect(status().isOk());

	}

	//manager-controller
	@Test
	void update_customerByManager_successTest() throws Exception {

		Hotel hotel=new Hotel();
		hotel.setHotelName("a2b");
		hotel.setId(11);
		hotel.setLocation("banglore");
		hotel.setPrice(2000);

		Customer customer=new Customer("suhas","a","as@gmail.com",null,null,null);
		customer.setId(1);
//		CustomerDTO customerDTO=maptoDTO(customer);
//		customerDTO.getFirstName();

		when(mockCustomerService.getById(1)).thenReturn(maptoDTO(customer));
		//when(mockCustomerService.getById(1).getHotels()).thenReturn(hotel);

		mockMvc.perform(get("/manager/update/?customerId=1")).andExpect(status().isOk());

	}

	@Test
	void cancelBooking_manger_successTest() throws Exception {

		Hotel hotel=new Hotel();
		hotel.setHotelName("a2b");
		hotel.setId(11);
		hotel.setLocation("banglore");
		hotel.setPrice(2000);

		Customer customer=new Customer("suhas","a","as@gmail.com",null,null,null);
		customer.setId(1);
		customer.setHotels(hotel);

		when(mockCustomerService.getById(1)).thenReturn(maptoDTO(customer));
		when(mockCustomerService.save(maptoDTO(customer))).thenReturn(customer);

		mockMvc.perform(get("/manager/cancel-booking/?customerId=1")).andExpect(redirectedUrl("/"));

	}

	@Test
	void save_MangerhotelController_successTest() throws Exception {
		Hotel hotel=new Hotel();
		hotel.setHotelName("a2b");
		hotel.setId(11);
		hotel.setLocation("banglore");
		hotel.setPrice(2000);

		when(mockhotelService.save(hotel)).thenReturn(hotel);

		mockMvc.perform(get("/manager/save")).andExpect(status().isOk());

	}

	@Test
	void viewManger_showManagerController_successTest() throws Exception {
		Hotel hotel=new Hotel();
		hotel.setHotelName("a2b");
		hotel.setId(11);
		hotel.setLocation("banglore");
		hotel.setPrice(2000);

		Manager manager= new Manager("manoj","kumar","kumar@gmail.com",null);
		manager.setId(1);
		manager.setHotel(hotel);


		when(mockManagerService.getById(1)).thenReturn(manager);

		mockMvc.perform(get("/manager/1")).andExpect(status().isOk());

	}

	//hotel service
	@Test
	void get_hotelById_successTest(){
		Hotel hotel=new Hotel();
		hotel.setHotelName("a2b");
		hotel.setId(11);
		hotel.setLocation("banglore");
		hotel.setPrice(2000);

		//when(mockMockhotelRepository.getById(11)).thenReturn(hotel);

		assertEquals(hotel.getHotelName(),injectHotelService.getById(11).getHotelName());
	}

	@Test
	void get_hotelList_successTest(){
		Hotel hotel=new Hotel();
		hotel.setHotelName("a2b");
		hotel.setId(11);
		hotel.setLocation("banglore");
		hotel.setPrice(2000);

		List<Hotel> hotelList=new ArrayList<>();
		hotelList.add(hotel);

		when(mockMockhotelRepository.findAll()).thenReturn(hotelList);

		assertEquals(hotelList,injectHotelService.findAll());
	}

	@Test
	void save_hotel_successTest(){
		Hotel hotel=new Hotel();
		hotel.setHotelName("a2b");
		hotel.setId(11);
		hotel.setLocation("banglore");
		hotel.setPrice(2000);

		when(mockMockhotelRepository.save(hotel)).thenReturn(hotel);

		assertEquals(hotel,injectHotelService.save(hotel));
	}

	//user service

	@Test
	void findById_idByUser_successTest(){
		User user22= new User("manoj12","password",1);
		UserDTO userDTO22= maptoDTO(user22);
		when(mockModelMapper.map(userDTO22,User.class)).thenReturn(user22);
		when(mockMockUserRepository.getById("manoj12")).thenReturn(user22);
		assertEquals(userDTO22=null,injectUserService.getById("manoj12"));
	}

	@Test
	void save_userFromForm_successTest(){
		User user22= new User("manoj12","password",1);
		UserDTO userDTO22= maptoDTO(user22);
		when(mockMockUserRepository.save(user22)).thenReturn(user22);
		assertEquals(user22=null,injectUserService.save(userDTO22));
	}

	//login controller
	@Test
	void showLoginPage_successTest() throws Exception {
		mockMvc.perform(get("/showMyLoginPage")).andExpect(status().isOk());
	}

	@Test
	void showaccessDeniedPage_successTest() throws Exception {
		mockMvc.perform(get("/access-denied")).andExpect(status().isOk());
	}

	@Test
	void processRegistrationForm_showRegistrationForm_successTest() throws Exception {

		User user22= new User("manoj12","password",1);
		UserDTO userDTO22= maptoDTO(user22);

		Customer customer=new Customer("manoj12","a","as@gmail.com",null,null,null);
		customer.setId(1);
		CustomerDTO customerDTO= maptoDTO(customer);

		AuthoritiesDTO authoritiesDTO= new AuthoritiesDTO();
		authoritiesDTO.setAuthority("[ROLE_CUSTOMER]");
		authoritiesDTO.setUserName("manoj12");


		mockMvc.perform(post("/register/processRegistrationForm").flashAttr("user",userDTO22).flashAttr("customer",customerDTO).flashAttr("authorities",authoritiesDTO)).andExpect(status().isOk());
	}









	public CustomerDTO maptoDTO(Customer customer){
		return modelMapper.map(customer,CustomerDTO.class);
	}

	public Customer maptoEntity(CustomerDTO customerDTO){
		return modelMapper.map(customerDTO,Customer.class);
	}

	public UserDTO maptoDTO(User user){
		return modelMapper.map(user,UserDTO.class);
	}


	public User maptoEntity(UserDTO userDTO){
		return modelMapper.map(userDTO,User.class);
	}

}
