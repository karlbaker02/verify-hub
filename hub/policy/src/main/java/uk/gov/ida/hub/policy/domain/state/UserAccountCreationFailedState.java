package uk.gov.ida.hub.policy.domain.state;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Optional;
import org.joda.time.DateTime;
import uk.gov.ida.hub.policy.domain.AbstractState;
import uk.gov.ida.hub.policy.domain.SessionId;

import java.net.URI;

public class UserAccountCreationFailedState extends AbstractState implements ResponseProcessingState, ResponsePreparedState {

    private static final long serialVersionUID = 3462121540778040610L;

    private Optional<String> relayState;

    @JsonCreator
    public UserAccountCreationFailedState(
        @JsonProperty("requestId") String requestId,
        @JsonProperty("authnRequestIssuerEntityId") String authnRequestIssuerEntityId,
        @JsonProperty("sessionExpiryTimestamp") DateTime sessionExpiryTimestamp,
        @JsonProperty("assertionConsumerServiceUri") URI assertionConsumerServiceUri,
        @JsonProperty("relayState") Optional<String> relayState,
        @JsonProperty("sessionId") SessionId sessionId,
        @JsonProperty("transactionSupportsEidas") boolean transactionSupportsEidas) {

        super(requestId, authnRequestIssuerEntityId, sessionExpiryTimestamp, assertionConsumerServiceUri, sessionId, transactionSupportsEidas);

        this.relayState = relayState;
    }

    public Optional<String> getRelayState() {
        return relayState;
    }
}
