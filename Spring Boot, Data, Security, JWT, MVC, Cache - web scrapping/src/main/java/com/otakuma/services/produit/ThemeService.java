package com.otakuma.services.produit;

import javax.validation.ValidationException;

import org.springframework.stereotype.Service;

import com.otakuma.data.Theme;
import com.otakuma.repositories.ThemeRepository;
import com.otakuma.services.BaseService;

@Service
public class ThemeService  extends BaseService<Theme, ThemeRepository>  {

	@Override
	protected Class<Theme> tableClass() {
		return Theme.class;
	}

	@Override
	protected boolean valider(Theme t) throws ValidationException {
		// TODO Auto-generated method stub
		return false;
	}

}
