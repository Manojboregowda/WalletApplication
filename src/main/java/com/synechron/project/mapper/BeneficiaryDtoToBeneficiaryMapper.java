package com.synechron.project.mapper;

import com.synechron.project.dto.BeneficiaryDto;
import com.synechron.project.entities.Beneficiary;
import com.synechron.project.entities.Customer;

public class BeneficiaryDtoToBeneficiaryMapper {
	
	

    public static Beneficiary beneficiaryDtoToBeneficiary(BeneficiaryDto beneficiaryDto) {
        // Create a new Beneficiary entity
        Beneficiary beneficiary = new Beneficiary();

        // Map simple fields
        beneficiary.setBeneficiaryMobileNumber(beneficiaryDto.getBeneficiaryMobileNumber());
        beneficiary.setBeneficiaryName(beneficiaryDto.getBeneficiaryName());

     

        return beneficiary;
    }
}
