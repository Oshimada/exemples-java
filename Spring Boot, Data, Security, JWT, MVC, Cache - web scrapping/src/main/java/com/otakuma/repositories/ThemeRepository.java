package com.otakuma.repositories;

import org.springframework.data.repository.CrudRepository;

import com.otakuma.data.Theme;

public interface ThemeRepository extends CrudRepository<Theme , Long>, JpaSpecs<Theme>{

}
