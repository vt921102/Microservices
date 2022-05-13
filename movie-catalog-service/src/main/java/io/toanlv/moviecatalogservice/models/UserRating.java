package io.toanlv.moviecatalogservice.models;

import java.util.List;

public class UserRating {
    private String userId;
    private List<Rating> userRating;

    public UserRating() {
    }

    public UserRating(String userId, List<Rating> userRating) {
        this.userId = userId;
        this.userRating = userRating;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<Rating> getUserRating() {
        return userRating;
    }

    public void setUserRating(List<Rating> userRating) {
        this.userRating = userRating;
    }

    public UserRating(List<Rating> userRating) {
        this.userRating = userRating;
    }
}
