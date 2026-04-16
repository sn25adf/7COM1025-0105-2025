package com.flc.test;

import com.flc.model.Lesson;
import com.flc.model.Member;
import com.flc.model.Booking;
import com.flc.model.Review;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LessonTest {

    private Lesson lesson;
    private Member member1;
    private Member member2;
    private Member member3;
    private Member member4;
    private Member member5;

    @BeforeEach
    public void setUp() {
        lesson = new Lesson("Yoga", 1, 5, "Saturday", "Morning", 10.0);
        member1 = new Member(1, "Alice");
        member2 = new Member(2, "Bob");
        member3 = new Member(3, "Charlie");
        member4 = new Member(4, "David");
        member5 = new Member(5, "Eve");
    }

    @Test
    public void testLessonInitialization() {
        assertEquals("Yoga", lesson.getExerciseName());
        assertEquals(1, lesson.getWeekend());
        assertEquals("Saturday", lesson.getDay());
        assertEquals("Morning", lesson.getTime());
        assertEquals(10.0, lesson.getPrice());
        assertEquals(0, lesson.getMemberCount());
    }

    @Test
    public void testAddMemberSuccessfully() {
        assertTrue(lesson.addMember(member1));
        assertEquals(1, lesson.getMemberCount());
    }

    @Test
    public void testCannotAddSameMemberTwice() {
        assertTrue(lesson.addMember(member1));
        assertFalse(lesson.addMember(member1)); //this should fail because the member is already added
        assertEquals(1, lesson.getMemberCount());
    }

    @Test
    public void testLessonCapacity() {
        assertTrue(lesson.addMember(member1));
        assertTrue(lesson.addMember(member2));
        assertTrue(lesson.addMember(member3));
        assertTrue(lesson.addMember(member4));
        // the 5th member should fail because the lesson is full
        assertFalse(lesson.addMember(member5));
        assertEquals(4, lesson.getMemberCount());
    }

    @Test
    public void testGetBooking() {
        lesson.addMember(member1);
        Booking booking = lesson.getBooking(member1);
        assertNotNull(booking);
        assertEquals(member1, booking.getMember());
    }

    @Test
    public void testAddReviewAndAverageRating() {
        lesson.addMember(member1);
        lesson.addMember(member2);
        
        Booking b1 = lesson.getBooking(member1);
        b1.markAttended();
        Booking b2 = lesson.getBooking(member2);
        b2.markAttended();
        
        lesson.addReview(new Review(member1, 5, "Great class!"));
        lesson.addReview(new Review(member2, 3, "It was okay."));
        
        assertEquals(4.0, lesson.getAverageRating());
    }
}
