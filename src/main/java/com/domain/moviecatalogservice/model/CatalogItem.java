package com.domain.moviecatalogservice.model;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CatalogItem {

	private String name;
	private String desc;
	private int rating;
	
}
