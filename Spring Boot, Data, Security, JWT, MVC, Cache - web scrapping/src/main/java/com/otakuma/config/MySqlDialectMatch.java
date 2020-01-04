package com.otakuma.config;

import org.hibernate.dialect.MySQLDialect;
import org.hibernate.dialect.function.SQLFunctionTemplate;
import org.hibernate.type.StandardBasicTypes;

public class MySqlDialectMatch extends MySQLDialect {


	public MySqlDialectMatch() {
		super();
		registerFunction("match",
				new SQLFunctionTemplate(StandardBasicTypes.DOUBLE, "match(?1) against (?2 in boolean mode)"));
	}
}

