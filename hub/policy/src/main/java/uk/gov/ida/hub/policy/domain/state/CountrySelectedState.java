package uk.gov.ida.hub.policy.domain.state;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Optional;
import org.joda.time.DateTime;
import uk.gov.ida.hub.policy.domain.AbstractState;
import uk.gov.ida.hub.policy.domain.LevelOfAssurance;
import uk.gov.ida.hub.policy.domain.SessionId;

import java.io.Serializable;
import java.net.URI;
import java.util.Collections;
import java.util.List;

public class CountrySelectedState extends AbstractState implements CountrySelectingState, Serializable {

    private static final long serialVersionUID = -285602589000108606L;

    // TODO: Record matching service entity id
    private String countryEntityId;
    private final Optional<String> relayState;
    private List<LevelOfAssurance> levelsOfAssurance = Collections.EMPTY_LIST;

    @JsonCreator
    public CountrySelectedState(
            @JsonProperty("countryEntityId") String countryEntityId,
            @JsonProperty("relayState") Optional<String> relayState,
            @JsonProperty("requestId") String requestId,
            @JsonProperty("requestIssuerId") String requestIssuerId,
            @JsonProperty("sessionExpiryTimestamp") DateTime sessionExpiryTimestamp,
            @JsonProperty("assertionConsumerServiceUri") URI assertionConsumerServiceUri,
            @JsonProperty("sessionId") SessionId sessionId,
            @JsonProperty("transactionSupportsEidas") boolean transactionSupportsEidas,
            @JsonProperty("levelsOfAssurance") List<LevelOfAssurance> levelsOfAssurance) {
        super(requestId,
                requestIssuerId,
                sessionExpiryTimestamp,
                assertionConsumerServiceUri,
                sessionId,
                transactionSupportsEidas);
        this.relayState = relayState;
        this.countryEntityId = countryEntityId;
        this.levelsOfAssurance = levelsOfAssurance;
    }

    @Override
    public Optional<String> getRelayState() {
        return relayState;
    }

    public String getCountryEntityId() {
        return countryEntityId;
    }

    public List<LevelOfAssurance> getLevelsOfAssurance() {
        return levelsOfAssurance;
    }
}
