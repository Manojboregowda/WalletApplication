package com.synechron.project.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.synechron.project.dao.BankAccountDao;
import com.synechron.project.dto.BankAccountDto;

import com.synechron.project.entities.BankAccount;
import com.synechron.project.entities.Wallet;
import com.synechron.project.exception.AccountNotFoundException;
import com.synechron.project.exception.AccountServiceException;
import com.synechron.project.exception.CustomerNotFoundException;
import com.synechron.project.mapper.BankAccountDtoToBankAccountMapper;
import com.synechron.project.mapper.BankAccountMapperToBankAccountDto;
import com.synechron.project.repository.BankAccountRepository;
import com.synechron.project.repository.WalletRepository;


@Service
public class BankAccountServiceImpl implements BankAccountService {
    @Autowired
    BankAccountDao dao;
    @Autowired
    private WalletRepository repository;
    @Autowired
    private BankAccountRepository accountRepository;
    @Autowired
    private BankAccountDtoToBankAccountMapper dto;

    @Override
    public BankAccountDto addAccount(BankAccountDto accountDto) {
        try {
            Wallet wallet = repository.findById(accountDto.getWalletId())
                .orElseThrow(CustomerNotFoundException::new);
            
            BankAccount entity = dto.toEntity(accountDto);
            entity.setWallet(wallet);
            wallet.addBankAccount(entity);
            
            wallet = repository.saveAndFlush(wallet);
            
            BankAccount savedAccount = wallet.getBankAccounts()
                .stream()
                .filter(acc -> acc.getAccountNo() == entity.getAccountNo())
                .findFirst()
                .orElseThrow(() -> new AccountServiceException("Error saving account"));
            
            return new BankAccountMapperToBankAccountDto().toDto(savedAccount);
            
        } catch (Exception e) {
            throw new AccountServiceException("Error adding account: " + e.getMessage());
        }
    }

    @Override
    public List<BankAccountDto> getAllAccount(int id) {
        try {
            Wallet wallet = repository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException("Wallet not found with id: " + id));

            if (wallet.getBankAccounts() == null || wallet.getBankAccounts().isEmpty()) {
                return new ArrayList<>();
            }

            List<BankAccount> accounts = wallet.getBankAccounts();
            List<BankAccountDto> accountDtoList = new ArrayList<>();
            BankAccountMapperToBankAccountDto mapper = new BankAccountMapperToBankAccountDto();
            
            for (BankAccount acc : accounts) {
                if (acc != null) {
                    accountDtoList.add(mapper.toDto(acc));
                }
            }
            
            return accountDtoList;
        } catch (CustomerNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new AccountServiceException("Error retrieving accounts: " + e.getMessage());
        }
    }

    @Override
    public BankAccountDto getAccountById(int id) {
        try {
            BankAccount acc = dao.findById(id);
            if (acc == null) {
                throw new AccountNotFoundException("Account not found with id: " + id);
            }
            BankAccountMapperToBankAccountDto mapper = new BankAccountMapperToBankAccountDto();
            return mapper.toDto(acc);
        } catch (Exception e) {
            throw new AccountServiceException("Error retrieving account");
        }
    }
    
    @Override
	public List<BankAccountDto> getAccountByWId(Integer wallet_id) {
		try {
			List<BankAccount> acc = dao.findByWId(wallet_id);
			if (acc == null) {
                throw new AccountNotFoundException("Wallet not found with id: " + wallet_id);
            }
            BankAccountMapperToBankAccountDto mapper = new BankAccountMapperToBankAccountDto();
            return mapper.listtoDto(acc);
		} catch (Exception e) {
			throw new AccountServiceException("Error retrieving account");
		}
	}

    @Override
	public BankAccountDto updateAccount(BankAccountDto accountDto, int walletId, long accountNo) {
        try {
            Wallet wallet = repository.findById(walletId)
                .orElseThrow(() -> new CustomerNotFoundException("Wallet not found with ID: " + walletId));

            BankAccount account = wallet.getBankAccounts().stream()
                .filter(acc -> acc.getAccountNo() == accountNo)
                .findFirst()
                .orElseThrow(() -> new AccountNotFoundException("Account not found with account number: " + accountNo));

            account.setBankName(accountDto.getBankName());
            account.setBalance(accountDto.getBalance());

            repository.save(wallet); // Persist the changes

            return new BankAccountMapperToBankAccountDto().toDto(account);
        } catch (Exception e) {
            throw new AccountServiceException("Error updating account: " + e.getMessage());
        }
    }

    @Override
    public void deleteAccount(int walletId, long accountNo) {
        try {
            Wallet wallet = repository.findById(walletId)
                .orElseThrow(() -> new CustomerNotFoundException("Wallet not found with ID: " + walletId));

            BankAccount account = wallet.getBankAccounts().stream()
                .filter(acc -> acc.getAccountNo() == accountNo)
                .findFirst()
                .orElseThrow(() -> new AccountNotFoundException("Account not found with account number: " + accountNo));

            wallet.getBankAccounts().remove(account); // Remove account from wallet
            
            repository.save(wallet); // Persist the changes
        } catch (Exception e) {
            throw new AccountServiceException("Error deleting account: " + e.getMessage());
        }
    }

	

}
	

