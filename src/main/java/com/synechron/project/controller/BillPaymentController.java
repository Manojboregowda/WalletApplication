package com.synechron.project.controller;
 
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.synechron.project.dto.BillPaymentDto;
import com.synechron.project.services.BillPaymentService;
 
@RestController
@RequestMapping("/billpayment")
public class BillPaymentController {
 
    @Autowired
    private BillPaymentService service;
 
    @PostMapping("/addBill")
    public ResponseEntity<BillPaymentDto> addBillPayment(@RequestBody BillPaymentDto billPaymentDto) {
        BillPaymentDto addedPayment = service.addBillPayment(billPaymentDto);
        return new ResponseEntity<>(addedPayment, HttpStatus.CREATED);
    }
 
    @GetMapping("/bills")
    public ResponseEntity<List<BillPaymentDto>> getAllBillPayments() {
        List<BillPaymentDto> billPayments = service.getAllBillPayments();
        return new ResponseEntity<>(billPayments, HttpStatus.OK);
    }
 
    @GetMapping("/bills/{id}")
    public ResponseEntity<BillPaymentDto> getBillPaymentById(@PathVariable int id) {
        BillPaymentDto billPayment = service.getBillPaymentById(id);
        return new ResponseEntity<>(billPayment, HttpStatus.OK);
    }
}
 