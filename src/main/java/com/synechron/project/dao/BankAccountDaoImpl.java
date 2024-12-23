package com.synechron.project.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.synechron.project.dto.BankAccountDto;
import com.synechron.project.entities.BankAccount;
import com.synechron.project.entities.Customer;
import com.synechron.project.exception.AccountNotFoundException;
import com.synechron.project.exception.AccountServiceException;
import com.synechron.project.exception.CustomerNotFoundException;
import com.synechron.project.repository.BankAccountRepository;

@Repository
public class BankAccountDaoImpl implements BankAccountDao{
	
	
	    @Autowired
	    BankAccountRepository repository;

	    @Override
	    public BankAccount addAccount(BankAccount account) {
	        try {
	        	
	            return account;
	        } catch (Exception e) {
	            throw new AccountServiceException("Error saving account");
	        }
	    }

	    @Override
	    public List<BankAccount> getAllAccount() {
	        try {
	            List<BankAccount> list= repository.findAll();
	            if(list.isEmpty()) {
	            	throw new CustomerNotFoundException();
	            }else  {
	            	return list;
	            }
	        } catch (Exception e) {
	            throw new AccountServiceException("Error retrieving accounts");
	        }
	    }

	    @Override
	    public BankAccount findById(int id) {
	        try {
	            Optional<BankAccount> optionalAccount = repository.findById(id);
	            if (optionalAccount.isPresent()) {
	                return optionalAccount.get();
	            } else {
	                throw new AccountNotFoundException("Account not found with id: " + id);
	            }
	        } catch (Exception e) {
	            throw new AccountServiceException("Error retrieving account");
	        }
	    }
	    
	    @Override
		public List<BankAccount> findByWId(Integer id) {
			try {
				List<BankAccount> account = repository.findByWId(id);
				if(account!=null) {
					return account;
				}else {
					throw new AccountNotFoundException("Wallet not found with id: " + id);
				}
			} catch (Exception e) {
				throw new AccountServiceException("Error retrieving account");
			}
		}

	    @Override
	    public BankAccount updateAccount(BankAccount bank) {
	        try {
	            return repository.save(bank);
	        } catch (Exception e) {
	            throw new AccountServiceException("Error updating account");
	        }
	    }

	    @Override
	    public void deleteAccount(int id) {
	        try {
	            repository.deleteById(id);
	        } catch (Exception e) {
	            throw new AccountServiceException("Error deleting account");
	        }
	    }

		
	}

