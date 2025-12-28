package com.militaryassetmanagementsystem.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.militaryassetmanagementsystem.model.Base;
import com.militaryassetmanagementsystem.model.User;
import com.militaryassetmanagementsystem.repo.BaseRepository;
import com.militaryassetmanagementsystem.repo.UserRepository;

@Service
public class BaseService {

    @Autowired
    private BaseRepository baseRepo;

    @Autowired
    private UserRepository userRepo;

    // Add base
    public Base addBase(Base base) {
        return baseRepo.save(base);
    }

    // Get all bases
    public List<Base> getAllBases() {
        return baseRepo.findAll();
    }

    // Get base by id
    public Optional<Base> getBaseById(Long id) {
        return baseRepo.findById(id);
    }

    // Update base
    public Base updateBase(Long id, Base base) {
        Base existingBase = baseRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Base not found with id " + id));
        existingBase.setName(base.getName());
        existingBase.setLocation(base.getLocation());
        return baseRepo.save(existingBase);
    }

    // Delete base
    public void deleteBase(Long id) {
        baseRepo.deleteById(id);
    }

    // ✅ Get bases by user role
    public List<Base> getBasesByRole(String role) {
        List<Base> bases = new ArrayList<>();

        if (role.equalsIgnoreCase("ADMIN")) {
            // Admin sees all bases
            bases = baseRepo.findAll();
        } else {
            // Other roles: fetch users with this role
            List<User> users = userRepo.findByRole(role.toUpperCase());

            for (User user : users) {
                if (user.getBase() != null) {
                    bases.add(user.getBase());
                }
            }
        }
        return bases;
    }
}
