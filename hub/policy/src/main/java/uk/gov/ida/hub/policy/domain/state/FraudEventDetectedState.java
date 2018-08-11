package uk.gov.ida.hub.policy.domain.state;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.joda.time.DateTime;
import uk.gov.ida.hub.policy.domain.SessionId;

import java.net.URI;

public class FraudEventDetectedState extends AuthnFailedErrorState {

    private static final long serialVersionUID = -8284392098372162493L;

    @JsonCreator
    public FraudEventDetectedState(
            @JsonProperty("requestId") String requestId,
            @JsonProperty("requestIssuerId") String requestIssuerId,
            @JsonProperty("sessionExpiryTimestamp") DateTime sessionExpiryTimestamp,
            @JsonProperty("assertionConsumerServiceUri") URI assertionConsumerServiceUri,
            @JsonProperty("relayState") String relayState,
            @JsonProperty("sessionId") SessionId sessionId,
            @JsonProperty("idpEntityId") String idpEntityId,
            @JsonProperty("forceAuthentication") Boolean forceAuthentication,
            @JsonProperty("transactionSupportsEidas") boolean transactionSupportsEidas) {

        super(
                requestId,
                requestIssuerId,
                sessionExpiryTimestamp,
                assertionConsumerServiceUri,
                relayState,
                sessionId,
                idpEntityId,
                forceAuthentication,
                transactionSupportsEidas);
    }
}
