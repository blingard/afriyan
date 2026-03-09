package org.ligot.afriyan.config;

import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

@Configuration
public class RedisConfig {

    @Bean
    public CacheManager cacheManager(RedisConnectionFactory factory) {
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofHours(1)) // TTL par défaut d'une heure
                .disableCachingNullValues()
                .serializeKeysWith(
                        RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair
                        .fromSerializer(new GenericJackson2JsonRedisSerializer()));

        return RedisCacheManager.builder(factory)
                .cacheDefaults(config)
                .withCacheConfiguration("centrePartenaire", config.entryTtl(Duration.ofHours(2)))
                .withCacheConfiguration("centrePartenaireByUserId", config.entryTtl(Duration.ofHours(2)))
                .withCacheConfiguration("centrePartenaireProches", config.entryTtl(Duration.ofMinutes(30)))
                .withCacheConfiguration("articles", config.entryTtl(Duration.ofHours(2)))
                .withCacheConfiguration("articlesByType", config.entryTtl(Duration.ofHours(1)))
                .withCacheConfiguration("articlesByCategory", config.entryTtl(Duration.ofHours(1)))
                .withCacheConfiguration("articlesTop6", config.entryTtl(Duration.ofMinutes(30)))
                .withCacheConfiguration("articlesPage", config.entryTtl(Duration.ofMinutes(30)))
                .withCacheConfiguration("articlesActive", config.entryTtl(Duration.ofHours(1)))
                .build();
    }
}
