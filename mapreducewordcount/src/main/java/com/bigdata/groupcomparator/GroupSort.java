package com.bigdata.groupcomparator;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

/**
 * Created by 17021629 on 2017/7/8.
 */
public class GroupSort {
    static class SortMapper extends Mapper<LongWritable,Text,OrderBean,NullWritable> {
        OrderBean bean=new OrderBean();
        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            //读取行
            String line=value.toString();
            //分割
            String[] fields=line.split(",");
            //写出
            bean.set(new Text(fields[0].toString()),new DoubleWritable(Double.parseDouble(fields[1])));
            context.write(bean,NullWritable.get());
        }
    }

    static class SortReducer extends Reducer<OrderBean,NullWritable,OrderBean,NullWritable>{
        @Override
        protected void reduce(OrderBean key, Iterable<NullWritable> values, Context context)
                throws IOException, InterruptedException {
            context.write(key,NullWritable.get());
        }
    }

    public static void main(String[] args) throws Exception {
        //配置
        Configuration conf =new Configuration();
        //获取Job
        Job job =Job.getInstance(conf);
        //设置涉及类
        job.setJarByClass(GroupSort.class);
        job.setMapperClass(SortMapper.class);
        job.setReducerClass(SortReducer.class);
        job.setMapOutputKeyClass(OrderBean.class);
        job.setMapOutputValueClass(NullWritable.class);
        job.setOutputKeyClass(OrderBean.class);
        job.setOutputValueClass(NullWritable.class);
        //设置分区
        job.setPartitionerClass(ItemIdPartitioner.class);
        job.setNumReduceTasks(2);
        job.setGroupingComparatorClass(MyGroupingComparator.class);
        //输入输出
        FileInputFormat.setInputPaths(job,new Path(args[0]));
        FileOutputFormat.setOutputPath(job,new Path(args[1]));
        //执行
        boolean res=job.waitForCompletion(true);
        if(!res){
            System.exit(0);
        }
    }
}
