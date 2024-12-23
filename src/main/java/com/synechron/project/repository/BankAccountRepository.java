package com.synechron.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.synechron.project.entities.BankAccount;
import java.util.List;

@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount, Integer> {

	List<BankAccount> findByAccountNo(long accountNo);

	@Query(value = "SELECT * FROM bank_account b WHERE b.wallet_id=:wallet_id",nativeQuery = true)
	List<BankAccount> findByWId(Integer wallet_id);
}
