package com.hilton.assignment.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.cache.RedisCacheManagerBuilderCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.serializer.*;

import java.time.Duration;

/**
 * @Copyright Any portion of this assignment's code are not allowed to use in business or production.
 *
 * This will cache like.
 * <p>
 * 127.0.0.1:6379> KEYS *
 * 1) "ipCache::1.2.7.8"
 * 2) "ipCache::1.2.7.3"
 * 3) "ipCache::1.2.7.9"
 * 127.0.0.1:6379> get ipCache::1.2.7.8
 * "\xac\xed\x00\x05sr\x00\"com.hilton.assignment.model.IpData\x00\x00\x00\x00\x00\x00\x00\x01\x02\x00\x0fL\x00\x02ast\x00\x12L
 * java/lang/String;L\x00\x04cityq\x00~\x00\x01L\x00\acountryq\x00~\x00\x01L\x00\x0bcountryCodeq\x00~\x00\x01L\x00\x02idt
 * \x00\x13Ljava/lang/Integer;L\x00\x03ispq\x00~\x00\x01L\x00\x03latt\x00\x12Ljava/lang/Double;L\x00\x03lonq\x00~\x00\x03L
 * \x00\x03orgq\x00~\x00\x01L\x00\x05queryq\x00~\x00\x01L\x00\x06regionq\x00~\x00\x01L\x00\nregionNameq\x00~\x00\x01L\x00
 * \x06statusq\x00~\x00\x01L\x00\btimezoneq\x00~\x00\x01L\x00\x03zipq\x00~\x00\x01xpt\x00\x00t\x00\bQingzhout\x00\x05Chinat
 * \x00\x02CNpt\x00\x0bCHINANET-FJsr\x00\x10java.lang.Double\x80\xb3\xc2J)k\xfb\x04\x02\x00\x01D\x00\x05valuexr\x00\x10
 * java.lang.Number\x86\xac\x95\x1d\x0b\x94\xe0\x8b\x02\x00\x00xp@:{\xd3\xc3a\x13@sq\x00~\x00\n@]{33333t\x00\x0bChinanet
 * FJt\x00\a1.2.7.8t\x00\x02FJt\x00\x06Fujiant\x00\asuccesst\x00\rAsia/Shanghaiq\x00~\x00\x05"
 *
 * @author Anjana Yadav
 */
@Configuration
@EnableCaching
public class RedisCacheConfig {

    @Bean
    public RedisCacheManagerBuilderCustomizer redisCacheManagerBuilderCustomizer() {
        return (builder) -> builder
                .withCacheConfiguration("ipCache",
                        RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofMinutes(1)));
    }

    @Bean
    public RedisCacheConfiguration cacheConfiguration() {
        return RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(1))
                .disableCachingNullValues()
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(
                        new GenericJackson2JsonRedisSerializer())
                );
    }
}