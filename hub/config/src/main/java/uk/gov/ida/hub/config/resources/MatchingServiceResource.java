package uk.gov.ida.hub.config.resources;

import com.codahale.metrics.annotation.Timed;
import uk.gov.ida.hub.config.Urls;
import uk.gov.ida.hub.config.application.MatchingService;
import uk.gov.ida.hub.config.dto.MatchingServiceConfigEntityDataDto;
import uk.gov.ida.hub.config.exceptions.NoMatchingServiceException;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.net.URI;
import java.util.Collection;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Path(Urls.ConfigUrls.MATCHING_SERVICE_ROOT)
@Produces(MediaType.APPLICATION_JSON)
public class MatchingServiceResource {
    private final MatchingService matchingService;

    @Inject
    public MatchingServiceResource(MatchingService matchingService ) {
        this.matchingService = matchingService;
    }

    @GET
    @Path(Urls.ConfigUrls.MATCHING_SERVICE_PATH)
    @Timed
    public MatchingServiceConfigEntityDataDto getMatchingService(
            @PathParam(Urls.SharedUrls.ENTITY_ID_PARAM) String entityId) {
        MatchingService.MatchingServiceTransaction matchingServiceTransaction = matchingService.getMatchingService(entityId);

        return toMatchingServiceDto(matchingServiceTransaction);
    }

    @GET
    @Timed
    public Collection<MatchingServiceConfigEntityDataDto> getMatchingServices() {
        return matchingService.getMatchingServices().stream()
                .map(this::toMatchingServiceDto)
                .collect(toList());
    }

    private MatchingServiceConfigEntityDataDto toMatchingServiceDto(MatchingService.MatchingServiceTransaction matchingServiceTransaction ) {
        return new MatchingServiceConfigEntityDataDto(
                matchingServiceTransaction.getEntityId(),
                matchingServiceTransaction.getUri(),
                matchingServiceTransaction.getEntityId(),
                matchingServiceTransaction.getHealthCheckEnabled(),
                matchingServiceTransaction.isOnboarding(),
                matchingServiceTransaction.getUserAccountCreationUri());
    }
}
