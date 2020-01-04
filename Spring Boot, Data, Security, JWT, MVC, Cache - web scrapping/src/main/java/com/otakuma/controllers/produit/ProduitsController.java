package com.otakuma.controllers.produit;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.otakuma.config.Routes;
import com.otakuma.data.Produit;
import com.otakuma.dto.ProduitInfosResponse;
import com.otakuma.services.produit.ProduitService;

@RestController
@RequestMapping(Routes.PRODUITS)
public class ProduitsController {

	@Autowired
	private ProduitService service;
	

	@RequestMapping(Routes.PING)
	public String ping() {
		if (service != null && service.check())
			return "Pong!";
		return null;
	}

	@Secured("PROD_SELECT")
	@RequestMapping("/username")
	public void debug() {
	    SecurityContext security = SecurityContextHolder.getContext();
	    System.err.println(security.getAuthentication());
	}

	@PreAuthorize("hasAuthority('PROD_SELECT')") //  and (#size == null or #size > 0)
	@RequestMapping({ Routes.DEF, Routes.LIST, Routes.SLASH })
	public Iterable<Produit> list(
			@RequestParam(required = false, value = "code") String code,
			@RequestParam(required = false, value = "nom") String titre,
			@RequestParam(required = false, value = "ispromo") String ispromo, /* */
			
			@RequestParam(required = false, value = "prixmin") Double prixmin,
			@RequestParam(required = false, value = "prixmax") Double prixmax,
			
			@RequestParam(required = false, value = "prixpromomin") Double prixpromomin,
			@RequestParam(required = false, value = "prixpromomax") Double prixpromomax,
			
			@RequestParam(required = false, value = "qtemin") Integer qtemin,
			@RequestParam(required = false, value = "qtemax") Integer qtemax,
			
			@RequestParam(required = false, value = "minreduc") Integer minreduc,
			@RequestParam(required = false, value = "maxreduc") Integer maxreduc,
			
			@RequestParam(required = false, value = "size") Integer size,
			@RequestParam(required = false, value = "page") Integer page) {
		
		return service.listRecherche(code, titre, ispromo, 
				prixmin, prixmax, prixpromomin, prixpromomax,
				qtemin, qtemax, minreduc, maxreduc, size, page); 
	}


	@Secured("PROD_SELECT")
	@RequestMapping(Routes.INFOS)
	public List<ProduitInfosResponse> infos() {
		return service.getInfos();
	}

	@Secured("PROD_SELECT")
	@RequestMapping(Routes.CODE)
	public Produit getByCode(@PathVariable String code) {
		try {
			return service.getByCode(code);
		}
		catch(Exception ex) {
			service.erreur(ex);
			return null;
		}
	}

	@Secured("PROD_ALL")
	@RequestMapping(Routes.ID)
	public Produit getById(@PathVariable Long id) {
		return service.getById(id);
	}

	@Secured("PROD_INSERT")
	@RequestMapping(method = RequestMethod.POST, value = { Routes.DEF, Routes.SLASH, Routes.INSERT })
	public Produit add(@RequestBody Produit produit) {
		return service.add(produit);
	}

	@Secured("PROD_UPDATE")
	@RequestMapping(method = RequestMethod.PUT, value = Routes.CODE)
	public Produit update(@RequestBody Produit produit, @PathVariable String code) {
		return service.update(code, produit);
	}

	@Secured("PROD_DELETE")
	@RequestMapping(method = RequestMethod.DELETE, value = Routes.CODE)
	public Produit delete(@PathVariable String code) {
		return service.delete(code);
	}
}
