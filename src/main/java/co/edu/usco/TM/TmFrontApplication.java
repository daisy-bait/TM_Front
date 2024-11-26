package co.edu.usco.TM;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
public class TmFrontApplication {

	public static void main(String[] args) {
		SpringApplication.run(TmFrontApplication.class, args);
	}

}
