package com.paypal.bfs.test.employeeserv;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.paypal.bfs.test.employeeserv.domains.AddressDTO;
import com.paypal.bfs.test.employeeserv.domains.EmployeeDTO;
import com.paypal.bfs.test.employeeserv.respository.AddressRepository;
import com.paypal.bfs.test.employeeserv.respository.EmployeeRepository;

@SpringBootApplication
@EnableAutoConfiguration
public class EmployeeservApplication {
    public static void main(String[] args) {
        SpringApplication.run(EmployeeservApplication.class, args);
    }
    
    @Bean
    public CommandLineRunner mappingDemo(EmployeeRepository empRepository,
                                         AddressRepository addressRepository) {
        return args -> {
        	
        	String sDate="31/12/1998";  
            Date date=new SimpleDateFormat("dd/MM/yyyy").parse(sDate);  

            // create a user instance
            EmployeeDTO emp = new EmployeeDTO("John Doe", "john.doe@example.com", date);

            // create an address instance
            AddressDTO address = new AddressDTO("3933","Bidwell","fremont", "CA",
                    "usa", "94538");

            // set child reference
            address.setEmployee(emp);

            // set parent reference
            emp.setAddress(address);

            // save the parent
            // which will save the child (address) as well
            empRepository.save(emp);
        };
    }
}