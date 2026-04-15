package com.flc.model;

public class Review {

    private Member member;
    private int rating;
    private String comment;

    public Review(Member member, int rating, String comment) {
        this.member = member;
        this.rating = rating;
        this.comment = comment;
    }

    public int getRating() {
        return rating;
    }

    public String toString() {
        return member.getName() + " Rating: " + rating + " Comment: " + comment;
    }
}