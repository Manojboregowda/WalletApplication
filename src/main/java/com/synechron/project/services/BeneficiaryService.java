package com.synechron.project.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.synechron.project.dto.BeneficiaryDto;
import com.synechron.project.exception.BeneficiaryException;
import com.synechron.project.exception.CustomerException;

public interface BeneficiaryService {
    
    BeneficiaryDto addBeneficiary(BeneficiaryDto beneficiaryDto, Integer walletId) throws BeneficiaryException;

    List<BeneficiaryDto> findAllByCustomer() throws BeneficiaryException;

    BeneficiaryDto viewBeneficiary(String beneficiaryName) throws BeneficiaryException, CustomerException;

    List<BeneficiaryDto> viewAllBeneficiary(int customerId) throws BeneficiaryException;

    BeneficiaryDto deleteBeneficiary(String beneficiaryMobileNumber) throws BeneficiaryException, CustomerException;
}