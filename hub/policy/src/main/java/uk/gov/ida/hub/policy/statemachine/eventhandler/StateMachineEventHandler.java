package uk.gov.ida.hub.policy.statemachine.eventhandler;

import uk.gov.ida.hub.policy.statemachine.Event;
import uk.gov.ida.hub.policy.statemachine.Session;

public interface StateMachineEventHandler {

    void handleEvent(Event event, Session session);

}
