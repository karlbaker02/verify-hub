package uk.gov.ida.hub.policy.domain.state;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Optional;
import org.joda.time.DateTime;
import uk.gov.ida.hub.policy.domain.AbstractState;
import uk.gov.ida.hub.policy.domain.SessionId;

import java.io.Serializable;
import java.net.URI;
import java.util.Objects;

public final class NoMatchState extends AbstractState implements ResponseProcessingState, ResponsePreparedState, Serializable {

    private static final long serialVersionUID = 4256395503097984488L;

    private final String identityProviderEntityId;
    private final Optional<String> relayState;

    @JsonCreator
    public NoMatchState(
            @JsonProperty("requestId") String requestId,
            @JsonProperty("identityProviderEntityId") String identityProviderEntityId,
            @JsonProperty("requestIssuerId") String requestIssuerId,
            @JsonProperty("sessionExpiryTimestamp") DateTime sessionExpiryTimestamp,
            @JsonProperty("assertionConsumerServiceUri") URI assertionConsumerServiceUri,
            @JsonProperty("relayState") Optional<String> relayState,
            @JsonProperty("sessionId") SessionId sessionId,
            @JsonProperty("transactionSupportsEidas") boolean transactionSupportsEidas) {

        super(requestId, requestIssuerId, sessionExpiryTimestamp, assertionConsumerServiceUri, sessionId, transactionSupportsEidas);

        this.identityProviderEntityId = identityProviderEntityId;
        this.relayState = relayState;
    }

    public String getIdentityProviderEntityId() {
        return identityProviderEntityId;
    }

    public Optional<String> getRelayState() {
        return relayState;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("NoMatchState{");
        sb.append("identityProviderEntityId='").append(identityProviderEntityId).append('\'');
        sb.append(", relayState=").append(relayState);
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

        NoMatchState that = (NoMatchState) o;

        return Objects.equals(identityProviderEntityId, that.identityProviderEntityId) &&
            Objects.equals(relayState, that.relayState) &&
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
            identityProviderEntityId,
            relayState,
            getTransactionSupportsEidas(),
            getRequestId(),
            getRequestIssuerEntityId(),
            getSessionExpiryTimestamp(),
            getAssertionConsumerServiceUri(),
            getSessionId());
    }
}
