package com.dev.kevin.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity(name = "colaborador")
public class ColaboradorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long idColaborador;  // Considerar 'Long' se a coluna no banco for BIGINT ou INT.

    @Column(name = "NOME", nullable = false)
    private String nome; // Adicionado "nullable = false" se necessário.

    @Column(name = "SENHA", nullable = false)
    private String senha; // Considerar "nullable = false" se senha for obrigatória.

    @Column(name = "SETOR", nullable = false)
    private String setor;
    
    @Column(name = "SCORE")
    private String score;


    @ManyToOne
    @JoinColumn(name = "chefe_id")
    private ChefeEntity chefe;
    
    // Construtor padrão
    public ColaboradorEntity() {
    }

	public Long getIdColaborador() {
		return idColaborador;
	}

	public void setIdColaborador(Long idColaborador) {
		this.idColaborador = idColaborador;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getSetor() {
		return setor;
	}

	public void setSetor(String setor) {
		this.setor = setor;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public ChefeEntity getChefe() {
		return chefe;
	}

	public void setChefe(ChefeEntity chefe) {
		this.chefe = chefe;
	}

	
    // Getters e Setters
   
}
