package org.example;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Calendar {
    @JsonProperty("working_hours")
    private TimeRange workingHours;
    @JsonProperty("planned_meeting")
    private List<TimeRange> plannedMeeting;

    public Calendar(){};
    public Calendar(TimeRange workingHours, List<TimeRange> plannedMeeting) {
        this.workingHours = workingHours;
        this.plannedMeeting = plannedMeeting;
    }

    public TimeRange getWorkingHours() {
        return workingHours;
    }

    public List<TimeRange> getPlannedMeeting() {
        return plannedMeeting;
    }
}