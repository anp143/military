package com.militaryassetmanagementsystem.repo;

import java.time.LocalDate;
import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;

import com.militaryassetmanagementsystem.model.Transaction;

public interface TransactionRepository extends JpaRepository <Transaction, Long>{

	  List<Transaction> findByAssetId(Long assetId);

	List<Transaction> findByDate(LocalDate date);
}
