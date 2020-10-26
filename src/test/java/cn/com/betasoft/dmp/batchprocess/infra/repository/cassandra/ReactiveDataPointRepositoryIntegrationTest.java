package cn.com.betasoft.dmp.batchprocess.infra.repository.cassandra;

import cn.com.betasoft.dmp.batchprocess.domain.model.DataPoint;
import cn.com.betasoft.dmp.batchprocess.domain.model.DataPointKey;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootTest
public class ReactiveDataPointRepositoryIntegrationTest {

    @Autowired
    DataPointRepository dataPointRepository;

    @Test
    public void testAddDataPoint() {
        DataPointKey dataPointKey = DataPointKey.builder()
                .metric("Windows:CPU_LOAD")
                .day(LocalDate.now())
                .offset(LocalTime.now())
                .build();
        Map<String, String> tags = Stream.of(new String[][]{
                {"moc", "Windows"},
                {"mo", "windows:uuid=1123344,domain=defaultEngine"}
        }).collect(Collectors.toMap(data -> data[0], data -> data[1]));
        DataPoint dataPoint = DataPoint.builder()
                .dataPointKey(dataPointKey)
                .tags(tags)
                .value(ThreadLocalRandom.current().nextDouble(100))
                .ttl(3600)
                .build();

        Mono<DataPoint> dataPointInsert = dataPointRepository
                .insert(dataPoint, dataPoint.getTtl());

        StepVerifier
                .create(dataPointInsert)
                .expectNextCount(1)
                .verifyComplete();
    }
}
