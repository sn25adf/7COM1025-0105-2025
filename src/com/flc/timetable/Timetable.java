package com.flc.timetable;

import com.flc.model.Lesson;
import java.util.ArrayList;
import java.util.List;

public class Timetable {

    private List<Lesson> lessons = new ArrayList<>();

    public void addLesson(Lesson lesson) {
        lessons.add(lesson);
    }

    public List<Lesson> getLessons() {
        return lessons;
    }

    public List<Lesson> getLessonsByDay(String day) {
        List<Lesson> filtered = new ArrayList<>();
        for (Lesson lesson : lessons) {
            if (lesson.getDay().equalsIgnoreCase(day)) {
                filtered.add(lesson);
            }
        }
        return filtered;
    }

    public List<Lesson> getLessonsByExercise(String exercise) {
        List<Lesson> filtered = new ArrayList<>();
        for (Lesson lesson : lessons) {
            if (lesson.getExerciseName().equalsIgnoreCase(exercise)) {
                filtered.add(lesson);
            }
        }
        return filtered;
    }
}