package com.synechron.project.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.synechron.project.dto.BeneficiaryDto;
import com.synechron.project.dto.CustomerDto;
import com.synechron.project.entities.Beneficiary;
import com.synechron.project.entities.Customer;
import com.synechron.project.entities.Wallet;
import com.synechron.project.exception.BeneficiaryException;
import com.synechron.project.exception.CustomerException;
import com.synechron.project.mapper.BeneficiaryDtoToBeneficiaryMapper;
import com.synechron.project.mapper.BeneficiaryMapperToBeneficiaryDto;
import com.synechron.project.repository.BeneficiaryRepository;
import com.synechron.project.repository.CustomerRepository;
import com.synechron.project.repository.WalletRepository;

import jakarta.transaction.Transactional;


@Service
public class BeneficiaryServiceImpl implements BeneficiaryService {

    @Autowired 
    private BeneficiaryRepository beneficiaryRepository;

    @Autowired 
    private CustomerService customerService; 

    @Autowired 
    private WalletRepository walletRepository; // Add Wallet repository 
    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public BeneficiaryDto addBeneficiary(BeneficiaryDto beneficiaryDto, Integer walletId) throws BeneficiaryException {

    	Wallet wallet = walletRepository.findById(walletId)
    	    .orElseThrow(() -> new BeneficiaryException("Wallet with ID " + walletId + " does not exist."));

    	Optional<Beneficiary> existingBeneficiary = beneficiaryRepository
    	    .findByBeneficiaryMobileNumberAndWallet(beneficiaryDto.getBeneficiaryMobileNumber(), wallet);

        if (existingBeneficiary.isPresent()) {
            throw new BeneficiaryException("Beneficiary with mobile number "
                    + beneficiaryDto.getBeneficiaryMobileNumber() + " already exists for this wallet.");
        }

        Beneficiary beneficiary = BeneficiaryDtoToBeneficiaryMapper.beneficiaryDtoToBeneficiary(beneficiaryDto);

        beneficiary.setWallet(wallet);

        Beneficiary savedBeneficiary = beneficiaryRepository.save(beneficiary);

        wallet.getBeneficiaries().add(savedBeneficiary);
        walletRepository.save(wallet);


        return BeneficiaryMapperToBeneficiaryDto.beneficiaryToBeneficiaryDto(savedBeneficiary);
    }
 



    @Override 
    public List<BeneficiaryDto> findAllByCustomer() throws BeneficiaryException { 
        List<Beneficiary> beneficiaries = beneficiaryRepository.findAll(); 
        if (beneficiaries.isEmpty()) { 
            throw new BeneficiaryException("No Beneficiary Exists"); 
        } 
        return beneficiaries.stream() 
                .map(BeneficiaryMapperToBeneficiaryDto::beneficiaryToBeneficiaryDto) 
                .collect(Collectors.toList()); 
    } 

    @Override 
    public BeneficiaryDto viewBeneficiary(String beneficiaryName) throws BeneficiaryException { 
        Optional<Beneficiary> optionalBeneficiary = beneficiaryRepository.findByBeneficiaryName(beneficiaryName); 
        if (optionalBeneficiary.isEmpty()) { 
            throw new BeneficiaryException("No Beneficiary Exists"); 
        } 
  
        return BeneficiaryMapperToBeneficiaryDto.beneficiaryToBeneficiaryDto(optionalBeneficiary.get()); 
    } 

    @Override 
    public List<BeneficiaryDto> viewAllBeneficiary(int cId) throws BeneficiaryException { 
      Optional<Customer> byId = customerRepository.findById(cId);
      if(byId!=null) {
    	  Customer customer = byId.get();
    	  List<Beneficiary> beneficiaries = customer.getWallet().getBeneficiaries();
    	  List<BeneficiaryDto> beneficiaries2 = new ArrayList<>();
    	  for(Beneficiary beneficiary : beneficiaries) {
    		  beneficiaries2.add(BeneficiaryMapperToBeneficiaryDto.beneficiaryToBeneficiaryDto(beneficiary));
    	  }
    	  return beneficiaries2;
      }
      else throw new CustomerException("Customer with the ID not found in the database");
    } 

    @Override
    @Transactional
    public BeneficiaryDto deleteBeneficiary(String beneficiaryMobileNumber) throws BeneficiaryException {
        Beneficiary beneficiary = beneficiaryRepository.findById(beneficiaryMobileNumber)
            .orElseThrow(() -> new BeneficiaryException("Beneficiary not found"));

        Wallet wallet = beneficiary.getWallet();
        
        if (wallet != null) {
            wallet.removeBeneficiary(beneficiary);
            walletRepository.save(wallet);
        }

        beneficiaryRepository.delete(beneficiary);

        return BeneficiaryMapperToBeneficiaryDto.beneficiaryToBeneficiaryDto(beneficiary);
    }
    
}