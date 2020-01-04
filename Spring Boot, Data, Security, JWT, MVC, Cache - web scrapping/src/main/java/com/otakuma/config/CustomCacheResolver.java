package com.otakuma.config;
import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.interceptor.CacheOperationInvocationContext;
import org.springframework.cache.interceptor.CacheResolver;

public class CustomCacheResolver implements CacheResolver {

    private final CacheManager cacheManager;

    public CustomCacheResolver(CacheManager cacheManager){
        this.cacheManager = cacheManager;
    }

    @Override
    public Collection<? extends Cache> resolveCaches(CacheOperationInvocationContext<?> context) {
       return context.getOperation().getCacheNames().stream()
            .map(cacheName -> cacheManager.getCache(cacheName))
             .filter(cache -> cache != null)
             .collect(Collectors.toList());
    }

}