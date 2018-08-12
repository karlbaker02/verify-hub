package uk.gov.ida.hub.policy.configuration;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.Valid;

public class CacheConfiguration {

    @Valid
    @JsonProperty
    protected InfinispanConfiguration infinispan;

    @Valid
    @JsonProperty
    protected RedisConfiguration redis;

    public InfinispanConfiguration getInfinispan() {
        return infinispan;
    }

    public RedisConfiguration getRedis() {
        return redis;
    }
}
