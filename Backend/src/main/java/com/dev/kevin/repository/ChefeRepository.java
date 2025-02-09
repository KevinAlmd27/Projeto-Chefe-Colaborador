package com.dev.kevin.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dev.kevin.entity.ChefeEntity;


public interface ChefeRepository  extends JpaRepository<ChefeEntity, Long>{

	ChefeEntity findBySetor(String setor);
	
}
