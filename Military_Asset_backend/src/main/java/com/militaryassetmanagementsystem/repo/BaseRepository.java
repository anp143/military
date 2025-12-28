package com.militaryassetmanagementsystem.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.militaryassetmanagementsystem.model.Base;

public interface BaseRepository extends JpaRepository<Base, Long> {
	
}
