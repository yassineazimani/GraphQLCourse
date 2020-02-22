package com.tuto.graphql.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

@Component
public class CacheUtils {

    @Autowired
    private CacheManager cacheManager;

    /**
     * Efface le cache
     * @param cacheName Nom du cache à effacer
     */
    public void clearCacheByName(String cacheName){
        if(StringUtils.isNotEmpty(cacheName)){
            Cache cache = cacheManager.getCache(cacheName);
            if(cache != null){
                cache.clear();
            }
        }
    }// clearCacheByName()

}// CacheUtils
