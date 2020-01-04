package com.otakuma.services.admin;

import java.sql.Timestamp;
import java.util.Collection;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.otakuma.data.admin.ImpUserDetails;
import com.otakuma.data.admin.Token;
import com.otakuma.repositories.admin.TokenRepository;
import com.otakuma.services.BaseService;
import com.otakuma.utils.Const;
import com.otakuma.utils.RandString;
import com.otakuma.utils.Utils;

@Service
public class TokenService extends BaseService<Token, TokenRepository>{

	
	@Autowired
	Utils utils;
	
	@Override
	protected boolean valider(Token t) throws ValidationException {
		// TODO Auto-generated method stub
		return false;
	}

	@Cacheable(value = "tokens", key = "#token", condition = "#token != null")
	public Token getToken(String token) {
		return repo.findByToken(token);
	}

	public String genereToken(UserDetails details, Timestamp debut, Timestamp fin) {
		
		
		String str = new RandString().next();
			
		ImpUserDetails dt = (ImpUserDetails) details;

		Token token = repo.findByPseudo(details.getUsername());
		if( token == null)
			token = new Token(dt.getId(), dt.getUsername(), str, debut, fin);
		else 
		{
			cacheEvictOldToken(token.getToken());
			token.setToken(str);
			token.setDebut(debut);
			token.setFin(fin);
		}
		repo.save(token);
		return str;
	}
	@CacheEvict(value = "tokens", key = "#token", condition = "#token != null")
	public void cacheEvictOldToken(String token) { }

	@Scheduled(fixedDelay = 10 * Const.MIN_TO_MILLIS)
	private void deleteExpiredTokens() {
		Collection<Token> tokens = repo.getExpired(utils.now());
		if(v.isNullOrEmpty(tokens))
			return;
		tokens.forEach(t -> repo.delete(t));
	}

	@Override
	protected Class<Token> tableClass() {
		return Token.class;
	}

}
