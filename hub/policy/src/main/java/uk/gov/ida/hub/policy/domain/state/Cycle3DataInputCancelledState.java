package uk.gov.ida.hub.policy.domain.state;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Optional;
import org.joda.time.DateTime;
import uk.gov.ida.hub.policy.domain.AbstractState;
import uk.gov.ida.hub.policy.domain.SessionId;

import java.io.Serializable;
import java.net.URI;
import java.util.Objects;

public class Cycle3DataInputCancelledState extends AbstractState implements ResponsePreparedState, Serializable {

    private static final long serialVersionUID = 9016732137997928472L;

    private final Optional<String> relayState;

    @JsonCreator
    public Cycle3DataInputCancelledState(
        @JsonProperty("requestId") final String requestId,
        @JsonProperty("sessionExpiryTimestamp") final DateTime sessionExpiryTimestamp,
        @JsonProperty("relayState") final Optional<String> relayState,
        @JsonProperty("requestIssuerId") final String requestIssuerId,
        @JsonProperty("assertionConsumerServiceUri") final URI assertionConsumerServiceUri,
        @JsonProperty("sessionId") final SessionId sessionId,
        @JsonProperty("transactionSupportsEidas") final boolean transactionSupportsEidas) {

        super(requestId, requestIssuerId, sessionExpiryTimestamp, assertionConsumerServiceUri, sessionId, transactionSupportsEidas);

        this.relayState = relayState;
    }

    public Optional<String> getRelayState() {
        return relayState;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Cycle3DataInputCancelledState{");
        sb.append("relayState=").append(relayState);
        sb.append(", requestId='").append(getRequestId()).append('\'');
        sb.append(", sessionId=").append(getSessionId());
        sb.append(", requestIssuerEntityId='").append(getRequestIssuerEntityId()).append('\'');
        sb.append(", sessionExpiryTimestamp=").append(getSessionExpiryTimestamp());
        sb.append(", assertionConsumerServiceUri=").append(getAssertionConsumerServiceUri());
        sb.append(", transactionSupportsEidas=").append(getTransactionSupportsEidas());
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Cycle3DataInputCancelledState that = (Cycle3DataInputCancelledState) o;

        return Objects.equals(relayState, that.relayState) &&
            getTransactionSupportsEidas() == that.getTransactionSupportsEidas() &&
            Objects.equals(getRequestId(), that.getRequestId()) &&
            Objects.equals(getRequestIssuerEntityId(), that.getRequestIssuerEntityId()) &&
            Objects.equals(getSessionExpiryTimestamp(), that.getSessionExpiryTimestamp()) &&
            Objects.equals(getAssertionConsumerServiceUri(), that.getAssertionConsumerServiceUri()) &&
            Objects.equals(getSessionId(), that.getSessionId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            relayState,
            getTransactionSupportsEidas(),
            getRequestId(),
            getRequestIssuerEntityId(),
            getSessionExpiryTimestamp(),
            getAssertionConsumerServiceUri(),
            getSessionId());
    }
}
