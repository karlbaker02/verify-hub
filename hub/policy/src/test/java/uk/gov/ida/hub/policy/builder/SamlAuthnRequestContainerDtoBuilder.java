package uk.gov.ida.hub.policy.builder;

import uk.gov.ida.hub.policy.domain.SamlAuthnRequestContainerDto;
import uk.gov.ida.shared.utils.string.StringEncoding;

import java.util.Optional;

import static java.util.Optional.empty;
import static java.util.Optional.ofNullable;

public class SamlAuthnRequestContainerDtoBuilder {

    private String samlRequest = StringEncoding.toBase64Encoded("blah");
    private Optional<String> relayState = empty();
    private String principalIPAddressAsSeenByHub = "NOT SET IN BUILDER";

    public static SamlAuthnRequestContainerDtoBuilder aSamlAuthnRequestContainerDto() {
        return new SamlAuthnRequestContainerDtoBuilder();
    }

    public SamlAuthnRequestContainerDto build() {
        return new SamlAuthnRequestContainerDto(
                samlRequest,
                relayState, principalIPAddressAsSeenByHub);
    }

    public SamlAuthnRequestContainerDtoBuilder withSamlRequest(String samlAuthnRequest) {
        this.samlRequest = samlAuthnRequest;
        return this;
    }

    public SamlAuthnRequestContainerDtoBuilder withRelayState(String relayState) {
        this.relayState = ofNullable(relayState);
        return this;
    }

    public SamlAuthnRequestContainerDtoBuilder withPrincipalIPAddressAsSeenByHub(String ipAddress) {
        this.principalIPAddressAsSeenByHub = ipAddress;
        return this;
    }
}
