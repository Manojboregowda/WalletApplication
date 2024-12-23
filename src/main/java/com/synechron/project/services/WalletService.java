package com.synechron.project.services;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import org.springframework.stereotype.Service;

import com.synechron.project.dto.CustomerDto;
import com.synechron.project.dto.WalletDto;
import com.synechron.project.entities.Customer;
import com.synechron.project.enums.BillType;
@Service
public interface WalletService {
	
	CustomerDto createAccount(CustomerDto customer);
	CustomerDto showBalance(String mobileNo);
	CustomerDto fundTransfer(String sourceMobileNo, String targetMobileNo, BigDecimal amount , BillType billType);
	CustomerDto depositAmount(String mobileNo, BigDecimal amount);
    List<CustomerDto> getList();
    CustomerDto updateAccount(int id, CustomerDto customerDto);
    CustomerDto addMoney(int customerId, BigDecimal amount);
    CustomerDto transferToWallet(int walletId, int accountId, BigDecimal amount);
//    String transferBankToBankThroughWallet(int fromAccountId, int toAccountId, BigDecimal amount);
    public CustomerDto transferMoneyToBeneficiary(int customerId, String beneficiaryMobile, BigDecimal amount) ;
	

}
