package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class App {
    public static void main(String[] args) {
        System.out.println("Hello world!");
    }

    public Calendar parseCalendarFromJsonFile(File file){
        if(file == null) return null;
        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();
        try{
            Calendar calendar = mapper.readValue(file, Calendar.class);
            if(calendar.getWorkingHours() == null || calendar.getPlannedMeeting() == null) return null;
            return calendar;
        }catch (IOException exception){
            return null;
        }
    }

    public List<TimeRange> getFreeSlots(Calendar firstCalendar, Calendar secondCalendar, LocalTime meetingDuration){
        if(firstCalendar == null || secondCalendar == null || meetingDuration == null) return null;

        List<TimeRange> mergedMeetings = mergeAndSortAllMeetings(firstCalendar.getPlannedMeeting(), secondCalendar.getPlannedMeeting());

        List<TimeRange> freeSlots = new ArrayList<>();

        LocalTime potentialFreeSlotStartTime = findPotentialFirstFreeSlotStartTime(firstCalendar, secondCalendar);
        for(TimeRange timeRange : mergedMeetings){
            if(isFreeSlot(potentialFreeSlotStartTime, timeRange.getStartTime(), meetingDuration)){
                freeSlots.add(new TimeRange(potentialFreeSlotStartTime, timeRange.getStartTime()));
            }
            potentialFreeSlotStartTime = timeRange.getEndTime();
        }

        LocalTime potentialFreeSlotEndTime = findPotentialLastFreeSlotEndTime(firstCalendar, secondCalendar);
        if(isFreeSlot(potentialFreeSlotStartTime, potentialFreeSlotEndTime, meetingDuration)){
            freeSlots.add(new TimeRange(potentialFreeSlotStartTime, potentialFreeSlotEndTime));
        }
        return freeSlots;
    }

    public ArrayList<ArrayList<String>> formatToStringArrayList(List<TimeRange> freeSlots){
        if(freeSlots == null) return null;
        ArrayList<ArrayList<String>> freeSlotsAsArrayList = new ArrayList<>();
        for(TimeRange slot : freeSlots){
            ArrayList<String> slotList = new ArrayList<>();
            slotList.add(slot.getStartTime().toString());
            slotList.add(slot.getEndTime().toString());
            freeSlotsAsArrayList.add(slotList);
        }
        return freeSlotsAsArrayList;
    }

    private List<TimeRange> mergeAndSortAllMeetings(List<TimeRange> firstCalendarMeetings, List<TimeRange> secondCalendarMeetings){
        if(firstCalendarMeetings == null || secondCalendarMeetings == null) return null;

        List<TimeRange> mergedList = new ArrayList<>(firstCalendarMeetings);
        mergedList.addAll(secondCalendarMeetings);

        Collections.sort(mergedList);

        return mergedList;
    }

    private LocalTime findPotentialFirstFreeSlotStartTime(Calendar firstCalendar, Calendar secondCalendar){
        LocalTime firstCalendarStartWorkTime = firstCalendar.getWorkingHours().getStartTime();
        LocalTime secondCalendarStartWorkTime = secondCalendar.getWorkingHours().getStartTime();
        if(firstCalendarStartWorkTime.isBefore(secondCalendarStartWorkTime)){
           return secondCalendarStartWorkTime;
        }else{
            return firstCalendarStartWorkTime;
        }
    }

    private LocalTime findPotentialLastFreeSlotEndTime(Calendar firstCalendar, Calendar secondCalendar){
        LocalTime firstCalendarEndWorkTime = firstCalendar.getWorkingHours().getEndTime();
        LocalTime secondCalendarEndWorkTime = secondCalendar.getWorkingHours().getEndTime();
        if(firstCalendarEndWorkTime.isBefore(secondCalendarEndWorkTime)){
            return firstCalendarEndWorkTime;
        }else{
            return secondCalendarEndWorkTime;
        }
    }

    private boolean isFreeSlot(LocalTime startTime, LocalTime endTime, LocalTime minMeetingDuration){
        if(startTime.isBefore(endTime)){
            Duration slotDuration = Duration.between(startTime, endTime);
            long minMeetingDurationInMinutes = minMeetingDuration.getHour() * 60 + minMeetingDuration.getMinute();
            return slotDuration.toMinutes() >= minMeetingDurationInMinutes;
        }
        return false;
    }
}