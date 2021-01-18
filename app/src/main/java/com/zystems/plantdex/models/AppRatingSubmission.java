package com.zystems.plantdex.models;

public class AppRatingSubmission {
    private String userId;
    private int rating;

    public AppRatingSubmission() {
    }

    public AppRatingSubmission(String userId, int rating) {
        this.userId = userId;
        this.rating = rating;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
