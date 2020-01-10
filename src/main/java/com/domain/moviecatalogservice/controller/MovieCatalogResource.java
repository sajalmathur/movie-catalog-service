package com.domain.moviecatalogservice.controller;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import com.domain.moviecatalogservice.model.CatalogItem;
import com.domain.moviecatalogservice.model.Movie;
import com.domain.moviecatalogservice.model.Rating;
import com.domain.moviecatalogservice.model.UserRating;
import com.domain.moviecatalogservice.services.MovieInfo;
import com.domain.moviecatalogservice.services.UserRatingInfo;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {
	
	@Autowired
	private RestTemplate restTemplate;
	@Autowired
	private WebClient.Builder webClientBuilder;
	
	@Autowired
	MovieInfo movieInfo;
	
	@Autowired
	UserRatingInfo userRatingInfo;

	@RequestMapping(value="/{userId}")
	public List<CatalogItem> getCatalog(@PathVariable("userId") String userId){

		UserRating ratings = userRatingInfo.getUserRating(userId);

		return ratings.getRatings().stream().map(rating -> {
			return movieInfo.getCatalogItem(rating);
			/*//Asynchronous way(As restTemplate is going to be deprecated in future)
			Movie movie = webClientBuilder.build()
							.get()
							.uri("http://localhost:8083/movies/" + rating.getMovieId())
							.retrieve()
							.bodyToMono(Movie.class)
							.block();*/
			
		})
		.collect(Collectors.toList());

	}
	
}

