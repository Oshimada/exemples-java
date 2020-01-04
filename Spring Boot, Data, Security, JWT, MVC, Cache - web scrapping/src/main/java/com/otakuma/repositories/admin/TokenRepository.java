package com.otakuma.repositories.admin;

import java.sql.Timestamp;
import java.util.Collection;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.otakuma.data.admin.Token;
import com.otakuma.repositories.JpaSpecs;

public interface TokenRepository extends CrudRepository<Token, Long>, JpaSpecs<Token> {

	@CachePut(value = "tokens" , key = "#result.token" , condition = "#result != null")
	public Token findByToken(String token);

	public Token findByPseudo(String pseudo);

	@CachePut(value = "tokens", key = "#entity.token", condition = "#entity != null")
	@Override <S extends Token> S save(S entity);

	@Query("select t from Token t where fin < :now")
	public Collection<Token> getExpired(@Param("now") Timestamp now);

	@CacheEvict(value = "tokens", key = "#entity.token", condition = "#entity != null")
	@Override
	public void delete(Token entity);
}

