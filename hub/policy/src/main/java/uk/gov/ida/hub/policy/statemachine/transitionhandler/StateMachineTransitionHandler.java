package uk.gov.ida.hub.policy.statemachine.transitionhandler;

import uk.gov.ida.hub.policy.domain.SessionRepository;
import uk.gov.ida.hub.policy.statemachine.Session;
import uk.gov.ida.hub.policy.statemachine.StateTNG;

public abstract class StateMachineTransitionHandler {

    private Session session;
    private SessionRepository sessionRepository;
    private StateTNG startState;
    private StateTNG endState;

    public StateMachineTransitionHandler(){

    }

    public void transition() {
    }
}
