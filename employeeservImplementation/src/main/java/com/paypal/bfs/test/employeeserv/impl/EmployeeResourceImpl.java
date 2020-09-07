package com.paypal.bfs.test.employeeserv.impl;

import com.paypal.bfs.test.employeeserv.api.EmployeeResource;
import com.paypal.bfs.test.employeeserv.api.model.Address;
import com.paypal.bfs.test.employeeserv.api.model.Employee;
import com.paypal.bfs.test.employeeserv.domains.AddressDTO;
import com.paypal.bfs.test.employeeserv.domains.EmployeeDTO;
import com.paypal.bfs.test.employeeserv.respository.EmployeeRepository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Implementation class for employee resource.
 */
@RestController
public class EmployeeResourceImpl implements EmployeeResource {
	
	@Autowired
	EmployeeRepository employeeRepository;
	
    public static final String date_format = "yyyy/MM/dd";

    @Override
    public ResponseEntity<Employee> employeeGetById(String id) {

    	
		Optional<EmployeeDTO> employeeDto = employeeRepository.findById(Long.parseLong(id));
		EmployeeDTO emp = employeeDto.get();

		Employee employee = getEmployeeInfo(emp);

		Address address = getAddressInfo(emp);

		employee.setAddress(address);

		return new ResponseEntity<>(employee, HttpStatus.OK);
    }

	private Address getAddressInfo(EmployeeDTO emp) {
		 AddressDTO addressDTO = emp.getAddress();
         
         Address address = new Address();
         address.setAddress1(addressDTO.getAddress1());
         address.setAddress2(addressDTO.getAddress2());
         address.setCity(addressDTO.getCity());
         address.setCountry(addressDTO.getCountry());
         address.setState(addressDTO.getState());
         address.setZipcode(addressDTO.getZipCode());
         address.setAddressId(addressDTO.getAddressId().intValue());
         
         return address;
         
	}

	private Employee getEmployeeInfo(EmployeeDTO emp) {
		Employee employee = new Employee();
         employee.setId(emp.getEmployeeId().intValue());
         employee.setFirstName(emp.getFirstName());
         employee.setLastName(emp.getLastName());
         employee.setDateBirth(String.valueOf(emp.getDateOfBirth()));
		return employee;
	}

	@Override
	public ResponseEntity<String> createEmployee(@RequestBody Employee emp) throws ParseException {
		
		EmployeeDTO empDTO = createEmployeeDTO(emp);

		AddressDTO addressDTO = createAddressDTO(emp);

		addressDTO.setEmployee(empDTO);

		empDTO.setAddress(addressDTO);

		employeeRepository.save(empDTO);

		return new ResponseEntity<>("employee record created", HttpStatus.CREATED);
		
	}

	private AddressDTO createAddressDTO(Employee emp) {
		Address address = emp.getAddress();

		String add1 = address.getAddress1();
		String add2 = address.getAddress2();
		String city = address.getCity();
		String state = address.getState();
		String country = address.getCountry();
		String zipcode = address.getZipcode();

		AddressDTO addressDTO = new AddressDTO(add1, add2, city, state, country, zipcode);
		
		if (emp.getAddress().getAddressId() != null) {
			addressDTO.setAddressId(Long.valueOf(emp.getAddress().getAddressId()));
		}
		return addressDTO;
	}

	private EmployeeDTO createEmployeeDTO(Employee emp) throws ParseException {
		
		String fristName = emp.getFirstName();
		String lastName = emp.getLastName();
		String dob = emp.getDateBirth();

		Date date = new SimpleDateFormat(date_format).parse(dob);

		EmployeeDTO empDTO = new EmployeeDTO(fristName, lastName, date);
		
		if(emp.getId() != null) {
			empDTO.setEmployeeId(Long.valueOf(emp.getId()));
		}
		
		return empDTO;
	}
}
