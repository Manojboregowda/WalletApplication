package com.synechron.project.dto;

import com.synechron.project.entities.Wallet;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
public class TransactionDto {
    @NotNull
    private Long transactionId;

    @NotNull
    @Pattern(regexp = "^(Credit|Debit)$", message = "Transaction type must be either 'Credit' or 'Debit'")
    private String transactionType;

    @NotNull
    private LocalDate transactionDate;	

    @NotNull
    @Positive(message = "Amount must be positive")
    private double amount;
    
    @Size(max = 255, message = "Description must be less than 255 characters")
    private String description;

    @NotNull
    private Wallet wallet;

	
    
}