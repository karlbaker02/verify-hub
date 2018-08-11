package uk.gov.ida.hub.policy.domain.state;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Optional;
import org.joda.time.DateTime;
import uk.gov.ida.hub.policy.domain.AbstractState;
import uk.gov.ida.hub.policy.domain.LevelOfAssurance;
import uk.gov.ida.hub.policy.domain.SessionId;

import java.io.Serializable;
import java.net.URI;

public class UserAccountCreatedState extends AbstractState implements ResponseProcessingState, ResponsePreparedState, Serializable {

    private static final long serialVersionUID = -1020619173417432390L;

    private final String identityProviderEntityId;
    private final String matchingServiceAssertion;
    private final String relayState;
    private final LevelOfAssurance levelOfAssurance;
    private final boolean registering;

    @JsonCreator
    public UserAccountCreatedState(
            @JsonProperty("requestId") final String requestId,
            @JsonProperty("requestIssuerId") final String requestIssuerId,
            @JsonProperty("sessionExpiryTimestamp") final DateTime sessionExpiryTimestamp,
            @JsonProperty("assertionConsumerServiceUri") final URI assertionConsumerServiceUri,
            @JsonProperty("sessionId") final SessionId sessionId,
            @JsonProperty("identityProviderEntityId") final String identityProviderEntityId,
            @JsonProperty("matchingServiceAssertion") final String matchingServiceAssertion,
            @JsonProperty("relayState") final String relayState,
            @JsonProperty("levelOfAssurance") LevelOfAssurance levelOfAssurance,
            @JsonProperty("registering") boolean registering,
            @JsonProperty("transactionSupportsEidas") boolean transactionSupportsEidas) {
        super(requestId, requestIssuerId, sessionExpiryTimestamp, assertionConsumerServiceUri, sessionId, transactionSupportsEidas);
        this.identityProviderEntityId = identityProviderEntityId;
        this.matchingServiceAssertion = matchingServiceAssertion;
        this.relayState = relayState;
        this.levelOfAssurance = levelOfAssurance;
        this.registering = registering;
    }

    @Override
    public Optional<String> getRelayState() {
        return Optional.ofNullable(relayState);
    }

    public String getIdentityProviderEntityId() {
        return identityProviderEntityId;
    }

    public String getMatchingServiceAssertion() {
        return matchingServiceAssertion;
    }

    public LevelOfAssurance getLevelOfAssurance() {
        return levelOfAssurance;
    }

    public boolean isRegistering() {
        return registering;
    }
}
