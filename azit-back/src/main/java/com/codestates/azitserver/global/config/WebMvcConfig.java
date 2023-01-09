package com.codestates.azitserver.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.SerializationFeature;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/docs/**").addResourceLocations("classpath:/static/docs/");
	}

	/**
	 * Json stringify를 ObjectMapper로 변환할 때 개입.
	 * <br>
	 * null값을 모두 제외하고 Object으로 만듭니다.
	 */
	@Bean
	public Jackson2ObjectMapperBuilder jackson2ObjectMapperBuilder() {
		return new Jackson2ObjectMapperBuilder()
			.serializationInclusion(JsonInclude.Include.NON_NULL)
			.featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
			.visibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY)
			.visibility(PropertyAccessor.GETTER, JsonAutoDetect.Visibility.NONE)
			.visibility(PropertyAccessor.IS_GETTER, JsonAutoDetect.Visibility.NONE);
	}
}
