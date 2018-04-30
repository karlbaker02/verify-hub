package uk.gov.ida.hub.policy.statemachine;

import uk.gov.ida.hub.policy.domain.SessionId;
import uk.gov.ida.hub.policy.domain.SessionRepository;
import uk.gov.ida.hub.policy.domain.exception.SessionNotFoundException;
import uk.gov.ida.hub.policy.exception.SessionTimeoutException;
import uk.gov.ida.hub.policy.statemachine.eventhandler.StateMachineEventHandler;
import uk.gov.ida.hub.policy.statemachine.transitionhandler.StateMachineTransitionHandler;
import uk.gov.ida.hub.policy.statemachine.transitionhandler.TransitionHandlerFactory;

import static java.text.MessageFormat.format;

public final class StateMachineCoordinator {

    private Session session;
    private SessionRepository sessionRepository;
    private StateMachineEventHandler eventHandler;
    private StateMachineTransitionHandler transitionHandler;
    private StateTNG startState;
    private StateTNG endState;
    private Event event;
    private StateMachine stateMachine;

    public StateMachineCoordinator(SessionRepository sessionRepository, SessionId sessionId){
        this.sessionRepository = sessionRepository;
        session = sessionRepository.getSession(sessionId);
        if (session == null){
            throw new SessionNotFoundException(sessionId);
        }
        if (session.isTimedOut()){
            handleTimeOut();
        }
    }

    public void transition(){
        startState = session.getCurrentState();
        stateMachine = new StateMachine(startState);
        Transition transition = stateMachine.transition(event);
        StateMachineTransitionHandler transitionHandler = TransitionHandlerFactory.getTransitionHandler(transition);

        endState = stateMachine.transition(event).to();

        eventHandler.handleEvent(event, session);
        transitionHandler.handleTransition(transition, session);

        session.setCurrentState(endState);
        sessionRepository.updateSession(session);
    }

    public void handleTimeOut(){
        startState = session.getCurrentState();
        endState = stateMachine.transition(Event.Session_Time_Out_Triggered).to();

        session.setCurrentState(endState);
        sessionRepository.updateSession(session);
        SessionId sessionId = session.getSessionId();
        throw new SessionTimeoutException(format("Session {0} timed out.", sessionId.getSessionId()), sessionId, session.getRequestIssuerEntityId(), session.getSessionExpiryTimestamp(), session.getRequestId());
    }

    public void setEvent(Event event) {
        this.event = event;
    }
}
