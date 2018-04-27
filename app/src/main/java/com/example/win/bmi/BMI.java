package com.example.win.bmi;

import java.text.DecimalFormat;

public class BMI {

    private double height;
    private double weight;
    /**
     * 常量METRIC_SYSTEM为公制系统，单位为厘米和公斤
     * 常量IMPERIAL_SYSTEM为英制系统，单位为英寸和磅
     * system为当前对象采用的计量标准
     */
    public static final int METRIC_SYSTEM =0;
    public static final int IMPERIAL_SYSTEM = 1;
    private int system = 0;

    public BMI(int system) {
        this.system = system;
    }

    /**
     *
     * @param height 身高
     * @param weight 体重
     */

    public BMI(double height, double weight,int system) {
        this(system);
        this.height = height;
        this.weight = weight;
    }


    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public int getSystem() {
        return system;
    }

    public void setSystem(int system) {
        this.system = system;
    }

    private double bmiOfImperial() {
        return 703 * weight / Math.pow(height, 2);
    }

    private double bmiOfMetric() {
        return weight / Math.pow(height / 100, 2);
    }

    /**
     * 根据不同计量标准计算BMI值
     * @return Double类型的BMI值
     */
    public double getBmiValue() {
        switch (system) {
            case BMI.METRIC_SYSTEM:
                return bmiOfMetric();
            case BMI.IMPERIAL_SYSTEM:
                return bmiOfImperial();
            default:
                return 0.0d;
        }
    }

    /**
     * 计算BMI值并格式化
     * @return 格式化的String类型的BMI值
     */
    public String getBmiValueOfString() {
        return new DecimalFormat("0.00").format(getBmiValue());
    }

    /**
     * 获取针对BMI的建议
     * @return 以int表示的针对BMI值的建议
     */
    public int getBmiAdvice() {

        long int_bmi = Math.round(getBmiValue());
        if (int_bmi < 20) {
            return 2;
        } else if (int_bmi > 25) {
            return 1;
        } else {
            return 0;
        }
    }
}

