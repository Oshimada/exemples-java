package com.otakuma.repositories.admin;

import org.springframework.data.repository.CrudRepository;

import com.otakuma.data.admin.AdminRole;
import com.otakuma.repositories.JpaSpecs;

public interface AdminRoleRepository extends CrudRepository<AdminRole, Long>, JpaSpecs<AdminRole> {

	
}
