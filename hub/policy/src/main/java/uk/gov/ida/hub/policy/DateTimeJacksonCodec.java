package uk.gov.ida.hub.policy;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import org.joda.time.DateTime;
import org.redisson.client.codec.JsonJacksonMapCodec;
import uk.gov.ida.hub.policy.domain.SessionId;

public class DateTimeJacksonCodec extends JsonJacksonMapCodec {
    public DateTimeJacksonCodec() {
        super(SessionId.class, DateTime.class);
    }

    @Override
    protected void init(ObjectMapper objectMapper) {
        objectMapper.registerModule(new JodaModule());
        super.init(objectMapper);
    }
}
