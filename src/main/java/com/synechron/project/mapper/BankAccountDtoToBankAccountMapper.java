package com.synechron.project.mapper;

import org.springframework.stereotype.Component;

import com.synechron.project.dto.BankAccountDto;

import com.synechron.project.entities.BankAccount;
import com.synechron.project.entities.Wallet;

@Component
public class BankAccountDtoToBankAccountMapper {
	
	public  BankAccount toEntity(BankAccountDto dto) {
		BankAccount account = new BankAccount();
        account.setAccountId(dto.getAccountId());
        account.setAccountNo(dto.getAccountNo());
        account.setBalance(dto.getBalance());
        account.setBankName(dto.getBankName());
        account.setIfscCode(dto.getIfscCode());

        return account;
    }

}
