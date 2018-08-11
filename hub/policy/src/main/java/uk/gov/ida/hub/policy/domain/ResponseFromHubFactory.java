package uk.gov.ida.hub.policy.domain;

import java.util.Optional;
import uk.gov.ida.common.shared.security.IdGenerator;

import javax.inject.Inject;
import java.net.URI;

import static java.util.Optional.ofNullable;

public class ResponseFromHubFactory {

    private final IdGenerator idGenerator;

    @Inject
    public ResponseFromHubFactory(IdGenerator idGenerator) {
        this.idGenerator = idGenerator;
    }

    public ResponseFromHub createSuccessResponseFromHub(
            String inResponseTo,
            String matchingServiceAssertion,
            Optional<String> relayState,
            String authnRequestIssuerEntityId,
            URI assertionConsumerServiceUri) {

        return new ResponseFromHub(
                idGenerator.getId(),
                inResponseTo,
                authnRequestIssuerEntityId,
                ofNullable(matchingServiceAssertion),
                relayState,
                assertionConsumerServiceUri,
                TransactionIdaStatus.Success
        );
    }

    public ResponseFromHub createNoAuthnContextResponseFromHub(
            String inResponseTo,
            Optional<String> relayState,
            String authnRequestIssuerEntityId,
            URI assertionConsumerServiceUri) {

        return new ResponseFromHub(
                idGenerator.getId(),
                inResponseTo,
                authnRequestIssuerEntityId,
                Optional.<String>empty(),
                relayState,
                assertionConsumerServiceUri,
                TransactionIdaStatus.NoAuthenticationContext
        );
    }

    public ResponseFromHub createNoMatchResponseFromHub(
            String inResponseTo,
            Optional<String> relayState,
            String authnRequestIssuerEntityId,
            URI assertionConsumerServiceUri) {

        return new ResponseFromHub(
                idGenerator.getId(),
                inResponseTo,
                authnRequestIssuerEntityId,
                Optional.<String>empty(),
                relayState,
                assertionConsumerServiceUri,
                TransactionIdaStatus.NoMatchingServiceMatchFromHub
        );
    }

    public ResponseFromHub createAuthnFailedResponseFromHub(
            String inResponseTo,
            Optional<String> relayState,
            String authnRequestIssuerEntityId,
            URI assertionConsumerServiceUri) {

        return new ResponseFromHub(
                idGenerator.getId(),
                inResponseTo,
                authnRequestIssuerEntityId,
                Optional.<String>empty(),
                relayState,
                assertionConsumerServiceUri,
                TransactionIdaStatus.AuthenticationFailed
        );
    }

    public ResponseFromHub createRequesterErrorResponseFromHub(
            String requestId,
            Optional<String> relayState,
            String requestIssuerId,
            URI assertionConsumerServiceUri) {

        return new ResponseFromHub(
            idGenerator.getId(),
            requestId,
            requestIssuerId,
            Optional.<String>empty(),
            relayState,
            assertionConsumerServiceUri,
            TransactionIdaStatus.RequesterError
        );
    }
}
