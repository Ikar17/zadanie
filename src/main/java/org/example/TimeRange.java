package org.example;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalTime;

public class TimeRange {
    @JsonProperty("start")
    private LocalTime startTime;
    @JsonProperty("end")
    private LocalTime endTime;

    public TimeRange(){};
    public TimeRange(LocalTime startTime, LocalTime endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }
}