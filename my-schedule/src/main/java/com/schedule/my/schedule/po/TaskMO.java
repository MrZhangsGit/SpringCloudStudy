package com.schedule.my.schedule.po;

import lombok.Data;

/**
 * @author zhangs
 * @Description
 * @createDate 2019/1/17
 */
@Data
public class TaskMO {
    /**
     * 执行频率(0:循环执行|1:执行一次)
     */
    private Integer frequency;

    /**
     * 格式 yyyy/MM/dd HH:mm:ss
     */
    private String startTime;

    /**
     * 格式 yyyy/MM/dd HH:mm:ss
     */
    private String endTime;

    /**
     * 开始年份
     */
    private Integer startYear;

    /**
     * 结束年份
     */
    private Integer endYear;

    /**
     * 开始月份
     */
    private Integer startMonth;

    /**
     * 结束月份
     */
    private Integer endMonth;

    /**
     * 开始日
     */
    private Integer startDay;

    /**
     * 结束日
     */
    private Integer endDay;

    /**
     * 开始小时
     */
    private Integer startHour;

    /**
     * 结束小时
     */
    private Integer endHour;

    /**
     * 开始分
     */
    private Integer startMinute;

    /**
     * 结束分
     */
    private Integer endMinute;

    /**
     * 开始秒
     */
    private Integer startSecond;

    /**
     * 结束秒
     */
    private Integer endSecond;

    /**
     * 起始延迟时间（单位:s）
     */
    private Long initialDelay;

    /**
     * 任务间隔
     */
    private Long period;
}
