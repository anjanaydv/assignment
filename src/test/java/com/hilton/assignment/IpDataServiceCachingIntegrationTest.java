package com.hilton.assignment;

import com.hilton.assignment.config.RedisCacheConfig;
import com.hilton.assignment.model.IpData;
import com.hilton.assignment.repository.IpRepo;
import com.hilton.assignment.service.IPService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.cache.CacheAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import redis.embedded.RedisServer;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * @Copyright Any portion of this assignment's code are not allowed to use in business or production.
 *
 * Cache integration test.
 * Cache hit-miss test.
 *
 * @author Anjana
 */
@Import({ RedisCacheConfig.class, IPService.class })
@ExtendWith(SpringExtension.class)
@ImportAutoConfiguration(classes = { CacheAutoConfiguration.class, RedisAutoConfiguration.class })
@EnableCaching
public class IpDataServiceCachingIntegrationTest {

    @MockBean
    private IpRepo ipRepo;

    @Autowired
    private IPService ipService;

    @Autowired
    private CacheManager cacheManager;

    @Test
    void givenRedisCaching_whenFindItemById_thenItemReturnedFromCache() {
        IpData ipData = new IpData();
        String ip = "1.1.1.1";
        ipData.setQuery(ip);
        given(ipRepo.findByQuery(ip))
                .willReturn(ipData);

        IpData ipDataCacheMiss = ipService.getIpData(ip);
        IpData ipDataCacheHit = ipService.getIpData(ip);

        assertThat(ipDataCacheMiss).isEqualTo(ipData);
        assertThat(ipDataCacheHit.getQuery()).isEqualTo(ipData.getQuery());

        // ipRepo should be invoked only fist time.
        verify(ipRepo, times(1)).findByQuery(ip);
        assertThat(ipDataFromCache().getQuery()).isEqualTo(ip);
    }

    private IpData ipDataFromCache() {
        return (IpData) cacheManager.getCache("ipCache").get("1.1.1.1").get();
    }

    @TestConfiguration
    static class EmbeddedRedisConfiguration {

        private final RedisServer redisServer;

        public EmbeddedRedisConfiguration() {
            this.redisServer = new RedisServer();
        }

        @PostConstruct
        public void startRedis() {
            redisServer.start();
        }

        @PreDestroy
        public void stopRedis() {
            this.redisServer.stop();
        }
    }
}
