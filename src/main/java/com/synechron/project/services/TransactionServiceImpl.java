package com.synechron.project.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.synechron.project.dto.TransactionDto;
import com.synechron.project.entities.Customer;
import com.synechron.project.entities.Transaction;
import com.synechron.project.entities.Wallet;
import com.synechron.project.exception.CustomerNotFoundException;
import com.synechron.project.mapper.TransactionDtoToTransactionMapper;
import com.synechron.project.mapper.TransactionMapperToTransactionDto;
import com.synechron.project.repository.CustomerRepository;
import com.synechron.project.repository.TransactionRepository;
import com.synechron.project.repository.WalletRepository;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private CustomerRepository customerRepository;
    
    @Autowired
    private WalletRepository walletRepository;

    @Override
    public TransactionDto addTransaction(TransactionDto transactionDto, Wallet wallet) {
        if (wallet == null) {
            throw new RuntimeException("Wallet is null, cannot add transaction.");
        }

        Transaction transaction = TransactionDtoToTransactionMapper.transactionDtoToTransaction(transactionDto);

        transaction.setWallet(wallet);

        wallet.getTransactions().add(transaction);

        Transaction savedTransaction = transactionRepository.save(transaction);
        walletRepository.save(wallet); 

        return TransactionMapperToTransactionDto.transactionToTransactionDto(savedTransaction);
    }


    @Override
    public List<TransactionDto> viewAllTransactions(int id) {
    	Optional<Customer> byId = customerRepository.findById(id);
    	if(byId.isPresent()) {
    		Customer customer = byId.get();
    		Wallet wallet = customer.getWallet();
    		List<Transaction> transactions = wallet.getTransactions();
    		ArrayList<TransactionDto> arrayList = new ArrayList<>();
    		for(Transaction transaction : transactions) {
    			arrayList.add(TransactionMapperToTransactionDto.transactionToTransactionDto(transaction));
    		}
    		return arrayList;
    	}
    	else 
    		throw new CustomerNotFoundException("customer is not found with this ID ");
         
    }

    @Override
    public List<TransactionDto> viewTransactionsByDate(int id, LocalDate from, LocalDate to) {
        Optional<Customer> byId = customerRepository.findById(id);
        if (byId.isPresent()) {
            Customer customer = byId.get();
            Wallet wallet = customer.getWallet();
            List<Transaction> transactions = transactionRepository.findByTransactionDateBetweenAndWallet(
                from, to, wallet
            );
            List<TransactionDto> transactionDtos = transactions.stream()
                    .map(TransactionMapperToTransactionDto::transactionToTransactionDto)
                    .collect(Collectors.toList());

            return transactionDtos;
        }
        throw new CustomerNotFoundException("Customer with ID " + id + " not found");
    }


    @Override
    public List<TransactionDto> viewAllTransactions(String type) {
        List<Transaction> transactions = transactionRepository.findByTransactionType(type);
        return transactions.stream()
                .map(TransactionMapperToTransactionDto::transactionToTransactionDto)
                .collect(Collectors.toList());
    }
}