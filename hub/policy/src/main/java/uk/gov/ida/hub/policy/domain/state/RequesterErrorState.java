package uk.gov.ida.hub.policy.domain.state;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Optional;
import org.joda.time.DateTime;
import uk.gov.ida.hub.policy.domain.AbstractState;
import uk.gov.ida.hub.policy.domain.SessionId;

import java.net.URI;

public class RequesterErrorState extends AbstractState implements IdpSelectingState, ResponsePreparedState {

    private static final long serialVersionUID = -1738587884705979267L;

    private String relayState;
    private Boolean forceAuthentication;

    @JsonCreator
    public RequesterErrorState(
            @JsonProperty("requestId") String requestId,
            @JsonProperty("authnRequestIssuerEntityId") String authnRequestIssuerEntityId,
            @JsonProperty("sessionExpiryTimestamp") DateTime sessionExpiryTimestamp,
            @JsonProperty("assertionConsumerServiceUri") URI assertionConsumerServiceUri,
            @JsonProperty("relayState") String relayState,
            @JsonProperty("sessionId") SessionId sessionId,
            @JsonProperty("forceAuthentication") Boolean forceAuthentication,
            @JsonProperty("transactionSupportsEidas") boolean transactionSupportsEidas) {

        super(requestId, authnRequestIssuerEntityId, sessionExpiryTimestamp, assertionConsumerServiceUri, sessionId, transactionSupportsEidas);

        this.relayState = relayState;
        this.forceAuthentication = forceAuthentication;
    }

    @Override
    public Optional<Boolean> getForceAuthentication() {
        return Optional.fromNullable(forceAuthentication);
    }

    public Optional<String> getRelayState() {
        return Optional.fromNullable(relayState);
    }
}
