package com.synechron.project.dao;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import com.synechron.project.dto.CustomerDto;
import com.synechron.project.entities.Customer;
import com.synechron.project.entities.Wallet;
import com.synechron.project.enums.BillType;

public interface WalletDao {
	

	Customer createAccount(Customer customer);
	Customer showBalance(String mobileNo);
	Customer fundTransfer(String sourceMobileNo, String targetMobileNo, BigDecimal amount , BillType billType);
	Customer depositAmount(String mobileNo, BigDecimal amount);
	List<Customer> getList();
	Customer updateAccount(int id, Customer customer);
	Customer addMoney(int customerId, BigDecimal amount);
	public Customer transferToWallet(int fromAccountId, int toAccountId, BigDecimal amount);
//	   public String transferToBankAccount(int walletId, int accountId, BigDecimal amount);
	Customer transferMoneyToBeneficiary(int walletId, String beneficiaryMobile, BigDecimal amount);
	
	  
	

}
