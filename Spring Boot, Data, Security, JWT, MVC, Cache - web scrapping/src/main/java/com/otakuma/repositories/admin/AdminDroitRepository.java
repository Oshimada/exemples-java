package com.otakuma.repositories.admin;

import org.springframework.data.repository.CrudRepository;

import com.otakuma.data.admin.AdminDroit;
import com.otakuma.repositories.JpaSpecs;

public interface AdminDroitRepository extends CrudRepository<AdminDroit, Long>, JpaSpecs<AdminDroit>  {

}
