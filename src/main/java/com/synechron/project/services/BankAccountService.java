package com.synechron.project.services;

import java.util.List;

import com.synechron.project.dto.BankAccountDto;
import com.synechron.project.entities.BankAccount;

public interface BankAccountService {

	BankAccountDto addAccount(BankAccountDto account);

	List<BankAccountDto> getAllAccount(int walletId);

	BankAccountDto getAccountById(int id);

	BankAccountDto updateAccount(BankAccountDto accountDto, int walletId, long accountNo);

	void deleteAccount(int walletId, long accountNo);
	
	List<BankAccountDto> getAccountByWId(Integer wallet_id);

}
