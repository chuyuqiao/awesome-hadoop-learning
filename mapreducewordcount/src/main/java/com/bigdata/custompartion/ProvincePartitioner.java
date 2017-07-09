package com.bigdata.custompartion;

import com.bigdata.serializebean.FlowBean;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by chuyuqiao on 2017/7/7.
 */
public class ProvincePartitioner extends Partitioner<Text,FlowBean> {

    public static Map<String,Integer> provinceDict=new HashMap<String, Integer>();

    static {
        provinceDict.put("137",0);
        provinceDict.put("133",1);
        provinceDict.put("138",2);
        provinceDict.put("135",3);

    }
    public int getPartition(Text text, FlowBean flowBean, int i) {
        String prefix=text.toString().substring(0,3);
        Integer provinceId=provinceDict.get(prefix);
        return provinceId==null?4:provinceId;
    }
}
