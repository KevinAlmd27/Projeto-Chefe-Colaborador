package com.dev.kevin.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.dev.kevin.entity.ChefeEntity;
import com.dev.kevin.entity.ColaboradorEntity;
import com.dev.kevin.repository.ChefeRepository;
import com.dev.kevin.repository.ColaboradorRepository;
import com.dev.kevin.util.SenhaUtil;

@Service
public class ColaboradorModel {

	@Autowired
	ColaboradorRepository colaboradorRepository;

	@Autowired
	ChefeRepository chefeRepository;

	@GetMapping("/{idColaborador}")
	public ColaboradorEntity findById(@PathVariable long idColaborador) {
		return colaboradorRepository.findById(idColaborador).get();
	}

	public List<ColaboradorEntity> findAll() {
		return colaboradorRepository.findAll();
	}

	public ColaboradorEntity save(ColaboradorEntity colaboradorEntity) {

		colaboradorEntity.setScore(SenhaUtil.validacaoSenha(colaboradorEntity.getSenha()));

		colaboradorEntity.setSenha(SenhaUtil.criptografiaSenha(colaboradorEntity.getSenha()));

		return colaboradorRepository.save(colaboradorEntity);
	}

	public void excluirColaborador(long idColaborador) {
		if (colaboradorRepository.existsById(idColaborador)) {

			colaboradorRepository.deleteById(idColaborador);
		} else {
			throw new RuntimeException("Colaborador Não Encontrado");
		}
	}

	@Transactional
	public ColaboradorEntity associaColaborador(Long idColaborador) {

		ColaboradorEntity colaborador = colaboradorRepository.findById(idColaborador)
				.orElseThrow(() -> new IllegalArgumentException("Subordinado não encontrado"));
		
		ChefeEntity chefe = chefeRepository.findBySetor(colaborador.getSetor());
		if(chefe == null) {
			throw new IllegalArgumentException("Chefe não encontrado");
		}
		colaborador.setChefe(chefe);

		if(chefe.getColaboradores() == null) {
			chefe.setColaboradores(new ArrayList<>());
		}
		
		if(!chefe.getColaboradores().contains(colaborador)) {
			chefe.getColaboradores().add(colaborador);
		}

		colaboradorRepository.save(colaborador);
		chefeRepository.save(chefe);

		return colaborador;
	}

}
