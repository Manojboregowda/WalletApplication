package com.synechron.project.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

import com.synechron.project.entities.Wallet;
import com.synechron.project.enums.BillType;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder

public class BillPaymentDto {
    
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    @NotNull(message = "Bill ID cannot be null")
    private int billId;

    
    private Wallet wallet;

    @NotNull(message = "Bill type cannot be null")
    @Pattern(regexp = "^[A-Za-z\\s]+$", message = "Bill type must only contain letters and spaces")
    private BillType billType;

    @NotNull(message = "Amount cannot be null")
    @Positive(message = "Amount must be greater than zero")
    @Min(value = 0, message = "Amount cannot be negative")
    @Max(value = 1000000, message = "Amount cannot exceed 1,000,000")
    private double amount;

    @Future(message = "Payment date must be in the future")
    private LocalDate paymentDate;
}