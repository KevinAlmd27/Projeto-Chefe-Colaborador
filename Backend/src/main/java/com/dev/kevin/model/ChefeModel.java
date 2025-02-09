package com.dev.kevin.model;

import com.dev.kevin.entity.ChefeEntity;
import com.dev.kevin.repository.ChefeRepository;

import jakarta.persistence.EntityNotFoundException;

public class ChefeModel {

	ChefeRepository chefeRepository;
	
	public ChefeEntity getChefeId(Long id) {
		return chefeRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Chefe Não encontrado"));
	}
}
