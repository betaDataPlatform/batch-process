package cn.com.betasoft.dmp.batchprocess.infra.repository.cassandra;

import cn.com.betasoft.dmp.batchprocess.domain.model.DataPoint;
import cn.com.betasoft.dmp.batchprocess.domain.model.DataPointKey;
import org.springframework.stereotype.Repository;

@Repository
public interface DataPointRepository
        extends ExtendedReactiveCassandraRepository<DataPoint, DataPointKey>, CustomizeDataPointRepository {
}
