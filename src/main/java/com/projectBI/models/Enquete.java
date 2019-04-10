package com.projectBI.models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

@Entity
public class Enquete {
	
	@OneToMany
	private List<Funcionario> funcionarios;
	
	@NotNull
	private String pergunta;
	
	@NotNull
	private boolean resposta;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int codigoEnquete;

	
	public void setPergunta(String pergunta) {
		this.pergunta = pergunta;
	}
	
	public boolean getResposta() {
		return resposta;
	}
	
	public void setResposta(boolean resposta) {
		this.resposta = resposta;
	}
	
	public int getCodigo() {
		return codigoEnquete;
	}

}
