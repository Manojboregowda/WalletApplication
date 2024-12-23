package com.synechron.project.mapper;

import com.synechron.project.dto.TransactionDto;
import com.synechron.project.entities.Transaction;

public class TransactionDtoToTransactionMapper {

    public static Transaction transactionDtoToTransaction(TransactionDto transactionDto) {
    	if (transactionDto == null) {
            return null;
        }
        return Transaction.builder()
                .transactionId(transactionDto.getTransactionId())
                .transactionType(transactionDto.getTransactionType())
                .transactionDate(transactionDto.getTransactionDate())
                .amount(transactionDto.getAmount())
                .description(transactionDto.getDescription())
                .wallet(transactionDto.getWallet())
                .build();
    }
}