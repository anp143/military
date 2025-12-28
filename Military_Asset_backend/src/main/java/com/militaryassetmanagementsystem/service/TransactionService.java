package com.militaryassetmanagementsystem.service;

import com.militaryassetmanagementsystem.dto.StockReportDTO;
import com.militaryassetmanagementsystem.model.Asset;
import com.militaryassetmanagementsystem.model.Base;
import com.militaryassetmanagementsystem.model.Transaction;
import com.militaryassetmanagementsystem.repo.AssetRepository;
import com.militaryassetmanagementsystem.repo.BaseRepository;
import com.militaryassetmanagementsystem.repo.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.*;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AssetRepository assetRepository;

    @Autowired
    private BaseRepository baseRepository;

    // ========================= CREATE TRANSACTION =========================
    @Transactional
    public Transaction createTransaction(Transaction transaction) {
        transaction.setDate(LocalDate.now());

        // Fetch asset using equipment_id
        Asset asset = assetRepository.findById(transaction.getAsset().getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Asset not found with ID: " + transaction.getAsset().getId()));
        transaction.setAsset(asset);

        // Fetch fromBase if provided
        if (transaction.getFromBase() != null) {
            Base fromBase = baseRepository.findById(transaction.getFromBase().getId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                            "From Base not found with ID: " + transaction.getFromBase().getId()));
            transaction.setFromBase(fromBase);
        }

        // Fetch toBase if provided (only for TRANSFER)
        if ("TRANSFER".equalsIgnoreCase(transaction.getTransactionType()) && transaction.getToBase() != null) {
            Base toBase = baseRepository.findById(transaction.getToBase().getId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                            "To Base not found with ID: " + transaction.getToBase().getId()));
            transaction.setToBase(toBase);
        } else {
            transaction.setToBase(null); // PURCHASE/ASSIGNMENT/EXPENDITURE shouldn't have a toBase
        }

        // Handle stock changes
        switch (transaction.getTransactionType().toUpperCase()) {
            case "PURCHASE":
                asset.setQuantity(asset.getQuantity() + transaction.getQuantity());
                assetRepository.save(asset);
                break;

            case "TRANSFER":
                if (asset.getQuantity() < transaction.getQuantity()) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                            "Insufficient stock for transfer. Available: " + asset.getQuantity());
                }

                asset.setQuantity(asset.getQuantity() - transaction.getQuantity());
                assetRepository.save(asset);

                // Add quantity to destination base asset
                if (transaction.getToBase() != null) {
                    Asset toBaseAsset = assetRepository.findByNameAndBase(asset.getName(), transaction.getToBase())
                            .orElseGet(() -> {
                                Asset newAsset = new Asset();
                                newAsset.setName(asset.getName());
                                newAsset.setType(asset.getType());
                                newAsset.setBase(transaction.getToBase());
                                newAsset.setQuantity(0);
                                return newAsset;
                            });
                    toBaseAsset.setQuantity(toBaseAsset.getQuantity() + transaction.getQuantity());
                    assetRepository.save(toBaseAsset);
                }
                break;

            case "EXPENDITURE":
            case "ASSIGNMENT":
                if (asset.getQuantity() < transaction.getQuantity()) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                            "Insufficient stock for " + transaction.getTransactionType() + ". Available: " + asset.getQuantity());
                }
                asset.setQuantity(asset.getQuantity() - transaction.getQuantity());
                assetRepository.save(asset);
                break;

            default:
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "Invalid transaction type: " + transaction.getTransactionType());
        }

        // Save transaction
        Transaction savedTransaction = transactionRepository.save(transaction);

        // Ensure full asset and base details in response
        savedTransaction.setAsset(asset);
        if (savedTransaction.getToBase() != null) {
            Base toBase = baseRepository.findById(savedTransaction.getToBase().getId()).orElse(null);
            savedTransaction.setToBase(toBase);
        }
        if (savedTransaction.getFromBase() != null) {
            Base fromBase = baseRepository.findById(savedTransaction.getFromBase().getId()).orElse(null);
            savedTransaction.setFromBase(fromBase);
        }

        return savedTransaction;
    }

    // ========================= GET ALL TRANSACTIONS =========================
    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    // ========================= GET TRANSACTION BY ID =========================
    public Transaction getTransactionById(Long id) {
        return transactionRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Transaction not found with ID: " + id));
    }

    public List<StockReportDTO> getStockReport() {
        List<Transaction> transactions = transactionRepository.findAll();
        Map<String, StockReportDTO> reportMap = new HashMap<>();

        for (Transaction tx : transactions) {
            Asset asset = tx.getAsset();
            if (asset == null) continue;

            // Common logic for both transfer and others
            Base base = null;
            String key = null;
            StockReportDTO dto = null;

            if ("TRANSFER".equalsIgnoreCase(tx.getTransactionType())) {
                // FROM BASE (Transfer Out)
                if (tx.getFromBase() != null) {
                    key = asset.getId() + "-" + tx.getFromBase().getId();
                    dto = reportMap.getOrDefault(
                            key,
                            new StockReportDTO(
                                    asset.getId(), asset.getName(), asset.getType(),
                                    tx.getFromBase().getId(), tx.getFromBase().getName(), tx.getFromBase().getLocation()
                            )
                    );
                    dto.setTransfersOut(dto.getTransfersOut() + tx.getQuantity());
                    reportMap.put(key, dto);
                }

                // TO BASE (Transfer In)
                if (tx.getToBase() != null) {
                    key = asset.getId() + "-" + tx.getToBase().getId();
                    dto = reportMap.getOrDefault(
                            key,
                            new StockReportDTO(
                                    asset.getId(), asset.getName(), asset.getType(),
                                    tx.getToBase().getId(), tx.getToBase().getName(), tx.getToBase().getLocation()
                            )
                    );
                    dto.setTransfersIn(dto.getTransfersIn() + tx.getQuantity());
                    reportMap.put(key, dto);
                }

                continue;
            }

            // For PURCHASE / ASSIGNMENT / EXPENDITURE
            base = (tx.getFromBase() != null) ? tx.getFromBase() : asset.getBase();
            if (base == null) continue;

            key = asset.getId() + "-" + base.getId();
            dto = reportMap.getOrDefault(
                    key,
                    new StockReportDTO(
                            asset.getId(), asset.getName(), asset.getType(),
                            base.getId(), base.getName(), base.getLocation()
                    )
            );

            switch (tx.getTransactionType().toUpperCase()) {
                case "PURCHASE":
                    dto.setPurchases(dto.getPurchases() + tx.getQuantity());
                    break;
                case "ASSIGNMENT":
                    dto.setAssignments(dto.getAssignments() + tx.getQuantity());
                    break;
                case "EXPENDITURE":
                    dto.setExpenditures(dto.getExpenditures() + tx.getQuantity());
                    break;
            }

            reportMap.put(key, dto);
        }

        // ✅ Now compute opening/closing using live DB stock
        for (StockReportDTO dto : reportMap.values()) {
            Asset asset = assetRepository.findById(dto.getAssetId()).orElse(null);
            if (asset != null) {
                int currentStock = asset.getQuantity();

                // opening = closing + outflows - inflows
                int opening = currentStock
                        - dto.getPurchases()
                        - dto.getTransfersIn()
                        + dto.getTransfersOut()
                        + dto.getAssignments()
                        + dto.getExpenditures();

                dto.setOpeningBalance(Math.max(opening, 0)); // avoid negative
                dto.calculateClosingBalance();
            }
        }

        return new ArrayList<>(reportMap.values());
    }
}