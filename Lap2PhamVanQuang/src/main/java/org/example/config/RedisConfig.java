package org.example.config;

import org.springframework.boot.autoconfigure.cache.RedisCacheManagerBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import redis.clients.jedis.JedisPoolConfig;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class RedisConfig {


    @Bean
    JedisConnectionFactory jedisConnectionFactory(){
        // set ConnectionFactory : init connection to redis
        JedisConnectionFactory factory = null;
        JedisPoolConfig p = new JedisPoolConfig();
        // standalone ~~ cluster / cluster sentinel
        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
        config.setHostName("localhost");
        config.setPort(6379);
        config.setPassword("admin@123");
        JedisClientConfiguration.JedisClientConfigurationBuilder jedisClientBuilder =
                JedisClientConfiguration.builder();
        jedisClientBuilder.usePooling().poolConfig(p);
        factory = new JedisConnectionFactory(config,jedisClientBuilder.build());
        return factory;
    }

    @Bean
    public RedisCacheManagerBuilderCustomizer customCacheManager(){
        // custom cache for some prefix
//        return (builder) -> builder
//                .withCacheConfiguration("T2208e" ,
//                   RedisCacheConfiguration.defaultCacheConfig()
//                           .entryTtl(Duration.ofMinutes(1))
//                        );
        return (builder)->{
            Map<String,RedisCacheConfiguration> configMap  = new HashMap<>();
            configMap.put("T2208e",RedisCacheConfiguration.defaultCacheConfig()
                           .entryTtl(Duration.ofMinutes(10))
                    .serializeValuesWith(RedisSerializationContext.SerializationPair
                            .fromSerializer(new GenericJackson2JsonRedisSerializer()))
            );
            configMap.put("T2208e:dihoc",RedisCacheConfiguration.defaultCacheConfig()
                    .entryTtl(Duration.ofMinutes(15))
                    .serializeValuesWith(RedisSerializationContext.SerializationPair
                            .fromSerializer(new GenericJackson2JsonRedisSerializer()))
            );
            builder.withInitialCacheConfigurations(configMap);
        };
    }
    // todo : set up for default
    @Bean
    public RedisCacheConfiguration defaultConfig(){
        return RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofHours(1))
                .serializeValuesWith(RedisSerializationContext.SerializationPair
                        .fromSerializer(new GenericJackson2JsonRedisSerializer()));
    }
}
