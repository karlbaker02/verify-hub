package uk.gov.ida.hub.policy.domain.state;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Optional;
import org.joda.time.DateTime;
import uk.gov.ida.hub.policy.domain.LevelOfAssurance;
import uk.gov.ida.hub.policy.domain.SessionId;

import java.net.URI;

public class EidasSuccessfulMatchState extends AbstractSuccessfulMatchState {

    private static final long serialVersionUID = 7677160699140073010L;

    @JsonCreator
    public EidasSuccessfulMatchState(
            @JsonProperty("requestId") String requestId,
            @JsonProperty("sessionExpiryTimestamp") DateTime sessionExpiryTimestamp,
            @JsonProperty("identityProviderEntityId") String identityProviderEntityId,
            @JsonProperty("matchingServiceAssertion") String matchingServiceAssertion,
            @JsonProperty("relayState") String relayState,
            @JsonProperty("requestIssuerId") String requestIssuerId,
            @JsonProperty("assertionConsumerServiceUri") URI assertionConsumerServiceUri,
            @JsonProperty("sessionId") SessionId sessionId,
            @JsonProperty("levelOfAssurance") LevelOfAssurance levelOfAssurance,
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
    }
}
