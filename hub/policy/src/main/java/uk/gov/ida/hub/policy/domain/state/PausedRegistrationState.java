package uk.gov.ida.hub.policy.domain.state;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Optional;
import org.joda.time.DateTime;
import uk.gov.ida.hub.policy.domain.AbstractState;
import uk.gov.ida.hub.policy.domain.SessionId;
import uk.gov.ida.hub.policy.domain.State;

import java.net.URI;

public class PausedRegistrationState extends AbstractState implements State {

    private static final long serialVersionUID = 8208525157755502287L;

    private Optional<String> relayState;

    @JsonCreator
    public PausedRegistrationState(
            @JsonProperty("requestId") String requestId,
            @JsonProperty("requestIssuerId") String requestIssuerId,
            @JsonProperty("sessionExpiryTimestamp") DateTime sessionExpiryTimestamp,
            @JsonProperty("assertionConsumerServiceUri") URI assertionConsumerServiceUri,
            @JsonProperty("sessionId") SessionId sessionId,
            @JsonProperty("transactionSupportsEidas") boolean transactionSupportsEidas,
            @JsonProperty("relayState") Optional<String> relayState) {
        super(requestId, requestIssuerId, sessionExpiryTimestamp, assertionConsumerServiceUri, sessionId, transactionSupportsEidas);
        this.relayState = relayState;
    }

    @Override
    public Optional<String> getRelayState() {
        return relayState;
    }
}
