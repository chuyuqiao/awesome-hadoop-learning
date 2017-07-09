package com.bigdata.inputformat;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.SequenceFileOutputFormat;

import java.io.IOException;

/**
 * 将多个小文件转化成一个文件
 * Created by 17021629 on 2017/7/8.
 */
public class ManyToOne {

    static class FileMapper extends Mapper<NullWritable,BytesWritable,Text,BytesWritable>{
        private Text filenameKey;

        /**
         * Called once at the beginning of the task.
         * 获取文件名
         * @param context
         * @throws IOException
         * @throws InterruptedException
         */
        @Override
        protected void setup(Context context) throws IOException, InterruptedException {
            InputSplit split=context.getInputSplit();
            Path path=((FileSplit)split).getPath();
            filenameKey=new Text(path.toString());
        }

        @Override
        protected void map(NullWritable key, BytesWritable value, Context context) throws IOException, InterruptedException {
            context.write(filenameKey,value);
        }

        public static void main(String[] args) throws Exception {
            //配置
            Configuration conf=new Configuration();
            Job job=Job.getInstance(conf,"fileManyToOne");
            //job配置
            job.setJarByClass(ManyToOne.class);
            job.setMapperClass(FileMapper.class);
            job.setMapOutputKeyClass(Text.class);
            job.setMapOutputValueClass(BytesWritable.class);
            job.setOutputKeyClass(Text.class);
            job.setOutputValueClass(BytesWritable.class);
            //输入输出格式
            job.setInputFormatClass(MyInputFormat.class);
            job.setOutputFormatClass(SequenceFileOutputFormat.class);
            //输入输出路径
            FileInputFormat.setInputPaths(job,new Path(args[0]));
            FileOutputFormat.setOutputPath(job,new Path(args[1]));
            //执行job
           boolean res=job.waitForCompletion(true);
           if(!res){
               System.out.println("job excute fail!");
           }
        }
    }
}
