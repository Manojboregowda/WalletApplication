package com.synechron.project.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.synechron.project.enums.BillType;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class BillPayment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int billId;

    @NotNull
    private BillType billType;

    @NotNull
    private double amount;

    private LocalDate paymentDate;
    
    @ManyToOne
    @JoinColumn(name = "walletId")
    @JsonIgnore 
    private Wallet wallet;

}