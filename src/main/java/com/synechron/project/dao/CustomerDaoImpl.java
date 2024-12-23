package com.synechron.project.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.synechron.project.dto.CustomerDto;
import com.synechron.project.dto.WalletDto;
import com.synechron.project.entities.Customer;
import com.synechron.project.entities.Wallet;
import com.synechron.project.exception.CustomerNotFoundException;
import com.synechron.project.mapper.CustomerDtoToCustomerMapper;
import com.synechron.project.mapper.CustomerMapperToCustomerDto;
import com.synechron.project.mapper.WalletMapperToWalletDto;
import com.synechron.project.repository.CustomerRepository;
import com.synechron.project.repository.WalletRepository;

@Repository
public class CustomerDaoImpl implements CustomerDao {

	@Autowired
	CustomerRepository repository;
	
	@Autowired
	WalletRepository wrepo;

	@Override
	public Customer addCustomer(Customer customer) {
		try {
			return repository.save(customer);
		} catch (Exception e) {
			throw new RuntimeException("Error saving customer");
		}
	}

	@Override
	public CustomerDto loginCustomer(String mobile, String password) {
		try {

			Customer customer = repository.findByMobileAndPassword(mobile, password);
			CustomerMapperToCustomerDto mapper = new CustomerMapperToCustomerDto();
			CustomerDto dto = mapper.toDto(customer);
			if (customer == null) {
				throw new CustomerNotFoundException("Customer not found");
			}
			return dto;
		} catch (Exception e) {
			throw new RuntimeException("Error retrieving customer");
		}
	}

	// Changes....
	@Override
			public WalletDto getWalletId(CustomerDto customer) {
				Wallet wallet =  wrepo.getWalletInfo(customer);
				WalletMapperToWalletDto mapper = new WalletMapperToWalletDto();
				WalletDto dto = mapper.walletToWalletDtoMapper(customer);
				return dto;
			}

	@Override
	public Integer getWalletId(int id) {
		// TODO Auto-generated method stub
		return wrepo.getWalletInfo(id);
	}
}
