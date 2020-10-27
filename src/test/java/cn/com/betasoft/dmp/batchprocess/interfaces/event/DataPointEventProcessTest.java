package cn.com.betasoft.dmp.batchprocess.interfaces.event;

import cn.com.betasoft.dmp.batchprocess.domain.event.DataPointEvent;
import cn.com.betasoft.dmp.batchprocess.domain.model.DataPoint;
import cn.com.betasoft.dmp.batchprocess.domain.model.DataPointKey;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationEventPublisher;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootTest
public class DataPointEventProcessTest {

    @Autowired
    ApplicationEventPublisher applicationEventPublisher;

    @Test
    public void testSendDataPointEvent() throws InterruptedException {
        DataPoint dataPoint1 = createDataPoint("CPU_LOAD", "Windows", "024fb79d16e346a4bbb35dcefdd88949");
        DataPoint dataPoint2 = createDataPoint("MEM_LOAD", "Windows", "024fb79d16e346a4bbb35dcefdd88949");

        applicationEventPublisher.publishEvent(new DataPointEvent(dataPoint1));
        applicationEventPublisher.publishEvent(new DataPointEvent(dataPoint2));
        Thread.sleep(5000);
    }

    private DataPoint createDataPoint(String metric, String moType, String uuid) {
        String moPath = String.format("%s.domain=\"%s\",uuid=\"%s\"", moType, "defaultEngine", uuid);
        DataPointKey dataPointKey = DataPointKey.builder()
                .metric(metric)
                .sourceId(moPath)
                .day(LocalDate.now())
                .offset(LocalTime.now())
                .build();
        Map<String, String> tags = Stream.of(new String[][]{
                {"moc", moType},
                {"mo", moPath}
        }).collect(Collectors.toMap(data -> data[0], data -> data[1]));
        DataPoint dataPoint = DataPoint.builder()
                .dataPointKey(dataPointKey)
                .tags(tags)
                .value(ThreadLocalRandom.current().nextDouble(100))
                .ttl(3600)
                .build();
        return dataPoint;
    }
}
