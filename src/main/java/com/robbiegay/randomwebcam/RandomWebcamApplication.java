package com.robbiegay.randomwebcam;

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

	@GetMapping("/randomWebcam")
	public ResponseEntity<WebcamPlayer> webcam() {
		HttpHeaders headers = new HttpHeaders();
		headers.set("x-windy-key", "API-KEY");
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
}
