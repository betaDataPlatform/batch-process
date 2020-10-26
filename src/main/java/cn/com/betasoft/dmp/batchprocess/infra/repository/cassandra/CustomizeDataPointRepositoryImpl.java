package cn.com.betasoft.dmp.batchprocess.infra.repository.cassandra;

import cn.com.betasoft.dmp.batchprocess.domain.model.DataPoint;
import org.springframework.data.cassandra.core.ReactiveCassandraOperations;
import reactor.core.publisher.Flux;

public class CustomizeDataPointRepositoryImpl implements CustomizeDataPointRepository {

    private ReactiveCassandraOperations operations;

    @Override
    public Flux<DataPoint> find(Flux<String> sqlFlux) {
        return sqlFlux.flatMap(sql -> operations.select(sql, DataPoint.class));
    }
}
