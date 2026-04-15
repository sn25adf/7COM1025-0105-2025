package com.flc.model;

import java.util.ArrayList;
import java.util.List;

public class Lesson {

    private String exerciseName;
    private int weekend;
    private int month;
    private String day;
    private String time;
    private double price;

    private static final int MAX_MEMBERS = 4;

    private List<Booking> bookings;
    private List<Review> reviews;

    public Lesson(String exerciseName, int weekend, int month, String day, String time, double price) {
        this.exerciseName = exerciseName;
        this.weekend = weekend;
        this.month = month;
        this.day = day;
        this.time = time;
        this.price = price;

        bookings = new ArrayList<>();
        reviews = new ArrayList<>();
    }

    // Add member booking (new)
    public boolean addMember(Member member) {
        if (getMemberCount() >= MAX_MEMBERS || hasMemberBooked(member)) {
            return false;
        }

        Booking newBooking = new Booking(member, this);
        bookings.add(newBooking);
        return true;
    }

    // Add an existing booking (for changing lessons)
    public boolean addExistingBooking(Booking booking) {
        if (getMemberCount() >= MAX_MEMBERS) {
            return false;
        }
        if (hasMemberBooked(booking.getMember())) {
            return false;
        }
        bookings.add(booking);
        return true;
    }

    public void removeBooking(Booking booking) {
        bookings.remove(booking);
    }

    private boolean hasMemberBooked(Member member) {
        for (Booking b : bookings) {
            if (!b.isCancelled() && b.getMember().equals(member)) {
                return true;
            }
        }
        return false;
    }

    // Get specific booking for a member
    public Booking getBooking(Member member) {
        for (Booking b : bookings) {
            if (!b.isCancelled() && b.getMember().equals(member)) {
                return b;
            }
        }
        return null;
    }

    // Number of active members
    public int getMemberCount() {
        int count = 0;
        for (Booking b : bookings) {
            if (!b.isCancelled()) {
                count++;
            }
        }
        return count;
    }

    // Number of attended members
    public int getAttendedCount() {
        int count = 0;
        for (Booking b : bookings) {
            if (b.isAttended()) {
                count++;
            }
        }
        return count;
    }

    // Add review
    public void addReview(Review review) {
        reviews.add(review);
    }

    // Calculate average rating
    public double getAverageRating() {
        if (reviews.isEmpty()) {
            return 0;
        }
        int total = 0;
        for (Review review : reviews) {
            total += review.getRating();
        }
        return (double) total / reviews.size();
    }

    public String getExerciseName() {
        return exerciseName;
    }

    public int getWeekend() {
        return weekend;
    }

    public int getMonth() {
        return month;
    }

    public double getPrice() {
        return price;
    }

    public String getDay() {
        return day;
    }

    public String getTime() {
        return time;
    }

    @Override
    public String toString() {
        return "M" + month + " W" + weekend + " " + exerciseName + " | " + day + " | " + time + " | £" + price +
                " | Members: " + getMemberCount() + "/" + MAX_MEMBERS;
    }
}