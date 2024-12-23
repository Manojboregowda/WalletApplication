package com.synechron.project.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WalletDto {
	
	@NotNull
	private BigDecimal balance = BigDecimal.ZERO;

	
	@NotNull
	private int walletId;

	@NotNull
	private int customerId;
}
