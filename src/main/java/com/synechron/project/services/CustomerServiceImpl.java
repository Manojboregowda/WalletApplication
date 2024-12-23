package com.synechron.project.services;

import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.synechron.project.dao.CustomerDao;
import com.synechron.project.dto.CustomerDto;
import com.synechron.project.dto.WalletDto;
import com.synechron.project.entities.Customer;
import com.synechron.project.exception.CustomerNotFoundException;
import com.synechron.project.exception.InvalidCredentialsException;
import com.synechron.project.mapper.CustomerDtoToCustomerMapper;
import com.synechron.project.mapper.CustomerMapperToCustomerDto;

@Service
public class CustomerServiceImpl implements CustomerService {
	@Autowired
	CustomerDao dao;

	@Override
	public CustomerDto addCustomer(CustomerDto customer) {
		try {
			CustomerDtoToCustomerMapper dto = new CustomerDtoToCustomerMapper();
			Customer cust = dao.addCustomer(dto.toEntity(customer));
			CustomerMapperToCustomerDto mapper = new CustomerMapperToCustomerDto();
			return mapper.toDto(cust);
		} catch (Exception e) {
			throw new RuntimeException("Error adding customer");
		}
	}

	@Override
	public CustomerDto loginCustomer(String mobile, String password) {
		try {
			return dao.loginCustomer(mobile, password);
		} catch (CustomerNotFoundException e) {
			throw new InvalidCredentialsException("Invalid mobile number or password");
		} catch (Exception e) {
			throw new RuntimeException("Error logging in customer");
		}
	}

	// changes..
	@Override
	public WalletDto getAccountInfo(CustomerDto customer) {
		try {
			WalletDto data= dao.getWalletId(customer);
			System.out.println(data);
			return data;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public Integer getWalletId(int id) {
		return dao.getWalletId(id);
	}
}
