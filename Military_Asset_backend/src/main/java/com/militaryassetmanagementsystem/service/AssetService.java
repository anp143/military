package com.militaryassetmanagementsystem.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.militaryassetmanagementsystem.model.Asset;
import com.militaryassetmanagementsystem.model.Base;
import com.militaryassetmanagementsystem.repo.AssetRepository;
import com.militaryassetmanagementsystem.repo.BaseRepository;

@Service
public class AssetService {

    @Autowired
    private AssetRepository repo;

    @Autowired
    private BaseRepository baseRepo;

    // ✅ Create Asset (updated for frontend sending base ID)
    public Asset addAsset(Asset a) {
        // Check if frontend sent a base object with ID
        if (a.getBase() == null || a.getBase().getId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Base is required");
        }

        Base base = baseRepo.findById(a.getBase().getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "Base not found with id " + a.getBase().getId()));

        a.setBase(base);

        // Save asset in MySQL
        return repo.save(a);
    }

    // ✅ Read all assets
    public List<Asset> getAllAssets() {
        return repo.findAll();
    }

    // ✅ Read asset by ID
    public Optional<Asset> getAssetById(Long id) {
        return repo.findById(id);
    }

    // ✅ Get assets by Base
    public List<Asset> getAssetsByBase(Long baseId) {
        return repo.findByBaseId(baseId);
    }

    // ✅ Update asset
    public Asset updateAsset(Long id, Asset updatedAsset) {
        return repo.findById(id).map(a -> {
            a.setName(updatedAsset.getName());
            a.setType(updatedAsset.getType());
            a.setQuantity(updatedAsset.getQuantity());

            if (updatedAsset.getBase() != null && updatedAsset.getBase().getId() != null) {
                Base base = baseRepo.findById(updatedAsset.getBase().getId())
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                                "Base not found with id " + updatedAsset.getBase().getId()));
                a.setBase(base);
            }

            return repo.save(a);
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Asset not found with id " + id));
    }

    // ✅ Delete asset
    public void deleteAsset(Long id) {
        if (!repo.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Asset not found with id " + id);
        }
        repo.deleteById(id);
    }

    // ✅ Update quantity safely (used in transactions)
    public Asset updateQuantity(Long assetId, int change) {
        Asset asset = repo.findById(assetId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Asset not found with id " + assetId));

        int newQuantity = asset.getQuantity() + change;

        if (newQuantity < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Insufficient stock for asset '" + asset.getName() + "'. Available: "
                            + asset.getQuantity() + ", Requested: " + Math.abs(change));
        }

        asset.setQuantity(newQuantity);
        return repo.save(asset);
    }
}
