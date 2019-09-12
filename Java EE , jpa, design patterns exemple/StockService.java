package ma.otaku.services.stock;

import java.util.HashSet;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import ma.otaku.services.servBehavior.BaseService;
import ma.otaku.services.servBehavior.ICodeService;
import ma.otaku.services.admin.AccesAdmin;
import ma.otaku.services.produit.AccesProduitsClient;
import ma.otaku.services.produit.AccesVariations;
import ma.otaku.services.triggers.TriggerQteNegative;
import ma.otaku.services.triggers.TriggerQtePositive;
import ma.otaku.services.triggers.stock.TriggerInsertStockQte;
import ma.otaku.services.triggers.stock.TriggerStockAssocie;
import ma.otaku.services.triggers.stock.TriggerUpdateStockQte;
import ma.otaku.data.admin.AdminDB;
import ma.otaku.data.admin.AdminLoginDB;
import ma.otaku.data.produit.ProduitClient;
import ma.otaku.data.produit.VariationDB;
import ma.otaku.data.stock.AchatStockItemDB;
import ma.otaku.data.stock.StockDB;
import ma.otaku.utils.Constantes;
import ma.otaku.utils.Utils;

public class StockService extends BaseService<StockDB> implements ICodeService<StockDB> {

	public StockService() {
		super(StockDB.class);

		insertTriggers.add(new TriggerQteNegative());
		insertTriggers.add(new TriggerInsertStockQte());
		insertTriggers.add(new TriggerStockAssocie());

		deleteTriggers.add(new TriggerQtePositive("Stock non vide"));

		updateTriggers.add(new TriggerQteNegative());
		updateTriggers.add(new TriggerUpdateStockQte(this));
	}

	public StockDB getByCode(String code) {
		return getEquals("code", code);
	}

	@Override
	protected Query isInDBQuery(StockDB t, EntityManager manager) throws Exception {
		return null;
	}

	@Override
	protected void verifierDonnees(StockDB t) throws Exception {
		
		if (v().isNullOrEmpty(t.getAchatStockItemCode()))
			throw new Exception("Code de l'achat item associé est invalide ");

		AchatStockItemService asitemservice = new AccesAchatStockItem();
		AchatStockItemDB item = asitemservice.getByCode(t.getAchatStockItemCode());

		if (item == null)
			throw new Exception("l'achatItem n'existe pas ou le code de l'achatItem associé est invalide");
		
		if (item.getAssocie())
			throw new Exception("Cet achat item est déjà associé a un stock");
		
		t.setAchatStockItemID(item.getAchatStockItemID());
		t.setVariationID(item.getVariationID());

		if (v().isNullOrNegatif(t.getQte()))
			throw new Exception("Quantité pour l'item " + t.getAchatStockItemCode() + " invalide");

		if (!t.getQte().equals(item.getQte()) && v().isNullOrEmpty(t.getMotif() )) 
			throw new Exception("QTE (" + item.getQte() + ") de l'achat item " + item.getCode() + " est differente " + 
			" de la QTE (" + t.getQte() + ") Stock que vous avez recu.\n Veuillez donner un motif pour ce changement.");
		

		String code = genereCode(t);
		if (!isCodeValide(code)) throw new Exception("Le code " + code + " de stock generé n'est pas valide");
		t.setCode(code);
	}

	@Override
	public String genereCode(StockDB t) {
		String suffix = t.getAchatStockItemCode().substring(0, 2);

		if (suffix.startsWith("VR")) {
			VariationDB var = new AccesVariations(AccesVariations.READONLY).getByID(t.getVariationID());
			String code = var.getCode();
			t.setVariationCode(code.substring(2));
		} else {
			ProduitClient var = new AccesProduitsClient(AccesVariations.READONLY)
					.getByID(t.getVariationID());
			t.setVariationCode(var.getCode().substring(3));
		}
		suffix += "_" + t.getVariationCode();
		return new Utils().nextCode(suffix, getLike("code", suffix + "%"));
	}

	@Override
	protected void erreurExistant(StockDB t) throws Exception {
		throw new Exception("Stock avec les memes données existe déjà " + t.getCode());
	}

	@Override
	protected void erreurInexistant(String t) throws Exception {
		throw new Exception("Stock " + t + " inéxistant");
	}

	@Override
	protected void deleteErrors(StockDB t) throws Exception { }

	@Override
	public boolean isCodeValide(String code) {
		return code.matches("VR_\\w\\w_\\w\\w\\d+_\\d+_\\d+") // stock de variation
			|| code.matches("PR_\\w\\w_\\w\\w_\\w\\w\\d+_\\d+");// stock de produit
	}

	public List<StockDB> getListeByAdmin(AdminLoginDB user, String code, Integer page) {

		List<StockDB> ls = null;
		if ( !v().isNullOrNegatif(page))
			ls = getEqualsList("authAdminID", user.getAdminID(), (page - 1) * 100, 100);
		else
			ls = getEqualsList("authAdminID", user.getAdminID());

		AdminDB admin = new AccesAdmin().getByID(user.getAdminID());

		for (StockDB s : ls) {
			if (s.getCode().startsWith("VR"))
				s.setVariationCode(new AccesVariations().getByID(s.getVariationID()).getCode());
			else
				s.setVariationCode(new AccesProduitsClient().getByID(s.getVariationID()).getCode());

			s.setAchatStockItemCode(new AccesAchatStockItem().getByID(s.getAchatStockItemID()).getCode());
			s.setAuthAdminNom(admin.getNom() + " " + admin.getPrenom());
		}
		return ls;
	}

/**
 * change l'etat du stock vers activé => la quantité est enlevée du produit/variation correspondante
 * @param code (sku) code de l'unité de stock
 */
	public StockDB desactiver(String code) throws Exception {
		StockDB sdb = getByCode(code);

		if (!sdb.getIsActive())
			return sdb;

		sdb.setIsActive(false);

		if (sdb.getCode().startsWith("VR")) {
			VariationsService varserv = new AccesVariations();
			VariationDB variation = varserv.getByID(sdb.getVariationID());
			variation.setQte(variation.getQte() - sdb.getQte());
			varserv.update(variation);
		} else {
			ProduitsClientService prodserv = new AccesProduitsClient();
			ProduitClient produit = prodserv.getByID(sdb.getVariationID());
			produit.setQte(produit.getQte() - sdb.getQte());
			prodserv.update(produit);
		}
		return update(sdb);
	}
/**
 * change l'etat du stock vers activé => la quantité est retournée vers le produit/variation correspondante
 * @param code (sku) code de l'unité de stock
 */
	public StockDB activer(String code) throws Exception {
		StockDB stockdb = getByCode(code);

		if (stockdb.getIsActive())
			return stockdb;
		stockdb.setIsActive(true);

		if (stockdb.getCode().startsWith("VR")) {
			VariationsService varserv = new AccesVariations();
			VariationDB variation = varserv.getByID(stockdb.getVariationID());
			variation.setQte(variation.getQte() + stockdb.getQte());
			varserv.update(variation);
		} else {
			ProduitsClientService prodserv = new AccesProduitsClient();
			ProduitClient produit = prodserv.getByID(stockdb.getVariationID());
			produit.setQte(produit.getQte() + stockdb.getQte());
			prodserv.update(produit);
		}
		return update(stockdb);
	}

	public List<StockDB> getListeRecherche(Integer page) {
		return getListeRecherche(page, null, null, null, null, null, null);
	}
	public List<StockDB> getListeRecherche(Integer page, String code) {
		return getListeRecherche(page, code, null, null, null, null, null);
	}
	public List<StockDB> getListeRecherche(Integer page, String code, Long authAdminID) {
		return getListeRecherche(page, code, authAdminID, null, null, null, null);
	}
	public List<StockDB> getListeRecherche(Integer page, String code, Long authAdminID, Long variationID) {
		return getListeRecherche(page, code, authAdminID, variationID, null, null, null);
	}
	public List<StockDB> getListeRecherche(Integer page, String code, Long authAdminID, Long variationID, Boolean isActive) {
		return getListeRecherche(page, code, authAdminID, variationID, null, null, isActive);
	}
	public List<StockDB> getListeRecherche(Integer page, String code, Long authAdminID, Long variationID, String mindate, String maxdate) {
		return getListeRecherche(page, code, authAdminID, variationID, mindate, maxdate, null);
	}
	
	public List<StockDB> getListeRecherche(Integer page, String code, Long authAdminID, Long variationID, String mindate, String maxdate, Boolean isActive) 
	{
		predicates = new HashSet<>();
		
		if (!v().isNullOrEmpty(code)) 
		{
			if (code.startsWith("PR_") || code.startsWith("VR_")) addStringPredicate("code", code );
			else addTextSearchPredicate("description", code);
		}

		// if (!v().isNullOrNegatif(authAdminID)) addEqualsPredicate("authAdminID", authAdminID);
		if (!v().isNullOrNegatif(variationID)) addEqualsPredicate("variationID", variationID);
		if (!v().isNullOrEmpty(mindate)) addDateSupOuEgalPredicate("dateInsertion", parse().ParseDate(mindate));
		if (!v().isNullOrEmpty(maxdate)) addDateSupOuEgalPredicate("dateInsertion", parse().ParseDate(maxdate));
		if (isActive != null) addEqualsPredicate("isActive", isActive);

		if (!v().isNullOrEmpty(code) && code.length() >= Constantes.MIN_LENGTH_RECHERCHE && !(code.startsWith("PR_") || code.startsWith("VR_")))
			return super.getRecherche("code", "asc", page, 100, "description", code);
		return super.getRecherche("code", "asc", page, 100);
	}
}




