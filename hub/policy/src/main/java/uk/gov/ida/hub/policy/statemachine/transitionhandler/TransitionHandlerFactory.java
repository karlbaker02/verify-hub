package uk.gov.ida.hub.policy.statemachine.transitionhandler;

import uk.gov.ida.hub.policy.statemachine.Event;
import uk.gov.ida.hub.policy.statemachine.StateMachine;
import uk.gov.ida.hub.policy.statemachine.StateTNG;
import uk.gov.ida.hub.policy.statemachine.Transition;

import java.util.HashMap;
import java.util.Map;

public class TransitionHandlerFactory {

    private static Map<Transition, Class<StateMachineTransitionHandler>> transitionHandlers = new HashMap<>();

    // Register TransitionHandlers.
    {
        StateMachine sm = new StateMachine();
        transitionHandlers.put(sm.getTransition(StateTNG.Session_Started, Event.Country_Selected), StateMachineTransitionHandler.class);
    }


    public static StateMachineTransitionHandler getTransitionHandler(Transition transition) {
        try {
            return transitionHandlers.get(transition).newInstance();
        } catch (InstantiationException e) {
            throw new TransitionHandlerNotFoundException(e);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        throw new TransitionHandlerNotFoundException();
    }

}
