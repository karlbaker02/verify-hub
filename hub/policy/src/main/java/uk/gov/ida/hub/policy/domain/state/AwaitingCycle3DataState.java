package uk.gov.ida.hub.policy.domain.state;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Optional;
import org.joda.time.DateTime;
import uk.gov.ida.hub.policy.domain.LevelOfAssurance;
import uk.gov.ida.hub.policy.domain.PersistentId;
import uk.gov.ida.hub.policy.domain.SessionId;

import java.io.Serializable;
import java.net.URI;

public class AwaitingCycle3DataState extends AbstractAwaitingCycle3DataState implements ResponseProcessingState, Serializable {

    private static final long serialVersionUID = 2909622650570769370L;

    private final String encryptedMatchingDatasetAssertion;
    private final String authnStatementAssertion;
    private final boolean registering;

    @JsonCreator
    public AwaitingCycle3DataState(
            @JsonProperty("requestId") String requestId,
            @JsonProperty("identityProviderEntityId") String identityProviderEntityId,
            @JsonProperty("sessionExpiryTimestamp") DateTime sessionExpiryTimestamp,
            @JsonProperty("requestIssuerId") String requestIssuerId,
            @JsonProperty("encryptedMatchingDatasetAssertion") String encryptedMatchingDatasetAssertion,
            @JsonProperty("authnStatementAssertion") String authnStatementAssertion,
            @JsonProperty("relayState") String relayState,
            @JsonProperty("assertionConsumerServiceUri") URI assertionConsumerServiceUri,
            @JsonProperty("matchingServiceEntityId") String matchingServiceEntityId,
            @JsonProperty("sessionId") SessionId sessionId,
            @JsonProperty("persistentId") PersistentId persistentId,
            @JsonProperty("levelOfAssurance") LevelOfAssurance levelOfAssurance,
            @JsonProperty("registering") boolean registering,
            @JsonProperty("transactionSupportsEidas") boolean transactionSupportsEidas) {

        super(
                requestId,
                requestIssuerId,
                sessionExpiryTimestamp,
                assertionConsumerServiceUri,
                sessionId,
                transactionSupportsEidas,
                identityProviderEntityId,
                matchingServiceEntityId,
                Optional.fromNullable(relayState),
                persistentId,
                levelOfAssurance);

        this.encryptedMatchingDatasetAssertion = encryptedMatchingDatasetAssertion;
        this.authnStatementAssertion = authnStatementAssertion;
        this.registering = registering;
    }
    public String getAuthnStatementAssertion() {
        return authnStatementAssertion;
    }

    public String getEncryptedMatchingDatasetAssertion() {
        return encryptedMatchingDatasetAssertion;
    }

    public boolean isRegistering() {
        return registering;
    }
}
