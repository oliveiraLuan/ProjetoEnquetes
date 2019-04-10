package com.projectBI.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
public class Funcionario {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int codigo;

	@ManyToOne
	private Enquete enquetes;

	@NotNull
	private String nome;

	@NotNull
	private String senha;

	@NotNull
	private String email;

	
	private String tipo;
	
	public Funcionario() {
		
	}
	public Funcionario(String nome, String email) {
		this.nome = nome;
		this.email = email;
	}
	
	public Funcionario(String nome, String email, String senha, String tipo) {
		this.nome = nome;
		this.email = email;
		this.senha = senha;
		this.tipo = tipo;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	public String getUsername() {
		// TODO Auto-generated method stub
		return this.email;
	}

	
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}


	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}
	
	// metodo que utiliza a interface PasswordAutentication para autenticar a senha do usuario 
	
	public boolean autentica(int senha) {
		if (senha != senha) {

			return false;

		} else {

			return false;

		}

	}
}
