package com.otakuma.controllers.produit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.otakuma.config.Routes;
import com.otakuma.services.produit.CategorieService;

@RestController
@RequestMapping(Routes.CATEGORIES)
public class CategoriesController {

	@Autowired
	CategorieService service;
	

	@RequestMapping(Routes.PING)
	public String ping() {
		if (service != null && service.check())
			return "Pong!";
		return null;
	}
}
