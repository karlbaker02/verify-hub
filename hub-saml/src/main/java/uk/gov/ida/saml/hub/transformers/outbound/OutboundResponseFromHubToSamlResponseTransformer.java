package uk.gov.ida.saml.hub.transformers.outbound;

import org.opensaml.saml.saml2.core.Assertion;
import org.opensaml.saml.saml2.core.Response;
import org.opensaml.saml.saml2.core.Status;
import uk.gov.ida.saml.core.OpenSamlXmlObjectFactory;
import uk.gov.ida.saml.core.domain.OutboundResponseFromHub;
import uk.gov.ida.saml.core.domain.TransactionIdaStatus;
import uk.gov.ida.saml.core.transformers.outbound.IdaResponseToSamlResponseTransformer;
import uk.gov.ida.saml.core.transformers.outbound.IdaStatusMarshaller;

import java.util.Optional;

public class OutboundResponseFromHubToSamlResponseTransformer extends IdaResponseToSamlResponseTransformer<OutboundResponseFromHub> {

    private final IdaStatusMarshaller<TransactionIdaStatus> statusMarshaller;
    private final AssertionFromIdpToAssertionTransformer assertionTransformer;

    public OutboundResponseFromHubToSamlResponseTransformer(
            IdaStatusMarshaller<TransactionIdaStatus> statusMarshaller,
            OpenSamlXmlObjectFactory openSamlXmlObjectFactory,
            AssertionFromIdpToAssertionTransformer assertionTransformer) {

        super(openSamlXmlObjectFactory);

        this.statusMarshaller = statusMarshaller;
        this.assertionTransformer = assertionTransformer;
    }

    @Override
    protected void transformAssertions(OutboundResponseFromHub originalResponse, Response transformedResponse) {
        Optional<String> matchingServiceAssertion = originalResponse.getMatchingServiceAssertion();
        if (matchingServiceAssertion.isPresent()) {
            Assertion transformedAssertion = assertionTransformer.transform(matchingServiceAssertion.get());
            transformedResponse.getAssertions().add(transformedAssertion);
        }
    }

    @Override
    protected Status transformStatus(OutboundResponseFromHub originalResponse) {
        return statusMarshaller.toSamlStatus(originalResponse.getStatus());
    }

    @Override
    protected void transformDestination(OutboundResponseFromHub originalResponse, Response transformedResponse) {
        transformedResponse.setDestination(originalResponse.getDestination().toASCIIString());
    }
}
