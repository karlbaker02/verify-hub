package uk.gov.ida.hub.policy.domain.state;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Optional;
import org.joda.time.DateTime;
import uk.gov.ida.hub.policy.domain.LevelOfAssurance;
import uk.gov.ida.hub.policy.domain.SessionId;

import java.net.URI;
import java.util.Objects;

public class SuccessfulMatchState extends AbstractSuccessfulMatchState {

    private static final long serialVersionUID = 383573706638201670L;

    private final boolean isRegistering;

    @JsonCreator
    public SuccessfulMatchState(
            @JsonProperty("requestId") String requestId,
            @JsonProperty("sessionExpiryTimestamp") DateTime sessionExpiryTimestamp,
            @JsonProperty("identityProviderEntityId") String identityProviderEntityId,
            @JsonProperty("matchingServiceAssertion") String matchingServiceAssertion,
            @JsonProperty("relayState") String relayState,
            @JsonProperty("requestIssuerId") String requestIssuerId,
            @JsonProperty("assertionConsumerServiceUri") URI assertionConsumerServiceUri,
            @JsonProperty("sessionId") SessionId sessionId,
            @JsonProperty("levelOfAssurance") LevelOfAssurance levelOfAssurance,
            @JsonProperty("isRegistering") boolean isRegistering,
            @JsonProperty("transactionSupportsEidas") boolean transactionSupportsEidas) {

        super(
                requestId,
                sessionExpiryTimestamp,
                identityProviderEntityId,
                matchingServiceAssertion,
                Optional.ofNullable(relayState),
                requestIssuerId,
                assertionConsumerServiceUri,
                sessionId,
                levelOfAssurance,
                transactionSupportsEidas);

        this.isRegistering = isRegistering;
    }

    public boolean isRegistering() {
        return isRegistering;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SuccessfulMatchState that = (SuccessfulMatchState) o;

        return Objects.equals(isRegistering, that.isRegistering) && super.equals(o);
    }

    @Override
    public int hashCode() {
        return Objects.hash(isRegistering, super.hashCode());
    }
}
