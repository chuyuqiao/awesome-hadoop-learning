package com.bigdata.serializebean;

import com.bigdata.custompartion.ProvincePartitioner;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

/**
 * Created by chuyuqiao on 2017/7/7.
 */
public class FlowCount {

    static class FlowCountMapper extends Mapper<LongWritable,Text,Text,FlowBean>{
        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            //将第一行转换成String
            String line=value.toString();
            //切分字段
            String[] fields=line.split("\t");
            //取出手机号
            String phoneNum=fields[0];
            //取出上行下行流量
            long upFlow=Long.parseLong(fields[1]);
            long dFlow=Long.parseLong(fields[2]);
            //输出
            context.write(new Text(phoneNum),new FlowBean(upFlow,dFlow));
        }
    }

    static class FlowCountReducer extends Reducer<Text,FlowBean,Text,FlowBean>{
        @Override
        protected void reduce(Text key, Iterable<FlowBean> values, Context context) throws IOException, InterruptedException {
            //统计
            long sum_upFlow=0;
            long sum_dFlow=0;
            for(FlowBean bean:values){
                sum_upFlow+=bean.getUpFlow();
                sum_dFlow+=bean.getdFlow();
            }
            //输出
            context.write(key,new FlowBean(sum_upFlow,sum_dFlow));

        }
    }

    public static void main(String[] args) throws Exception {
        //配置
        Configuration conf=new Configuration();
        //获取job
        Job job=Job.getInstance(conf);
        //指定jar
        job.setJarByClass(FlowCount.class);
        //指定mapper和reducer
        job.setMapperClass(FlowCountMapper.class);
        job.setReducerClass(FlowCountReducer.class);
        //自定义分区器和处理分区的reducetask
        job.setPartitionerClass(ProvincePartitioner.class);
        job.setNumReduceTasks(5);
        //指定Mapper输出类型
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(FlowBean.class);
        //指定最终输出类型
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(FlowBean.class);
        //指定输入输出目录
        FileInputFormat.setInputPaths(job,new Path(args[0]));
        FileOutputFormat.setOutputPath(job,new Path(args[1]));
        //提交job
        boolean res=job.waitForCompletion(true);
        if(!res){
            System.out.println("流量统计任务执行失败！");
        }
    }
}
