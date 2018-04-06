package uk.gov.ida.hub.samlproxy.factories;

import com.google.inject.Inject;
import org.opensaml.saml.saml2.core.Response;
import org.opensaml.saml.saml2.metadata.IDPSSODescriptor;
import uk.gov.ida.hub.samlproxy.security.AuthnResponseKeyStore;
import uk.gov.ida.saml.metadata.EidasMetadataResolverRepository;
import uk.gov.ida.saml.metadata.IdpMetadataPublicKeyStore;
import uk.gov.ida.saml.security.CredentialFactorySignatureValidator;
import uk.gov.ida.saml.security.SamlMessageSignatureValidator;
import uk.gov.ida.saml.security.SigningCredentialFactory;
import uk.gov.ida.saml.security.validators.ValidatedResponse;
import uk.gov.ida.saml.security.validators.signature.SamlResponseSignatureValidator;

public class EidasValidatorFactory {

    private SamlResponseSignatureValidator samlResponseSignatureValidator;
    private EidasMetadataResolverRepository metadataResolverRepository;

    @Inject
    public EidasValidatorFactory(EidasMetadataResolverRepository metadataResolverRepository) {
        this.metadataResolverRepository = metadataResolverRepository;
    }

    public ValidatedResponse getValidatedResponse(Response response) {
        String entityId = response.getIssuer().getValue();
        samlResponseSignatureValidator = new SamlResponseSignatureValidator(getSamlMessageSignatureValidator(entityId));
        return samlResponseSignatureValidator.validate(response, IDPSSODescriptor.DEFAULT_ELEMENT_NAME);
    }

    private SamlMessageSignatureValidator getSamlMessageSignatureValidator(String entityId) {
        return new SamlMessageSignatureValidator(
                new CredentialFactorySignatureValidator(
                        new SigningCredentialFactory(
                                new AuthnResponseKeyStore(
                                        new IdpMetadataPublicKeyStore(metadataResolverRepository.getMetadataResolver(entityId))))));
    }
}