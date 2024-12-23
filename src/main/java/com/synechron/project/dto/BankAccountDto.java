package com.synechron.project.dto;

import jakarta.persistence.Column;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class BankAccountDto {
	
	private int walletId;
	
	private int accountId;
	@NotNull
	@Pattern(regexp ="^[A-Z]{4}0[A-Z0-9]{6}$",message = "invalid")
	private String ifscCode;

	@NotNull
	@Column(unique = true)
	@Pattern(regexp = "^\\d{9,18}$",message = "invalid")
	private long accountNo;

	@NotNull
	@Pattern(regexp = "^[A-Za-z\\s]+(Bank|Cooperative Bank|)\\s?[A-Za-z\\s]*$",message = "invalid")
	private String bankName;

	@NotNull
	private double balance;
	
}
