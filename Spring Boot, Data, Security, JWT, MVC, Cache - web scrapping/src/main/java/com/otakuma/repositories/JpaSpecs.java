package com.otakuma.repositories;

import java.sql.Date;
import java.sql.Timestamp;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface JpaSpecs<T> extends JpaSpecificationExecutor<T> {

	
	
	public default Specification<T> match(String param) {
		return (t, cq, cb) -> {
			Expression<Double> match = cb.function("match", Double.class, t.get(param), cb.parameter(String.class, "text"));
			return cb.greaterThan(match, 0d);
		};
	}
	
	

	public default Specification<T> isNull(String param) {
		return (t, cq, cb) -> cb.isNull(t.get(param));
	}
	public default Specification<T> isNotNull(String param) {
		return (t, cq, cb) -> cb.isNotNull(t.get(param));
	}

	
	
	public default Specification<T> equal(String param, Object valeur) {
		return (t, cq, cb) -> cb.equal(t.get(param), valeur);
	}

	public default Specification<T> notEqual(String param, Object valeur) {
		return (t, cq, cb) -> cb.notEqual(t.get(param), valeur);
	}

	public default Specification<T> like(String param, String valeur) {
		return (t, cq, cb) -> cb.like(t.get(param), valeur);
	}

	public default Specification<T> notLike(String param, String valeur) {
		return (t, cq, cb) -> cb.notLike(t.get(param), valeur);
	}
	
	/* parametre supérieur à la valeur */
	
	public default Specification<T> sup(String param, Double valeur) {
		return (t, cq, cb) -> cb.greaterThan(t.get(param), valeur);
	}

	public default Specification<T> sup(String param, Integer valeur) {
		return (t, cq, cb) -> cb.greaterThan(t.get(param), valeur);
	}

	public default Specification<T> sup(String param, Date valeur) {
		return (t, cq, cb) -> cb.greaterThan(t.get(param), valeur);
	}

	public default Specification<T> sup(String param, Timestamp valeur) {
		return (t, cq, cb) -> cb.greaterThan(t.get(param), valeur);
	}
	
	/* parametre supérieur ou egal à la valeur */
	
	public default Specification<T> supOuEgal(String param, Double valeur) {
		return (t, cq, cb) -> cb.greaterThanOrEqualTo(t.get(param), valeur);
	}

	public default Specification<T> supOuEgal(String param, Integer valeur) {
		return (t, cq, cb) -> cb.greaterThanOrEqualTo(t.get(param), valeur);
	}

	public default Specification<T> supOuEgal(String param, Date valeur) {
		return (t, cq, cb) -> cb.greaterThanOrEqualTo(t.get(param), valeur);
	}

	public default Specification<T> supOuEgal(String param, Timestamp valeur) {
		return (t, cq, cb) -> cb.greaterThanOrEqualTo(t.get(param), valeur);
	}
	
	/* parametre inferieur à la valeur */

	public default Specification<T> inf(String param, Double valeur) {
		return (t, cq, cb) -> cb.lessThan(t.get(param), valeur);
	}

	public default Specification<T> inf(String param, Integer valeur) {
		return (t, cq, cb) -> cb.lessThan(t.get(param), valeur);
	}

	public default Specification<T> inf(String param, Date valeur) {
		return (t, cq, cb) -> cb.lessThan(t.get(param), valeur);
	}
	
	public default Specification<T> inf(String param, Timestamp valeur) {
		return (t, cq, cb) -> cb.lessThan(t.get(param), valeur);
	}
	
	/* parametre inferieur ou egal à la valeur */

	public default Specification<T> infOuEgal(String param, Double valeur) {
		return (t, cq, cb) -> cb.lessThanOrEqualTo(t.get(param), valeur);
	}

	public default Specification<T> infOuEgal(String param, Integer valeur) {
		return (t, cq, cb) -> cb.lessThanOrEqualTo(t.get(param), valeur);
	}

	public default Specification<T> infOuEgal(String param, Date valeur) {
		return (t, cq, cb) -> cb.lessThanOrEqualTo(t.get(param), valeur);
	}

	public default Specification<T> infOuEgal(String param, Timestamp valeur) {
		return (t, cq, cb) -> cb.lessThanOrEqualTo(t.get(param), valeur);
	}

	/*
			Expression<Number> quot = builder.quot(  root.get("prixPromo") , root.get("prixUnite") );
			Expression<Number> reduc = builder.diff(1D, quot);
	 */

	public default Specification<T> boundReduc(Integer minreduc, Integer maxreduc) {
		return (t, cq, cb) -> {
			Expression<Number> reduc = cb.diff(1D, cb.quot(t.get("prixPromo"), t.get("prixUnite")));

			Predicate pr1 = null;
			Predicate pr2 = null;
			
			if( minreduc != null ) pr1 = cb.ge(reduc, (Number)(Double.parseDouble(minreduc+"")/100) ); 
			if( maxreduc != null ) pr2 = cb.le(reduc, (Number)(Double.parseDouble(maxreduc+"")/100) );
			
			return pr1 == null ? pr2 : pr2 == null ? pr1 : cb.and(pr1, pr2);
		};
	}
	
}