package com.synechron.project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.synechron.project.dto.BeneficiaryDto;
import com.synechron.project.exception.BeneficiaryException;
import com.synechron.project.exception.CustomerException;
import com.synechron.project.exception.InSufficientFundsException;
import com.synechron.project.services.BeneficiaryService;

@RestController
@RequestMapping("/beneficiaries")
public class BeneficiaryController {

	@Autowired
	private BeneficiaryService beneficiaryService;

	// Add Beneficiary
	@PostMapping("/add")
	public ResponseEntity<?> addBeneficiary(
			@RequestBody BeneficiaryDto beneficiaryDto, 
			@RequestParam Integer walletId) { 
		try {
			BeneficiaryDto createdBeneficiary = beneficiaryService.addBeneficiary(beneficiaryDto, walletId);
			return new ResponseEntity<>(createdBeneficiary, HttpStatus.CREATED);
		} catch (BeneficiaryException | CustomerException | InSufficientFundsException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	@GetMapping("/viewbyname/{beneficiaryName}")
	public ResponseEntity<?> viewBeneficiary(@PathVariable String beneficiaryName) {
		try {
			BeneficiaryDto beneficiary = beneficiaryService.viewBeneficiary(beneficiaryName);
			return new ResponseEntity<>(beneficiary, HttpStatus.OK);
		} catch (BeneficiaryException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/view-all/{customerId}")
	public ResponseEntity<?> viewAllBeneficiaries(@PathVariable int customerId) {
		try {
			List<BeneficiaryDto> beneficiaries = beneficiaryService.viewAllBeneficiary(customerId);
			return new ResponseEntity<>(beneficiaries, HttpStatus.OK);
		} catch (BeneficiaryException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/delete/{beneficiaryMobileNumber}")
	public ResponseEntity<?> deleteBeneficiary(@PathVariable String beneficiaryMobileNumber) {
		try {
			BeneficiaryDto deletedBeneficiary = beneficiaryService.deleteBeneficiary(beneficiaryMobileNumber);
			return new ResponseEntity<>(deletedBeneficiary, HttpStatus.OK);
		} catch (BeneficiaryException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
}