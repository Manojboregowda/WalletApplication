package com.synechron.project.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "bank_account", uniqueConstraints = @UniqueConstraint(columnNames = { "accountNo" }))
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class BankAccount {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int accountId;

	private String ifscCode;

	@NotNull
	@Column(unique = true, nullable = false)
	private long accountNo;

	private String bankName;

	private double balance;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "wallet_id")
	private Wallet wallet;

}
