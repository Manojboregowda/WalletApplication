package com.synechron.project.mapper;

import java.util.ArrayList;
import java.util.List;

import com.synechron.project.dto.BankAccountDto;
import com.synechron.project.dto.CustomerDto;
import com.synechron.project.entities.BankAccount;
import com.synechron.project.entities.Customer;

public class BankAccountMapperToBankAccountDto {

	public BankAccountDto toDto(BankAccount account) {
		BankAccountDto dto = new BankAccountDto();
		dto.setAccountId(account.getAccountId());
		dto.setAccountNo(account.getAccountNo());
		dto.setBalance(account.getBalance());
		dto.setBankName(account.getBankName());
		dto.setIfscCode(account.getIfscCode());
		dto.setWalletId(account.getWallet().getWalletId());
		return dto;
	}

	public List<BankAccountDto> listtoDto(List<BankAccount> account) {
		List<BankAccountDto> list = new ArrayList<>();
		for (BankAccount bankAccount : account) {
			BankAccountDto dto = new BankAccountDto();
			dto.setAccountId(bankAccount.getAccountId());
			dto.setAccountNo(bankAccount.getAccountNo());
			dto.setBalance(bankAccount.getBalance());
			dto.setBankName(bankAccount.getBankName());
			dto.setIfscCode(bankAccount.getIfscCode());
			dto.setWalletId(bankAccount.getWallet().getWalletId());
			list.add(dto);
		}
		return list;
	}

}
