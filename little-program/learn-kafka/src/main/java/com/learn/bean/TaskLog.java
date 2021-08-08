package com.learn.bean;


import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * ClassName: TaskLog
 * Description:
 * date: 2021/8/7 16:13
 *
 * @author WLSH
 */
public class TaskLog {
    private String flowNo;
    private String busType;
    private String dsa;
    private int step;
    private String result;
    private String status;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss.sss")
    private Date time;

    public String getFlowNo() {
        return flowNo;
    }

    public void setFlowNo(String flowNo) {
        this.flowNo = flowNo;
    }

    public String getBusType() {
        return busType;
    }

    public void setBusType(String busType) {
        this.busType = busType;
    }

    public String getDsa() {
        return dsa;
    }

    public void setDsa(String dsa) {
        this.dsa = dsa;
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "TaskLog{" +
                "flowNo='" + flowNo + '\'' +
                ", busType='" + busType + '\'' +
                ", dsa='" + dsa + '\'' +
                ", step=" + step +
                ", result='" + result + '\'' +
                ", status='" + status + '\'' +
                ", time=" + time +
                '}';
    }
}
