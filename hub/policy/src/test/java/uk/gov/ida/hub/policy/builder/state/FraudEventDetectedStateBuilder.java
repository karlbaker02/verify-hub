package uk.gov.ida.hub.policy.builder.state;

import com.google.common.base.Optional;
import org.joda.time.DateTime;
import uk.gov.ida.hub.policy.domain.SessionId;
import uk.gov.ida.hub.policy.domain.state.FraudEventDetectedState;
import uk.gov.ida.hub.policy.domain.state.FraudEventDetectedStateTransitional;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

public class FraudEventDetectedStateBuilder {

    private String requestId = "requestId";
    private String requestIssuerId = "requestId";
    private DateTime sessionExpiryTimestamp = DateTime.now().plusHours(1);
    private URI assertionConsumerServiceUri = URI.create("assertionConsumerServiceUri");
    private SessionId sessionId = SessionId.createNewSessionId();
    private Optional<String> relayState = Optional.of("relayState");
    private String idpEntityId = "idpEntityId";
    private List<String> availableIdpEntityIds = Arrays.asList("this-idp", "that-idp");
    private Optional<Boolean> forceAuthentication = Optional.of(true);

    public static FraudEventDetectedStateBuilder aFraudEventDetectedState() {
        return new FraudEventDetectedStateBuilder();
    }

    public FraudEventDetectedStateTransitional buildTransitional() {
        return new FraudEventDetectedStateTransitional(
                requestId,
                requestIssuerId,
                sessionExpiryTimestamp,
                assertionConsumerServiceUri,
                relayState,
                sessionId,
                idpEntityId,
                forceAuthentication,
                false);
    }

    @Deprecated
    public FraudEventDetectedState build() {
        return new FraudEventDetectedState(
                requestId,
                requestIssuerId,
                sessionExpiryTimestamp,
                assertionConsumerServiceUri,
                relayState,
                sessionId,
                idpEntityId,
                availableIdpEntityIds,
                forceAuthentication,
                false);
    }
}
