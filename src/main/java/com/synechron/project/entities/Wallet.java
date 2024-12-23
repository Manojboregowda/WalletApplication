package com.synechron.project.entities;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Wallet {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int walletId;
	private BigDecimal balance = BigDecimal.ZERO;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "customerId")
	@JsonBackReference
	private Customer customer;

	@OneToMany(mappedBy = "wallet", fetch = FetchType.EAGER)
	@JsonManagedReference

	private List<BillPayment> billPayments;

	@OneToMany(mappedBy = "wallet", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<BankAccount> bankAccounts;

	public void addBankAccount(BankAccount bankAccount) {
		if (bankAccounts == null) {
			bankAccounts = new ArrayList<>();
		}
		bankAccounts.add(bankAccount);
		bankAccount.setWallet(this);
	}

	@OneToMany(mappedBy = "wallet", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<Transaction> transactions;

	@OneToMany(mappedBy = "wallet", cascade = { CascadeType.PERSIST, CascadeType.MERGE,
			CascadeType.REMOVE }, orphanRemoval = true)
	@JsonIgnore
	private List<Beneficiary> beneficiaries = new ArrayList<>();

	public void removeBeneficiary(Beneficiary beneficiary) {
		if (beneficiaries != null) {
			beneficiaries.remove(beneficiary);
			beneficiary.setWallet(null);
		}
	}

}
