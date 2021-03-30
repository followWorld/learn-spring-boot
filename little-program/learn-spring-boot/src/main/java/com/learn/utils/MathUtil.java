package com.learn.utils;

import cn.hutool.core.util.NumberUtil;

import java.math.BigDecimal;
import java.time.LocalDate;

public class MathUtil extends NumberUtil{
	

	public static Integer add(Integer num1, Integer num2) {
		if (num1 == null)
			num1 = 0;
		if (num2 == null)
			num2 = 0;
		return num1 + num2;
	}
	
	public static Integer sub(Integer num1, Integer num2) {
		if (num1 == null) {
			num1 = 0; 
		}
		if (num2 == null) {
			num2 = 0;
		}
		return num1 - num2;
	}
	
	public static Integer mul(Integer num1,Integer num2) {
		if (num1 == null) {
			num1 = 0; 
		}
		if (num2 == null) {
			num2 = 0;
		}
		return num1*num2; 
	}

	public static Double div(Integer num, Integer sum) {
		if(num == null) {
			num = 0;
		}
		if(sum == null || sum == 0) {
			return 0.0;
		}
		return div((double)num,(double)sum,2);
	}
	
	  /**
     * 比较获取较大的BigDecimal
     *
     * @param value1
     * @param value2
     * @return 两个值中较大的一个
     */
    public static BigDecimal getBigBigDecimal(BigDecimal value1, BigDecimal value2) {
        BigDecimal result;
        if (value1 == null) {
            result = value2;
        } else {
            result = value2 == null ? value1 : value1.max(value2);
        }
        return result;
    }

    /**
     * 比较获取较小的BigDecimal
     *
     * @param value1
     * @param value2
     * @return 两个值中较小的一个
     */
    public static BigDecimal getSmallBigDecimal(BigDecimal value1, BigDecimal value2) {
        BigDecimal result;
        if (value1 == null) {
            result = value2;
        } else {
            result = value2 == null ? value1 : value1.min(value2);
        }
        return result;
    }

    /**
     * 按时间权重计算两个BigDecimal的平均值
     *
     * @param monthValue 当月之前天数的平均值
     * @param TodayValue 今天的平均值
     * @return 这个月份的平均值
     */
    public static BigDecimal getTimeAvg(BigDecimal monthValue, BigDecimal TodayValue) {
        LocalDate today = LocalDate.now();
        int dayOfMonth = today.getDayOfMonth();
        BigDecimal result;
        if (monthValue == null) {
            result = TodayValue;
        } else {
            result = TodayValue == null ?
                    monthValue.multiply(new BigDecimal(dayOfMonth - 1)).divide(new BigDecimal(dayOfMonth)) :
                    (monthValue.multiply(new BigDecimal(dayOfMonth - 1)).add(TodayValue)).divide(new BigDecimal(dayOfMonth));
        }
        return result;
    }

}
