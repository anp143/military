package com.militaryassetmanagementsystem.controller;

import com.militaryassetmanagementsystem.dto.StockReportDTO;
import com.militaryassetmanagementsystem.model.Transaction;
import com.militaryassetmanagementsystem.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;


@RestController
@RequestMapping("/api/transactions")
@CrossOrigin(origins = "http://localhost:4200")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    // ✅ PURCHASE
    @PostMapping("/purchase")
    public Transaction purchase(@RequestBody Transaction transaction) {
        transaction.setTransactionType("PURCHASE");
        return transactionService.createTransaction(transaction);
    }

    // ✅ TRANSFER
    @PostMapping("/transfer")
    public Transaction transfer(@RequestBody Transaction transaction) {
        transaction.setTransactionType("TRANSFER");
        return transactionService.createTransaction(transaction);
    }

    // ✅ EXPENDITURE
    @PostMapping("/expenditure")
    public Transaction expenditure(@RequestBody Transaction transaction) {
        transaction.setTransactionType("EXPENDITURE");
        return transactionService.createTransaction(transaction);
    }

    // ✅ ASSIGNMENT
    @PostMapping("/assignment")
    public Transaction assignment(@RequestBody Transaction transaction) {
        transaction.setTransactionType("ASSIGNMENT");
        return transactionService.createTransaction(transaction);
    }

    // ✅ Get all transactions
    @GetMapping
    public List<Transaction> getAllTransactions() {
        return transactionService.getAllTransactions();
    }

    // ✅ Get transaction by ID
    @GetMapping("/{id}")
    public Transaction getTransactionById(@PathVariable Long id) {
        return transactionService.getTransactionById(id);
    }

    // ✅ NEW: Dashboard / Stock Report
    @GetMapping("/report")
    public List<StockReportDTO> getStockReport() {
        return transactionService.getStockReport();
    }


}


