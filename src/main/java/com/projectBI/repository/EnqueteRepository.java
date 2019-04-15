package com.projectBI.repository;

import org.springframework.data.repository.CrudRepository;

import com.projectBI.models.Enquete;

public interface EnqueteRepository extends CrudRepository<Enquete, String> {
	
}