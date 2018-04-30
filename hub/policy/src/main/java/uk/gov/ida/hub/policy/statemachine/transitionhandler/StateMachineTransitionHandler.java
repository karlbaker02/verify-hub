package uk.gov.ida.hub.policy.statemachine.transitionhandler;

import uk.gov.ida.hub.policy.domain.SessionRepository;
import uk.gov.ida.hub.policy.statemachine.Session;
import uk.gov.ida.hub.policy.statemachine.Transition;

public class StateMachineTransitionHandler {

    private Session session;
    private SessionRepository sessionRepository;
    private Transition transition;

    public StateMachineTransitionHandler(){
    }

    public void handleTransition(Transition transition, Session session) {
    }
}
