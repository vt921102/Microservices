package io.toanlv.movieinfoservice.resource;

import io.toanlv.movieinfoservice.models.Movie;
import io.toanlv.movieinfoservice.models.MovieSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@RestController
@RequestMapping("/movies")
public class MovieResource {
    @Value("${api.key}")
    private String apiKey;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private  WebClient.Builder webClientBuider;
    @RequestMapping("/{movieId}")
    public Movie getMovieInfo(@PathVariable("movieId") String movieId) {
        MovieSummary movieSumary = restTemplate.getForObject("https://api.themoviedb.org/3/movie/"+movieId+"?api_key="+apiKey, MovieSummary.class);
/*        MovieSumary  movieSumary = webClientBuider.build()
                .get()
                .uri("https://api.themoviedb.org/3/movie/"+movieId+"?api_key="+apiKey)
                .retrieve()
                .bodyToMono(MovieSumary.class)
                .block();*/
        return new Movie(movieId, movieSumary.getTitle(),movieSumary.getOverview());
    }
}
