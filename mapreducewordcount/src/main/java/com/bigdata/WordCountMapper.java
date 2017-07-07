package com.bigdata;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * Created by chuyuqiao on 2017/7/7.
 */
public class WordCountMapper extends Mapper<LongWritable,Text,Text,IntWritable> {
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        //得到输入的每一行数据
        String line=value.toString();
        //通过空格分割
        String[] words=line.split(" ");
        //循环遍历输出
        for(String word:words){
            context.write(new Text(word),new IntWritable(1));
        }

    }
}
