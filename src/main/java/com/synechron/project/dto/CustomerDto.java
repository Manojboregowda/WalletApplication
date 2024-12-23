package com.synechron.project.dto;

import com.synechron.project.entities.Customer;
import com.synechron.project.entities.Wallet;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class CustomerDto {
	
	private int customerId;
	private String customerName;
	@NotNull
	@Column(unique = true, nullable = false)
	@Pattern(regexp = "^[6-9]\\d{9}$")
	private String mobile;
	@NotNull
	@Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$")
	private String password;
	
	private WalletDto walletDto;
	
	
}
