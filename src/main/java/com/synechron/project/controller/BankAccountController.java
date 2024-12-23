package com.synechron.project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.synechron.project.dto.BankAccountDto;
import com.synechron.project.exception.AccountNotFoundException;
import com.synechron.project.exception.AccountServiceException;
import com.synechron.project.exception.CustomerNotFoundException;
import com.synechron.project.exception.ResourceNotFoundException;
import com.synechron.project.services.BankAccountService;

@CrossOrigin(originPatterns = "http://localhost:4200")
@RestController
public class BankAccountController {
	@Autowired
	BankAccountService service;

	@PostMapping("/addaccount")
	public ResponseEntity<?> addAccount(@RequestBody BankAccountDto account) {
		try {
			BankAccountDto addedAccount = service.addAccount(account);
			return new ResponseEntity<>(addedAccount, HttpStatus.CREATED);
		} catch (AccountServiceException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

//    @PostMapping("/addaccountchanged")
//    public ResponseEntity<?> addAccount1(@RequestBody BankAccountDto)

	@GetMapping("/getaccount/{walletId}")
	public ResponseEntity<?> getAllAccount(@PathVariable int walletId) {
		try {
			List<BankAccountDto> accounts = service.getAllAccount(walletId);
			return new ResponseEntity<>(accounts, HttpStatus.OK);
		} catch (AccountServiceException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/getaccountbyaccid/{id}")
	public ResponseEntity<?> getAccountById(@PathVariable("id") int id) {
		try {
			BankAccountDto account = service.getAccountById(id);
			return new ResponseEntity<>(account, HttpStatus.OK);
		} catch (AccountNotFoundException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (AccountServiceException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/getAllAccounts/{wallet_id}")
	public ResponseEntity<?> getAllAccountsByWId(@PathVariable Integer wallet_id) {
		try {
			List<BankAccountDto> account = service.getAccountByWId(wallet_id);
			return new ResponseEntity<>(account, HttpStatus.OK);
		} catch (AccountNotFoundException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (AccountServiceException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/updateaccount/{walletId}/{accountNo}")
	public ResponseEntity updateAccount(@PathVariable("walletId") int walletId,
			@PathVariable("accountNo") long accountNo, @RequestBody BankAccountDto accountDto) {
		try {
			BankAccountDto updatedAccount = service.updateAccount(accountDto, walletId, accountNo);
			return new ResponseEntity<>(updatedAccount, HttpStatus.OK);
		} catch (AccountNotFoundException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (CustomerNotFoundException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (AccountServiceException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/deleteaccount/{walletId}/{accountNo}")
	public ResponseEntity deleteAccount(@PathVariable("walletId") int walletId,
			@PathVariable("accountNo") long accountNo) {
		try {
			service.deleteAccount(walletId, accountNo);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (AccountNotFoundException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (CustomerNotFoundException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (AccountServiceException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}

//	@Autowired
//	    BankAccountService service;
//
//	    @PostMapping("/addaccount")
//	    public ResponseEntity<BankAccountDto> addAccount(@RequestBody BankAccountDto account) {
//	        try {
//	            BankAccountDto addedAccount = service.addAccount(account);
//	            return new ResponseEntity<>(addedAccount, HttpStatus.CREATED);
//	        } catch (Exception e) {
//	            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
//	        }
//	    }
//
//	    @GetMapping("/getaccount")
//	    public ResponseEntity<List<BankAccountDto>> getAllAccount() {
//	        try {
//	            List<BankAccountDto> accounts = service.getAllAccount();
//	            return new ResponseEntity<>(accounts, HttpStatus.OK);
//	        } catch (Exception e) {
//	            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
//	        }
//	    }
//
//	    @GetMapping("/getaccount/{id}")
//	    public ResponseEntity<BankAccountDto> getAccountById(@PathVariable("id") int id) {
//	        try {
//	            BankAccountDto account = service.getAccountById(id);
//	            return new ResponseEntity<>(account, HttpStatus.OK);
//	        } catch (ResourceNotFoundException e) {
//	            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
//	        } catch (Exception e) {
//	            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
//	        }
//	    }
//
//	    @PutMapping("/updateaccount/{id}")
//	    public ResponseEntity<BankAccountDto> updateAccount(@PathVariable("id") int id, @RequestBody BankAccountDto accountDto) {
//	        try {
//	            BankAccountDto updatedAccount = service.updateAccount(accountDto, id);
//	            return new ResponseEntity<>(updatedAccount, HttpStatus.OK);
//	        } catch (ResourceNotFoundException e) {
//	            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
//	        } catch (Exception e) {
//	            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
//	        }
//	    }
//
//	    @DeleteMapping("/account/{id}")
//	    public ResponseEntity<Void> deleteAccount(@PathVariable("id") int id) {
//	        try {
//	            service.deleteAccount(id);
//	            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//	        } catch (ResourceNotFoundException e) {
//	            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//	        } catch (Exception e) {
//	            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//	        }
//	    }
//}
//
