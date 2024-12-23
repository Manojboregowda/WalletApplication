package com.synechron.project.mapper;

import com.synechron.project.dto.TransactionDto;
import com.synechron.project.entities.Transaction;

public class TransactionMapperToTransactionDto {

	public static TransactionDto transactionToTransactionDto(Transaction transaction) {
	    if (transaction == null) {
	        return null;
	    }
	    TransactionDto transactionDto = new TransactionDto();
	    transactionDto.setTransactionId(transaction.getTransactionId());
	    transactionDto.setTransactionType(transaction.getTransactionType());
	    transactionDto.setTransactionDate(transaction.getTransactionDate());
	    transactionDto.setAmount(transaction.getAmount());
	    transactionDto.setDescription(transaction.getDescription());
	    transactionDto.setWallet(transaction.getWallet());
	    return transactionDto;
	}

}
