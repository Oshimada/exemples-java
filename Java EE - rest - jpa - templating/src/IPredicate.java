package ma.otaku.services.servBehavior;

import java.util.HashSet;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public interface IPredicate<T> {

	public void execute(CriteriaBuilder builder, Root<T> root, HashSet<Predicate> pr);
}
