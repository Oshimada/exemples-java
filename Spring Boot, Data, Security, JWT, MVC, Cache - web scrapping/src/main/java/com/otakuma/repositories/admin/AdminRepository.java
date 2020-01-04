package com.otakuma.repositories.admin;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.otakuma.data.admin.Admin;
import com.otakuma.data.admin.AdminRole;
import com.otakuma.repositories.JpaSpecs;

public interface AdminRepository extends CrudRepository<Admin, Long>, JpaSpecs<Admin> {

	@Query("select adminrole from Admin where adminid = :id")
	public AdminRole getRoleById(@Param("id") Long id);
}
