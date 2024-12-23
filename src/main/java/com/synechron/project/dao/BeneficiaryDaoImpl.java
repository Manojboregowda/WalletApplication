package com.synechron.project.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.synechron.project.dto.BeneficiaryDto;
import com.synechron.project.entities.Beneficiary;
import com.synechron.project.exception.BeneficiaryException;
import com.synechron.project.exception.CustomerException;
import com.synechron.project.repository.BeneficiaryRepository;

@Repository
public class BeneficiaryDaoImpl implements BeneficiaryDao {

	@Override
	public BeneficiaryDto addBeneficiary(BeneficiaryDto beneficiaryDto, Integer walletId) throws BeneficiaryException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<BeneficiaryDto> findAllByCustomer() throws BeneficiaryException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BeneficiaryDto viewBeneficiary(String beneficiaryName) throws BeneficiaryException, CustomerException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<BeneficiaryDto> viewAllBeneficiary(int customerId) throws BeneficiaryException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BeneficiaryDto deleteBeneficiary(String beneficiaryMobileNumber)
			throws BeneficiaryException, CustomerException {
		// TODO Auto-generated method stub
		return null;
	}

//    @Autowired
//    private BeneficiaryRepository beneficiaryRepository;
//
//    @Override
//    public void addBeneficiary(Beneficiary beneficiary) {
//        beneficiaryRepository.save(beneficiary);
//    }
//
//  
//
//    @Override
//    public void deleteBeneficiary(Beneficiary beneficiary) {
//        beneficiaryRepository.delete(beneficiary);
//    }
}