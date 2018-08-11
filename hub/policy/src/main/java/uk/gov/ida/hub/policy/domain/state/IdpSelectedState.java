package uk.gov.ida.hub.policy.domain.state;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Optional;
import org.joda.time.DateTime;
import uk.gov.ida.hub.policy.domain.AbstractState;
import uk.gov.ida.hub.policy.domain.LevelOfAssurance;
import uk.gov.ida.hub.policy.domain.SessionId;

import java.io.Serializable;
import java.net.URI;
import java.util.List;

public class IdpSelectedState extends AbstractState implements IdpSelectingState, Serializable {

    private static final long serialVersionUID = -2851353851977677375L;

    private final String idpEntityId;
    private final String matchingServiceEntityId;

    private Boolean useExactComparisonType;
    private List<LevelOfAssurance> levelsOfAssurance;
    private final Boolean forceAuthentication;
    private final String relayState;
    private final boolean registering;
    private final LevelOfAssurance requestedLoa;
    private final List<String> availableIdentityProviders;

    @JsonCreator
    public IdpSelectedState(
            @JsonProperty("requestId") String requestId,
            @JsonProperty("idpEntityId") String idpEntityId,
            @JsonProperty("matchingServiceEntityId") String matchingServiceEntityId,
            @JsonProperty("levelsOfAssurance") List<LevelOfAssurance> levelsOfAssurance,
            @JsonProperty("useExactComparisonType") Boolean useExactComparisonType,
            @JsonProperty("forceAuthentication") Boolean forceAuthentication,
            @JsonProperty("assertionConsumerServiceUri") URI assertionConsumerServiceUri,
            @JsonProperty("requestIssuerId") String requestIssuerId,
            @JsonProperty("relayState") String relayState,
            @JsonProperty("sessionExpiryTimestamp") DateTime sessionExpiryTimestamp,
            @JsonProperty("registering") boolean registering,
            @JsonProperty("requestedLoa") LevelOfAssurance requestedLoa,
            @JsonProperty("sessionId") SessionId sessionId,
            @JsonProperty("availableIdentityProviders") List<String> availableIdentityProviders,
            @JsonProperty("transactionSupportsEidas") boolean transactionSupportsEidas) {

        super(requestId, requestIssuerId, sessionExpiryTimestamp, assertionConsumerServiceUri, sessionId, transactionSupportsEidas);

        this.idpEntityId = idpEntityId;
        this.matchingServiceEntityId = matchingServiceEntityId;
        this.levelsOfAssurance = levelsOfAssurance;
        this.useExactComparisonType = useExactComparisonType;
        this.forceAuthentication = forceAuthentication;
        this.relayState = relayState;
        this.registering = registering;
        this.requestedLoa = requestedLoa;
        this.availableIdentityProviders = availableIdentityProviders;
    }

    public String getIdpEntityId() {
        return idpEntityId;
    }

    public Optional<Boolean> getForceAuthentication() {
        return Optional.ofNullable(forceAuthentication);
    }

    public Optional<String> getRelayState() {
        return Optional.ofNullable(relayState);
    }

    public List<String> getAvailableIdentityProviderEntityIds() {
        return availableIdentityProviders;
    }

    public boolean isRegistering() {
        return registering;
    }

    public LevelOfAssurance getRequestedLoa() {
        return requestedLoa;
    }

    public String getMatchingServiceEntityId() {
        return matchingServiceEntityId;
    }

    public Boolean getUseExactComparisonType() {
        return useExactComparisonType;
    }

    public List<LevelOfAssurance> getLevelsOfAssurance() {
        return levelsOfAssurance;
    }
}
