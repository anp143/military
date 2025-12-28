package com.militaryassetmanagementsystem.controller;

import com.militaryassetmanagementsystem.model.Asset;
import com.militaryassetmanagementsystem.service.AssetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/assets")

@CrossOrigin(origins = "http://localhost:4200")
public class AssetController {

    @Autowired
    private AssetService assetService;

    @GetMapping
    public List<Asset> getAllAssets() {
        return assetService.getAllAssets();
    }

    @GetMapping("/{id}")
    public Optional<Asset> getAsset(@PathVariable Long id) {
        return assetService.getAssetById(id);
    }

    @PostMapping
    public Asset createAsset(@RequestBody Asset asset) {
        return assetService.addAsset(asset);
    }

    @PutMapping("/{id}")
    public Asset updateAsset(@PathVariable Long id, @RequestBody Asset asset) {
        return assetService.updateAsset(id, asset);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAsset(@PathVariable Long id) {
        try {
            assetService.deleteAsset(id);
            return ResponseEntity.ok("Asset deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(409).body(e.getMessage()); // Conflict if linked to transaction
        }
    }
}
