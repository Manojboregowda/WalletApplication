package com.synechron.project.mapper;

import com.synechron.project.dto.CustomerDto;
import com.synechron.project.dto.WalletDto;
import com.synechron.project.entities.Wallet;

public class WalletMapperToWalletDto {
	
	public static WalletDto walletToWalletDtoMapper(Wallet wallet) {
		return WalletDto.builder().balance(wallet.getBalance()).build();
	}

	public static WalletDto walletToWalletDtoMapper(CustomerDto customer) {
		return WalletDto.builder().walletId(customer.getCustomerId()).build();
	}
}
