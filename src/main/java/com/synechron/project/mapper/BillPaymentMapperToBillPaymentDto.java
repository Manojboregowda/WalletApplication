package com.synechron.project.mapper;
 
import com.synechron.project.dto.BillPaymentDto;
import com.synechron.project.entities.BillPayment;
 
public class BillPaymentMapperToBillPaymentDto {
 
    public BillPaymentDto toDto(BillPayment billPayment) {
        BillPaymentDto dto = new BillPaymentDto();
        dto.setBillId(billPayment.getBillId());
        dto.setWallet(billPayment.getWallet()); 
        dto.setBillType(billPayment.getBillType());
        dto.setAmount(billPayment.getAmount());
        dto.setPaymentDate(billPayment.getPaymentDate());
        dto.setBillType(billPayment.getBillType());
        return dto;
    }
}