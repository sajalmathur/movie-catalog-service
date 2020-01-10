package com.domain.moviecatalogservice.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.domain.moviecatalogservice.model.CatalogItem;
import com.domain.moviecatalogservice.model.Movie;
import com.domain.moviecatalogservice.model.Rating;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Service
public class MovieInfo {
	
	@Autowired
	private RestTemplate restTemplate;

	@HystrixCommand(fallbackMethod="getFallbackCatalogItem")
	public CatalogItem getCatalogItem(Rating rating){
		Movie movie = restTemplate.getForObject("http://movie-info-service/movies/" + rating.getMovieId(), Movie.class); 
		return new CatalogItem(movie.getName(),"Desc", rating.getRating());
	}

	public CatalogItem getFallbackCatalogItem(Rating rating){
		return new CatalogItem("Movie name not found","", rating.getRating());
	}
	
}
