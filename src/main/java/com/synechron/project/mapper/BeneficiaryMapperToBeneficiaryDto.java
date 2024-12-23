package com.synechron.project.mapper;

import com.synechron.project.dto.BeneficiaryDto;
import com.synechron.project.entities.Beneficiary;

public class BeneficiaryMapperToBeneficiaryDto {

    public static BeneficiaryDto beneficiaryToBeneficiaryDto(Beneficiary beneficiary) {
        BeneficiaryDto beneficiaryDto = new BeneficiaryDto();

        beneficiaryDto.setBeneficiaryMobileNumber(beneficiary.getBeneficiaryMobileNumber());
        beneficiaryDto.setBeneficiaryName(beneficiary.getBeneficiaryName());

        if (beneficiary.getWallet() != null) {
            beneficiaryDto.setWalletId(beneficiary.getWallet().getWalletId());
        }

        return beneficiaryDto;
    }
}
