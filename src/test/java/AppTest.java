import org.example.App;
import org.example.Calendar;
import org.example.TimeRange;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.time.LocalTime;

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
}