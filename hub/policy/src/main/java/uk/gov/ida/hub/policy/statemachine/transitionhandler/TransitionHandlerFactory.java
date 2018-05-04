package uk.gov.ida.hub.policy.statemachine.transitionhandler;

import uk.gov.ida.hub.policy.statemachine.StateTNG;
import uk.gov.ida.hub.policy.statemachine.Transition;

import java.util.HashMap;
import java.util.Map;

public class TransitionHandlerFactory {

    private static Map<Transition, Class<StateMachineTransitionHandler>> transitionHandlers = new HashMap<>();

    // Register TransitionHandlers.
    {
        transitionHandlers.put(Transition.of(StateTNG.Session_Started, StateTNG.Idp_Selected), StateMachineTransitionHandler.class);
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
