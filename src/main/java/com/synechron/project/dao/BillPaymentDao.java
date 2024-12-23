package com.synechron.project.dao;


import java.util.List;
import com.synechron.project.entities.BillPayment;
 
public interface BillPaymentDao {
    BillPayment addBillPayment(BillPayment billPayment);
    List<BillPayment> getAllBillPayments();
    BillPayment findById(int id);
}