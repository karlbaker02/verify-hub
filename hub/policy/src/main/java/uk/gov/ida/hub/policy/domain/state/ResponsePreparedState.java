package uk.gov.ida.hub.policy.domain.state;

import java.util.Optional;
import uk.gov.ida.hub.policy.domain.State;

public interface ResponsePreparedState extends State {
    Optional<String> getRelayState();
}
