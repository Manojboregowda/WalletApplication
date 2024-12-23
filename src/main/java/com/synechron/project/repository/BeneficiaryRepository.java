package com.synechron.project.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.synechron.project.entities.Beneficiary;
import com.synechron.project.entities.Wallet;

import jakarta.transaction.Transactional;

@Repository
public interface BeneficiaryRepository extends JpaRepository<Beneficiary, String> {

    Optional<Beneficiary> findByBeneficiaryMobileNumberAndWallet(String mobileNumber, Wallet wallet);

    Optional<Beneficiary> findById(String beneficiaryMobileNumber);

    List<Beneficiary> findByWalletWalletId(Integer walletId);

    Beneficiary findByBeneficiaryNameAndWallet(String beneficiaryName, Wallet wallet);

    Optional<Beneficiary> findByBeneficiaryName(String beneficiaryName);
    
    @Modifying
    @Transactional
    @Query("DELETE FROM Beneficiary b WHERE b.beneficiaryMobileNumber = :mobileNumber")
    void deleteByMobileNumberCustom(@Param("mobileNumber") String mobileNumber);
}
