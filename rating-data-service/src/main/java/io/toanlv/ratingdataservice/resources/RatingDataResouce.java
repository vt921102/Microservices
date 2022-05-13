package io.toanlv.ratingdataservice.resources;

import io.toanlv.ratingdataservice.models.Rating;
import io.toanlv.ratingdataservice.models.UserRating;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/ratingdata")
public class RatingDataResouce {
    @RequestMapping("/{movieId}")
    public Rating getRating(@PathVariable("movieId") String movieId) {

        return new Rating(movieId, 4);
    }

    @RequestMapping("user/{userId}")
    public UserRating geUsertRating(@PathVariable("userId") String userId) {

        List<Rating> ratings = Arrays.asList(
                new Rating("100", 4),
                new Rating("550", 5),
                new Rating("300", 6)
        );
        UserRating userRating = new UserRating(ratings);
        return userRating;
    }

}
