package com.tukorea.cogTest;

import com.tukorea.cogTest.config.WebConfig;
import com.tukorea.cogTest.security.config.SecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.ApplicationPidFileWriter;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@Import({WebConfig.class, SecurityConfig.class})
@EnableScheduling
public class CogTestApplication {

	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(CogTestApplication.class);
		application.addListeners(new ApplicationPidFileWriter());
		application.run(CogTestApplication.class, args);
	}

}