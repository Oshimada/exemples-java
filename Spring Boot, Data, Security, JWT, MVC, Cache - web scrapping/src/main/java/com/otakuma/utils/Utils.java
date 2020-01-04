package com.otakuma.utils;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

public class Utils {

	public long countdown(Date dateFinPromo) {
		if (dateFinPromo == null)
			return 0;
		long secs = (dateFinPromo.getTime() - now().getTime()) / 1000;
		return secs < 0 ? 0 : secs;
	}

	public long calculCountdown(Timestamp dateFinPromo) {
		if (dateFinPromo == null)
			return 0;
		long secs = (dateFinPromo.getTime() - now().getTime()) / 1000;
		return secs < 0 ? 0 : secs;
	}

	public Timestamp now() {
		return Timestamp.from(zonedDate().toInstant());
	}

	
	public Timestamp nowPlus(int heures, int minutes, int secondes) {
		return Timestamp.from(zonedDate().toInstant().plusMillis(
			heures * Const.HEURE_TO_MILLIS+ minutes * Const.MIN_TO_MILLIS+ secondes * Const.SEC_TO_MILLIS ));
	}

	public ZonedDateTime zonedDate() {
		return ZonedDateTime.ofInstant(Instant.now(), ZoneId.of("Africa/Casablanca"));
	}

	public String shortTime() {
		return now().toString().split("\\.")[0];
	}

	public int attrOccurences(List<String> options, int debut, int fin) {

		String opt1 = null;
		String opt2 = null;

		for (int i = 0; i < options.size(); i++) {
			opt1 = options.get(i).substring(debut, fin);
			for (int j = 0; j < options.size(); j++) {
				if (j == i)
					continue;
				opt2 = options.get(j).substring(debut, fin);

				if (opt1.equals(opt2))
					return 1;
			}
		}
		return 0;
	}

}
