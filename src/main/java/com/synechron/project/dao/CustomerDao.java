package com.synechron.project.dao;


import com.synechron.project.dto.CustomerDto;
import com.synechron.project.dto.WalletDto;
import com.synechron.project.entities.Customer;

public interface CustomerDao {

	Customer addCustomer(Customer customer);

	CustomerDto loginCustomer(String mobile, String password);
	
	//changes..
	WalletDto getWalletId(CustomerDto customer);
	
	Integer getWalletId(int id);

}
