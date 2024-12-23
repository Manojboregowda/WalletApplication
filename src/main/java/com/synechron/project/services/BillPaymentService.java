package com.synechron.project.services;
 
import java.util.List;
import com.synechron.project.dto.BillPaymentDto;
 
public interface BillPaymentService {
    BillPaymentDto addBillPayment(BillPaymentDto billPayment);
    List<BillPaymentDto> getAllBillPayments();
    BillPaymentDto getBillPaymentById(int id);
}