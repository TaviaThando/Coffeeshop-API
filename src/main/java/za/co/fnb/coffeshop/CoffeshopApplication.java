package za.co.fnb.coffeshop;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication//(exclude={DataSourceAutoConfiguration.class})
public class  CoffeshopApplication {

	public static void main(String[] args) {
		SpringApplication.run(CoffeshopApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
		return args -> System.out.println("******************COFFEESHOP APPLICATION RUNNING*************");
	}

}
