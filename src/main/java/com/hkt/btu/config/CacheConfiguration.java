package com.hkt.btu.config;

import java.time.Duration;

import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;

import org.hibernate.cache.jcache.ConfigSettings;
import io.github.jhipster.config.JHipsterProperties;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache = jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                .build());
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, com.hkt.btu.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, com.hkt.btu.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, com.hkt.btu.domain.User.class.getName());
            createCache(cm, com.hkt.btu.domain.Authority.class.getName());
            createCache(cm, com.hkt.btu.domain.User.class.getName() + ".authorities");
            createCache(cm, com.hkt.btu.domain.StockItem.class.getName());
            createCache(cm, com.hkt.btu.domain.StockBalance.class.getName());
            createCache(cm, com.hkt.btu.domain.StockTransaction.class.getName());
            createCache(cm, com.hkt.btu.domain.AttrValue.class.getName());
            createCache(cm, com.hkt.btu.domain.Attr.class.getName());
            createCache(cm, com.hkt.btu.domain.Spec.class.getName());
            createCache(cm, com.hkt.btu.domain.Type.class.getName());
            createCache(cm, com.hkt.btu.domain.Type.class.getName() + ".specs");
            createCache(cm, com.hkt.btu.domain.Type.class.getName() + ".skuses");
            createCache(cm, com.hkt.btu.domain.Sku.class.getName());
            createCache(cm, com.hkt.btu.domain.Sku.class.getName() + ".types");
            createCache(cm, com.hkt.btu.domain.AttrValue.class.getName() + ".attrs");
            createCache(cm, com.hkt.btu.domain.Attr.class.getName() + ".attrValues");
            createCache(cm, com.hkt.btu.domain.Attr.class.getName() + ".specs");
            createCache(cm, com.hkt.btu.domain.Spec.class.getName() + ".attrs");
            createCache(cm, com.hkt.btu.domain.Spec.class.getName() + ".types");
            // jhipster-needle-ehcache-add-entry
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache == null) {
            cm.createCache(cacheName, jcacheConfiguration);
        }
    }

}
