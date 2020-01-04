package com.otakuma.repositories;

import org.springframework.data.repository.CrudRepository;

import com.otakuma.data.Categorie;

public interface CategorieRepository extends CrudRepository<Categorie , Long>, JpaSpecs<Categorie>  {

}
