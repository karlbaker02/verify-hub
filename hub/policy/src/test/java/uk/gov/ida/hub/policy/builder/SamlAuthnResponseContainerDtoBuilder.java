package uk.gov.ida.hub.policy.builder;

import uk.gov.ida.hub.policy.contracts.SamlAuthnResponseContainerDto;
import uk.gov.ida.hub.policy.domain.SessionId;
import uk.gov.ida.shared.utils.string.StringEncoding;

import java.util.UUID;

public class SamlAuthnResponseContainerDtoBuilder {

    private String samlResponse = StringEncoding.toBase64Encoded("blah");
    private SessionId sessionId = new SessionId(UUID.randomUUID().toString());
    private String principalIPAddressAsSeenByHub = "NOT SET IN BUILDER";


    public static SamlAuthnResponseContainerDtoBuilder aSamlAuthnResponseContainerDto() {
        return new SamlAuthnResponseContainerDtoBuilder();
    }

    public SamlAuthnResponseContainerDto build() {
        return new SamlAuthnResponseContainerDto(samlResponse, sessionId, principalIPAddressAsSeenByHub);
    }

    public SamlAuthnResponseContainerDtoBuilder withSessionId(SessionId sessionId) {
        this.sessionId = sessionId;
        return this;
    }

    public SamlAuthnResponseContainerDtoBuilder withPrincipalIPAddressAsSeenByHub(String ipAddress) {
        this.principalIPAddressAsSeenByHub = ipAddress;
        return this;
    }

    public SamlAuthnResponseContainerDtoBuilder withSamlResponse(String samlResponse) {
        this.samlResponse = samlResponse;
        return this;
    }

}
