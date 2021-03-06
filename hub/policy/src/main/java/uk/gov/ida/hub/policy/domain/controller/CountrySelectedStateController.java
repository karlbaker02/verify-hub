package uk.gov.ida.hub.policy.domain.controller;

import org.joda.time.DateTime;
import uk.gov.ida.hub.policy.PolicyConfiguration;
import uk.gov.ida.hub.policy.contracts.EidasAttributeQueryRequestDto;
import uk.gov.ida.hub.policy.contracts.MatchingServiceConfigEntityDataDto;
import uk.gov.ida.hub.policy.domain.AssertionRestrictionsFactory;
import uk.gov.ida.hub.policy.domain.AuthnRequestFromHub;
import uk.gov.ida.hub.policy.domain.EidasCountryDto;
import uk.gov.ida.hub.policy.domain.InboundResponseFromCountry;
import uk.gov.ida.hub.policy.domain.LevelOfAssurance;
import uk.gov.ida.hub.policy.domain.PersistentId;
import uk.gov.ida.hub.policy.domain.ResponseFromHub;
import uk.gov.ida.hub.policy.domain.ResponseFromHubFactory;
import uk.gov.ida.hub.policy.domain.SessionId;
import uk.gov.ida.hub.policy.domain.State;
import uk.gov.ida.hub.policy.domain.StateController;
import uk.gov.ida.hub.policy.domain.StateTransitionAction;
import uk.gov.ida.hub.policy.domain.exception.StateProcessingValidationException;
import uk.gov.ida.hub.policy.domain.state.CountryAuthnFailedErrorState;
import uk.gov.ida.hub.policy.domain.state.CountrySelectedState;
import uk.gov.ida.hub.policy.domain.state.EidasCycle0And1MatchRequestSentState;
import uk.gov.ida.hub.policy.logging.HubEventLogger;
import uk.gov.ida.hub.policy.proxy.MatchingServiceConfigProxy;
import uk.gov.ida.hub.policy.proxy.TransactionsConfigProxy;

import java.util.Optional;

public class CountrySelectedStateController implements StateController, ErrorResponsePreparedStateController, CountrySelectingStateController, AuthnRequestCapableController {

    private final CountrySelectedState state;
    private final HubEventLogger hubEventLogger;
    private final StateTransitionAction stateTransitionAction;
    private final PolicyConfiguration policyConfiguration;
    private final TransactionsConfigProxy transactionsConfigProxy;
    private final MatchingServiceConfigProxy matchingServiceConfigProxy;
    private final ResponseFromHubFactory responseFromHubFactory;
    private final AssertionRestrictionsFactory assertionRestrictionFactory;

    public CountrySelectedStateController(
            final CountrySelectedState state,
            final HubEventLogger hubEventLogger,
            final StateTransitionAction stateTransitionAction,
            final PolicyConfiguration policyConfiguration,
            final TransactionsConfigProxy transactionsConfigProxy,
            final MatchingServiceConfigProxy matchingServiceConfigProxy,
            final ResponseFromHubFactory responseFromHubFactory,
            final AssertionRestrictionsFactory assertionRestrictionFactory) {
        this.state = state;
        this.hubEventLogger = hubEventLogger;
        this.stateTransitionAction = stateTransitionAction;
        this.policyConfiguration = policyConfiguration;
        this.transactionsConfigProxy = transactionsConfigProxy;
        this.matchingServiceConfigProxy = matchingServiceConfigProxy;
        this.responseFromHubFactory = responseFromHubFactory;
        this.assertionRestrictionFactory = assertionRestrictionFactory;
    }

    // TODO: Add to check if state has matching service entity id
    public String getMatchingServiceEntityId() {
        return transactionsConfigProxy.getMatchingServiceEntityId(state.getRequestIssuerEntityId());
    }

    public String getCountryEntityId() {
        return state.getCountryEntityId();
    }

    public AuthnRequestFromHub getRequestFromHub() {
        Optional<EidasCountryDto> countryDto = this.transactionsConfigProxy.getEidasSupportedCountries().stream()
                .filter(eidasCountryDto -> eidasCountryDto.getEntityId().equals(state.getCountryEntityId())).findFirst();

        AuthnRequestFromHub requestToSendFromHub = new AuthnRequestFromHub(
                state.getRequestId(),
                state.getLevelsOfAssurance(),
                false,
                state.getCountryEntityId(),
                com.google.common.base.Optional.of(true),
                state.getSessionExpiryTimestamp(),
                false,
                countryDto.map(EidasCountryDto::getOverriddenSsoUrl).orElse(null));

        hubEventLogger.logRequestFromHub(state.getSessionId(), state.getRequestIssuerEntityId());
        return requestToSendFromHub;
    }

    public EidasAttributeQueryRequestDto getEidasAttributeQueryRequestDto(InboundResponseFromCountry translatedResponse) {
        validateSuccessfulResponse(translatedResponse);

        final String matchingServiceEntityId = getMatchingServiceEntityId();
        MatchingServiceConfigEntityDataDto matchingServiceConfig = matchingServiceConfigProxy.getMatchingService(matchingServiceEntityId);

        return new EidasAttributeQueryRequestDto(
                state.getRequestId(),
                state.getRequestIssuerEntityId(),
                state.getAssertionConsumerServiceUri(),
                assertionRestrictionFactory.getAssertionExpiry(),
                matchingServiceEntityId,
                matchingServiceConfig.getUri(),
                DateTime.now().plus(policyConfiguration.getMatchingServiceResponseWaitPeriod()),
                matchingServiceConfig.isOnboarding(),
                translatedResponse.getLevelOfAssurance().get(),
                new PersistentId(translatedResponse.getPersistentId().get()),
                com.google.common.base.Optional.absent(),
                com.google.common.base.Optional.absent(),
                translatedResponse.getEncryptedIdentityAssertionBlob().get()
        );
    }

    @Override
    public ResponseFromHub getErrorResponse() {
        return responseFromHubFactory.createNoAuthnContextResponseFromHub(
                state.getRequestId(),
                state.getRelayState(),
                state.getRequestIssuerEntityId(),
                state.getAssertionConsumerServiceUri());
    }

    @Override
    public void selectCountry(String countryEntityId) {
        CountrySelectedState countrySelectedState = new CountrySelectedState(
                countryEntityId,
                state.getRelayState(),
                state.getRequestId(),
                state.getRequestIssuerEntityId(),
                state.getSessionExpiryTimestamp(),
                state.getAssertionConsumerServiceUri(),
                state.getSessionId(),
                state.getTransactionSupportsEidas(),
                state.getLevelsOfAssurance()
        );

        stateTransitionAction.transitionTo(countrySelectedState);
        hubEventLogger.logCountrySelectedEvent(countrySelectedState);
    }

    public void handleSuccessResponseFromCountry(InboundResponseFromCountry translatedResponse, String principalIpAddressAsSeenByHub) {
        validateSuccessfulResponse(translatedResponse);

        hubEventLogger.logIdpAuthnSucceededEvent(
                state.getSessionId(),
                state.getSessionExpiryTimestamp(),
                state.getCountryEntityId(),
                state.getRequestIssuerEntityId(),
                new PersistentId(translatedResponse.getPersistentId().get()),
                state.getRequestId(),
                state.getLevelsOfAssurance().get(0),
                state.getLevelsOfAssurance().get(state.getLevelsOfAssurance().size() - 1),
                translatedResponse.getLevelOfAssurance().get(),
                com.google.common.base.Optional.absent(),
                principalIpAddressAsSeenByHub);

        stateTransitionAction.transitionTo(createEidasCycle0And1MatchRequestSentState(translatedResponse));
    }

    public void handleAuthenticationFailedResponseFromCountry(String principalIpAddressAsSeenByHub) {
        hubEventLogger.logIdpAuthnFailedEvent(
                state.getSessionId(),
                state.getRequestIssuerEntityId(),
                state.getSessionExpiryTimestamp(),
                state.getRequestId(),
                principalIpAddressAsSeenByHub);

        stateTransitionAction.transitionTo(createCountryAuthnFailedErrorState());
    }

    private void validateSuccessfulResponse(InboundResponseFromCountry translatedResponse) {
        if (!translatedResponse.getPersistentId().isPresent()) {
            throw StateProcessingValidationException.missingMandatoryAttribute(state.getRequestId(), "persistentId");
        }

        if (!translatedResponse.getEncryptedIdentityAssertionBlob().isPresent()) {
            throw StateProcessingValidationException.missingMandatoryAttribute(state.getRequestId(), "encryptedIdentityAssertionBlob");
        }

        if (!translatedResponse.getLevelOfAssurance().isPresent()) {
            throw StateProcessingValidationException.noLevelOfAssurance();
        }
        else {
            validateLevelOfAssurance(translatedResponse.getLevelOfAssurance().get());
        }
    }

    private void validateLevelOfAssurance(LevelOfAssurance loa) {
        if (!state.getLevelsOfAssurance().contains(loa)) {
            throw StateProcessingValidationException.wrongLevelOfAssurance(Optional.ofNullable(loa), state.getLevelsOfAssurance());
        }
    }

    private State createEidasCycle0And1MatchRequestSentState(final InboundResponseFromCountry translatedResponse) {
        return new EidasCycle0And1MatchRequestSentState(
                state.getRequestId(),
                state.getRequestIssuerEntityId(),
                state.getSessionExpiryTimestamp(),
                state.getAssertionConsumerServiceUri(),
                new SessionId(state.getSessionId().getSessionId()),
                state.getTransactionSupportsEidas(),
                translatedResponse.getIssuer(),
                state.getRelayState().orNull(),
                translatedResponse.getLevelOfAssurance().get(),
                getMatchingServiceEntityId(),
                translatedResponse.getEncryptedIdentityAssertionBlob().get(),
                new PersistentId(translatedResponse.getPersistentId().get())
        );
    }

    private State createCountryAuthnFailedErrorState() {
        return new CountryAuthnFailedErrorState(
                state.getRequestId(),
                state.getRequestIssuerEntityId(),
                state.getSessionExpiryTimestamp(),
                state.getAssertionConsumerServiceUri(),
                state.getRelayState().orNull(),
                state.getSessionId(),
                state.getCountryEntityId(),
                state.getLevelsOfAssurance());
    }
}
