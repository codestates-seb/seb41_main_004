package com.codestates.azitserver.domain.auth;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import com.codestates.azitserver.domain.auth.jwt.JwtTokenizer;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.io.Decoders;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class JwtTokenizerTest {
	private static JwtTokenizer jwtTokenizer;
	private String secretKey;
	private String base64EncodedSecretKey;

	@BeforeAll
	public void init() {
		jwtTokenizer = new JwtTokenizer();
		secretKey = "LEKMFU1070BMCKNRC0243785BTH4B72GJHBC7N8IF89J89J3N8DNJ2HFD8IUHV892NJ4F8DF2JD9SJF9SLMC0BY3H587TYD7CHMNCW88235792HKVKLJWF728792Y4JKFV79824JCH78Y42J789HCJHDJF876FBKJH427I8562";
		base64EncodedSecretKey = jwtTokenizer.encodeBase64SecretKey(secretKey);
	}

	@Test
	public void encodeBase64SecretKeyTest() {
		assertThat(secretKey, is(new String(Decoders.BASE64.decode(base64EncodedSecretKey))));
	}

	@Test
	public void generateAccessTokenTest() {
		Map<String, Object> claims = new HashMap<>();
		claims.put("memberId", 1);
		claims.put("roles", List.of("USER"));

		String subject = "test access token";
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MINUTE, 10);
		Date expiration = calendar.getTime();

		String accessToken = jwtTokenizer.generateAccessToken(claims, subject, expiration, base64EncodedSecretKey);

		assertThat(accessToken, notNullValue());
	}

	@Test
	public void generateRefreshTokenTest() {
		String subject = "test refresh token";
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.HOUR, 24);
		Date expiration = calendar.getTime();

		String refreshToken = jwtTokenizer.generateRefreshToken(subject, expiration, base64EncodedSecretKey);

		assertThat(refreshToken, notNullValue());
	}

	@DisplayName("does not throw any Exception when jws verify")
	@Test
	public void verifySignatureTest() {
		String accessToken = getAccessToken(Calendar.MINUTE, 10);
		assertDoesNotThrow(() -> jwtTokenizer.verifySignature(accessToken, base64EncodedSecretKey));
	}

	@DisplayName("throw ExpiredJwtException when jws verify")
	@Test
	public void verifyExpirationTest() throws InterruptedException {
		String accessToken = getAccessToken(Calendar.SECOND, 1);
		assertDoesNotThrow(() -> jwtTokenizer.verifySignature(accessToken, base64EncodedSecretKey));

		TimeUnit.MILLISECONDS.sleep(1500);

		assertThrows(ExpiredJwtException.class,
			() -> jwtTokenizer.verifySignature(accessToken, base64EncodedSecretKey));
	}

	@Test
	public void getClaimsTest() {
		String accessToken = getAccessToken(Calendar.MINUTE, 10);
		Map<String, Object> claims = jwtTokenizer.getClaims(accessToken, base64EncodedSecretKey).getBody();

		assertThat(claims.get("memberId"), is(1));
		assertThat(((List)claims.get("roles")).get(0), is("USER"));
	}

	@Test
	public void calendarToDateTest() {
		Calendar calendar = Calendar.getInstance();
		long now = calendar.getTimeInMillis();

		calendar.setTime(new Date());
		calendar.add(Calendar.MINUTE, 10);
		Date expiration = calendar.getTime();

		assertThat(expiration.getTime(), is(now + (10 * 60 * 1000)));
	}

	private String getAccessToken(int timeUnit, int timeAmount) {
		Map<String, Object> claims = new HashMap<>();
		claims.put("memberId", 1);
		claims.put("roles", List.of("USER"));

		String subject = "test access token";
		Calendar calendar = Calendar.getInstance();
		calendar.add(timeUnit, timeAmount);
		Date expiration = calendar.getTime();
		String accessToken = jwtTokenizer.generateAccessToken(claims, subject, expiration, base64EncodedSecretKey);

		return accessToken;
	}
}
