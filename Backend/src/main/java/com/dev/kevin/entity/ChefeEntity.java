package com.dev.kevin.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "chefe")
public class ChefeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY )
	private Long idChefe;
	
	private String nome;
	
	private String setor;
	
	@OneToMany(mappedBy = "chefe", cascade = CascadeType.ALL)
	private List<ColaboradorEntity> colaboradores;

	public ChefeEntity() {
	}
	
	public Long getIdChefe() {
		return idChefe;
	}

	public void setIdChefe(Long idChefe) {
		this.idChefe = idChefe;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSetor() {
		return setor;
	}

	public void setSetor(String setor) {
		this.setor = setor;
	}

	public List<ColaboradorEntity> getColaboradores() {
		return colaboradores;
	}

	public void setColaboradores(List<ColaboradorEntity> colaboradores) {
		this.colaboradores = colaboradores;
	}

	
	
}

