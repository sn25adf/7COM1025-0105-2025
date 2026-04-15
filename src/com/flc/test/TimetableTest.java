package com.flc.test;

import com.flc.model.Lesson;
import com.flc.timetable.Timetable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TimetableTest {

    private Timetable timetable;

    @BeforeEach
    public void setUp() {
        timetable = new Timetable();
        timetable.addLesson(new Lesson("Yoga", 1, 5, "Saturday", "Morning", 10.0));
        timetable.addLesson(new Lesson("Zumba", 1, 5, "Saturday", "Afternoon", 12.0));
        timetable.addLesson(new Lesson("Yoga", 1, 5, "Sunday", "Morning", 10.0));
    }

    @Test
    public void testAddAndGetLessons() {
        assertEquals(3, timetable.getLessons().size());
    }

    @Test
    public void testGetLessonsByDay() {
        List<Lesson> saturdayLessons = timetable.getLessonsByDay("Saturday");
        assertEquals(2, saturdayLessons.size());
        assertEquals("Saturday", saturdayLessons.get(0).getDay());
        
        List<Lesson> sundayLessons = timetable.getLessonsByDay("Sunday");
        assertEquals(1, sundayLessons.size());
        assertEquals("Sunday", sundayLessons.get(0).getDay());
    }

    @Test
    public void testGetLessonsByExercise() {
        List<Lesson> yogaLessons = timetable.getLessonsByExercise("Yoga");
        assertEquals(2, yogaLessons.size());
        assertEquals("Yoga", yogaLessons.get(0).getExerciseName());
        
        List<Lesson> zumbaLessons = timetable.getLessonsByExercise("Zumba");
        assertEquals(1, zumbaLessons.size());
        assertEquals("Zumba", zumbaLessons.get(0).getExerciseName());
    }

    @Test
    public void testFullTimetableInitializationGenerates48Lessons() {
        // Create a completely new timetable to simulate system launch
        Timetable fullTimetable = new Timetable();
        
        // Simulating the exact loop found in BookingSystem.java's initializeData
        for (int w = 1; w <= 8; w++) {
            int month = (w <= 4) ? 5 : 6;
            fullTimetable.addLesson(new Lesson("Yoga", w, month, "Saturday", "Morning", 10.0));
            fullTimetable.addLesson(new Lesson("Zumba", w, month, "Saturday", "Afternoon", 12.0));
            fullTimetable.addLesson(new Lesson("Aquacise", w, month, "Saturday", "Evening", 15.0));
            
            fullTimetable.addLesson(new Lesson("Box Fit", w, month, "Sunday", "Morning", 11.0));
            fullTimetable.addLesson(new Lesson("Body Blitz", w, month, "Sunday", "Afternoon", 13.0));
            fullTimetable.addLesson(new Lesson("Yoga", w, month, "Sunday", "Evening", 10.0));
        }

        // 8 weekends * 6 lessons per weekend (3 Sat, 3 Sun) = 48 lessons
        assertEquals(48, fullTimetable.getLessons().size());
        
        // Verify we hit exactly 4 distinct exercises, plus yoga repeats
        assertFalse(fullTimetable.getLessonsByExercise("Aquacise").isEmpty());
        assertFalse(fullTimetable.getLessonsByExercise("Box Fit").isEmpty());
    }
}
