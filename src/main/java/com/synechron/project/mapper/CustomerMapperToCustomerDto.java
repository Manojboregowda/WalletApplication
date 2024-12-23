package com.synechron.project.mapper;

import com.synechron.project.dto.CustomerDto;
import com.synechron.project.entities.Customer;

public class CustomerMapperToCustomerDto {
	
	 public static CustomerDto toDto(Customer customer) {
	        CustomerDto dto = new CustomerDto();
	        dto.setCustomerId(customer.getCustomerId());
	        dto.setCustomerName(customer.getCustomerName());
	        dto.setMobile(customer.getMobile());
	        dto.setPassword(customer.getPassword());
	        dto.setWalletDto(WalletMapperToWalletDto.walletToWalletDtoMapper(customer.getWallet()));
	        return dto;
	    }
}
