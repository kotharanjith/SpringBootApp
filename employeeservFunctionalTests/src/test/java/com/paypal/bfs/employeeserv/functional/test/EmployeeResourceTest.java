package com.paypal.bfs.employeeserv.functional.test;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.ws.rs.core.MediaType;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.paypal.bfs.test.employeeserv.EmployeeservApplication;
import com.paypal.bfs.test.employeeserv.api.model.Address;
import com.paypal.bfs.test.employeeserv.api.model.Employee;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=EmployeeservApplication.class)
@AutoConfigureMockMvc
public class EmployeeResourceTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Test
	public void createEmployeeAPI() throws Exception {

		Employee emp = new Employee();
		emp.setFirstName("John Doe");
		emp.setLastName("doe");
		emp.setDateBirth("1998/12/31");

		Address add = new Address();

		add.setAddress1("3933");
		add.setAddress2("Bidwell");
		add.setCity("fremont");
		add.setState("CA");
		add.setCountry("USA");
		add.setZipcode("94538");

		emp.setAddress(add);

		mockMvc.perform(MockMvcRequestBuilders.post("/v1/bfs/employees").content(asJsonString(emp))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated());
	}
	 
	@Test
	public void getEmployeeAPI() throws Exception {

		mockMvc.perform(MockMvcRequestBuilders.get("/v1/bfs/employees/1").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}
	
	public static String asJsonString(final Object obj) {
	    try {
	        return new ObjectMapper().writeValueAsString(obj);
	    } catch (Exception e) {
	        throw new RuntimeException(e);
	    }
	}

}
