package com.flc.model;

public class Booking {

    private static int idCounter = 1000;
    
    private int bookingId;
    private Member member;
    private Lesson lesson;
    private String status;

    public Booking(Member member, Lesson lesson) {
        this.bookingId = idCounter++;
        this.member = member;
        this.lesson = lesson;
        this.status = "Booked";
    }

    public int getBookingId() {
        return bookingId;
    }

    public Member getMember() {
        return member;
    }

    public Lesson getLesson() {
        return lesson;
    }

    public void setLesson(Lesson lesson) {
        this.lesson = lesson;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isCancelled() {
        return status.equalsIgnoreCase("Cancelled");
    }

    public void cancel() {
        this.status = "Cancelled";
    }

    public boolean isAttended() {
        return status.equalsIgnoreCase("Attended");
    }

    public void markAttended() {
        this.status = "Attended";
    }

    public void markChanged() {
        this.status = "Changed";
    }
}