package uk.gov.ida.hub.policy.statemachine.eventhandler;

import uk.gov.ida.hub.policy.domain.SessionId;
import uk.gov.ida.hub.policy.domain.SessionRepository;
import uk.gov.ida.hub.policy.logging.HubEventLogger;
import uk.gov.ida.hub.policy.statemachine.Session;

public class IdpSelectedEventHandler extends StateMachineEventHandler {
    private final HubEventLogger hubEventLogger;
    private final String selectedIdpEntityId;

//    @Inject
//    public IdpSelectedEventHandler(SessionRepository sessionRepository, HubEventLogger hubEventLogger){
//        this.sessionRepository = sessionRepository;
//        this.hubEventLogger = hubEventLogger;
//    }

    public IdpSelectedEventHandler(SessionRepository sessionRepository, HubEventLogger hubEventLogger, SessionId sessionId, String selectedIdpEntityId){
        super(sessionRepository, sessionId);
        this.hubEventLogger = hubEventLogger;
        this.selectedIdpEntityId = selectedIdpEntityId;
    }

    @Override
    public void delegatedEventHandling(Session session) {

        session.setIdpEntityId(selectedIdpEntityId);
    }

}
