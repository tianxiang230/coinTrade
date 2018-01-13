package com.tx.coin.utils;

import java.text.DecimalFormat;
import java.util.List;

/**
 * 数学公式计算
 * Created by 你慧快乐 on 2018-1-12.
 */
public class MathUtil {
    /**
     * Double类型的最大值，太大的double值，相乘会达到无穷大
     */
    private final static double dmax = 999;
    /**
     * Double类型的最小值
     */
    private final static double dmin = Double.MIN_VALUE;

    /**
     * 方差s^2=[(x1-x)^2 +...(xn-x)^2]/n
     */
    public static double Variance(double[] x) {
        int m = x.length;
        double sum = 0;
        //求和
        for (int i = 0; i < m; i++) {
            sum += x[i];
        }
        //求平均值
        double dAve = sum / m;
        double dVar = 0;
        //求方差
        for (int i = 0; i < m; i++) {
            dVar += (x[i] - dAve) * (x[i] - dAve);
        }
        return dVar / m;
    }

    /**
     * 标准差σ=sqrt(s^2)
     */
    public static double StandardDiviation(double[] x) {
        int m = x.length;
        double sum = 0;
        for (int i = 0; i < m; i++) {
            sum += x[i];
        }
        //求平均值
        double dAve = sum / m;
        double dVar = 0;
        //求方差
        for (int i = 0; i < m; i++) {
            dVar += (x[i] - dAve) * (x[i] - dAve);
        }
        return Math.sqrt(dVar / m);
    }

    /**
     * 求平均值
     * @param data
     * @return
     */
    public static double avg(double[] data){
        double sum=0;
        for(double d:data){
            sum+=d;
        }
        return sum/data.length;
    }

    /**
     * 求平均值
     * @param data
     * @return
     */
    public static double avg(List<Double> data){
        double sum=0;
        for(double d:data){
            sum+=d;
        }
        return sum/data.size();
    }

    public static void main(String[] args) {
        int n=100;
        double[] x = {31.45,32,32.555,32.347,32.376,31.694,32.106,32.402,32.916,32.834,33.457,32.982,33.291,33.261,33.532,33.531,33.348,33.303,33.591,33.611};

        DecimalFormat df = new DecimalFormat("0.0000");
        //计算方差
        double dV = Variance(x);
        System.out.println("方差=" + df.format(dV));
        //计算标准差
        double dS = StandardDiviation(x);
        System.out.println("标准差=" + df.format(dS));

        double avg=avg(x);
        System.out.println("平均值="+df.format(avg));
    }
}
