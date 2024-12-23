package com.synechron.project.repository;


 
import org.springframework.data.jpa.repository.JpaRepository;
import com.synechron.project.entities.BillPayment;
 
public interface BillPaymentRepository extends JpaRepository<BillPayment, Integer> {
}