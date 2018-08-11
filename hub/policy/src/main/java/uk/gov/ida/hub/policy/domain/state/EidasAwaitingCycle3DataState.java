package uk.gov.ida.hub.policy.domain.state;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Optional;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.StandardToStringStyle;
import org.joda.time.DateTime;
import uk.gov.ida.hub.policy.domain.LevelOfAssurance;
import uk.gov.ida.hub.policy.domain.PersistentId;
import uk.gov.ida.hub.policy.domain.SessionId;

import java.io.Serializable;
import java.net.URI;
import java.util.Objects;

public class EidasAwaitingCycle3DataState extends AbstractAwaitingCycle3DataState implements ResponseProcessingState, Serializable {

    private static final long serialVersionUID = -9056285913241958733L;

    private final String encryptedIdentityAssertion;

    public EidasAwaitingCycle3DataState(
        @JsonProperty("requestId") final String requestId,
        @JsonProperty("requestIssuerId") final String requestIssuerId,
        @JsonProperty("sessionExpiryTimestamp") final DateTime sessionExpiryTimestamp,
        @JsonProperty("assertionConsumerServiceUri") final URI assertionConsumerServiceUri,
        @JsonProperty("sessionId") final SessionId sessionId,
        @JsonProperty("transactionSupportsEidas") final boolean transactionSupportsEidas,
        @JsonProperty("identityProviderEntityId") final String identityProviderEntityId,
        @JsonProperty("matchingServiceAdapterEntityId") final String matchingServiceAdapterEntityId,
        @JsonProperty("relayState") final Optional<String> relayState,
        @JsonProperty("persistentId") final PersistentId persistentId,
        @JsonProperty("levelOfAssurance") final LevelOfAssurance levelOfAssurance,
        @JsonProperty("encryptedIdentityAssertion") final String encryptedIdentityAssertion) {

        super(
            requestId,
            requestIssuerId,
            sessionExpiryTimestamp,
            assertionConsumerServiceUri,
            sessionId,
            transactionSupportsEidas,
            identityProviderEntityId,
            matchingServiceAdapterEntityId,
            relayState,
            persistentId,
            levelOfAssurance);

        this.encryptedIdentityAssertion = encryptedIdentityAssertion;
    }

    public String getEncryptedIdentityAssertion() {
        return encryptedIdentityAssertion;
    }

    @Override
    public String toString() {
        final StandardToStringStyle style = new StandardToStringStyle();
        style.setUseIdentityHashCode(false);
        return ReflectionToStringBuilder.toString(this, style);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EidasAwaitingCycle3DataState that = (EidasAwaitingCycle3DataState) o;

        return Objects.equals(encryptedIdentityAssertion, that.encryptedIdentityAssertion) &&
            Objects.equals(getIdentityProviderEntityId(), that.getIdentityProviderEntityId()) &&
            Objects.equals(getMatchingServiceEntityId(), that.getMatchingServiceEntityId()) &&
            Objects.equals(getRelayState(), that.getRelayState()) &&
            Objects.equals(getPersistentId(), that.getPersistentId()) &&
            getLevelOfAssurance() == that.getLevelOfAssurance() &&
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
            encryptedIdentityAssertion,
            getIdentityProviderEntityId(),
            getMatchingServiceEntityId(),
            getRelayState(),
            getPersistentId(),
            getLevelOfAssurance(),
            getTransactionSupportsEidas(),
            getRequestId(),
            getRequestIssuerEntityId(),
            getSessionExpiryTimestamp(),
            getAssertionConsumerServiceUri(),
            getSessionId());
    }
}
