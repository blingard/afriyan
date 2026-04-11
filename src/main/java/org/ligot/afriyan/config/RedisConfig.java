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
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Sort;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class RedisConfig {

        @Bean
        public CacheManager cacheManager(RedisConnectionFactory factory) {
                // Configuration de l'ObjectMapper pour Redis
                ObjectMapper mapper = new ObjectMapper();
                mapper.findAndRegisterModules(); // Recherche et enregistre modules (JavaTime, Jdk8)

                // Désactivation de l'écriture des dates en timestamps pour plus de lisibilité
                mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
                // Tolérance sur les propriétés inconnues
                mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

                // Configuration de la visibilité pour accéder aux champs privés sans getters
                mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);

                // Enregistrement des Mixins pour supporter Page et Sort de Spring Data
                mapper.addMixIn(PageImpl.class, PageImplMixin.class);
                mapper.addMixIn(Sort.class, SortMixin.class);

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
                                .build();
        }

        /**
         * Mixin pour la désérialisation de PageImpl
         */
        @JsonIgnoreProperties(ignoreUnknown = true, value = {"pageable"})
        abstract static class PageImplMixin {
                @JsonCreator
                public PageImplMixin(@JsonProperty("content") List<?> content,
                                     @JsonProperty("number") int number,
                                     @JsonProperty("size") int size,
                                     @JsonProperty("totalElements") long totalElements) {
                }
        }

        /**
         * Mixin pour la désérialisation de Sort
         */
        @JsonIgnoreProperties(ignoreUnknown = true)
        abstract static class SortMixin {
                @JsonCreator
                public static Sort by(@JsonProperty("orders") List<Sort.Order> orders) {
                        return Sort.by(orders);
                }
        }
}
