package com.otakuma.config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = CacheConfig.class)
public class CacheConfigTest {
	
    @Autowired
    private ApplicationContext context;

    @Test
    public void shouldGetSimpleCacheManager() {
        CacheManager cacheManager = context.getBean(CacheManager.class);
        
        assertNotNull(cacheManager);
        assertEquals(SimpleCacheManager.class, cacheManager.getClass());
    }

    @Test
    public void shouldListCacheNames() {
    	String cacheNoms[] = {
        		"produits", 
        		"produitsbycode"
        	};
        CacheManager cacheManager = context.getBean(CacheManager.class);
        List<String> cacheNames = Arrays.asList(cacheNoms);

        for (String cacheName : cacheNames) {
            assertNotNull(cacheManager.getCache(cacheName));
        }
    }
}