package uk.gov.ida.hub.policy.statemachine;

import java.util.Objects;

public class Transition {

    private final StateTNG fromState;
    private final StateTNG toState;

    public static Transition of(StateTNG fromState, StateTNG toState){
        return new Transition(fromState, toState);
    }

    private Transition(StateTNG fromState, StateTNG toState){
        this.fromState = fromState;
        this.toState = toState;
    }

    public StateTNG from() {
        return fromState;
    }

    public StateTNG to() {
        return toState;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transition that = (Transition) o;
        return fromState == that.fromState &&
                toState == that.toState;
    }

    @Override
    public int hashCode() {
        return Objects.hash(fromState, toState);
    }
}
