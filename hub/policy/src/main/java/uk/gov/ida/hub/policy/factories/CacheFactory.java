package uk.gov.ida.hub.policy.factories;

import org.joda.time.DateTime;
import org.redisson.Redisson;
import org.redisson.codec.SerializationCodec;
import org.redisson.config.Config;
import uk.gov.ida.hub.policy.PolicyConfiguration;
import uk.gov.ida.hub.policy.controllogic.MultiCache;
import uk.gov.ida.hub.policy.domain.SessionId;
import uk.gov.ida.hub.policy.domain.State;
import uk.gov.ida.shared.dropwizard.infinispan.util.InfinispanCacheManager;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentMap;

public class CacheFactory {

    public static ConcurrentMap<SessionId, State> getStateCache(
            PolicyConfiguration configuration,
            InfinispanCacheManager infinispanCacheManager
    ) {
        List<ConcurrentMap<SessionId, State>> caches = new ArrayList<>();
        if (configuration.getRedis() != null) {
            Config redisConfig = configuration.getRedis().setCodec(new SerializationCodec());
            caches.add(Redisson.create(redisConfig).getMap("state_cache"));
        }

        if (configuration.getInfinispan() != null) {
            caches.add(infinispanCacheManager.getCache("state_cache"));
        }

        return new MultiCache<>(caches);
    }

    public static ConcurrentMap<SessionId, DateTime> getDatetimeCache(
            PolicyConfiguration configuration,
            InfinispanCacheManager infinispanCacheManager
    ) {
        List<ConcurrentMap<SessionId, DateTime>> caches = new ArrayList<>();
        if (configuration.getRedis() != null) {
            Config redisConfig = configuration.getRedis().setCodec(new SerializationCodec());
            caches.add(Redisson.create(redisConfig).getMap("datetime_cache"));
        }

        if (configuration.getInfinispan() != null) {
            caches.add(infinispanCacheManager.getCache("datetime_cache"));
        }

        return new MultiCache<>(caches);
    }
}
