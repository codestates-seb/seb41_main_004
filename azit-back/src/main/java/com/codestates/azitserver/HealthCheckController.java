package com.codestates.azitserver;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController {
	@GetMapping("/health-check")
	public ResponseEntity<?> healthCheck(@RequestParam(required = false) String name) {
		String greet = name == null ? "Hello Admin!" : String.format("Hello %s!", name);

		Map<String, Object> response = new HashMap<>();
		response.put("greet", greet);
		response.put("time", LocalDateTime.now());

		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}