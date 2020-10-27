package cn.com.betasoft.dmp.batchprocess.application.internal.commandservices;

import cn.com.betasoft.dmp.batchprocess.domain.model.DataPoint;
import cn.com.betasoft.dmp.batchprocess.infra.repository.cassandra.DataPointRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class DataPointCommandService {

    DataPointRepository dataPointRepository;

    public DataPointCommandService(DataPointRepository dataPointRepository) {
        this.dataPointRepository = dataPointRepository;
    }

    public Mono<DataPoint> saveDataPoint(DataPoint dataPoint) {
        return dataPointRepository.insert(dataPoint, dataPoint.getTtl());
    }
}
