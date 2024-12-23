package com.synechron.project.dao;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.synechron.project.entities.Transaction;
import com.synechron.project.exception.TransactionException;
import com.synechron.project.repository.TransactionRepository;

@Repository
public class TransactionDaoImpl implements TransactionDao {

    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public void addTransaction(Transaction transaction) {
        try {
            transactionRepository.save(transaction);
        } catch (Exception e) {
            throw new TransactionException("Failed to add transaction: " + e.getMessage());
        }
    }

    @Override
    public List<Transaction> viewAllTransactions() {
        try {
            return transactionRepository.findAll();
        } catch (Exception e) {
            throw new TransactionException("Failed to retrieve all transactions: " + e.getMessage());
        }
    }

    @Override
    public List<Transaction> viewTransactionsByDate(LocalDate from, LocalDate to) {
        try {
            return transactionRepository.findByTransactionDateBetween(from, to);
        } catch (Exception e) {
            throw new TransactionException("Failed to retrieve transactions by date: " + e.getMessage());
        }
    }

    @Override
    public List<Transaction> viewAllTransactions(String type) {
        try {
            return transactionRepository.findByTransactionType(type);
        } catch (Exception e) {
            throw new TransactionException("Failed to retrieve transactions by type: " + e.getMessage());
        }
    }
}