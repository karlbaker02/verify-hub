package uk.gov.ida.hub.policy.domain.state;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Optional;
import org.joda.time.DateTime;
import uk.gov.ida.hub.policy.domain.AbstractState;
import uk.gov.ida.hub.policy.domain.SessionId;

import java.io.Serializable;
import java.net.URI;

public class TimeoutState extends AbstractState implements Serializable {

    private static final long serialVersionUID = -4390191044338229404L;

    @JsonCreator
    public TimeoutState(
            @JsonProperty("requestId") String requestId,
            @JsonProperty("requestIssuerId") String requestIssuerId,
            @JsonProperty("sessionExpiryTimestamp") DateTime sessionExpiryTimestamp,
            @JsonProperty("assertionConsumerServiceUri") URI assertionConsumerServiceUri,
            @JsonProperty("sessionId") SessionId sessionId,
            @JsonProperty("transactionSupportsEidas") boolean transactionSupportsEidas) {

        super(requestId, requestIssuerId, sessionExpiryTimestamp, assertionConsumerServiceUri, sessionId, transactionSupportsEidas);
    }

    @Override
    public Optional<String> getRelayState() {
        return Optional.empty();
    }
}
