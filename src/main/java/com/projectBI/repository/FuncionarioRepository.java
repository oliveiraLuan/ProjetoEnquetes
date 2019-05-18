package com.projectBI.repository;

import org.springframework.data.repository.CrudRepository;

import com.projectBI.models.Funcionario;

public interface FuncionarioRepository extends CrudRepository<Funcionario, String>{
	Funcionario findByEmail(String email);
}
