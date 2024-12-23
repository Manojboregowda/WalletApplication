package com.synechron.project.services;
 
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.synechron.project.dao.BillPaymentDao;
import com.synechron.project.dto.BillPaymentDto;
import com.synechron.project.entities.BillPayment;
import com.synechron.project.entities.Wallet;
import com.synechron.project.exception.AccountServiceException;
import com.synechron.project.mapper.BillPaymentDtoToBillPaymentMapper;
import com.synechron.project.mapper.BillPaymentMapperToBillPaymentDto;
import com.synechron.project.repository.WalletRepository;
 
@Service
public class BillPaymentServiceImpl implements BillPaymentService {
 
    @Autowired
    private BillPaymentDao dao;
    @Autowired
    private WalletRepository walletRepository;
 
    @Override
    public BillPaymentDto addBillPayment(BillPaymentDto billPaymentDto) {
        BillPaymentDtoToBillPaymentMapper mapper = new BillPaymentDtoToBillPaymentMapper();
        BillPayment billPayment = dao.addBillPayment(mapper.toEntity(billPaymentDto));
        Wallet wallet = billPayment.getWallet();
      
        BillPaymentMapperToBillPaymentDto dtoMapper = new BillPaymentMapperToBillPaymentDto();
        return dtoMapper.toDto(billPayment);
    }
 
    @Override
    public List<BillPaymentDto> getAllBillPayments() {
        List<BillPayment> billPayments = dao.getAllBillPayments();
        List<BillPaymentDto> billPaymentDtos = new ArrayList<>();
        BillPaymentMapperToBillPaymentDto mapper = new BillPaymentMapperToBillPaymentDto();
        for (BillPayment billPayment : billPayments) {
            billPaymentDtos.add(mapper.toDto(billPayment));
        }
        return billPaymentDtos;
    }
 
    @Override
    public BillPaymentDto getBillPaymentById(int id) {
        BillPayment billPayment = dao.findById(id);
        if (billPayment == null) {
            throw new AccountServiceException("Bill payment not found with id: " + id);
        }
        BillPaymentMapperToBillPaymentDto mapper = new BillPaymentMapperToBillPaymentDto();
        return mapper.toDto(billPayment);
    }
}