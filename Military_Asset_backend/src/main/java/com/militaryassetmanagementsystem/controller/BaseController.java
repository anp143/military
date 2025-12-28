package com.militaryassetmanagementsystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.militaryassetmanagementsystem.model.Base;
import com.militaryassetmanagementsystem.service.BaseService;

@RestController
@RequestMapping("/api/bases")
@CrossOrigin(origins = "http://localhost:4200")

public class BaseController {

    @Autowired
    private BaseService service;

    @PostMapping
    public Base addBase(@RequestBody Base base) {
        return service.addBase(base);
    }

    @GetMapping
    public List<Base> getAllBases() {
        return service.getAllBases();
    }

    @GetMapping("/{id}")
    public Base getBaseById(@PathVariable Long id) {
        return service.getBaseById(id).orElseThrow(() -> new RuntimeException("Base not found"));
    }

    @PutMapping("/{id}")
    public Base updateBase(@PathVariable Long id, @RequestBody Base base) {
        return service.updateBase(id, base);
    }

    @DeleteMapping("/{id}")
    public String deleteBase(@PathVariable Long id) {
        service.deleteBase(id);
        return "Base deleted successfully!";
    }

    // ✅ New endpoint: get bases by role
    @GetMapping("/by-role/{role}")
    public List<Base> getBasesByRole(@PathVariable String role) {
        return service.getBasesByRole(role);
    }
}
