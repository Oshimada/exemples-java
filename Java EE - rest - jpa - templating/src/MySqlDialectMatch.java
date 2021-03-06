package ma.otaku.services.servBehavior;

import org.hibernate.dialect.MySQL5Dialect;
import org.hibernate.dialect.function.SQLFunctionTemplate;
import org.hibernate.type.StandardBasicTypes;

public class MySqlDialectMatch extends MySQL5Dialect {

	  public MySqlDialectMatch() {
	    super();
	    registerFunction("match", new SQLFunctionTemplate(StandardBasicTypes.DOUBLE,
	        "match(?1) against (?2 in boolean mode)"));
	  }
	}
