package com.codestates.azitserver;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TestCommandLineRunner implements CommandLineRunner {
	@Value("${mail.address.admin}")
	private String email;

	@Override
	public void run(String... args) throws Exception {
		System.out.println("-".repeat(88));
		System.out.println(email);
	}
}
