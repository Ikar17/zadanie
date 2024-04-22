package org.example;

import java.util.List;

public class Calendar {
    private TimeRange workingHours;
    private List<TimeRange> plannedMeeting;

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