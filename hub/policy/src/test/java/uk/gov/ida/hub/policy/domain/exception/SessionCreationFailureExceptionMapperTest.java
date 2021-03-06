package uk.gov.ida.hub.policy.domain.exception;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.slf4j.event.Level;
import uk.gov.ida.common.ErrorStatusDto;
import uk.gov.ida.common.ExceptionType;
import uk.gov.ida.hub.policy.Urls;
import uk.gov.ida.hub.policy.builder.domain.SessionIdBuilder;
import uk.gov.ida.hub.policy.domain.SessionId;
import uk.gov.ida.hub.policy.logging.HubEventLogger;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SessionCreationFailureExceptionMapperTest {

    private static final SessionId SESSION_ID = SessionIdBuilder.aSessionId().build();
    private static final String MESSAGE = "The session does not exist.";

    @Mock
    private HubEventLogger hubEventLogger;

    @Mock
    private HttpServletRequest servletRequest;

    private SessionCreationFailureExceptionMapper mapper;

    @Before
    public void setUp() {
        when(servletRequest.getParameter(Urls.SharedUrls.SESSION_ID_PARAM)).thenReturn(SESSION_ID.getSessionId());

        mapper = new SessionCreationFailureExceptionMapper(hubEventLogger);
        mapper.setHttpServletRequest(servletRequest);
    }

    @Test
    public void toResponse_shouldLogToAudit() {
        SessionCreationFailureException exception = new SessionCreationFailureException(MESSAGE, Level.ERROR, ExceptionType.DUPLICATE_SESSION);

        final Response response = mapper.toResponse(exception);

        verify(hubEventLogger).logErrorEvent(any(UUID.class), eq(SESSION_ID), eq(MESSAGE));
        final ErrorStatusDto errorStatusDto = (ErrorStatusDto) response.getEntity();
        assertThat(errorStatusDto.isAudited()).isEqualTo(true);
        assertThat(errorStatusDto.getExceptionType()).isEqualTo(ExceptionType.DUPLICATE_SESSION);
    }
}
