package com.synechron.project.mapper;
 
import com.synechron.project.dto.BillPaymentDto;
import com.synechron.project.entities.BillPayment;
 
public class BillPaymentDtoToBillPaymentMapper {
 
    public BillPayment toEntity(BillPaymentDto dto) {
        BillPayment billPayment = new BillPayment();
        billPayment.setBillId(dto.getBillId());
        // Assuming you will set the wallet object separately
        billPayment.setBillType(dto.getBillType());
        billPayment.setAmount(dto.getAmount());
        billPayment.setPaymentDate(dto.getPaymentDate());
        billPayment.setBillType(dto.getBillType());
        billPayment.setWallet(dto.getWallet());
        return billPayment;
    }
}