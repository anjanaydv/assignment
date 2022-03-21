package com.hilton.assignment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 *  @Copyright Any portion of this assignment's code are not allowed to use in business or production.
 * @author Anjana Yadav
 *
 */
@SpringBootApplication
@EnableCaching
@EnableAsync
public class AssignmentApplication {

	public static void main(String[] args) {
		SpringApplication.run(AssignmentApplication.class, args);
	}

}
