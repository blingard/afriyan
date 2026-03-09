package org.ligot.afriyan.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
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
                // Configuration de l'ObjectMapper pour Redis
                ObjectMapper mapper = new ObjectMapper();
                mapper.findAndRegisterModules(); // Recherche et enregistre modules (JavaTime, Jdk8)

                // Désactivation de l'écriture des dates en timestamps pour plus de lisibilité
                mapper.configure(com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
                // Tolérance sur les propriétés inconnues
                mapper.configure(com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
                                false);

                // Configuration de la visibilité pour accéder aux champs privés sans getters
                mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);

                // Activation du typage par défaut pour la désérialisation des structures
                // polymorphes
                mapper.activateDefaultTyping(
                                LaissezFaireSubTypeValidator.instance,
                                ObjectMapper.DefaultTyping.EVERYTHING,
                                JsonTypeInfo.As.PROPERTY);

                GenericJackson2JsonRedisSerializer serializer = new GenericJackson2JsonRedisSerializer(mapper);

                RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
                                .entryTtl(Duration.ofHours(1))
                                .disableCachingNullValues()
                                .serializeKeysWith(RedisSerializationContext.SerializationPair
                                                .fromSerializer(new StringRedisSerializer()))
                                .serializeValuesWith(
                                                RedisSerializationContext.SerializationPair.fromSerializer(serializer));

                return RedisCacheManager.builder(factory)
                                .cacheDefaults(config)
                                .withCacheConfiguration("centrePartenaire", config.entryTtl(Duration.ofHours(2)))
                                .withCacheConfiguration("centrePartenaireByUserId",
                                                config.entryTtl(Duration.ofHours(2)))
                                .withCacheConfiguration("centrePartenaireProches",
                                                config.entryTtl(Duration.ofMinutes(30)))
                                .withCacheConfiguration("articles", config.entryTtl(Duration.ofHours(2)))
                                .withCacheConfiguration("articlesByType", config.entryTtl(Duration.ofHours(1)))
                                .withCacheConfiguration("articlesByCategory", config.entryTtl(Duration.ofHours(1)))
                                .withCacheConfiguration("articlesTop6", config.entryTtl(Duration.ofMinutes(30)))
                                .withCacheConfiguration("articlesPage", config.entryTtl(Duration.ofMinutes(30)))
                                .withCacheConfiguration("articlesActive", config.entryTtl(Duration.ofHours(1)))
                                .withCacheConfiguration("elearningFormations", config.entryTtl(Duration.ofHours(2)))
                                .withCacheConfiguration("elearningChapters", config.entryTtl(Duration.ofHours(2)))
                                .withCacheConfiguration("elearningParagraphs", config.entryTtl(Duration.ofHours(2)))
                                .withCacheConfiguration("elearningFormationsPage", config.entryTtl(Duration.ofHours(1)))
                                .withCacheConfiguration("elearningFormationsActive",
                                                config.entryTtl(Duration.ofHours(1)))
                                .withCacheConfiguration("elearningFormationsByCategory",
                                                config.entryTtl(Duration.ofHours(1)))
                                .build();
        }
}
