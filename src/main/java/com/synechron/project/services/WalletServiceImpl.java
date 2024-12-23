package com.synechron.project.services;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.synechron.project.dao.WalletDao;
import com.synechron.project.dto.CustomerDto;
import com.synechron.project.dto.WalletDto;
import com.synechron.project.entities.Customer;
import com.synechron.project.entities.Wallet;
import com.synechron.project.enums.BillType;
import com.synechron.project.exception.CustomerException;
import com.synechron.project.mapper.CustomerDtoToCustomerMapper;
import com.synechron.project.mapper.CustomerMapperToCustomerDto;
import com.synechron.project.mapper.WalletDtoToWalletMapper;
import com.synechron.project.repository.CustomerRepository;
import com.synechron.project.repository.WalletRepository;
@Service
public class WalletServiceImpl implements WalletService{
	

	@Autowired
	private WalletDao dao;
	
	

	@Override
	public CustomerDto createAccount(CustomerDto customer) {
		Customer entity = CustomerDtoToCustomerMapper.toEntity(customer);
	   Customer account = dao.createAccount(entity);
	   CustomerDto customerDTo = CustomerMapperToCustomerDto.toDto(account);
	   return customerDTo;
	}



	@Override
	public CustomerDto showBalance(String mobileNo) {
		Customer showBalance = dao.showBalance(mobileNo);
		CustomerDto customerDto = CustomerMapperToCustomerDto.toDto(showBalance);
		return customerDto;
	}

	@Override
	public CustomerDto fundTransfer(String sourceMobileNo, String targetMobileNo, BigDecimal amount , BillType billType) {
		Customer fundTransfer = dao.fundTransfer(sourceMobileNo, targetMobileNo, amount , billType );
	return	CustomerMapperToCustomerDto.toDto(fundTransfer);
	}

	@Override
	public CustomerDto depositAmount(String mobileNo, BigDecimal amount) {
		Customer depositAmount = dao.depositAmount(mobileNo, amount);
		return CustomerMapperToCustomerDto.toDto(depositAmount);
	}

	@Override
	public List<CustomerDto> getList() {
		ArrayList<CustomerDto> arrayList = new ArrayList<>();
		List<Customer> lists = dao.getList();
		for(Customer customer : lists) {
			arrayList.add(CustomerMapperToCustomerDto.toDto(customer));
		}
		return arrayList;
	}

	@Override
	public CustomerDto updateAccount(int id, CustomerDto customerDto) {
		Customer entity = CustomerDtoToCustomerMapper.toEntity(customerDto);
		Customer updateAccount = dao.updateAccount(id ,entity);
		
				return CustomerMapperToCustomerDto.toDto(updateAccount);
	}

	@Override
	public CustomerDto addMoney(int customerId, BigDecimal amount) {
		
		Customer money = dao.addMoney(customerId, amount);
		return CustomerMapperToCustomerDto.toDto(money);
	}
	
	  @Override
	    public CustomerDto transferToWallet(int walletId, int accountId, BigDecimal amount) {
	        Customer transferToWallet = dao.transferToWallet(accountId, accountId, amount);
	        return CustomerMapperToCustomerDto.toDto(transferToWallet);
	    }
	  
	  @Override
	public CustomerDto transferMoneyToBeneficiary(int customerId, String beneficiaryMobile, BigDecimal amount) {
	Customer transferMoneyToBeneficiary = dao.transferMoneyToBeneficiary(customerId, beneficiaryMobile, amount);
		return CustomerMapperToCustomerDto.toDto(transferMoneyToBeneficiary);
	}

//	    @Override
//	    public String transferBankToBankThroughWallet(int fromAccountId, int toAccountId, BigDecimal amount) {
//	        return dao.transferBankToBankThroughWallet(fromAccountId, toAccountId, amount);
//	    }

}
