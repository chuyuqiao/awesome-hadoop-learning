package com.bigdata.inputformat;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;

import java.io.IOException;

/**
 * 自定义InputFormat
 * Created by 17021629 on 2017/7/8.
 */
public class MyInputFormat extends FileInputFormat<NullWritable,BytesWritable> {
    /**
     * process entire files.
     *
     * @param context
     * @param filename
     * @return
     */
    @Override
    protected boolean isSplitable(JobContext context, Path filename) {
        //设置小文件不可分割
        return false;
    }

    public RecordReader createRecordReader(InputSplit split, TaskAttemptContext context)
            throws IOException, InterruptedException {
        MyRecordReader recordReader=new MyRecordReader();
        recordReader.initialize(split,context);
        return recordReader;
    }
}
