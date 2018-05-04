package uk.gov.ida.hub.policy.statemachine.eventhandler;

import uk.gov.ida.hub.policy.domain.SessionId;
import uk.gov.ida.hub.policy.domain.SessionRepository;
import uk.gov.ida.hub.policy.logging.HubEventLogger;
import uk.gov.ida.hub.policy.statemachine.Event;
import uk.gov.ida.hub.policy.statemachine.Session;

public class IdpSelectedEventHandler implements StateMachineEventHandler {
    private final HubEventLogger hubEventLogger;
    private final String selectedIdpEntityId;

//    @Inject
//    public IdpSelectedEventHandler(SessionRepository sessionRepository, HubEventLogger hubEventLogger){
//        this.sessionRepository = sessionRepository;
//        this.hubEventLogger = hubEventLogger;
//    }

    public IdpSelectedEventHandler(SessionRepository sessionRepository, HubEventLogger hubEventLogger, SessionId sessionId, String selectedIdpEntityId){
        this.hubEventLogger = hubEventLogger;
        this.selectedIdpEntityId = selectedIdpEntityId;
    }

    @Override
    public void handleEvent(Event event, Session session) {
        session.setIdpEntityId(selectedIdpEntityId);
    }

}
