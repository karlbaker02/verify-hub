package uk.gov.ida.hub.policy.builder.domain;

import uk.gov.ida.hub.policy.domain.ResponseFromHub;
import uk.gov.ida.hub.policy.domain.TransactionIdaStatus;

import java.net.URI;
import java.util.Optional;
import java.util.UUID;

import static java.util.Optional.empty;

public class ResponseFromHubBuilder {

    private String authnRequestIssuerEntityId = UUID.randomUUID().toString();
    private String responseId = UUID.randomUUID().toString();
    private String inResponseTo = UUID.randomUUID().toString();
    private uk.gov.ida.hub.policy.domain.TransactionIdaStatus status = TransactionIdaStatus.Success;
    private Optional<String> matchingDatasetAssertion = empty();
    private Optional<String> relayState = empty();
    private URI assertionConsumerServiceUri = URI.create("/default-index");

    public static ResponseFromHubBuilder aResponseFromHubDto() {
        return new ResponseFromHubBuilder();
    }

    public ResponseFromHubBuilder withRelayState(String relayState) {
        this.relayState = Optional.ofNullable(relayState);
        return this;
    }

    public ResponseFromHub build() {
        return new ResponseFromHub(
                responseId,
                inResponseTo,
                authnRequestIssuerEntityId,
                matchingDatasetAssertion,
                relayState,
                assertionConsumerServiceUri,
                status
        );
    }
}

