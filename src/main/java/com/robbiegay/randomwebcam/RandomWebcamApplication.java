package com.robbiegay.randomwebcam;

import com.google.gson.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@SpringBootApplication
@RestController
public class RandomWebcamApplication {

	public static void main(String[] args) {
		SpringApplication.run(RandomWebcamApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	@Autowired
	private RestTemplate restTemplate;

	// Remove before committing:
	private String apiKey = System.getenv("API_KEY");

	@GetMapping("/randomWebcam")
	public String gsonWebcam() {
		HttpHeaders headers = new HttpHeaders();
		headers.set("x-windy-key", apiKey);
		String getRandomWebcam = "https://api.windy.com/api/webcams/v2/list/property=live/limit=1/orderby=random/category=beach";

		HttpEntity entity = new HttpEntity(headers);

		ResponseEntity<String> response = restTemplate.exchange(
				getRandomWebcam, HttpMethod.GET, entity, String.class);

		String responseJson = response.getBody();

		RandomWebcam targetObject = new Gson().fromJson(responseJson, RandomWebcam.class);

		String webcamId = targetObject.result.webcams.get(0).id;
		String webcamTitle = targetObject.result.webcams.get(0).title;

		return webcamId + "," + webcamTitle;
	}

	@GetMapping("/test")
	public String test() {
		return "{ \"test\": \"just a test\", \"why\": \"because I like tests\", \"how\": \"just returns some made up json\" }";
	}

	public class RandomWebcam {
		public String status;
		public Result result;

		public class Result {
			public int offset;
			public int limit;
			public int total;
			public List<Webcams> webcams;

			public class Webcams {
				public String id;
				public String status;
				public String title;
			}
		}
	}
}
