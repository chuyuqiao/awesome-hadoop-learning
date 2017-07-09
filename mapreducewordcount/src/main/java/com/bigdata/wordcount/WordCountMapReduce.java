package com.bigdata.wordcount;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

/**
 * Created by chuyuqiao on 2017/7/7.
 */
public class WordCountMapReduce {
    public static void main(String[] args) throws Exception{
        //创建配置对象
        Configuration conf=new Configuration();
        //创建job对象
        Job job=Job.getInstance(conf,"wordCount");
        //设置运行job的类
        job.setJarByClass(WordCountMapReduce.class);
        //设置Mapper类
        job.setMapperClass(WordCountMapper.class);
        //设置Reduce类
        job.setReducerClass(WordCountReducer.class);
        //设置Map输出的key value
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);
        //设置reducer输出的key value
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);
        //设置输入输出路径
        FileInputFormat.setInputPaths(job,new Path(args[0]));
        FileOutputFormat.setOutputPath(job,new Path(args[1]));
        //提交job
        boolean b=job.waitForCompletion(true);
        if(!b){
            System.out.println("wordCount task fail!");
        }

    }
}
