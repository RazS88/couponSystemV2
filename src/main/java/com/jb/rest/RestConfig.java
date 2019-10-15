package com.jb.rest;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//helps to get bean and make the map with session for each login and he will be connect to only one token
//this is for use the bean and read it in @Context
@Configuration
public class RestConfig {
	// bean is singleton for each token and will be in hash map
	@Bean(name = "tokens")
	public Map<String, ClientSession> tokenMap() {
		return new HashMap<>();
	}

}
