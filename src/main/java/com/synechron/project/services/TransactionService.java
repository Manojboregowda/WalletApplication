package com.synechron.project.services;

import com.synechron.project.dto.TransactionDto;
import com.synechron.project.entities.Wallet;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
@Service
public interface TransactionService {
    TransactionDto addTransaction(TransactionDto transactionDto,Wallet wallet );
    List<TransactionDto> viewAllTransactions(int cid);
    List<TransactionDto> viewTransactionsByDate(int id,LocalDate from, LocalDate to);
    List<TransactionDto> viewAllTransactions(String type);
}
