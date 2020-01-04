package com.otakuma.repositories.admin;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.otakuma.data.admin.AdminLogin;
import com.otakuma.data.admin.ImpUserDetails;
import com.otakuma.repositories.JpaSpecs;

public interface AdminLoginRepository extends CrudRepository<AdminLogin, Long>, JpaSpecs<AdminLogin> {

	@Query("select new com.otakuma.data.admin.ImpUserDetails(adminID, password, pseudo)  from AdminLogin where pseudo = :username")
	Optional<ImpUserDetails> getUserDetailsByUsername(@Param("username") String username);
}
