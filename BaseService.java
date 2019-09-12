package ma.otaku.services.servBehavior;
import java.io.UncheckedIOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.ServletContext;
import ma.otaku.servlets.OtakuContextListener;
import ma.otaku.utils.Constantes;
import ma.otaku.utils.Parse;
import ma.otaku.utils.Utils;
import ma.otaku.utils.Validateur;


@SuppressWarnings("rawtypes")
public abstract class BaseService<T> {
	
	protected byte INIT_PARAM = DEFAULT;
	public static final byte DEFAULT = 0;
	public static final byte READONLY = 1;

	private EntityManagerFactory factory;
	protected ServletContext context;

	protected final Class<T> TABLE;

	private String nomtable;

	private Utils utils;
	private Parse parse;
	private Validateur valid;

	/** Utilisée comme remplacement des triggers MySQL (Google Cloud MySQL 2ème generation interdit les triggers) */
	protected HashSet<Trigger> insertTriggers;
	/** Utilisée comme remplacement des triggers MySQL (Google Cloud MySQL 2ème generation interdit les triggers) */
	protected HashSet<Trigger> deleteTriggers;
	/** Utilisée comme remplacement des triggers MySQL (Google Cloud MySQL 2ème generation interdit les triggers) */
	protected HashSet<Trigger> updateTriggers;

	protected HashSet<IPredicate> predicates;


	protected BaseService(ServletContext context, Class<T> table, byte param) {
		if (context != null)
			this.context = context;
		if (this.context != null)
			this.factory = (EntityManagerFactory) this.context.getAttribute("factory");
		this.TABLE = table;
		nomtable = table.getSimpleName();

		switch (param) {

		case READONLY:
			break;

		default:
			insertTriggers = new HashSet<>();
			deleteTriggers = new HashSet<>();
			updateTriggers = new HashSet<>();
			break;
		}
 
	}

	protected AccesTable(ServletContext context, Class<T> table) {
		this(context, table, DEFAULT);
	}

	protected AccesTable(Class<T> table, byte param) {
		this(OtakuContextListener.context, table, param);
	}

	protected AccesTable(Class<T> table) {
		this(table, DEFAULT);
	}

	/**
	 * [ (optionelle) utilisée dans isInDB -> update] <br/>isInDB
	 * pour étendre la vérification de l'unicité dans update. <br/>
	 * exemple de requete a utiliser dans manager.createQuery() pour verifier si une image est déjà utilisée dans la bdd: <br/>
	 * {@code image1 in( :img1, :img2, :img3) OR}<br/>
	 * {@code image2 in( :img1, :img2, :img3) OR}<br/>
	 * {@code image3 in( :img1, :img2, :img3) OR}<br/>
	 * {@code ...}
	 * @param T t l'objet à verifier
	 * @return javax.persistence.Query
	 * @throws Exception données invalides
	 */
	protected abstract Query isInDBQuery(T t, EntityManager manager) throws Exception;

	/**
	 * [ (optionelle) à implémenter pour insert et update] <br/>
	 * contient l'implementation des verifications des données, et l'initialisation des variations vides de la forme : <br/>
	 * {@code if(data1 invalide) throw new ValidationException("data1 invalide");}<br/>
	 * {@code if(data2 invalide) throw new ValidationException("data2 invalide");}<br/>
	 * {@code ...}
	 * @param T t l'objet à valider
	 * @return void
	 * @throws Exception données invalides
	 */
	protected abstract void verifierDonnees(T t) throws Exception;

	/** @throws Exception si l'objet existe dans la bdd */
	protected abstract void erreurExistant(T t) throws Exception;

	/** @throws Exception si l'objet n'existe pas dans la bdd */
	protected abstract void erreurInexistant(String t) throws Exception;

	/** @throws Exception s'il y a des erreurs dans deleteObject() */
	protected abstract void deleteErrors(T t) throws Exception;

	public T getEquals(String filtre, Object valeur) 
	{
		return getObjet(filtre, valeur, "=", 1);
	}

	/* ******************* = list *********************** */
	public List<T> getEqualsList(String filtre, Object valeur) 
	{
		return getList(filtre, valeur, "=", 0, Constantes.MAX_LIST_COUNT);
	}

	public List<T> getEqualsList(String filtre, Object valeur, int min, int max) 
	{
		return getList(filtre, valeur, "=", min, max);
	}

	/********************* like list *********************** */
	public List<T> getLike(String filtre, String valeur) 
	{
		return getList(filtre, valeur, "like", 0, Constantes.MAX_LIST_COUNT);
	}

	public List<T> getLike(String filtre, String valeur, int min, int max) 
	{
		return getList(filtre, valeur, "like", min, max);
	}

	/********************** < list ********************* */
	public List<T> getLessThan(String filtre, Object valeur) 
	{
		return getList(filtre, valeur, "<", 0, Constantes.MAX_LIST_COUNT);
	}

	/********************** < list ********************* */
	public List<T> getLessThan(String filtre, Object valeur, int min, int max) 
	{
		return getList(filtre, valeur, "<", min, max);
	}

	/********************** > list ********************* */
	public List<T> getGreaterThan(String filtre, Object valeur) 
	{
		return getList(filtre, valeur, ">", 0, Constantes.MAX_LIST_COUNT);
	}

	/********************** > list ********************* */
	public List<T> getGreaterThan(String filtre, Object valeur, int min, int max) 
	{
		return getList(filtre, valeur, ">", min, max);
	}

	/********************** <= list ********************* */
	public List<T> getLessThanOrEquals(String filtre, Object valeur) 
	{
		return getList(filtre, valeur, "<=", 0, Constantes.MAX_LIST_COUNT);
	}

	/********************** >= list ********************* */
	public List<T> getGreaterThanOrEquals(String filtre, Object valeur) 
	{
		return getList(filtre, valeur, ">=", 0, Constantes.MAX_LIST_COUNT);
	}

	/************* verif strings ************/
	protected void stringPredicate(String param, String value, CriteriaBuilder builder, Root<T> root, HashSet<Predicate> pr) 
	{
		if (!v().isNullOrEmpty(value)) pr.add(builder.like(root.get(param), "%" + value.trim().replace(" ", "%") + "%"));
	}

	
	/** Genere et ajoute like Predicate à la liste pr si la valeur(value) !empty */
	protected void likePredicate(String param, String value, CriteriaBuilder builder, Root<T> root, HashSet<Predicate> pr) 
	{
		if (!v().isNullOrEmpty(value)) pr.add(builder.like(root.get(param), value.trim()));
	}

	/**
	 * Genere et ajoute full text recherche Predicate à la liste pr si la valeur(value) != null && !empty
	 * la recherche utilise {@link ma.otaku.services.servBehavior.MySqlDialectMatch}<br/>
	 * **** OBLIGATOIRE **** ajouter le parametre au Query. (la chaine utilisée dans la methode mysql match("params...") against(col1, col2...)<br/>
	 * ex : manager.createQuery(criteria).setParameter("nom", "+premier_mot deuxieme_mot ...");
	 * 
	 */
	protected void textPredicate(String param, String value, CriteriaBuilder builder, Root<T> root, HashSet<Predicate> pr) 
	{
		if (value != null && value.trim().length() >= Constantes.MIN_LENGTH_RECHERCHE) 
		{
			Expression<Double> match = builder.function("match", Double.class, root.get(param), builder.parameter(String.class, param));
			pr.add(builder.greaterThan(match, 0d));
		}
	}

	/**
	 * Appelle la méthode textPredicate() si la valeur(value) !empty && la valeur.length > la taille minimale du full text search
	 * ( consultez la javadoc de {@link #textPredicate} pour en savoir plus) <br/>
	 * sinon si valeur != empty appelle la méthode stringPredicate() ( consultez la javadoc de {@link #stringPredicate} pour en savoir plus)
	 */
	protected void fullTextOrStringPredicate(String param, String value, CriteriaBuilder builder, Root<T> root, HashSet<Predicate> pr) 
	{
		if (value != null && value.trim().length() >= Constantes.MIN_LENGTH_RECHERCHE)
			textPredicate(param, value, builder, root, pr);
		else if(!v().isNullOrEmpty(value))
			stringPredicate(param, value, builder, root, pr);
	}

	/** Genere et ajoute superieur ou egal Predicate à la liste pr si la valeur(value) > la valeur minimale entrée(MIN) */
	protected void supOuEgalPredicate(String param, Integer value, Integer MIN, CriteriaBuilder builder, Root<T> root, HashSet<Predicate> pr) 
	{
		if (value != null && value > MIN) pr.add(builder.greaterThanOrEqualTo(root.get(param), value));
	}

	/** Genere et ajoute superieur ou egal Predicate à la liste pr si la valeur(value) > la valeur minimale entrée(MIN) */
	protected void supOuEgalPredicate(String param, Double value, Double MIN, CriteriaBuilder builder, Root<T> root, HashSet<Predicate> pr) 
	{
		if (value != null && value > MIN)
			pr.add(builder.greaterThanOrEqualTo(root.get(param), value));
	}

	/** Genere et ajoute date superieure ou egale Predicate à la liste pr si la valeur(value) != null <br/>
	 * si la date maximale(max) entrée != null le prédicate ne s'ajoute pas si max > value */
	protected void dateSupOuEgalPredicate(String param, Date value, Date max, CriteriaBuilder builder, Root<T> root, HashSet<Predicate> pr) 
	{
		if (value != null) {
			if (max != null && max.compareTo(value) < 0) return; // TODO Exception or not ? nope
			pr.add(builder.greaterThanOrEqualTo(root.get(param), value));
		}
	}

	/** Genere et ajoute inferieur ou egal Predicate à la liste pr si la valeur(value) > la valeur minimale entrée(MIN) */
	protected void infOuEgalPredicate(String param, Integer value, Integer MIN, CriteriaBuilder builder, Root<T> root, HashSet<Predicate> pr) 
	{
		if (value != null && value > MIN) pr.add(builder.lessThanOrEqualTo(root.get(param), value));
	}

	/** Genere et ajoute inferieur ou egal Predicate à la liste pr si la valeur(value) > la valeur minimale entrée(MIN) */
	protected void infOuEgalPredicate(String param, Double value, Double MIN, CriteriaBuilder builder, Root<T> root,
			HashSet<Predicate> pr) {
		if (value != null && value > MIN)
			pr.add(builder.lessThanOrEqualTo(root.get(param), value));
	}

	/** Genere et ajoute date inferieure ou egale Predicate à la liste pr si la valeur(value) != null <br/>
	 * si la date maximale(max) entrée != null le prédicate ne s'ajoute pas si max > value */
	protected void dateInfOuEgalPredicate(String param, Date value, Date max, CriteriaBuilder builder, Root<T> root,
			HashSet<Predicate> pr) {
		if (value != null) {
			if (max != null && max.compareTo(value) < 0)
				return; // TODO Exception or not ? nope
			pr.add(builder.lessThanOrEqualTo(root.get(param), value));
		}
	}

	/** Genere et ajoute egal Predicate à la liste pr si la valeur(value) != null */
	protected void egalPredicate(String param, Object value, CriteriaBuilder builder, Root<T> root, HashSet<Predicate> pr) {
		if (value != null) pr.add(builder.equal(root.get(param), value));
	}
	
	/** Genere et ajoute not egal Predicate à la liste pr si la valeur(value) != null */
	protected void notEgalPredicate(String param, Object value, CriteriaBuilder builder, Root<T> root, HashSet<Predicate> pr) {
		if (value != null) pr.add(builder.notEqual(root.get(param), value));
	}


	/** Appelle {@code javax.persistence.EntityManager.find}. puis ferme le manager*/
	public T getByID(Long id) {
		T t = null;
		EntityManager manager = null;

		try 
		{
			t = (T) getFactory().createEntityManager().find(TABLE, id);
		} 
		catch (Exception ex) 
		{
			ex.printStackTrace();
		} 
		finally 
		{
			if (manager != null && manager.isOpen()) manager.close();
		}
		return t;
	}

	/**
	 * 
	 * @param "parametre1", valeur1, "parametre2", valeur2... utilisez le parametre
	 *        "maxresult" , 1 pour avoir un resultat unique
	 * @return List<T>
	 */
	public List<T> dynamiqueEgalSelect(Object... obj) {
		if (obj == null)
			return null;
		for (int i = 0; i < obj.length; i += 2)
			obj[i] += " =";
		return dynamiqueSelect(obj);
	}

	@SuppressWarnings("unchecked")
	/**
	 * 
	 * @param "parametre1 operateur1", valeur1, "parametre2 operateur2", valeur2...
	 *        utilisez le parametre "maxresult" , 1 pour avoir un resultat unique
	 * @return List<T>
	 */
	public List<T> dynamiqueSelect(Object... obj) {

		List<T> t = null;
		EntityManager manager = null;
		try 
		{
			manager = getFactory().createEntityManager();
			StringBuilder req = new StringBuilder("SELECT P FROM " + nomtable + " P WHERE ");

			int length = obj.length >= 2 && obj[obj.length - 2].equals("maxresult") ? obj.length - 2 : obj.length;

			for (int i = 0; i < length; i += 2) 
			{
				req.append(obj[i + 1] != null ? (" " + obj[i] + " :s" + i + " ") : (" " + obj[i] + " "));
				if (length - i > 2) req.append("AND");
			}

			Query query = manager.createQuery(req.toString());

			for (int i = 1; i < length; i += 2)
				if (obj[i] != null) query.setParameter("s" + (i - 1), obj[i]);

			if (length < obj.length) query.setMaxResults((int) obj[obj.length - 1]);

			t = query.getResultList();

		} catch (Exception e) 
		{
			t = null;
			e.printStackTrace();
		} finally 
		{
			if (manager != null && manager.isOpen()) manager.close();
		}
		return t;
	}

	public Long count() {
		Long count = 0l;
		EntityManager manager = null;
		try {
			manager = getFactory().createEntityManager();
			Query query = manager.createQuery("SELECT Count(T.*) FROM " + TABLE.getSimpleName() + " T ");
			count = (Long) query.getSingleResult();
			
		} catch (Exception ex) {
			ex.printStackTrace();
			count = null;
		} finally {
			if (manager != null && manager.isOpen())
				manager.close();
		}
		return count;
	}

	@SuppressWarnings("unchecked")
	private T getObjet(String filtre, Object valeur, String operateur, int max) 
	{
		if (filtre == null || valeur == null) return null;

		T t = null;
		EntityManager manager = null;
		try {

			manager = getFactory().createEntityManager();

			Query query = manager.createQuery( "SELECT P FROM " + TABLE.getSimpleName() + " P where " + filtre + " " + operateur + " :val")
				.setParameter("val", valeur).setMaxResults(max);

			t = (T) query.getSingleResult();
		} 
		catch (NoResultException noResultat) { t = null; } 
		catch (Exception e) { t = null; e.printStackTrace(); } 
		finally { if (manager != null && manager.isOpen()) manager.close(); }
		return t;
	}

	/**
	 * @param filtre
	 * @param valeur
	 * @param operateur peut être =,<,>,<=,>=,like.. default =
	 * @param max nb maximum de lignes. default 100
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected List<T> getList(String filtre, Object valeur, String operateur, Integer min, Integer max) {
		boolean nofiltre = filtre == null || valeur == null;
		List<T> t = null;
		EntityManager manager = null;
		try {
			manager = getFactory().createEntityManager();
			Query query;
			if (nofiltre)
				query = manager.createQuery("SELECT P FROM " + nomtable + " P "); // + (filtre != null && filtre ==
																					// "code" ? "order by code":"")

			else
				query = manager
						.createQuery("SELECT P FROM " + nomtable + " P where " + filtre + " " + operateur + " :val")
						.setParameter("val", valeur);

			if (!v().isNullOrNegatif(min))
				query.setFirstResult(min);
			if (!v().isNullOrNegatif(min))
				query.setMaxResults(max);

			t = query.getResultList();
			
		} catch (UncheckedIOException ue) {
			throw ue;
		} catch (Exception e) {
			t = null;
			e.printStackTrace();
		} finally {
			if (manager != null && manager.isOpen())
				manager.close();
		}
		return t;
	}

	/**
	 * 
	 * @param filtre
	 * @param valeur
	 * @param operateur peut être =,<,>,<=,>=,like.. default =
	 * @param max       max lignes. default 100
	 * @return
	 */
	protected List<T> getList(String filtre, Object valeur, String operateur) {
		return getList(filtre, valeur, operateur, 0, 100);
	}

	protected List<T> getList(String filtre, Object valeur, int max) {
		return getList(filtre, valeur, "=", 0, max);
	}

	protected List<T> getList(String filtre, Object valeur) {
		return getList(filtre, valeur, "=", 0, 100);
	}

	public List<T> getList(Integer size) {
		return getList(null, null, "=", 0, size);
	}

	public List<T> getList() {
		return getList(null, null, "=", null, null);
	}

	/** Insert T, utilise {@link #basicInsert(T)} et necessite l'implementation de {@link # verifierDonnees}<br/> 
	 * si {@link #isInDBQuery} est implémentée elle va etre appelée par isInDB() ici**/
	public T insert(T t) throws Exception {
		verifierDonnees(t);
		if (isInDB(t))
			erreurExistant(t);
		return basicInsert(t);
	}
	/** [méthode déconseillée, utilisez {@link #insert(T) } plutôt <br/>
	 * insert T avec {@link #triggerTriggers} before & after */
	protected final T basicInsert(T t) throws Exception {

		triggerTriggers(t, insertTriggers, Trigger.BEFORE);
		EntityManager manager = null;

		try {
			manager = getFactory().createEntityManager();
			manager.getTransaction().begin();
			manager.persist(t);
			manager.flush();
			
			manager.getTransaction().commit();

			triggerTriggers(t, insertTriggers, Trigger.AFTER);

		} catch (Exception e) {
			if (manager != null && manager.getTransaction().isActive()) manager.getTransaction().rollback();
			t = null;
			e.printStackTrace();
		} finally {
			if (manager != null && manager.isOpen())
				manager.close();
		}
		return t;
	}

	/** insert une List<T> en appellant {@link #basicinsert(List)} **/
	public List<T> insert(List<T> tliste) throws Exception {
		return basicinsert(tliste);
	}

	/** insert une List<T> avec {@link #triggerTriggers} before & after <br/>
	 *  l'utilisation necessite l'implementation de {@link # verifierDonnees}<br/> 
	 * si {@link #isInDBQuery} est implémentée elle va etre appelée par isInDB() ici**/
	protected List<T> basicinsert(List<T> tliste) throws Exception {
		List<T> newt = new ArrayList<>();
		EntityManager manager = null;
		try {
			for (T t : tliste)
				triggerTriggers(t, insertTriggers, Trigger.BEFORE);

			manager = getFactory().createEntityManager();
			for (T t : tliste) {
				verifierDonnees(t);
				if (isInDB(t))
					erreurExistant(t);

				manager.getTransaction().begin();

				manager.persist(t);
				manager.flush();

				manager.getTransaction().commit();
			}

			for (T t : newt)
				triggerTriggers(t, insertTriggers, Trigger.AFTER);
		} catch (Exception ex) {
			if (manager != null && manager.getTransaction().isActive()) manager.getTransaction().rollback();
			newt = null;
			ex.printStackTrace();
			System.err.println("---- " + ex.getMessage() + " ---------");
		} finally {
			if(manager!= null && manager.isOpen()) manager.close();
		}
		return newt;
	}
	/** retourne l'objet s'il  existe dans la bdd selon les critères implémentés dans isInDBQuery
	 * necessite que {@link #isInDBQuery} soit implémentée, sinon retourne null
	 */
	@SuppressWarnings("unchecked")
	protected T isObjectInDB(T t) {
		T obj = null;
		EntityManager manager = null;
		try 
		{
			manager = getFactory().createEntityManager();
			Query query = isInDBQuery(t, manager);
			obj = (T) query.getSingleResult();
		} 
		catch (Exception e) {
			obj = null;
		} 
		finally 
		{
			if (manager != null && manager.isOpen()) manager.close();
		}
		return obj;
	}

	/** retourne true s'il  existe dans la bdd selon les critères implémentés dans isInDBQuery
	 */
	public boolean isInDB(T t) { return isObjectInDB(t) != null; }

	protected T delete(String param, Object value) throws Exception {
		try 
		{
			T newt = getEquals(param, value);
			if (newt == null) erreurInexistant((String) value);
			deleteObject(newt);

			return newt;
		} 
		catch (Exception ex) 
		{
			ex.printStackTrace();
			System.err.println("---- " + ex.getMessage() + " ---------");
			return null;
		}
	}
	/**
	 * execute les méthodes des classes trigger execute() selon leur mode d'execution ( BEFORE, AFTER, EITHER )
	 *  ces classes implémentent implémentent l'interface {@link ma.otaku.services.servBehavior.Trigger}
	 */
	@SuppressWarnings("unchecked")
	protected void triggerTriggers(T t, HashSet<Trigger> triggers, int mode) throws Exception {
		for (Trigger trigger : triggers)
			if (trigger.getMode() == mode) trigger.execute(t);
	}

	/** supprime une List d'objets T <br/> 
	 * utilise {@link #triggerTriggers} before & after **/
	public void delete(List<T> tliste) throws Exception {

		for (T t : tliste)
			triggerTriggers(t, deleteTriggers, Trigger.BEFORE);
		EntityManager manager = null;

		try {
			manager = getFactory().createEntityManager();
			manager.getTransaction().begin();

			for (T t : tliste)
				manager.remove(manager.contains(t) ? t : manager.merge(t));

			manager.getTransaction().commit();

			for (T t : tliste)
				triggerTriggers(t, deleteTriggers, Trigger.AFTER);

		} catch (Exception ex) {
			if (manager != null && manager.getTransaction().isActive()) manager.getTransaction().rollback();
			ex.printStackTrace();
		} finally {
			if (manager != null && manager.isOpen())
				manager.close();
		}
	}

	/** supprime un objet T <br/> 
	 * utilise {@link #triggerTriggers} before & after **/
	public void deleteObject(T t) throws Exception {
		triggerTriggers(t, deleteTriggers, Trigger.BEFORE);

		EntityManager manager = null;

		try {
			manager = getFactory().createEntityManager();
			manager.getTransaction().begin();
			manager.remove(manager.contains(t) ? t : manager.merge(t));
			manager.getTransaction().commit();

			triggerTriggers(t, deleteTriggers, Trigger.AFTER);

		} catch (Exception ex) {
			if (manager != null && manager.getTransaction().isActive()) manager.getTransaction().rollback();
			ex.printStackTrace();
		} finally {
			if (manager != null && manager.isOpen())
				manager.close();
		}
	}

	/** update un objet T <br/> 
	 * utilise {@link #triggerTriggers} before & after **/
	public T update(T t) throws Exception {
		triggerTriggers(t, updateTriggers, Trigger.BEFORE);

		EntityManager manager = null;
		try {
			manager = getFactory().createEntityManager();
			manager.getTransaction().begin();
			T newt = manager.merge(t);
			
			manager.getTransaction().commit();

			triggerTriggers(newt, updateTriggers, Trigger.AFTER);
		} catch (Exception ex) {
			if (manager != null && manager.getTransaction().isActive()) manager.getTransaction().rollback();
			t = null;
			ex.printStackTrace();
			System.err.println("---- " + ex.getMessage() + " ---------");
		} finally {
			if (manager != null && manager.isOpen())
				manager.close();
		}
		return t;
	}


	/** update une List d'objets T <br/> 
	 * utilise {@link #triggerTriggers} before & after **/
	public List<T> update(List<T> tliste) throws Exception {
		List<T> newt = new ArrayList<>();
		EntityManager manager = null;
		try 
		{
			for (T t : tliste) triggerTriggers(t, updateTriggers, Trigger.BEFORE);

			manager = getFactory().createEntityManager();
			manager.getTransaction().begin();

			for (T t : tliste) newt.add(manager.merge(t));

			manager.getTransaction().commit();

			for (T t : newt) triggerTriggers(t, updateTriggers, Trigger.AFTER);
		} 
		catch (Exception ex) 
		{
			if (manager != null && manager.getTransaction().isActive()) manager.getTransaction().rollback();
			newt = null;
			ex.printStackTrace();
			System.err.println("---- " + ex.getMessage() + " ---------");
		} 
		finally 
		{
			if (manager != null && manager.isOpen()) manager.close();
		}
		return newt;
	}

	/**
	 * recherche avec {@link javax.persistence.criteria.CriteriaBuilder} <br/>
	 * si {@link #textPredicate} est utilisée les mots recherchés doivent tous être présents 
	 * dans le texte (+mot1* +mot2*.. avec boolean mode)
	 * @param tri colonne de tri
	 * @param ordretype asc || desc
	 * @param page minimum 1
	 * @param size
	 * @param params [position, valeur..] optionnel , utilisée souvent avec avec 
	 * {@link #textPredicate(String, String, CriteriaBuilder, Root, HashSet)}
	 * @return Tist<T>
	 */
	public List<T> getRecherche(String tri, String ordretype, Integer page, int size, Object... params) {
		return getRecherche(tri, ordretype, page, size, "* +",  params);
	}
	/**
	 * recherche avec {@link javax.persistence.criteria.CriteriaBuilder} <br/>
	 * si {@link #textPredicate} est utilisée les mots recherchés doivent serviront a trier 
	 * les texts par qui contiennent le plus de mots (+mot1* mot2* mot3*.. avec boolean mode)
	 * @param tri colonne de tri
	 * @param ordretype asc || desc
	 * @param page minimum 1
	 * @param size
	 * @param params [position, valeur..] optionnel , utilisée souvent avec avec 
	 * {@link #textPredicate(String, String, CriteriaBuilder, Root, HashSet)}
	 * @return Tist<T>
	 */
	public List<T> getLikeRecherche(String tri, String ordretype, Integer page, int size, Object... params) {
		return getRecherche(tri, ordretype, page, size, "* ",  params);
	}
	
	@SuppressWarnings("unchecked")
	private List<T> getRecherche(String tri, String ordretype, Integer page, int size,String remplaceEspace, Object... params) {
		List<T> listedb = null;
		EntityManager manager = null;
		page = v().isNotSupZero(page) ? 1 : page;
		try {
			manager = getFactory().createEntityManager();

			CriteriaBuilder builder = manager.getCriteriaBuilder();
			CriteriaQuery<T> criteria = builder.createQuery(TABLE);
			Root<T> root = criteria.from(TABLE);
			criteria.select(root);
			HashSet<Predicate> pr = new HashSet<>();

			for (IPredicate p : predicates)
				p.execute(builder, root, pr);

			criteria.where(builder.and(pr.toArray(new Predicate[pr.size()])));
			Query query = manager.createQuery(criteria);
			if (!v().isNullOrEmpty(params))
				for (int i = 1; i < params.length; i++) {
					String text = (String) params[i];
					String tx = "+" + text.replaceAll("[^-\\wéèàêô\\s]", "").trim().replace(" ", remplaceEspace) + "*";
					query.setParameter((String) params[i - 1],
							tx.replace("+-", "-").replace("- ", "-").replace("+ ", "+"));
				}

			query.setFirstResult((page - 1) * size).setMaxResults(size);
			if (!v().isNullOrEmpty(tri)) {
				if (v().isEqual(ordretype, "desc"))
					criteria.orderBy(builder.desc(root.get(tri)));
				else
					criteria.orderBy(builder.asc(root.get(tri)));
			}

			listedb = ((List<T>) query.getResultList());

		} catch (Exception e) {
			e.printStackTrace();
			listedb = null;
		} finally {
			if(manager!= null && manager.isOpen()) manager.close();
		}
		return listedb;
	}
	/** appelle {@link #dateSupOuEgalPredicate} pour injecter les Predicates dans getRecherche */
	public void addDateSupOuEgalPredicate(String param, Date value) {
		predicates.add((IPredicate<T>) (CriteriaBuilder builder, Root<T> root, HashSet<Predicate> pr) -> {
			dateSupOuEgalPredicate(param, value, null, builder, root, pr);
		});
	}

	/** appelle {@link #dateInfOuEgalPredicate} pour injecter les Predicates dans getRecherche */
	public void addDateInfOuEgalPredicate(String param, Date value) {
		predicates.add((IPredicate<T>) (CriteriaBuilder builder, Root<T> root, HashSet<Predicate> pr) -> {
			dateInfOuEgalPredicate(param, value, null, builder, root, pr);
		});
	}
	/** appelle {@link #notEgalPredicate} pour injecter les Predicates dans getRecherche */
	public void addNotEqualsPredicate(String param, Object value) {
		predicates.add((IPredicate<T>) (CriteriaBuilder builder, Root<T> root, HashSet<Predicate> pr) -> {
			notEgalPredicate(param, value, builder, root, pr);
		});
	}
	/** appelle {@link #egalPredicate}  pour injecter les Predicates dans getRecherche */
	public void addEqualsPredicate(String param, Long value) {
		predicates.add((IPredicate<T>) (CriteriaBuilder builder, Root<T> root, HashSet<Predicate> pr) -> {
			egalPredicate(param, value, builder, root, pr);
		});
	}
	/** appelle {@link #egalPredicate} pour injecter les Predicates dans getRecherche */
	public void addEqualsPredicate(String param, Boolean value) {
		predicates.add((IPredicate<T>) (CriteriaBuilder builder, Root<T> root, HashSet<Predicate> pr) -> {
			egalPredicate(param, value, builder, root, pr);
		});
	}
	/** appelle {@link #fullTextOrStringPredicate} pour injecter les Predicates dans getRecherche */
	public void addLikePredicate(String param, String value) {
		predicates.add((IPredicate<T>) (CriteriaBuilder builder, Root<T> root, HashSet<Predicate> pr) -> {
			fullTextOrStringPredicate(param, value, builder, root, pr);
		});
	}
	/** appelle {@link #stringPredicate} pour injecter les Predicates dans getRecherche */
	public void addStringPredicate(String param, String value) {
		predicates.add((IPredicate<T>) (CriteriaBuilder builder, Root<T> root, HashSet<Predicate> pr) -> {
			stringPredicate(param, value, builder, root, pr);
		});
	}
	/** appelle {@link #fullTextOrStringPredicate} pour injecter les Predicates dans getRecherche */
	public void addTextSearchPredicate(String textparam, String value) {
		predicates.add((IPredicate<T>) (CriteriaBuilder builder, Root<T> root, HashSet<Predicate> pr) -> {
			fullTextOrStringPredicate(textparam, value, builder, root, pr);
		});
	}

	protected EntityManagerFactory getFactory() {
		if (factory == null)
			this.factory = (EntityManagerFactory) context.getAttribute("factory");
		return factory;
	}

	public void setFactory(EntityManagerFactory factory) {
		this.factory = factory;
	}

	/**
	 * instance de la classe {@link ma.otaku.utils.Utils}
	 */
	public Utils utils() 
	{
		if (utils == null) utils = new Utils();
		return utils;
	}

	/**
	 * instance de la classe {@link ma.otaku.utils.Parse}
	 */
	public Parse parse() 
	{
		if (parse == null) parse = new Parse();
		return parse;
	}

	/** instance de la classe {@link ma.otaku.utils.Validateur} */
	public Validateur validateur() 
	{
		if (valid == null) valid = new Validateur();
		return valid;
	}

	/**
	 * (racourcis) meme fonction que validateur()
	 * @return {@link ma.otaku.utils.Validateur}
	 */
	public Validateur v() 
	{
		return validateur();
	}

	/** @return ServletContext */
	public Object getContextAttribute(String name) 
	{
		return context.getAttribute(name);
	}
}
