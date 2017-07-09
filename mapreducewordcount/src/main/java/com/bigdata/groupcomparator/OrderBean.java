package com.bigdata.groupcomparator;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * Created by 17021629 on 2017/7/8.
 */
public class OrderBean implements WritableComparable<OrderBean> {
    private Text itemid;
    private DoubleWritable amount;

    public OrderBean() {
    }

    public OrderBean(Text itemid, DoubleWritable amount) {
        this.set(itemid, amount);
    }

    public void set(Text itemid, DoubleWritable amount) {
        this.itemid = itemid;
        this.amount = amount;
    }

    public Text getItemid() {
        return itemid;
    }

    public void setItemid(Text itemid) {
        this.itemid = itemid;
    }

    public DoubleWritable getAmount() {
        return amount;
    }

    public void setAmount(DoubleWritable amount) {
        this.amount = amount;
    }

    /**
     * 比较订单先比较订单号再比较销量
     * 数量按倒序排序
     * @param o
     * @return
     */
    public int compareTo(OrderBean o) {
        int ret=this.getItemid().compareTo(o.getItemid());
        if(ret == 0){
            //倒序
            ret=-this.getAmount().compareTo(o.getAmount());
        }
        return ret;
    }

    public void write(DataOutput dataOutput) throws IOException {
        dataOutput.writeUTF(itemid.toString());
        dataOutput.writeDouble(amount.get());
    }

    public void readFields(DataInput dataInput) throws IOException {
        this.set(new Text(dataInput.readUTF()), new DoubleWritable(dataInput.readDouble()));
    }

    @Override
    public String toString() {
        return itemid.toString()+"\t"+amount.get();
    }
}
