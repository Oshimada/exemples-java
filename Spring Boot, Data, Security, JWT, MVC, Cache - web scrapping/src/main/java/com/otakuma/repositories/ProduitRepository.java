package com.otakuma.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.otakuma.data.Produit;
import com.otakuma.dto.ProduitInfosResponse;

public interface ProduitRepository extends JpaRepository<Produit , Long>, JpaSpecs<Produit> {

	
	@Query(nativeQuery = true, value = "select * from Produit where match(nom) against(:text in boolean mode)")
	public List<Produit> recherche(@Param("text") String nom);
	
	public List<Produit> findByCategorieId( Long categorieId);
	
	@Query("SELECT new com.otakuma.dto.ProduitInfosResponse(P.nom, P.code, C.nom, T.nom) FROM Produit P JOIN P.categorie C JOIN P.theme T")
	public List<ProduitInfosResponse> getInfos();
	
	
	public Page<Produit> findAll(Pageable p);
	public Page<Produit> findAllByOrderByNomAsc(Pageable p);
	public Iterable<Produit> findAllByOrderByNomAsc();
	
	@Caching( put = { @CachePut(value = "produitsbycode", key = "#result.code", condition = "#result != null"),
		@CachePut(value = "produits", key = "#result.produitID", condition = "#result != null")})
	public Produit findByCode(String code);

	@Caching( put = { @CachePut(value = "produitsbycode", key = "#result.code", condition = "#result != null"),
		@CachePut(value = "produits", key = "#result.produitID", condition = "#result != null")})
	@Override public Optional<Produit> findById(Long id);

	@Caching(evict = { @CacheEvict(value = "produitsbycode", key = "#result.code", condition = "#result != null"), 
		@CacheEvict(value = "produits", key = "#result.produitID", condition = "#result != null") })
	@Override <S extends Produit> S save(S entity);
	
	@Caching(evict = { @CacheEvict(value = "produitsbycode", key = "#entity.code", condition = "#entity != null"), 
		@CacheEvict(value = "produits", key = "#entity.produitID", condition = "#entity != null") })
	@Override void delete(Produit entity);
	
}
