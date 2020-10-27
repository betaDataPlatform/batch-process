package cn.com.betasoft.dmp.batchprocess.domain.event;

import cn.com.betasoft.dmp.batchprocess.domain.model.DataPoint;
import org.springframework.context.ApplicationEvent;
import org.springframework.util.Assert;

public class DataPointEvent extends ApplicationEvent {

    private DataPoint dataPoint;

    public DataPointEvent(Object source) {
        super(source);
        Assert.isTrue(source instanceof DataPoint, "source type is not DataPoint.");
        this.dataPoint = (DataPoint) source;
    }

    public DataPoint getDataPoint() {
        return dataPoint;
    }
}