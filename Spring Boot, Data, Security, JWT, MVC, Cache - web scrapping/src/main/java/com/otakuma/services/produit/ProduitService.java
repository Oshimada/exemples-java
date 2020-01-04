package com.otakuma.services.produit;

import java.util.HashMap;
import java.util.List;

import javax.validation.ValidationException;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.otakuma.data.Produit;
import com.otakuma.dto.ProduitInfosResponse;
import com.otakuma.repositories.ProduitRepository;
import com.otakuma.services.BaseService;
import com.otakuma.utils.Const;

@Service
public class ProduitService extends BaseService<Produit, ProduitRepository> {

	public ProduitService() {
	}

	public List<ProduitInfosResponse> getInfos() {
		return repo.getInfos();
	}

	@Cacheable(value = "produits", key = "#id", condition = "#id!=null")
	public Produit getById(Long id) {

		Produit p = null;
		try {
			p = repo.findById(id).get();
		} catch (Exception ex) {
			erreur(ex);
		}
		return p;
	}

	@Cacheable(value = "produitsbycode", key = "#code", condition = "#code!=null")
	public Produit getByCode(String code) throws NotFoundException, Exception {
		Produit p = null;
		try {
			p = repo.findByCode(code);
			if (p == null)
				throw new Exception("Produit '" + code + "' introuvable.");
		} catch (Exception e) {
			throw e;
		}
		return p;
	}

	public Produit add(Produit p) {
		return super.add(p);
	}

	public Produit update(String code, Produit p) {
		try {
			Produit newp = repo.findByCode(code);
			copie(p, newp);
			super.update(newp);
			return newp;
		} catch (Exception ex) {
			erreur(ex);
			return null;
		}
	}

	public Produit delete(String code) {
		try {
			Produit p = repo.findByCode(code);
			return delete(p);
		} catch (Exception ex) {
			erreur(ex);
			return null;
		}
	}

	private void copie(Produit p, Produit newp) {
		
		 if(p.getCategorie() != null) newp.setCategorie(p.getCategorie());
		 if(p.getTheme() != null) newp.setTheme(p.getTheme());
		 
		if (p.getCode() != null) newp.setCode(p.getCode());
		if (p.getNom() != null) newp.setNom(p.getNom());
		if (p.getIsActive() != null) newp.setIsActive(p.getIsActive());

		newp.setKeywords(p.getKeywords());
		newp.setShortDescription(p.getShortDescription());
		newp.setDescription(p.getDescription());

		if (!v.isNullOrEmpty(p.getImage1()) && !p.getImage1().equals(newp.getImage1())) {
			newp.setImage1(p.getImage1());
			newp.setThumbnail(getParser().imageToThumbnail(p.getImage1()));
		}

		if (v.isImageValide(p.getImage2())) newp.setImage2(p.getImage2());
		if (v.isImageValide(p.getImage3())) newp.setImage3(p.getImage3());

		if (!v.isNullOrNegatif(p.getPrixUnite())) newp.setPrixUnite(p.getPrixUnite());
		if (!v.isNullOrNegatif(p.getPrixPromo())) newp.setPrixPromo(p.getPrixPromo());

		newp.setStringDateDebutPromo(p.getStringDateDebutPromo());
		newp.setStringDateFinPromo(p.getStringDateFinPromo());

		if (p.getHasVariations() != null) newp.setHasVariations(p.getHasVariations());
	}

	private boolean isCodeValide(String code) {
		return !v.isNullOrEmpty(code) && code.matches("\\w*");
	}

	
	public Iterable<Produit> listRecherche(String code, String titre, String promo, Double prixmin, Double prixmax,
			Double prixpromomin, Double prixpromomax, Integer qtemin, Integer qtemax, Integer minreduc,
			Integer maxreduc, Integer size, Integer page) {

		Specification<Produit> spec = null;
		if (!v.isNullOrEmpty(code)) spec = and(spec, repo.like("code", "%" + code + "%"));

		if (!v.isNullOrEmpty(promo)) {
			if (promo.equals(Const.EN_PROMO))
				spec = and(spec, repo.infOuEgal("dateDebutPromo", utils.now()).and(repo.sup("dateFinPromo", utils.now())));
			else if (promo.equals(Const.FUTUR_PROMO))
				spec = and(spec, repo.sup("dateDebutPromo", utils.now()).and(repo.sup("dateFinPromo", utils.now())));
			else if (promo.equals(Const.NOT_PROMO))
				spec = and(spec, repo.isNull("dateFinPromo").or(repo.infOuEgal("dateFinPromo", utils.now())));
		}

		if(!v.isNullOrNegatif(prixmin)) spec = and(spec, repo.supOuEgal("prixUnite", prixmin));
		if(!v.isNullOrNegatif(prixmax)) spec = and(spec, repo.infOuEgal("prixUnite", prixmax));
		
		if(!v.isNullOrNegatif(prixpromomin)) spec = and(spec, repo.infOuEgal("prixPromo", prixpromomin));
		if(!v.isNullOrNegatif(prixpromomax)) spec = and(spec, repo.infOuEgal("prixPromo", prixpromomax));

		if(!v.isNullOrNegatif(qtemin)) spec = and(spec, repo.supOuEgal("qte", qtemin));
		if(!v.isNullOrNegatif(qtemax)) spec = and(spec, repo.infOuEgal("qte", qtemax));

		spec = and(spec, repo.boundReduc(minreduc, maxreduc));
		page = v.isNullOrNegatif(page) ? 0 : page;
		size = v.isNullOrNegatif(size) ? Const.DEFAULT_SIZE : page;
		if(!v.isNullOrEmpty(titre)) {
		
			if (!v.isNullOrEmpty(titre)) 
				spec = and(spec, repo.match("nom"));
			HashMap<String, String> map = new HashMap<>();
			map.put("text", titre);
			
			return recherche(spec, map, page, size);
		}
		
		return repo.findAll(spec, PageRequest.of(page, size));
	}


	@Override
	protected boolean valider(Produit p) throws ValidationException {
		if (p == null)
			throw new ValidationException("Produit NULL");
		if (!v.isTitreValide(p.getNom()))
			throw new ValidationException("Nom '" + p.getNom() + "' invalide");
		if (!isCodeValide(p.getCode()))
			throw new ValidationException("Code '" + p.getCode() + "' invalide");

		return true;
	}

	@Override
	protected Class<Produit> tableClass() {
		return Produit.class;
	}

	/*
	 * public Object getOrCreate(String key) { return produits.computeIfAbsent(key,
	 * new Function<String, Produit>() { public Produit apply(String k) { return new
	 * Produit(); } }); }
	 */

}
