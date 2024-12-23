package com.synechron.project.entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name="customer", uniqueConstraints = @UniqueConstraint(columnNames = {"mobile"}))
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Customer {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)	
	private int customerId;
	private String customerName;
	private String mobile;
	private String password;

	@OneToOne(mappedBy = "customer")
	  @JsonBackReference
	private Wallet wallet;
	

}
