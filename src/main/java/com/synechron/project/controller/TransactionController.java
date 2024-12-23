package com.synechron.project.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.synechron.project.dto.TransactionDto;
import com.synechron.project.entities.Wallet;
import com.synechron.project.services.TransactionService;

@RestController
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping("/addTransaction")
    public TransactionDto addTransaction(@RequestBody TransactionDto transactionDto) {
        return transactionService.addTransaction(transactionDto,null);
    }

    @GetMapping("/transactions/{cid}")
    public List<TransactionDto> viewAllTransactions(@PathVariable int cid) {
        return transactionService.viewAllTransactions(cid);
    }

    @GetMapping("/viewTransactionsByDate/{id}")
    public List<TransactionDto> viewTransactionsByDate(@PathVariable int id, @RequestParam("from") LocalDate from, @RequestParam("to") LocalDate to) {
        return transactionService.viewTransactionsByDate(id, from, to);
    }

    @GetMapping("/viewAllTransactions/{type}")
    public List<TransactionDto> viewAllTransactions(@PathVariable("type") String type) {
        return transactionService.viewAllTransactions(type);
    }
}
