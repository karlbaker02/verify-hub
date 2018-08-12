package uk.gov.ida.hub.policy.configuration;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.redisson.config.Config;

import javax.validation.Valid;

public class RedisConfiguration {

    @Valid
    @JsonProperty
    private boolean enabled;

    @Valid
    @JsonProperty
    private Config configuration;

    public boolean isEnabled() {
        return enabled;
    }

    public Config getConfiguration() {
        return configuration;
    }
}
