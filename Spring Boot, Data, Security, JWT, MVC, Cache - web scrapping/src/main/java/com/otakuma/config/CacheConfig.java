package com.otakuma.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.interceptor.CacheResolver;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class CacheConfig  extends CachingConfigurerSupport {

	
    public CacheConfig() {
    }
    
    @Bean
    public CacheManager cacheManager() {

    	String cacheNoms[] = {
    		"tokens",
    		"roles",
    		"produits", 
    		"produitsbycode"
    	};
    	
        SimpleCacheManager cacheManager = new SimpleCacheManager();
        List<ConcurrentMapCache> caches = new ArrayList<ConcurrentMapCache>();
        
        for(String s : cacheNoms)
        	caches.add(new ConcurrentMapCache(s));
        
        cacheManager.setCaches(caches);
        
        return cacheManager;
    }

    @Bean
    @Override
    public CacheResolver cacheResolver() {
        return new CustomCacheResolver(cacheManager());
    }

}




