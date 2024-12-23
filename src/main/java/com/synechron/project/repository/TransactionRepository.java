package com.synechron.project.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.synechron.project.entities.Transaction;
import com.synechron.project.entities.Wallet;
@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findByWallet_Customer_CustomerId(int customerId);

    List<Transaction> findByWallet_WalletId(int walletId);

    public List<Transaction> findByTransactionDateBetween(LocalDate startDate, LocalDate endDate);

    List<Transaction> findByTransactionType(String transactionType);

	List<Transaction> findByTransactionDateBetweenAndWallet(LocalDate from, LocalDate to, Wallet wallet);
}
