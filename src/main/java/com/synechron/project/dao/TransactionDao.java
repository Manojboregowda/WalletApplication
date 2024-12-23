package com.synechron.project.dao;

import java.time.LocalDate;
import java.util.List;

import com.synechron.project.entities.Transaction;

public interface TransactionDao {
    void addTransaction(Transaction transaction);
    List<Transaction> viewAllTransactions();
    List<Transaction> viewTransactionsByDate(LocalDate from, LocalDate to);
    List<Transaction> viewAllTransactions(String type);
}
