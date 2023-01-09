package com.codestates.azitserver.global.exception.controller;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codestates.azitserver.global.exception.dto.ExceptionCode;

@RestController
@RequestMapping("/api/errors")
public class ExceptionRestController {
	@GetMapping
	public ResponseEntity<Map<Object, String>> getAll() {
		return new ResponseEntity<>(toMap(ExceptionCode.values()), HttpStatus.OK);
	}

	private Map<Object, String> toMap(ExceptionCode[] values) {
		return Arrays.stream(values)
			.collect(Collectors.toMap(ExceptionCode::getStatus, ExceptionCode::getMessage));
	}
}
