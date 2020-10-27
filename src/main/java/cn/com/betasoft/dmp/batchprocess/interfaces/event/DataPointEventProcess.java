package cn.com.betasoft.dmp.batchprocess.interfaces.event;

import cn.com.betasoft.dmp.batchprocess.application.internal.commandservices.DataPointCommandService;
import cn.com.betasoft.dmp.batchprocess.domain.event.DataPointEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;
import reactor.core.publisher.BufferOverflowStrategy;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import javax.annotation.PostConstruct;

@Component
@Slf4j
public class DataPointEventProcess {

    ApplicationContext applicationContext;

    DataPointCommandService dataPointCommandService;

    @Value("${dataPoint.buffer.size}")
    private int dataPointBufferSize;

    public DataPointEventProcess(ApplicationContext applicationContext,
                                 DataPointCommandService dataPointCommandService) {
        this.applicationContext = applicationContext;
        this.dataPointCommandService = dataPointCommandService;
    }

    @PostConstruct
    public void start() {
        Flux<DataPointEvent> bridge = Flux.create(fluxSink -> {
            ConfigurableApplicationContext context = (ConfigurableApplicationContext) applicationContext;
            // void onApplicationEvent(E event);
            context.addApplicationListener((ApplicationListener<DataPointEvent>) dataPointEvent -> {
                log.info("event source: " + Thread.currentThread().getName());
                fluxSink.next(dataPointEvent);
            });
        });
        // -Dreactor.bufferSize.small=20 设置队列的长度，默认256
        // maxSize不是队列的长度，是reactor把数据发送给下游前缓存的数据量
        bridge.onBackpressureBuffer(dataPointBufferSize, dataPointEvent -> {
                    log.info("discard dataPoint: {}.", dataPointEvent.getDataPoint());
                },
                BufferOverflowStrategy.DROP_OLDEST)
                .publishOn(Schedulers.newParallel("dataPointSave", 2))
                .doOnEach(dataPointEvent -> log.info("map: " + Thread.currentThread().getName()))
                .flatMap(dataPointEvent -> dataPointCommandService.saveDataPoint(dataPointEvent.getDataPoint()))
                .subscribe();
    }

}
