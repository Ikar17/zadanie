import org.example.App;
import org.example.Calendar;
import org.example.TimeRange;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AppTest {
    @Test
    @DisplayName("Function parseCalendarFromJsonFile when file doesn't exists")
    public void test1(){
        String fileName = "src/test/resources/file_test1.txt";
        File file = new File(fileName);
        App app = new App();

        Calendar expected = null;
        Calendar actual = app.parseCalendarFromJsonFile(file);

        assertEquals(expected, actual, "Return value should be null");
    }

    @Test
    @DisplayName("Function parseCalendarFromJsonFile when file is empty")
    public void test2(){
        String fileName = "src/test/resources/file_test2.txt";
        File file = new File(fileName);
        App app = new App();

        Calendar expected = null;
        Calendar actual = app.parseCalendarFromJsonFile(file);

        assertEquals(expected, actual, "Return value should be null");
    }

    @Test
    @DisplayName("Function parseCalendarFromJsonFile when file is corrupted (doesn't contain working hours)")
    public void test3(){
        String fileName = "src/test/resources/file_test3.txt";
        File file = new File(fileName);
        App app = new App();

        Calendar expected = null;
        Calendar actual = app.parseCalendarFromJsonFile(file);

        assertEquals(expected, actual, "Return value should be null");
    }

    @Test
    @DisplayName("Function parseCalendarFromJsonFile when file is corrupted (doesn't contain planned meetings)")
    public void test4(){
        String fileName = "src/test/resources/file_test4.txt";
        File file = new File(fileName);
        App app = new App();

        Calendar expected = null;
        Calendar actual = app.parseCalendarFromJsonFile(file);

        assertEquals(expected, actual, "Return value should be null");
    }

    @Test
    @DisplayName("Function parseCalendarFromJsonFile when file is correct")
    public void test5(){
        String fileName = "src/test/resources/file_test5.txt";
        File file = new File(fileName);
        App app = new App();

        Calendar actual = app.parseCalendarFromJsonFile(file);

        assertNotNull(actual, "Return value shouldn't be null");
        assertNotNull(actual.getWorkingHours(), "Object workingHours shouldn't be null");
        assertNotNull(actual.getPlannedMeeting(), "Object plannedMeeting shouldn't be null");
        assertEquals(LocalTime.of(9,0), actual.getWorkingHours().getStartTime(), "Start of working should be 9:00");
        assertEquals(LocalTime.of(19,55), actual.getWorkingHours().getEndTime(), "End of working should be 19:55");

        String[] expectedMeetingTime = {
                "09:00", "10:30",
                "12:00", "13:00",
                "16:00", "18:00"
        };

        int expectedMeetingTimeIndex = 0;
        String message;

        assertEquals(3, actual.getPlannedMeeting().size(), "Incorrect size of meetings");

        for(TimeRange actualMeetingTime : actual.getPlannedMeeting()){
            message = String.format("Start of meeting should be %s",expectedMeetingTime[expectedMeetingTimeIndex]);
            assertEquals(LocalTime.parse(expectedMeetingTime[expectedMeetingTimeIndex]), actualMeetingTime.getStartTime(), message);

            message = String.format("End of meeting should be %s",expectedMeetingTime[expectedMeetingTimeIndex + 1]);
            assertEquals(LocalTime.parse(expectedMeetingTime[expectedMeetingTimeIndex + 1]), actualMeetingTime.getEndTime(), message);

            expectedMeetingTimeIndex += 2;
        }
    }

    @Test
    @DisplayName("Function parseCalendarFromJsonFile when file is correct")
    public void test6(){
        String fileName = "src/test/resources/file_test6.txt";
        File file = new File(fileName);
        App app = new App();

        Calendar actual = app.parseCalendarFromJsonFile(file);

        assertNotNull(actual, "Return value shouldn't be null");
        assertNotNull(actual.getWorkingHours(), "Object workingHours shouldn't be null");
        assertNotNull(actual.getPlannedMeeting(), "Object plannedMeeting shouldn't be null");
        assertEquals(LocalTime.of(10,0), actual.getWorkingHours().getStartTime(), "Start of working should be 9:00");
        assertEquals(LocalTime.of(18,30), actual.getWorkingHours().getEndTime(), "End of working should be 19:55");

        String[] expectedMeetingTime = {
                "10:00", "11:30",
                "12:30", "14:30",
                "14:30", "15:00",
                "16:00", "17:00"
        };

        int expectedMeetingTimeIndex = 0;
        String message;

        assertEquals(4, actual.getPlannedMeeting().size(), "Incorrect size of meetings");

        for(TimeRange actualMeetingTime : actual.getPlannedMeeting()){
            message = String.format("Start of meeting should be %s",expectedMeetingTime[expectedMeetingTimeIndex]);
            assertEquals(LocalTime.parse(expectedMeetingTime[expectedMeetingTimeIndex]), actualMeetingTime.getStartTime(), message);

            message = String.format("End of meeting should be %s",expectedMeetingTime[expectedMeetingTimeIndex + 1]);
            assertEquals(LocalTime.parse(expectedMeetingTime[expectedMeetingTimeIndex + 1]), actualMeetingTime.getEndTime(), message);

            expectedMeetingTimeIndex += 2;
        }
    }

    @Test
    @DisplayName("Function getFreeSlots when one of the two providing calendars is null")
    public void test7(){
        String fileName = "src/test/resources/file_test5.txt";
        File file = new File(fileName);
        App app = new App();

        Calendar firstCalendar = null;
        Calendar secondCalendar = app.parseCalendarFromJsonFile(file);
        LocalTime meetingDuration = LocalTime.of(0, 30);

        List<TimeRange> actual = app.getFreeSlots(firstCalendar, secondCalendar, meetingDuration);
        assertNull(actual, "Return value should be null");
    }

    @Test
    @DisplayName("Function getFreeSlots when one of the two providing calendars is null")
    public void test8(){
        App app = new App();

        Calendar firstCalendar = null;
        Calendar secondCalendar = null;
        LocalTime meetingDuration = LocalTime.of(0, 30);

        List<TimeRange> actual = app.getFreeSlots(firstCalendar, secondCalendar, meetingDuration);
        assertNull(actual, "Return value should be null");
    }

    @Test
    @DisplayName("Function getFreeSlots when one of the two providing calendars is null")
    public void test9(){
        String fileName = "src/test/resources/file_test5.txt";
        File file = new File(fileName);
        App app = new App();

        Calendar firstCalendar = app.parseCalendarFromJsonFile(file);
        Calendar secondCalendar = null;
        LocalTime meetingDuration = LocalTime.of(0, 30);

        List<TimeRange> actual = app.getFreeSlots(firstCalendar, secondCalendar, meetingDuration);
        assertNull(actual, "Return value should be null");
    }

    @Test
    @DisplayName("Function getFreeSlots when one of the two providing calendars is null")
    public void test10(){
        String fileName = "src/test/resources/file_test5.txt";
        File file = new File(fileName);
        App app = new App();

        Calendar firstCalendar = app.parseCalendarFromJsonFile(file);
        Calendar secondCalendar = null;
        LocalTime meetingDuration = LocalTime.of(0, 30);

        List<TimeRange> actual = app.getFreeSlots(firstCalendar, secondCalendar, meetingDuration);
        assertNull(actual, "Return value should be null");
    }

    @Test
    @DisplayName("Function getFreeSlots when two calendars are correct")
    public void test11(){
        String firstFileName = "src/test/resources/file_test5.txt";
        File firstFile = new File(firstFileName);
        String secondFileName = "src/test/resources/file_test6.txt";
        File secondFile = new File(secondFileName);
        App app = new App();

        Calendar firstCalendar = app.parseCalendarFromJsonFile(firstFile);
        Calendar secondCalendar = app.parseCalendarFromJsonFile(secondFile);
        LocalTime meetingDuration = LocalTime.of(0, 30);

        List<TimeRange> actual = app.getFreeSlots(firstCalendar, secondCalendar, meetingDuration);
        assertNotNull(actual, "Return value shouldn't be null");

        TimeRange[] expectedFreeSlots = {
                new TimeRange(LocalTime.of(11,30), LocalTime.of(12,0)),
                new TimeRange(LocalTime.of(15,0), LocalTime.of(16,0)),
                new TimeRange(LocalTime.of(18,0), LocalTime.of(18,30))
        };

        assertEquals(3, actual.size(), "Incorrect size of free slots");

        for(int index = 0; index < 3; ++index){
            TimeRange actualFreeSlot = actual.get(index);
            TimeRange expectedFreeSlot = expectedFreeSlots[index];
            assertEquals(expectedFreeSlot.getStartTime(), actualFreeSlot.getStartTime(), "Incorrect start time of free slot");
            assertEquals(expectedFreeSlot.getEndTime(), actualFreeSlot.getEndTime(), "Incorrect end time of free slot");
        }
    }

    @Test
    @DisplayName("Function formatToStringArrayList when parametr is null")
    public void test12(){
        App app = new App();
        List<TimeRange> freeSlotsList = null;
        ArrayList<ArrayList<String>> formatedFreeSlotsList = app.formatToStringArrayList(freeSlotsList);
        assertNull(formatedFreeSlotsList, "Return value should be null");
    }

    @Test
    @DisplayName("Function formatToStringArrayList when providing list is correct")
    public void test13(){
        String firstFileName = "src/test/resources/file_test5.txt";
        File firstFile = new File(firstFileName);
        String secondFileName = "src/test/resources/file_test6.txt";
        File secondFile = new File(secondFileName);
        App app = new App();

        Calendar firstCalendar = app.parseCalendarFromJsonFile(firstFile);
        Calendar secondCalendar = app.parseCalendarFromJsonFile(secondFile);
        LocalTime meetingDuration = LocalTime.of(0, 30);

        List<TimeRange> freeSlotsList = app.getFreeSlots(firstCalendar, secondCalendar, meetingDuration);

        ArrayList<ArrayList<String>> formatedFreeSlotsList = app.formatToStringArrayList(freeSlotsList);
        assertNotNull(formatedFreeSlotsList, "Return value shouldn't be null");

        ArrayList<ArrayList<String>> expected = new ArrayList<>();
        expected.add(new ArrayList<>(List.of("12:30", "13:00")));
        expected.add(new ArrayList<>(List.of("15:00", "16:00")));
        expected.add(new ArrayList<>(List.of("18:00", "18:30")));

        assertEquals(3, formatedFreeSlotsList.size(), "List should contain 3 slots");

        for(int index = 0; index < 3; ++index){
            ArrayList<String> actualSlot = formatedFreeSlotsList.get(index);
            ArrayList<String> expectedSlot = expected.get(index);

            assertNotNull(actualSlot, "Return slot shouldn't be null");
            assertEquals(actualSlot.get(0), expectedSlot.get(0));
            assertEquals(actualSlot.get(1), expectedSlot.get(1));
        }
    }

}