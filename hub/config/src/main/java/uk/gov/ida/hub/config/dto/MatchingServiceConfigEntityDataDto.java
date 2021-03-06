package uk.gov.ida.hub.config.dto;

import java.net.URI;

public class MatchingServiceConfigEntityDataDto {

    private String entityId;
    private URI uri;
    private String transactionEntityId;
    private boolean healthCheckEnabled;
    private boolean onboarding;
    private URI userAccountCreationUri;

    @SuppressWarnings("unused")
    private MatchingServiceConfigEntityDataDto() {
    }

    public MatchingServiceConfigEntityDataDto(String entityId,
                                              URI uri,
                                              String transactionEntityId,
                                              boolean checkEnabled,
                                              boolean onboarding,
                                              URI userAccountCreationUri) {
        this.entityId = entityId;
        this.uri = uri;
        this.transactionEntityId = transactionEntityId;
        this.healthCheckEnabled = checkEnabled;
        this.onboarding = onboarding;
        this.userAccountCreationUri = userAccountCreationUri;
    }

    public String getEntityId() {
        return entityId;
    }

    public URI getUri() {
        return uri;
    }

    public String getTransactionEntityId() {
        return transactionEntityId;
    }

    public boolean isHealthCheckEnabled() {
        return healthCheckEnabled;
    }

    public boolean isOnboarding() {
        return onboarding;
    }

    public URI getUserAccountCreationUri() {
        return userAccountCreationUri;
    }
}
