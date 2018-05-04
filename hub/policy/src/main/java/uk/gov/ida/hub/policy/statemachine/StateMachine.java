package uk.gov.ida.hub.policy.statemachine;

import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

public class StateMachine {

    private StateTNG currentState;

    /**
     * Gets a collection of all the valid transitions in the StateMachine.
     *
     * The StateMachine will never return a Transition that is not in this collection.
     *
     */
    public static Set<Transition> getTransitions(){
        Set<Transition> transitions = new HashSet<>();
        for(StateTNG from : StateTNG.values()) {
            for (Event event : Event.values()) {
                StateTNG to = getStateForEvent(from, event);
                if (to != null){
                    transitions.add(Transition.of(from, to));
                }
            }
        }

        return transitions;
    }

    private static StateTNG getStateForEvent(StateTNG currentState, Event event){
        switch (currentState){

            case Authn_Failed_Error:
                switch(event){
                    case Idp_Selected: return StateTNG.Idp_Selected;
                    case Try_Another_Idp_Selected: return StateTNG.Session_Started;
                    case Session_Time_Out_Triggered: return StateTNG.Session_Timed_Out;
                }
                break;
            case Awaiting_Cycle3_Data:
                switch(event){
                    case Cancellation_Received: return StateTNG.Cycle3_Data_Input_Cancelled;
                    case Cycle3_Data_Submitted: return StateTNG.Cycle3_Match_Request_Sent;
                    case Session_Time_Out_Triggered: return StateTNG.Session_Timed_Out;
                }
                break;
            case Country_Selected:
                switch(event){
                    case Country_Selected: return StateTNG.Country_Selected;
                    case Transition_To_Eidas_Cycle0_And1_Match_Request_Sent_State: return StateTNG.Eidas_Cycle0_And1_Match_Request_Sent;
                    case Session_Time_Out_Triggered: return StateTNG.Session_Timed_Out;
                }
                break;
            case Cycle0_And1_Match_Request_Sent:
                switch(event){
                    case No_Match_Response_From_Matching_Service: return StateTNG.Awaiting_Cycle3_Data;
                    case Response_Processing_Details_Received: return StateTNG.Matching_Service_Request_Error;
                    case Request_Failure: return StateTNG.Matching_Service_Request_Error;
//                    case No_Match_Response_From_Matching_Service: return StateTNG.NULL;
                    case User_Account_Created_Response_From_Matching_Service: return StateTNG.NULL;
                    case User_Account_Creation_Failed_Response_From_Matching_Service: return StateTNG.NULL;
                    case Match_Response_From_Matching_Service: return StateTNG.Successful_Match;
//                    case No_Match_Response_From_Matching_Service: return StateTNG.User_Account_Ceation_Request_Sent;
                    case Session_Time_Out_Triggered: return StateTNG.Session_Timed_Out;
                }
                break;
            case Cycle3_Data_Input_Cancelled:
                switch(event){

                }
                break;
            case Cycle3_Match_Request_Sent:
                switch(event){
                    case Response_Processing_Details_Received: return StateTNG.Matching_Service_Request_Error;
                    case Cancellation_Received: return StateTNG.Cycle3_Data_Input_Cancelled;
                    case Request_Failure: return StateTNG.Matching_Service_Request_Error;
                    case User_Account_Created_Response_From_Matching_Service: return StateTNG.NULL;
                    case User_Account_Creation_Failed_Response_From_Matching_Service: return StateTNG.NULL;
                    case Match_Response_From_Matching_Service: return StateTNG.Successful_Match;
                    case No_Match_Response_From_Matching_Service: return StateTNG.Successful_Match;
                    case Session_Time_Out_Triggered: return StateTNG.Session_Timed_Out;
                }
                break;
            case Eidas_Awaiting_Cycle3_Data:
                switch(event){
                    case Cycle3_Data_Submitted: return StateTNG.Eidas_Cycle3_Match_Request_Sent;
                }
                break;
            case Eidas_Cycle0_And1_Match_Request_Sent:
                switch(event){
                    case Response_Processing_Details_Received: return StateTNG.Matching_Service_Request_Error;
                    case No_Match_Response_From_Matching_Service: return StateTNG.Eidas_Awaiting_Cycle3_Data;
                    case Match_Response_From_Matching_Service: return StateTNG.Eidas_Successful_Match;
                    case Request_Failure: return StateTNG.Matching_Service_Request_Error;
//                    case No_Match_Response_From_Matching_Service: return StateTNG.No_Match;
                    case User_Account_Created_Response_From_Matching_Service: return StateTNG.NULL;
                    case User_Account_Creation_Failed_Response_From_Matching_Service: return StateTNG.NULL;
                    case Session_Time_Out_Triggered: return StateTNG.Session_Timed_Out;
                }
                break;
            case Eidas_Cycle3_Match_Request_Sent:
                switch(event){
                }
                break;
            case Eidas_Successful_Match:
                switch(event){
                }
                break;
            case Fraud_Event_Detected:
                switch(event){
                }
                break;
            case Idp_Selected:
                switch(event){
                    case Authentication_Failed_Response_From_Idp: return StateTNG.Authn_Failed_Error;
                    case No_Authentication_Context_Response_From_Idp: return StateTNG.Authn_Failed_Error;
                    case Success_Response_From_Idp_Received: return StateTNG.Cycle0_And1_Match_Request_Sent;
                    case Fraud_Response_From_Idp: return StateTNG.Fraud_Event_Detected;
                    case Idp_Selected: return StateTNG.Idp_Selected;
                    case Paused_Registration_Response_From_Idp: return StateTNG.Paused_Registration;
                    case Requester_Error_Response_From_Idp: return StateTNG.Requester_Error;
//                    case No_Authentication_Context_Response_From_Idp: return StateTNG.Session_Started;
                    case Session_Time_Out_Triggered: return StateTNG.Session_Timed_Out;
                }
                break;
            case Matching_Service_Request_Error:
                switch(event){
                }
                break;
            case No_Match:
                switch(event){
                }
                break;
            case Paused_Registration:
                switch(event){
                }
                break;
            case Requester_Error:
                switch(event){
                    case Idp_Selected: return StateTNG.Idp_Selected;
                    case Session_Time_Out_Triggered: return StateTNG.Session_Timed_Out;
                }
                break;
            case Session_Started:
                switch(event){
                    case Idp_Selected: return StateTNG.Idp_Selected;
                    case Idp_Selected_For_Registration: return StateTNG.Registering_With_Idp;
                    case Country_Selected: return StateTNG.Country_Selected;
                    case Session_Time_Out_Triggered: return StateTNG.Session_Timed_Out;
                }
                break;
            case Successful_Match:
                switch(event){
                }
                break;
            case User_Account_Created:
                switch(event){
                }
                break;
            case User_Account_Creation_Failed:
                switch(event){
                }
                break;
            case User_Account_Creation_Request_Sent:
                switch(event){
                    case Response_Processing_Details_Received: return StateTNG.Matching_Service_Request_Error;
                    case Request_Failure: return StateTNG.Matching_Service_Request_Error;
                    case Match_Response_From_Matching_Service: return StateTNG.NULL;
                    case No_Match_Response_From_Matching_Service: return StateTNG.NULL;
                    case User_Account_Created_Response_From_Matching_Service: return StateTNG.User_Account_Created;
                    case User_Account_Creation_Failed_Response_From_Matching_Service: return StateTNG.User_Account_Creation_Failed;
                    case Session_Time_Out_Triggered: return StateTNG.Session_Timed_Out;
                }
                break;
        }
        return null;
    }

    public StateMachine(StateTNG initialState){
        this.currentState = initialState;
    }

    public StateTNG getCurrentState(){
        return currentState;
    }

    public StateTNG peekNextState(@NotNull Event event){
        StateTNG nextState = getStateForEvent(currentState, event);
        if (nextState == null){
            throw new InvalidStateException("Current State "+currentState+" cannot accept event "+event);
        }
        return nextState;
    }

    public Transition transition(Event event){
        StateTNG startState = this.currentState;
        this.currentState = peekNextState(event);
        return Transition.of(startState, this.currentState);
    }

}
