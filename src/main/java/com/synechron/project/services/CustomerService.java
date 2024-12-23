package com.synechron.project.services;

import com.synechron.project.dto.CustomerDto;
import com.synechron.project.dto.WalletDto;

public interface CustomerService {

	CustomerDto addCustomer(CustomerDto customer);

	CustomerDto loginCustomer(String mobile, String password);
	
	//changes
	WalletDto getAccountInfo(CustomerDto customer);
	
	Integer getWalletId(int id);

}
