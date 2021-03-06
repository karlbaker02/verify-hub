package uk.gov.ida.hub.policy.domain.state;

import com.google.common.base.Optional;
import org.joda.time.DateTime;
import uk.gov.ida.hub.policy.domain.LevelOfAssurance;
import uk.gov.ida.hub.policy.domain.SessionId;

import java.net.URI;

public abstract class MatchRequestSentState extends AbstractMatchRequestSentState {

    private static final long serialVersionUID = -1474957484318282399L;

    private final boolean registering;

    protected MatchRequestSentState(
            final String requestId,
            final String requestIssuerEntityId,
            final DateTime sessionExpiryTimestamp,
            final URI assertionConsumerServiceUri,
            final SessionId sessionId,
            final boolean transactionSupportsEidas,
            final String identityProviderEntityId,
            final String relayState,
            final LevelOfAssurance idpLevelOfAssurance,
            final boolean registering,
            final String matchingServiceAdapterEntityId) {

        super(
                requestId,
                requestIssuerEntityId,
                sessionExpiryTimestamp,
                assertionConsumerServiceUri,
                sessionId,
                transactionSupportsEidas,
                identityProviderEntityId,
                Optional.fromNullable(relayState),
                idpLevelOfAssurance,
                matchingServiceAdapterEntityId
        );

        this.registering = registering;
    }

    public boolean isRegistering() {
        return registering;
    }
}
