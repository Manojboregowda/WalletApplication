package com.synechron.project.dao;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.synechron.project.dto.BillPaymentDto;
import com.synechron.project.dto.CustomerDto;
import com.synechron.project.dto.TransactionDto;
import com.synechron.project.entities.BankAccount;
import com.synechron.project.entities.Customer;
import com.synechron.project.entities.Wallet;
import com.synechron.project.enums.BillType;
import com.synechron.project.repository.BankAccountRepository;
import com.synechron.project.repository.CustomerRepository;
import com.synechron.project.repository.WalletRepository;
import com.synechron.project.services.BillPaymentService;
import com.synechron.project.services.TransactionService;
import com.synechron.project.exception.CustomerException;
import com.synechron.project.exception.CustomerNotFoundException;
import com.synechron.project.exception.InSufficientFundsException;
import com.synechron.project.mapper.CustomerDtoToCustomerMapper;
import com.synechron.project.mapper.TransactionDtoToTransactionMapper;

@Repository
public class WalletDaoImpl implements WalletDao {

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private WalletRepository walletRepository;

	@Autowired
	private TransactionService transactionService;

	@Autowired
	private BillPaymentService billPaymentService;

	@Autowired
	private BankAccountRepository bankAccountRepository;

	@Override
	public Customer createAccount(Customer customer) {
		List<Customer> existingCustomers = customerRepository.findCustomerByMobile(customer.getMobile());

		if (existingCustomers.isEmpty()) {


			Customer savedCustomer = customerRepository.save(customer);

			Wallet wallet = new Wallet();
			wallet.setBalance(BigDecimal.ZERO);
			wallet.setCustomer(savedCustomer);

			Wallet savedWallet = walletRepository.save(wallet);

			savedCustomer.setWallet(savedWallet);

			return savedCustomer;
		}
		throw new CustomerException("Duplicate Mobile Number [ Already Registered with different customer ]");
	}

	@Override
	public Customer showBalance(String mobileNo) {
		List<Customer> customers = customerRepository.findCustomerByMobile(mobileNo);

		if (!customers.isEmpty()) {
			return customers.get(0);
		}


		throw new CustomerException("Customer not found with mobile number: " + mobileNo);
	}

	@Override
	public Customer fundTransfer(String sourceMobileNo, String targetMobileNo, BigDecimal amount , BillType billType) {
		List<Customer> sourceCustomers = customerRepository.findCustomerByMobile(sourceMobileNo);
		if (sourceCustomers.isEmpty()) {
			throw new CustomerException("Source customer not found with mobile number: " + sourceMobileNo);
		}
		Customer sourceCustomer = sourceCustomers.get(0);

		List<Customer> targetCustomers = customerRepository.findCustomerByMobile(targetMobileNo);
		if (targetCustomers.isEmpty()) {
			throw new CustomerException("Target customer not found with mobile number: " + targetMobileNo);
		}
		Customer targetCustomer = targetCustomers.get(0);

		Wallet sourceWallet = sourceCustomer.getWallet();
		Wallet targetWallet = targetCustomer.getWallet();
		if (sourceWallet == null || targetWallet == null) {
			throw new CustomerException("Wallet not found for one of the customers.");
		}

		if (sourceWallet.getBalance().compareTo(amount) < 0) {
			throw new InSufficientFundsException("Insufficient balance for transfer.");
		}

		sourceWallet.setBalance(sourceWallet.getBalance().subtract(amount));

		targetWallet.setBalance(targetWallet.getBalance().add(amount));

		walletRepository.save(sourceWallet);
		walletRepository.save(targetWallet);

		TransactionDto transactionDto = new TransactionDto();
		transactionDto.setAmount(amount.doubleValue());
		transactionDto.setDescription("An amount of INR "+ amount + "has been DEBITED to your account mobile number "+ sourceCustomer.getMobile() 
		+ " on "+ LocalDate.now()+". Total Avail.Bal INR "+ sourceWallet.getBalance());    	transactionDto.setTransactionType("Debit");
		transactionDto.setTransactionDate(LocalDate.now());
		transactionService.addTransaction(transactionDto,sourceWallet);

		TransactionDto transactionDtoTarget = new TransactionDto();
		transactionDtoTarget.setAmount(amount.doubleValue());
		transactionDtoTarget.setDescription("An amount of INR "+ amount + "has been CREDITED to your account mobile number "+ targetCustomer.getMobile() 
		+ " on "+ LocalDate.now()+". Total Avail.Bal INR "+ targetWallet.getBalance());        transactionDtoTarget.setTransactionType("Credit");
		transactionDtoTarget.setTransactionDate(LocalDate.now());
		transactionService.addTransaction(transactionDtoTarget, targetWallet);

		BillPaymentDto billPaymentDto = new BillPaymentDto();
		billPaymentDto.setBillType(billType);
		billPaymentDto.setPaymentDate(LocalDate.now());
		billPaymentDto.setWallet(sourceWallet);
		billPaymentDto.setAmount(amount.doubleValue());

		BillPaymentDto billPayment = billPaymentService.addBillPayment(billPaymentDto);

		return sourceCustomer;
	}

	@Override
	public Customer depositAmount(String mobileNo, BigDecimal amount) {
		List<Customer> customers = customerRepository.findCustomerByMobile(mobileNo);
		if (!customers.isEmpty()) {
			Customer customer = customers.get(0);
			Wallet wallet = customer.getWallet();

			if (wallet == null) {
				throw new RuntimeException("Wallet not found for customer with mobile number: " + mobileNo);
			}

			wallet.setBalance(wallet.getBalance().add(amount));

			TransactionDto transactionDto = new TransactionDto();
			transactionDto.setAmount(amount.doubleValue());
			transactionDto.setDescription("An amount of INR " + amount + " has been CREDITED to your account mobile number " 
					+ customer.getMobile() + " on " + LocalDate.now() + ". Total Available Balance INR " + wallet.getBalance());
			transactionDto.setTransactionType("Credit");
			transactionDto.setTransactionDate(LocalDate.now());

			transactionService.addTransaction(transactionDto, wallet); 

			BillPaymentDto billPaymentDto = new BillPaymentDto();
			billPaymentDto.setBillType(BillType.AMOUNT_TRANSFER);
			billPaymentDto.setPaymentDate(LocalDate.now());
			billPaymentDto.setWallet(wallet);
			billPaymentDto.setAmount(amount.doubleValue());

			BillPaymentDto billPayment = billPaymentService.addBillPayment(billPaymentDto);
			walletRepository.save(wallet);
			return customer;
		}

		throw new CustomerException("Customer not found with mobile number: " + mobileNo);
	}


	@Override
	public List<Customer> getList() {
		return customerRepository.findAll();
	}

	@Override
	public Customer updateAccount(int id, Customer customer) {
		Optional<Customer> existingCustomerOpt = customerRepository.findById(id);

		if (existingCustomerOpt.isPresent()) {
			Customer existingCustomer = existingCustomerOpt.get();

			existingCustomer.setCustomerName(customer.getCustomerName());
			existingCustomer.setMobile(customer.getMobile());
			existingCustomer.setPassword(customer.getPassword());

			Customer updatedCustomer = customerRepository.save(existingCustomer);

			return updatedCustomer;
		} else {
			throw new CustomerException("Customer not found with ID: " + id);
		}
	}

	@Override
	public Customer addMoney(int id, BigDecimal amount) {

		Optional<Customer> byId = customerRepository.findById(id);
		if(byId!=null) {
			Customer customer = byId.get();
			Wallet wallet = customer.getWallet();
			wallet.setBalance(wallet.getBalance().add(amount));
			walletRepository.save(wallet);
			customerRepository.save(customer);
			TransactionDto transactionDto = new TransactionDto();
			transactionDto.setAmount(amount.doubleValue());
			transactionDto.setDescription("An amount of INR "+ amount + "has been CREDITED to your account mobile number "+ customer.getMobile() 
			+ " on "+ LocalDate.now()+". Total Avail.Bal INR "+ wallet.getBalance());
			transactionDto.setTransactionType("Credit");
			transactionDto.setTransactionDate(LocalDate.now());
			transactionService.addTransaction(transactionDto,wallet);
			return customer;

		}
		else {
			throw new CustomerNotFoundException("Customer with the id : "+ id + " not found in the database");
		}
	}


	public Customer transferToWallet(int customerId, int accountNumber, BigDecimal amount) {
		Optional<Customer> byId = customerRepository.findById(customerId);
		Customer customer = null;
		if(byId!=null) {
			customer = byId.get();
		}
		List<BankAccount> byAccountNo = bankAccountRepository.findByAccountNo(accountNumber);


		Wallet wallet = customer.getWallet();
		BankAccount bankAccount = byAccountNo.get(0);
		if (bankAccount.getBalance() >= amount.doubleValue()) {
			wallet.setBalance(wallet.getBalance().add(amount));
			bankAccount.setBalance(bankAccount.getBalance() - amount.doubleValue());

			walletRepository.save(wallet);
			bankAccountRepository.save(bankAccount);
			TransactionDto transactionDto = new TransactionDto();
			transactionDto.setAmount(amount.doubleValue());
			transactionDto.setDescription("An amount of INR "+ amount + "has been CREDITED to your account mobile number "+ customer.getMobile() 
			+ " on "+ LocalDate.now()+". Total Avail.Bal INR "+ wallet.getBalance());
			transactionDto.setTransactionType("Credit");
			transactionDto.setTransactionDate(LocalDate.now());
			transactionService.addTransaction(transactionDto,wallet);
			return customer;
		}else {
			throw new CustomerNotFoundException();
		}


	}
	@Override
	public Customer transferMoneyToBeneficiary(int customerId, String beneficiaryMobile, BigDecimal amount) {
	   Optional<Customer> byId = customerRepository.findById(customerId);
	   if (byId.isEmpty()) {
	       throw new CustomerNotFoundException("Customer with id " + customerId + " not found");
	   }

	   Customer sourceCustomer = byId.get();
	   Wallet sourceWallet = sourceCustomer.getWallet();

	   List<Customer> targetCustomers = customerRepository.findCustomerByMobile(beneficiaryMobile);
	   if (targetCustomers.isEmpty()) {
	       throw new CustomerNotFoundException("Beneficiary with mobile " + beneficiaryMobile + " not found");
	   }

	   Customer targetCustomer = targetCustomers.get(0);
	   Wallet targetWallet = targetCustomer.getWallet();

	   if (sourceWallet.getBalance().compareTo(amount) < 0) {
	       throw new InSufficientFundsException("Insufficient balance for transfer");
	   }

	   sourceWallet.setBalance(sourceWallet.getBalance().subtract(amount));
	   targetWallet.setBalance(targetWallet.getBalance().add(amount));

	   walletRepository.save(sourceWallet);
	   walletRepository.save(targetWallet);

	   TransactionDto sourceTransaction = new TransactionDto();
	   sourceTransaction.setAmount(amount.doubleValue());
	   sourceTransaction.setDescription("An amount of INR " + amount + " has been DEBITED to your account mobile number " + sourceCustomer.getMobile() 
	           + " on " + LocalDate.now() + ". Total Avail.Bal INR " + sourceWallet.getBalance());
	   sourceTransaction.setTransactionType("Debit"); 
	   sourceTransaction.setTransactionDate(LocalDate.now());
	   transactionService.addTransaction(sourceTransaction, sourceWallet);

	   TransactionDto targetTransaction = new TransactionDto();
	   targetTransaction.setAmount(amount.doubleValue());
	   targetTransaction.setDescription("An amount of INR " + amount + " has been CREDITED to your account mobile number " + targetCustomer.getMobile()
	           + " on " + LocalDate.now() + ". Total Avail.Bal INR " + targetWallet.getBalance());
	   targetTransaction.setTransactionType("Credit");
	   targetTransaction.setTransactionDate(LocalDate.now());
	   transactionService.addTransaction(targetTransaction, targetWallet);

	   BillPaymentDto billPayment = new BillPaymentDto();
	   billPayment.setBillType(BillType.AMOUNT_TRANSFER);
	   billPayment.setPaymentDate(LocalDate.now());
	   billPayment.setWallet(sourceWallet);
	   billPayment.setAmount(amount.doubleValue());
	   billPaymentService.addBillPayment(billPayment);

	   return sourceCustomer;
	}
	//    public String transferBankToBankThroughWallet(int fromAccountId, int toAccountId, BigDecimal amount) {
	//        Optional<BankAccount> fromAccountOpt = bankAccountRepository.findById(fromAccountId);
	//        Optional<BankAccount> toAccountOpt = bankAccountRepository.findById(toAccountId);
	//
	//        if (fromAccountOpt.isPresent() && toAccountOpt.isPresent()) {
	//            BankAccount fromAccount = fromAccountOpt.get();
	//            BankAccount toAccount = toAccountOpt.get();
	//            Wallet wallet = fromAccount.getWallet();
	//
	//            if (fromAccount.getBalance() >= amount.doubleValue()) {
	//                fromAccount.setBalance(fromAccount.getBalance() - amount.doubleValue());
	//                wallet.setBalance(wallet.getBalance().add(amount));
	//
	//                toAccount.setBalance(toAccount.getBalance() + amount.doubleValue());
	//                wallet.setBalance(wallet.getBalance().subtract(amount));
	//
	//                bankAccountRepository.save(fromAccount);
	//                bankAccountRepository.save(toAccount);
	//                walletRepository.save(wallet);
	//
	//                return "Bank-to-Bank Transfer Successful Through Wallet!";
	//            } else {
	//                return "Insufficient Funds in Source Bank Account!";
	//            }
	//        }
	//        return "Bank Accounts Not Found!";
	//    }


}