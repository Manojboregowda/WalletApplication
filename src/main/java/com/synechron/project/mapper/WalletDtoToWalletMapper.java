package com.synechron.project.mapper;

import com.synechron.project.dto.WalletDto;
import com.synechron.project.entities.Wallet;

public class WalletDtoToWalletMapper {
	
	public static Wallet walletDtoToWalletMapper(WalletDto walletDto) {
		return Wallet.builder().balance(walletDto.getBalance()).build();
	}

}
