package uk.gov.ida.hub.policy.domain.state;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Optional;
import org.joda.time.DateTime;
import uk.gov.ida.hub.policy.domain.AbstractState;
import uk.gov.ida.hub.policy.domain.SessionId;

import java.io.Serializable;
import java.net.URI;

public class SessionStartedState extends AbstractState implements IdpSelectingState, CountrySelectingState, ResponseProcessingState, Serializable {

    private static final long serialVersionUID = -2890730003642035273L;

    private final String relayState;
    private final Boolean forceAuthentication;

    @JsonCreator
    public SessionStartedState(
            @JsonProperty("requestId") String requestId,
            @JsonProperty("relayState") String relayState,
            @JsonProperty("requestIssuerId") String requestIssuerId,
            @JsonProperty("assertionConsumerServiceUri") URI assertionConsumerServiceUri,
            @JsonProperty("forceAuthentication") Boolean forceAuthentication,
            @JsonProperty("sessionExpiryTimestamp") DateTime sessionExpiryTimestamp,
            @JsonProperty("sessionId") SessionId sessionId,
            @JsonProperty("transactionSupportsEidas") boolean transactionSupportsEidas) {

        super(requestId, requestIssuerId, sessionExpiryTimestamp, assertionConsumerServiceUri, sessionId, transactionSupportsEidas);

        this.relayState = relayState;
        this.forceAuthentication = forceAuthentication;
    }

    public Optional<String> getRelayState() {
        return Optional.ofNullable(relayState);
    }

    public Optional<Boolean> getForceAuthentication() {
        return Optional.ofNullable(forceAuthentication);
    }
}
