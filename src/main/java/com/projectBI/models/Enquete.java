package com.projectBI.models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class Enquete {
	
	@OneToMany
	private List<Funcionario> funcionarios;
	
	
	private int respostaSim;
	
	private int respostaNao;
	
	private int respostasPositivasComercial;
	
	private int respostasPositivasResidente;
	
	private int respostasPositivasGestao;
	
	private int respostasPositivasOperacional;
	
	private int respostasNegativasComercial;
	
	private int respostasNegativasResidente;
	
	private int respostasNegativasGestao;
	
	private int respostasNegativasOperacional;
	
	
	
	public int getRespostasNegativasOperacional() {
		return respostasNegativasOperacional;
	}

	public void setRespostasNegativasOperacional(int respostasNegativasOperacional) {
		this.respostasNegativasOperacional = respostasNegativasOperacional;
	}

	@Id
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
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

	public int getRespostasPositivasComercial() {
		return respostasPositivasComercial;
	}

	public void setRespostasPositivasComercial(int respostasPositivasComercial) {
		this.respostasPositivasComercial = respostasPositivasComercial;
	}

	public int getRespostasPositivasResidente() {
		return respostasPositivasResidente;
	}

	public void setRespostasPositivasResidente(int respostasPositivasResidente) {
		this.respostasPositivasResidente = respostasPositivasResidente;
	}

	public int getRespostasPositivasGestao() {
		return respostasPositivasGestao;
	}

	public void setRespostasPositivasGestao(int respostasPositivasGestao) {
		this.respostasPositivasGestao = respostasPositivasGestao;
	}

	public int getRespostasPositivasOperacional() {
		return respostasPositivasOperacional;
	}

	public void setRespostasPositivasOperacional(int respostasPositivasOperacional) {
		this.respostasPositivasOperacional = respostasPositivasOperacional;
	}

	public int getRespostasNegativasComercial() {
		return respostasNegativasComercial;
	}

	public void setRespostasNegativasComercial(int respostasNegativasComercial) {
		this.respostasNegativasComercial = respostasNegativasComercial;
	}

	public int getRespostasNegativasResidente() {
		return respostasNegativasResidente;
	}

	public void setRespostasNegativasResidente(int respostasNegativasResidente) {
		this.respostasNegativasResidente = respostasNegativasResidente;
	}

	public int getRespostasNegativasGestao() {
		return respostasNegativasGestao;
	}

	public void setRespostasNegativasGestao(int respostasNegativasGestao) {
		this.respostasNegativasGestao = respostasNegativasGestao;
	}
	
	
	
	
}
