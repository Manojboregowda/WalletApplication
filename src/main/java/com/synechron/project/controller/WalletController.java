package com.synechron.project.controller;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.synechron.project.dto.CustomerDto;
import com.synechron.project.dto.WalletDto;
import com.synechron.project.enums.BillType;
import com.synechron.project.services.WalletService;
@CrossOrigin(originPatterns = "http://localhost:4200")
@RestController
@RequestMapping("/wallet")
public class WalletController {

    @Autowired
    private WalletService walletService;

    @PostMapping("/create")
    public ResponseEntity<?> createAccount(@RequestBody CustomerDto customerDto) {
        try {
            CustomerDto account = walletService.createAccount(customerDto);
            return new ResponseEntity<>(account, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/balance")
    public ResponseEntity<?> showBalance(@RequestParam String mobileNo) {
        try {
            CustomerDto balance = walletService.showBalance(mobileNo);
            return new ResponseEntity<>(balance, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/fundtransfer")
    public ResponseEntity<?> fundTransfer(@RequestParam String sourceMobileNo, @RequestParam String targetMobileNo, @RequestParam BigDecimal amount ,@RequestParam BillType billType) {
        try {
            CustomerDto result = walletService.fundTransfer(sourceMobileNo, targetMobileNo, amount , billType);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/depositamount")
    public ResponseEntity<?> depositAmount(@RequestParam String mobileNo, @RequestParam BigDecimal amount) {
        try {
            CustomerDto result = walletService.depositAmount(mobileNo, amount);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/customers")
    public ResponseEntity<?> getList() {
        try {
            List<CustomerDto> list = walletService.getList();
            return new ResponseEntity<>(list, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/updateaccount/{id}")
    public ResponseEntity<?> updateAccount(@PathVariable int id, @RequestBody CustomerDto customerDto) {
        try {
            CustomerDto updatedAccount = walletService.updateAccount(id, customerDto);
            return new ResponseEntity<>(updatedAccount, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/addmoney/{customerId}")
    public ResponseEntity<?> addMoney(@PathVariable int customerId, @RequestParam BigDecimal amount) {
        try {
            CustomerDto result = walletService.addMoney(customerId, amount);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/transferToBank")
    public ResponseEntity<?> transferToWallet(@RequestParam int customerId,
                                                 @RequestParam int accountId,
                                                 @RequestParam BigDecimal amount) {
    	try {
        CustomerDto transferToWallet = walletService.transferToWallet(customerId, accountId, amount);
        
       return new ResponseEntity<>(transferToWallet, HttpStatus.OK);
    	}
    	catch(Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);

    	}
    }
    @PostMapping("/transfer/beneficiary")
    public ResponseEntity<?> transferMoneyToBeneficiary( @RequestParam int customerId, @RequestParam String beneficiaryMobile, @RequestParam BigDecimal amount) {
        try {
            CustomerDto result = walletService.transferMoneyToBeneficiary(
                customerId, beneficiaryMobile, amount);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    
}
