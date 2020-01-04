package com.otakuma.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.CrudRepository;

import com.otakuma.repositories.JpaSpecs;
import com.otakuma.utils.Parse;
import com.otakuma.utils.Utils;
import com.otakuma.utils.Validateur;

public abstract class BaseService<T, Repo extends CrudRepository<T, Long> & JpaSpecs<T>> {

	@Autowired
	protected Repo repo;

	protected Validateur v = new Validateur();
	protected Parse p = new Parse();
	protected Utils utils = new Utils();

	@Autowired
	protected EntityManager em;

	/** recherche dynamique avec Jpa Criteria 
	 * utilisée au cas où une recherche full text est necessaire 
	 * */
	public List<T> recherche(Specification<T> spec, HashMap<String, String> params, Integer page, Integer size) {

		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<T> criteria = builder.createQuery(tableClass());

		Root<T> root = criteria.from(tableClass());
		criteria.select(root);
		criteria.where(spec.toPredicate(root, criteria, builder));
		TypedQuery<T> query = em.createQuery(criteria);

		String tx;
		if (params.size() > 0)
			for (Entry<String, String> param : params.entrySet()) {
				tx = "+" + param.getValue().replaceAll("[^-\\wéèàêô\\s]", "").trim().replace(" ", "* +") + "*";
				query.setParameter(param.getKey(), tx.replace("+-", "-"));
			}
		query.setFirstResult(page * size).setMaxResults(size);
		
		return query.getResultList();
	}

	protected Specification<T> and(Specification<T> first, Specification<T> second) {
		return first == null ? second : second == null ? first : first.and(second);
	}

	protected abstract Class<T> tableClass();

	protected abstract boolean valider(T t) throws ValidationException;

	protected T add(T t) {
		valider(t);
		return repo.save(t);
	}

	protected T update(T t) {
		valider(t);
		return repo.save(t);
	}

	protected T delete(T t) {
		repo.delete(t);
		return t;
	}

	public void erreur(Exception ex) {
		System.err.print(ex.getClass().getSimpleName() + "  ");
		System.err.print("[" + this.getClass().getSimpleName() + ".");
		System.err.print(ex.getStackTrace()[0].getMethodName() + ":");
		System.err.print(ex.getStackTrace()[0].getLineNumber() + "] ");
		System.err.println(ex.getMessage());
	}

	public Validateur getValidateur() {
		return v;
	}

	public Parse getParser() {
		return p;
	}

	public boolean check() {
		return repo != null;
	}
}
