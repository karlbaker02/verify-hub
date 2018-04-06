package uk.gov.ida.hub.policy.statemachine;

public enum StateTNG {
    Authn_Failed_Error,
    Awaiting_Cycle3_Data,
    Country_Selected,
    Cycle0_And1_Match_Request_Sent,
    Cycle3_Data_Input_Cancelled,
    Cycle3_Match_Request_Sent,
    Eidas_Awaiting_Cycle3_Data,
    Eidas_Cycle0_And1_Match_Request_Sent,
    Eidas_Cycle3_Match_Request_Sent,
    Eidas_Successful_Match,
    Fraud_Event_Detected,
    Idp_Selected,
    Matching_Service_Request_Error,
    No_Match,
    Paused_Registration,
    Registering_With_Idp,
    Requester_Error,
    Session_Started,
    Session_Timed_Out,
    Successful_Match,
    User_Account_Created,
    User_Account_Creation_Failed,
    User_Account_Creation_Request_Sent,
    NULL
}
