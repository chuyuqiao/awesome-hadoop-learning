package com.bigdata.serializebean;

import org.apache.hadoop.io.Writable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * 把同一个用户的上行流量、下行流量进行累加，并计算出综合。
 * 数据实例：
 * 手机号 上行流量 下行流量
 * 18651663870 100 400
 * 18651663870 200 500
 * 统计结果
 * 18651663870 300 900 1300
 * Created by chuyuqiao on 2017/7/7.
 */
public class FlowBean implements Writable {
    //上行流量
    private long upFlow;
    //下行流量
    private long dFlow;
    //流量总和
    private long sumFlow;

    public FlowBean(long upFlow, long dFlow) {
        this.upFlow = upFlow;
        this.dFlow = dFlow;
        this.sumFlow = upFlow+dFlow;
    }

    public long getUpFlow() {
        return upFlow;
    }

    public void setUpFlow(long upFlow) {
        this.upFlow = upFlow;
    }

    public long getdFlow() {
        return dFlow;
    }

    public void setdFlow(long dFlow) {
        this.dFlow = dFlow;
    }

    public long getSumFlow() {
        return sumFlow;
    }

    public void setSumFlow(long sumFlow) {
        this.sumFlow = sumFlow;
    }


    public void write(DataOutput dataOutput) throws IOException {
        dataOutput.writeLong(upFlow);
        dataOutput.writeLong(dFlow);
        dataOutput.writeLong(sumFlow);
    }

    public void readFields(DataInput dataInput) throws IOException {
        upFlow=dataInput.readLong();
        dFlow=dataInput.readLong();
        sumFlow=dataInput.readLong();
    }

    @Override
    public String toString() {
        return upFlow+"\t"+dFlow+"\t"+sumFlow;
    }
}
