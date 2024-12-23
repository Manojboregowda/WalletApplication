package com.synechron.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.synechron.project.dto.CustomerDto;
import com.synechron.project.entities.Customer;
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer>{

	 @Query("SELECT c FROM Customer c WHERE c.mobile = :mobile AND c.password = :password")
	    Customer findByMobileAndPassword(@Param("mobile") String mobile, @Param("password") String password);
	 
	 @Query("SELECT c FROM Customer c WHERE c.mobile = :mobile")
	 public List<Customer> findCustomerByMobile(@Param("mobile") String mobile);

	 
	 @Query("SELECT c FROM Customer c JOIN FETCH c.wallet w WHERE c.mobile = :mobile")
	 Customer findCustomerWithWalletByMobile(@Param("mobile") String mobile);

	}