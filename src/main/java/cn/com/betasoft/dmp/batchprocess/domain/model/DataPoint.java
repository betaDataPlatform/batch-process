package cn.com.betasoft.dmp.batchprocess.domain.model;

import lombok.*;
import org.springframework.data.annotation.Transient;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.HashMap;
import java.util.Map;

@Setter
@Getter
@AllArgsConstructor
@ToString
@Builder
@Table(value = "data_point")
public class DataPoint {

    @PrimaryKey
    private DataPointKey dataPointKey;

    @Column("tags")
    private Map<String, String> tags = new HashMap<>();

    @Column("value")
    private Double value;

    @Transient
    private Integer ttl;
}
