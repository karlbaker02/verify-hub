package uk.gov.ida.hub.policy.builder.state;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import uk.gov.ida.hub.policy.domain.LevelOfAssurance;
import uk.gov.ida.hub.policy.domain.SessionId;
import uk.gov.ida.hub.policy.domain.state.CountryAuthnFailedErrorState;

import java.net.URI;
import java.util.List;
import java.util.UUID;

import static java.util.Collections.singletonList;
import static uk.gov.ida.hub.policy.builder.domain.SessionIdBuilder.aSessionId;
import static uk.gov.ida.saml.core.test.TestEntityIds.STUB_COUNTRY_ONE;

public class CountryAuthnFailedErrorStateBuilder {

    private String requestId = UUID.randomUUID().toString();
    private String requestIssuerId = "requestIssuerId";
    private URI assertionConsumerServiceIndex = URI.create("/default-service-index");
    private String relayState = null;
    private DateTime sessionExpiryTimestamp = DateTime.now(DateTimeZone.UTC).plusMinutes(10);
    private SessionId sessionId = aSessionId().build();
    private String countryEntityId = STUB_COUNTRY_ONE;
    private List<LevelOfAssurance> levelsOfAssurance = singletonList(LevelOfAssurance.LEVEL_2);

    public static CountryAuthnFailedErrorStateBuilder aCountryAuthnFailedErrorState() {
        return new CountryAuthnFailedErrorStateBuilder();
    }

    public CountryAuthnFailedErrorState build() {
        return new CountryAuthnFailedErrorState(
                requestId,
                requestIssuerId,
                sessionExpiryTimestamp,
                assertionConsumerServiceIndex,
                relayState,
                sessionId,
                countryEntityId,
                levelsOfAssurance);
    }

    public CountryAuthnFailedErrorStateBuilder withRequestIssuerId(String requestIssuerId) {
        this.requestIssuerId = requestIssuerId;
        return this;
    }

    public CountryAuthnFailedErrorStateBuilder withCountryEntityId(String countryEntityId) {
        this.countryEntityId = countryEntityId;
        return this;
    }

    public CountryAuthnFailedErrorStateBuilder withRequestId(String requestId) {
        this.requestId = requestId;
        return this;
    }

    public CountryAuthnFailedErrorStateBuilder withSessionId(SessionId sessionId) {
        this.sessionId = sessionId;
        return this;
    }

    public CountryAuthnFailedErrorStateBuilder withSessionExpiryTimestamp(DateTime sessionExpiryTimestamp) {
        this.sessionExpiryTimestamp = sessionExpiryTimestamp;
        return this;
    }
}
