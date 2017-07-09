package com.bigdata.groupcomparator;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.Partitioner;

/**
 * Created by 17021629 on 2017/7/8.
 */
public class ItemIdPartitioner extends Partitioner<OrderBean,NullWritable> {
    public int getPartition(OrderBean orderBean, NullWritable nullWritable, int i) {
        //相同id的订单bean，会发往相同的partition
        //而且产生的分区数，会根用户设置的reduce task数保持一致
        return (orderBean.getItemid().hashCode() & Integer.MAX_VALUE)%i;
    }
}
