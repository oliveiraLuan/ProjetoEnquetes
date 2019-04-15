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
	
	
	private int respostaSim;
	
	private int respostaNao;
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int codigoEnquete;
	
	public Enquete() {
		
	}	

	public List<Funcionario> getFuncionarios() {
		return funcionarios;
	}


	public void setFuncionarios(List<Funcionario> funcionarios) {
		this.funcionarios = funcionarios;
	}


	public int getRespostaSim() {
		return respostaSim;
	}


	public void setRespostaSim(int respostaSim) {
		this.respostaSim = respostaSim;
	}


	public int getRespostaNao() {
		return respostaNao;
	}


	public void setRespostaNao(int respostaNao) {
		this.respostaNao = respostaNao;
	}


	public int getCodigoEnquete() {
		return codigoEnquete;
	}


	public void setCodigoEnquete(int codigoEnquete) {
		this.codigoEnquete = codigoEnquete;
	}

	
	
}
