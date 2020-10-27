package cn.com.betasoft.dmp.batchprocess.domain.kairosdb;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;

/**
 * POST: /api/v1/datapoints
 * [
 *   {
 *       "name": "OSCPU_CPU_LOAD",
 *       "datapoints": [[1359788400000, 123], [1359788300000, 13.2], [1359788410000, 23.1]],
 *       "tags": {
 *           "moc": "Windows",
 *           "mo": "Windows.domain="defaultEngine",uuid="024fb79d16e346a4bbb35dcefdd88949""
 *       },
 *       "ttl": 300
 *   },
 *   {
 *       "name": "MEM_LOAD",
 *       "datapoints": [[1359788400000, 123], [1359788300000, 13.2], [1359788410000, 23.1]],
 *       "tags": {
 *           "moc": "l",
 *           "mo": "Windows.domain="defaultEngine",uuid="024fb79d16e346a4bbb35dcefdd88949""
 *       },
 *       "ttl": 300
 *   }
 * ]
 */
@Setter
@Getter
@NoArgsConstructor
public class Metric {

    @JsonProperty("name")
    private String name;

    @JsonProperty("tags")
    private Map<String,String> tags;

    @JsonProperty("datapoints")
    private List<Object[]> samplePoints;
}
