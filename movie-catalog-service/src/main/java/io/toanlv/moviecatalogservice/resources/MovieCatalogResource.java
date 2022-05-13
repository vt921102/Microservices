package io.toanlv.moviecatalogservice.resources;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import io.toanlv.moviecatalogservice.models.CatalogItem;
import io.toanlv.moviecatalogservice.models.Movie;
import io.toanlv.moviecatalogservice.models.Rating;
import io.toanlv.moviecatalogservice.models.UserRating;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {
    // create instant RestTemplate
    // vi da su dung bean roi nen restTemplate khong can cap phat o day nua
    @Autowired
    private RestTemplate restTemplate;
    // co the dung web client builder de thay the restemplate
    @Autowired
    private WebClient.Builder webClientBuilder;

    @Value("${movie_service.url}")
    private String movieServiceURL;

    @RequestMapping("/hello")
    public String hello(){
        return "Hello World!@";
    }



    @RequestMapping("/{userID}")
//    @HystrixCommand(fallbackMethod = "getFallbackCatalog")// neu co loi thi se goi den fallbackmethod
    public List<CatalogItem> getCatalog(@PathVariable("userID") String userID) {
        // get all related movie ID
        UserRating ratings = getUserRating(userID);
        return ratings.getUserRating().stream().map(rating -> {
            return getCatalogItem(rating);
        }).collect(Collectors.toList());
        //for each movie ID call movies info service and get detail

        // put them all together

    }

    @HystrixCommand(fallbackMethod = "getFallbackCatalogItem")
    private CatalogItem getCatalogItem(Rating rating) {
        // goi toi API de lay ra thang movie
        Movie movie = restTemplate.getForObject(movieServiceURL + rating.getMovieId(), Movie.class);
/*            Movie movie = webClientBuilder.build()
                    .get()
                    .uri("http://movie-info-service/movies/" + rating.getMovieId())
                    .retrieve()
                    .bodyToMono(Movie.class)
                    .block();*/
        return new CatalogItem(movie.getName(), movie.getOverview(), rating.getRating());
    }

    private CatalogItem getFallbackCatalogItem(Rating rating) {
        return new CatalogItem("No movie", "", 0);
    }

    @HystrixCommand(fallbackMethod = "getFallbackUserRating")
    private UserRating getUserRating(@PathVariable("userId") String userID) {
        UserRating userRating = new UserRating();
        List<Rating> ratings = restTemplate.getForObject("http://rating-data-service/ratingdata/user/" + userID, UserRating.class)
                .getUserRating();
        userRating.setUserId(userID);
        userRating.setUserRating(ratings);

        return userRating;
    }

    private UserRating getFallbackUserRating(@PathVariable("userId") String userID) {
        UserRating userRating = new UserRating();
        userRating.setUserId(userID);
        userRating.setUserRating(Arrays.asList(
                new Rating("100", 0)
        ));

        return userRating;
    }

/*    public List<CatalogItem> getFallbackCatalog(@PathVariable("userID") String userID) {
        // ham nay co the dung de doc data tu cache len chang han
        return Arrays.asList(new CatalogItem("No Moive", "", 0));
    }*/
}
