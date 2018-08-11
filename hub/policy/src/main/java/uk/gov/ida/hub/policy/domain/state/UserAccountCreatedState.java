package uk.gov.ida.hub.policy.domain.state;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.joda.time.DateTime;
import uk.gov.ida.hub.policy.domain.LevelOfAssurance;
import uk.gov.ida.hub.policy.domain.SessionId;

import java.net.URI;

public class UserAccountCreatedState extends AbstractUserAccountCreatedState {

    private static final long serialVersionUID = -1020619173417432390L;

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
        super(requestId, requestIssuerId, sessionExpiryTimestamp, assertionConsumerServiceUri, sessionId,
                identityProviderEntityId, matchingServiceAssertion, relayState, levelOfAssurance, registering, transactionSupportsEidas);
    }
}
