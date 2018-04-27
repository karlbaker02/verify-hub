package uk.gov.ida.hub.config.application;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.gov.ida.hub.config.data.ConfigEntityDataRepository;
import uk.gov.ida.hub.config.domain.MatchingServiceConfigEntityData;
import uk.gov.ida.hub.config.domain.TransactionConfigEntityData;

import java.net.URI;
import java.util.*;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static uk.gov.ida.hub.config.domain.builders.MatchingServiceConfigEntityDataBuilder.aMatchingServiceConfigEntityData;
import static uk.gov.ida.hub.config.domain.builders.TransactionConfigEntityDataBuilder.aTransactionConfigData;

@RunWith(MockitoJUnitRunner.class)
public class MatchingServiceTest {
    public static final String MATCHING_SERVICE_CONFIG_ENTITY_ID = "http://www.some-rp-ms.gov.uk";
    public static final String ANOTHER_MATCHING_SERVICE_CONFIG_ENTITY_ID = "http://www.some-rp-ms2.gov.uk";
    private static final String TRANSACTION_ENTITY_ID = "http://www.transaction.gov.uk/SAML2/MD";
    private static final String TRANSACTION_ENTITY_ID_2 = "http://www.transaction2.gov.uk/SAML2/MD";
    private static final String MATCHING_SERVICE_ENTITY_ID= "a-matching-service-entity-id";
    private static final String MATCHING_SERVICE_ENTITY_ID_2= "another-matching-service-entity-id";

    private MatchingService matchingService;

    @Before
    public void initialise() {
        matchingService = new MatchingService(transactionConfigEntityDataRepository, matchingServiceConfigEntityDataRepository);
    }

    @Mock
    private ConfigEntityDataRepository<TransactionConfigEntityData> transactionConfigEntityDataRepository;

    @Mock
    private ConfigEntityDataRepository<MatchingServiceConfigEntityData> matchingServiceConfigEntityDataRepository;

    @Test
    public void matchingServiceFound_WhenMatchingServiceExistsForTransactionEntityId() {
        MatchingServiceConfigEntityData matchingServiceConfigEntity = aMatchingServiceConfigEntityData()
                .withEntityId("http://www.some-rp-ms.gov.uk")
                .build();
        when(matchingServiceConfigEntityDataRepository.getData(TRANSACTION_ENTITY_ID)).thenReturn(Optional.of(matchingServiceConfigEntity));

        MatchingService.MatchingServiceTransaction expectedMatchingServiceTransaction =
                matchingService.new MatchingServiceTransaction(TRANSACTION_ENTITY_ID, matchingServiceConfigEntity);
        assertThat(matchingService.getMatchingService(TRANSACTION_ENTITY_ID)).isEqualTo(expectedMatchingServiceTransaction);
    }

    @Test(expected = NoSuchElementException.class)
    public void exceptionThrown_WhenMatchingServiceDoesNotExistsForTransactionEntityId() {
        when(matchingServiceConfigEntityDataRepository.getData(TRANSACTION_ENTITY_ID)).thenReturn(Optional.empty());

        matchingService.getMatchingService(TRANSACTION_ENTITY_ID);
    }

    @Test
    public void emptyListReturned_WhenNoTransactionConfigEntitiesExist() {
        when(transactionConfigEntityDataRepository.getAllData()).thenReturn(new HashSet<>());

        Collection<MatchingService.MatchingServiceTransaction> matchingServices = matchingService.getMatchingServices();
        assertThat(matchingServices.isEmpty()).isTrue();
    }

    @Test(expected = NoSuchElementException.class)
    public void exceptionThrown_WhenNoMatchingServiceConfigEntitiesExist() {
        TransactionConfigEntityData transactionConfigEntityData = aTransactionConfigData().build();
        when(transactionConfigEntityDataRepository.getAllData()).thenReturn(new HashSet<TransactionConfigEntityData>(){{
            add(transactionConfigEntityData);
        }});

        when(matchingServiceConfigEntityDataRepository.getData(transactionConfigEntityData.getMatchingServiceEntityId())).thenReturn(Optional.empty());

        matchingService.getMatchingServices();
    }

    @Test
    public void singleMatchingServiceReturned_WhenOnlyOneTransactionExists() {
        TransactionConfigEntityData transactionConfigEntityData = aTransactionConfigData()
                .withEntityId(TRANSACTION_ENTITY_ID)
                .withMatchingServiceEntityId(MATCHING_SERVICE_ENTITY_ID)
                .build();
        when(transactionConfigEntityDataRepository.getAllData()).thenReturn(new HashSet<TransactionConfigEntityData>(){{
            add(transactionConfigEntityData);
        }});

        MatchingServiceConfigEntityData matchingServiceConfigEntity = aMatchingServiceConfigEntityData()
                .withEntityId("http://www.some-rp-ms.gov.uk")
                .build();
        when(matchingServiceConfigEntityDataRepository.getData(MATCHING_SERVICE_ENTITY_ID))
                .thenReturn(Optional.of(matchingServiceConfigEntity));

        MatchingService.MatchingServiceTransaction expectedMatchingServiceTransaction =
                matchingService.new MatchingServiceTransaction(TRANSACTION_ENTITY_ID, matchingServiceConfigEntity);
        assertThat(matchingService.getMatchingServices())
                .hasSize(1)
                .contains(expectedMatchingServiceTransaction, expectedMatchingServiceTransaction);
    }

    @Test
    public void multipleMatchingServicesReturned_WhenMultipleTransactionsExists() {
        TransactionConfigEntityData transactionConfigEntityData = aTransactionConfigData()
                .withEntityId(TRANSACTION_ENTITY_ID)
                .withMatchingServiceEntityId(MATCHING_SERVICE_ENTITY_ID)
                .build();
        TransactionConfigEntityData transactionConfigEntityData2 = aTransactionConfigData()
                .withEntityId(TRANSACTION_ENTITY_ID_2)
                .withMatchingServiceEntityId(MATCHING_SERVICE_ENTITY_ID_2)
                .build();
        when(transactionConfigEntityDataRepository.getAllData()).thenReturn(new HashSet<TransactionConfigEntityData>(){{
            add(transactionConfigEntityData);
            add(transactionConfigEntityData2);
        }});

        MatchingServiceConfigEntityData matchingServiceConfigEntity = aMatchingServiceConfigEntityData()
                .withEntityId(MATCHING_SERVICE_CONFIG_ENTITY_ID)
                .build();
        MatchingServiceConfigEntityData matchingServiceConfigEntity2 = aMatchingServiceConfigEntityData()
                .withEntityId(ANOTHER_MATCHING_SERVICE_CONFIG_ENTITY_ID)
                .build();
        when(matchingServiceConfigEntityDataRepository.getData(MATCHING_SERVICE_ENTITY_ID))
                .thenReturn(Optional.of(matchingServiceConfigEntity));
        when(matchingServiceConfigEntityDataRepository.getData(MATCHING_SERVICE_ENTITY_ID_2))
                .thenReturn(Optional.of(matchingServiceConfigEntity2));

        MatchingService.MatchingServiceTransaction expectedMatchingServiceTransaction =
                matchingService.new MatchingServiceTransaction(TRANSACTION_ENTITY_ID, matchingServiceConfigEntity);
        MatchingService.MatchingServiceTransaction otherExpectedMatchingServiceTransaction =
                matchingService.new MatchingServiceTransaction(TRANSACTION_ENTITY_ID_2, matchingServiceConfigEntity2);
        assertThat(matchingService.getMatchingServices())
                .hasSize(2)
                .contains(expectedMatchingServiceTransaction, otherExpectedMatchingServiceTransaction);
    }
}
