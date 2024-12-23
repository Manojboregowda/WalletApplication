package com.synechron.project.dao;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.synechron.project.entities.BillPayment;
import com.synechron.project.exception.AccountServiceException;
import com.synechron.project.repository.BillPaymentRepository;
 
@Repository
public class BillPaymentDaoImpl implements BillPaymentDao {
 
    @Autowired
    private BillPaymentRepository repository;
 
    @Override
    public BillPayment addBillPayment(BillPayment billPayment) {
        try {
            return repository.save(billPayment);
        } catch (Exception e) {
            throw new AccountServiceException("Error saving bill payment");
        }
    }
 
    @Override
    public List<BillPayment> getAllBillPayments() {
        try {
            return repository.findAll();
        } catch (Exception e) {
            throw new AccountServiceException("Error retrieving bill payments");
        }
    }
 
    @Override
    public BillPayment findById(int id) {
        return repository.findById(id).orElse(null);
    }
}