package com.synechron.project.dao;

import java.util.List;
import java.util.Optional;

import com.synechron.project.dto.BankAccountDto;
import com.synechron.project.entities.BankAccount;

public interface BankAccountDao {

	BankAccount addAccount(BankAccount account);

	List<BankAccount> getAllAccount();

	BankAccount findById(int id);

	BankAccount updateAccount(BankAccount bank);
	void deleteAccount(int id);
	
	List<BankAccount> findByWId(Integer id);
}
