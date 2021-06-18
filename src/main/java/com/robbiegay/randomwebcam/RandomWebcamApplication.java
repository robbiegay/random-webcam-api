package com.robbiegay.randomwebcam;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.*;
import com.robbiegay.randomwebcam.models.WebcamPlayer;
import com.robbiegay.randomwebcam.models.WebcamResponse;
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

	//@Value("${api.key}")
	//private String apiKey;

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	@Autowired
	private RestTemplate restTemplate;

	// Remove before committing:
	private String apiKey = "insert key here";

	@GetMapping("/randomWebcam")
	public ResponseEntity<WebcamPlayer> webcam() {
		HttpHeaders headers = new HttpHeaders();
		headers.set("x-windy-key", apiKey);
		String url = "https://api.windy.com/api/webcams/v2/list/property=live/limit=1/orderby=random";

		HttpEntity entity = new HttpEntity(headers);

		ResponseEntity<WebcamResponse> response = restTemplate.exchange(
				url, HttpMethod.GET, entity, WebcamResponse.class);

		WebcamResponse webcamResponse = response.getBody();
		String id = webcamResponse.getId();

		String url2 = "https://api.windy.com/api/webcams/v2/list/webcam=" + id + "/?show=webcams:url";

		ResponseEntity<WebcamPlayer> response2 = restTemplate.exchange(
				url2, HttpMethod.GET, entity, WebcamPlayer.class);

		return response2;
	}

	// Using GSON to get Webcam Url
	@GetMapping("/gsonWebcam")
	public String gsonWebcam() {
		HttpHeaders headers = new HttpHeaders();
		headers.set("x-windy-key", apiKey);
		String getRandomWebcam = "https://api.windy.com/api/webcams/v2/list/property=live/limit=1/orderby=random";

		HttpEntity entity = new HttpEntity(headers);

		ResponseEntity<String> response = restTemplate.exchange(
				getRandomWebcam, HttpMethod.GET, entity, String.class);

		String responseJson = response.getBody();

		FooWithInner targetObject = new Gson().fromJson(responseJson, FooWithInner.class);

		String webcamId = targetObject.result.webcams.get(0).id;

		String getWebcamUrl = "https://api.windy.com/api/webcams/v2/list/webcam=" + webcamId + "/?show=webcams:url";

		ResponseEntity<String> response2 = restTemplate.exchange(
				getWebcamUrl, HttpMethod.GET, entity, String.class);

		String responseJson2 = response2.getBody();

		WebcamUrl targetObject2 = new Gson().fromJson(responseJson2, WebcamUrl.class);

		return targetObject2.result.webcams.get(0).url.current.desktop;
	}

	// GSON Test:
	@GetMapping("/gson")
	public String gsonTest() {
		//GSON
		String json = "{ \"status\": \"OK\", \"result\": { \"offset\": 0, \"limit\": 1, \"total\": 1523, \"webcams\": [ { \"id\": \"1500455972\", \"status\": \"active\", \"title\": \"Cartagena: Atamaría - Atamaria\" } ] } }";

		FooWithInner targetObject = new Gson().fromJson(json, FooWithInner.class);

		return targetObject.result.webcams.get(0).id;
	}

	public class FooWithInner {
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

	public class WebcamUrl {
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
				public Url url;

				public class Url {
					public Current current;
					public String edit;
					public Daylight daylight;

					public class Current {
						public String desktop;
						public String mobile;
					}

					public class Daylight {
						public String desktop;
						public String mobile;
					}
				}
			}
		}
	}
}

/*
{ \"status\": \"OK\", \"result\": { \"offset\": 0, \"limit\": 1, \"total\": 1523, \"webcams\": [ { \"id\": \"1500455972\", \"status\": \"active\", \"title\": \"Cartagena: Atamaría - Atamaria\" } ] } }
 */
