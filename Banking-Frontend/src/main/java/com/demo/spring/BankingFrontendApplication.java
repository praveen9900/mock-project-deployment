package com.demo.spring;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class BankingFrontendApplication implements WebMvcConfigurer {

	public static void main(String[] args) {
		SpringApplication.run(BankingFrontendApplication.class, args);
	}

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {

		registry.addViewController("/balance").setViewName("balanceDesign");
		registry.addViewController("/createAccount").setViewName("createAccDesign");
		registry.addViewController("/withdraw").setViewName("withdrawDesign");
		registry.addViewController("/deposit").setViewName("depositDesign");
		registry.addViewController("/transfer").setViewName("transferDesign");
		registry.addViewController("/statementInput").setViewName("statementDesign");
		registry.addViewController("/customer").setViewName("customerHome");

		registry.addViewController("/customerDetails").setViewName("detailsDesign");
		registry.addViewController("/openAcc").setViewName("openAccountDesign");
		registry.addViewController("/activate").setViewName("activateDesign");
		registry.addViewController("/deactivate").setViewName("deactivateDesign");
		registry.addViewController("/employee").setViewName("employeeHomeDesign");
		registry.addViewController("/login").setViewName("loginDesign");
		registry.addViewController("/register").setViewName("registerDesign");
	}

	@Bean
	public RestTemplate restTemplate() {
		RestTemplate restTemplate = new RestTemplate();
		HttpClient httpClient = HttpClientBuilder.create().build();
		restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(httpClient));

		return restTemplate;
	}
}
