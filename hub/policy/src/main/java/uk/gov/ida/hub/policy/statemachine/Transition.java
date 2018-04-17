package uk.gov.ida.hub.policy.statemachine;

public class Transition {
    private final StateTNG fromState;
    private final StateTNG toState;

    public Transition(StateTNG fromState, StateTNG toState){
        this.fromState = fromState;
        this.toState = toState;
    }

    public StateTNG from() {
        return fromState;
    }

    public StateTNG to() {
        return toState;
    }
}
