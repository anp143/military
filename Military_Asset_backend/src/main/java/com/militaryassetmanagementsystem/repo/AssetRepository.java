package com.militaryassetmanagementsystem.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.militaryassetmanagementsystem.model.Asset;
import com.militaryassetmanagementsystem.model.Base;

public interface AssetRepository extends JpaRepository <Asset, Long> {

	List<Asset> findByBaseId(Long baseId);

	Optional<Asset> findByNameAndBase(String name, Base toBase);

}
