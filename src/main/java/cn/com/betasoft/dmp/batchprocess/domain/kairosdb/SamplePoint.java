package cn.com.betasoft.dmp.batchprocess.domain.kairosdb;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@AllArgsConstructor
@ToString
public class SamplePoint {

    private long timestamp;

    private double value;
}
