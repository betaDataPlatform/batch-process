package cn.com.betasoft.dmp.batchprocess.infra.repository.cassandra;

import cn.com.betasoft.dmp.batchprocess.domain.model.DataPoint;
import reactor.core.publisher.Flux;

public interface CustomizeDataPointRepository {

    Flux<DataPoint> find(Flux<String> sqlFlux);
}
