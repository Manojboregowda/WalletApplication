package com.synechron.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.synechron.project.dto.CustomerDto;
import com.synechron.project.dto.WalletDto;
import com.synechron.project.repository.CustomerRepository;
import com.synechron.project.services.CustomerService;

@CrossOrigin(originPatterns = "http://localhost:4200")
@RestController
public class CustomerController {
	@Autowired
	CustomerService service;

	@Autowired
	CustomerRepository repo;

	@PostMapping("/signup")
	public ResponseEntity<?> addCustomer(@RequestBody CustomerDto customer) {
		try {
			CustomerDto addedCustomer = service.addCustomer(customer);
			return new ResponseEntity<>(addedCustomer, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/login/{mobile}/{password}")
	public ResponseEntity<?> loginCustomer(@PathVariable("mobile") String mobile,
			@PathVariable("password") String password) {
		try {
			CustomerDto customer = service.loginCustomer(mobile, password);
			// changes
			Integer wallet_id = service.getWalletId(customer.getCustomerId());
			System.out.println(wallet_id);
			WalletDto w = new WalletDto();
			w.setWalletId(wallet_id);
			System.out.println(w);
			customer.setWalletDto(w);
			if (customer != null) {
				return new ResponseEntity<>(customer, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}

// @PostMapping("/signup")
// public CustomerDto addCustomer(@RequestBody CustomerDto customer) {
// return service.addCustomer(customer);
// }
//
// @GetMapping("/login/{mobile}/{password}")
// public CustomerDto loginCustomer(@PathVariable("mobile") String
// mobile,@PathVariable("password") String password) {
// return service.loginCustomer(mobile,password);
// }
