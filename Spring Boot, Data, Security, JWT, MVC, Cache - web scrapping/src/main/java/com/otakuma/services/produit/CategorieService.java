package com.otakuma.services.produit;

import javax.validation.ValidationException;

import org.springframework.stereotype.Service;

import com.otakuma.data.Categorie;
import com.otakuma.repositories.CategorieRepository;
import com.otakuma.services.BaseService;

@Service
public class CategorieService extends BaseService<Categorie, CategorieRepository> {

	@Override
	protected boolean valider(Categorie t) throws ValidationException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected Class<Categorie> tableClass() { return Categorie.class; }
}
