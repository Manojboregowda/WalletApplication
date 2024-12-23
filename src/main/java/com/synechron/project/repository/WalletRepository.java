package com.synechron.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.synechron.project.dto.CustomerDto;
import com.synechron.project.entities.Customer;
import com.synechron.project.entities.Wallet;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Integer> {

//	List<Customer> findAll();

//	Customer save(Customer customer);

//	Customer find(String mobileNo); 

	// changes..
	@Query("FROM Wallet w WHERE w.customer=:customer")
	Wallet getWalletInfo(CustomerDto customer);
	
	@Query(value="Select wallet_id from paymentwallet.wallet where customer_id=:id", nativeQuery = true)
	int getWalletInfo(int id);
}
