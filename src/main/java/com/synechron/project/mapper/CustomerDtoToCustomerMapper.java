package com.synechron.project.mapper;

import com.synechron.project.dto.CustomerDto;
import com.synechron.project.entities.Customer;

public class CustomerDtoToCustomerMapper {
	
	public static  Customer toEntity(CustomerDto dto) {
        Customer customer = new Customer();
        customer.setCustomerName(dto.getCustomerName());
        customer.setMobile(dto.getMobile());
        customer.setPassword(dto.getPassword());
       

        return customer;
    }
}
